package com.gw.device.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.controller.NetDeviceController;
import com.gw.device.data.*;
import com.gw.device.service.NetDeviceService;
import com.gw.equipment.data.EquipmentNetDeviceOutData;
import com.gw.equipment.data.WirelessDeviceOutData;
import com.gw.exception.ServiceException;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.HttpClientUtil;
import com.gw.util.ReqApiConst;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 联网设备服务层
 *
 * @author SY
 *
 */
@Service
public class NetDeviceServiceImpl implements NetDeviceService {
	private static Long userid = null;
	// @Value("${red.server_port}")
	// private String serverPort;
	// @Value("${access_token}")
	// private String token;
	private Logger log = LoggerFactory.getLogger(NetDeviceController.class);
	@Autowired
	private UtUnitNetdeviceMapper netdeviceMapper;
	@Resource
	private UtUnitBuildareaMapper unitBuildareaMapper;
	@Autowired
	private UtUnitBaseinfoRelationMapper baseinfoRelationMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Autowired
	private UtBaseEqsystemMapper eqsystemMapper;
	@Autowired
	private UtEqEquipmentMapper utEqEquipmentMapper;
	@Autowired
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;
	@Resource
	private UtHdSiterwellMapper utHdSiterwellMapper;
	@Resource
	private PropertiesManageService propertiesManageService;
	@Value("${raw.data.database}")
	private String databaseName;

	@Override
	@Transactional
	public void add(NetDeviceInData inData) throws Exception {
		UtUnitNetdevice netdevice = new UtUnitNetdevice();
		if (Util.isEmpty(inData.getUnitid()) || Util.isEmpty(inData.getDeviceindex())
				|| Util.isEmpty(inData.getDeviceno()) || Util.isEmpty(inData.getOwnercode())) {
			throw new ServiceException("单位、设备编号、设备子号、设备类型均不能为空！");
		}
		if ("2".equals(inData.getDeviceindex())) {
			Example example = new Example(UtUnitNetdevice.class);
			example.createCriteria().andEqualTo("ownercode", inData.getOwnercode()).andEqualTo("deviceindex", 2)
					.andEqualTo("isdelete", 0);
			List<UtUnitNetdevice> list = netdeviceMapper.selectByExample(example);
			if (Util.isNotEmpty(list)) {
				throw new ServiceException("此设备编号下已经存在一个用户传输装置！");
			}
		}
		// 判断该设备是否已经存在
		Example example = new Example(UtUnitNetdevice.class);
		example.createCriteria().andEqualTo("ownercode", inData.getOwnercode())
				.andEqualTo("deviceindex", Integer.parseInt(inData.getDeviceindex()))
				.andEqualTo("deviceno", Integer.parseInt(inData.getDeviceno())).andEqualTo("isdelete", 0);
		List<UtUnitNetdevice> list = netdeviceMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			throw new ServiceException("保存失败，该设备已存在！");
		}
		BeanUtils.copyProperties(inData, netdevice);
		netdevice.setId(snowflakeIdWorker.nextId());
		netdevice.setDeviceindex(Integer.parseInt(inData.getDeviceindex()));
		netdevice.setDeviceno(Integer.parseInt(inData.getDeviceno()));
		netdevice.setUnitid(Long.valueOf(inData.getUnitid()));
		netdevice.setIsdelete(0);
		netdevice.setEqsystemid(Long.valueOf(inData.getEqsystemid()));
		Json json = sendMsgDevice(inData, 0);
		if (json.isSuccess()) {
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse(json.getObj().toString());
			Integer code = (Integer) map.get("Code");
			String msg = (String) map.get("Msg");
			if (200 != code) {
				log.error(msg);
				throw new ServiceException(msg);
			}
			log.error(msg);
		} else {
			throw new ServiceException("同步设备：未找到有效接入的设备！");
		}
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		netdevice.setCreatedate(date);
		netdevice.setCreateuser(inData.getUserId());
		netdevice.setUpdatedate(format.format(date));
		netdevice.setEquipmentmodel(
				Util.isEmpty(inData.getEquipmentmodel()) ? null : Long.valueOf(inData.getEquipmentmodel()));
		netdeviceMapper.insert(netdevice);
	}

	/**
	 * 同步设备信息
	 *
	 * @param inData
	 * @param Status
	 * @return
	 * @throws Exception
	 */
	public Json sendMsgDevice(NetDeviceInData inData, Integer Status) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		SdDevicesOutData outData = new SdDevicesOutData();
		if (Util.isNotEmpty(inData.getDeviceindex())) {
			outData.setDeviceIndex(Integer.parseInt(inData.getDeviceindex()));
		}
		if (Util.isNotEmpty(inData.getDeviceno())) {
			outData.setDeviceNo(Integer.parseInt(inData.getDeviceno()));
		}
		outData.setOwnerCode(inData.getOwnercode());
		outData.setDeviceName(inData.getName());
		String isIndependent = inData.getIsIndependent();
		outData.setIsIndependent("1".equals(isIndependent));
		if (Status == -1) {
			outData.setDeviceStatus(-1);
		}
		// 同步数据0
		// json.put("DeviceIndex", outData.getDeviceIndex());
		// JSONObject arr =
		// JSONObject.parseObject(JSONObject.toJSONString(outData));
		StringBuffer urlSb = new StringBuffer(properties.getRedServerPort());
		urlSb.append(String.format(ReqApiConst.POST_REDSERVER_SYNCHEQ_API,properties.getAccessToken(),userid,inData.getOwnercode(),13));
		Json json = HttpClientUtil.doPost(urlSb.toString(),
				JSONObject.toJSONString(outData, SerializerFeature.WriteMapNullValue));
		return json;
	}

	@Override
	public void update(NetDeviceInData inData) throws Exception {
		if (Util.isEmpty(inData.getUnitid())) {
			throw new ServiceException("单位不能为空！");
		}
		if (inData.getDeviceindex().equals("2")) {
			Example example = new Example(UtUnitNetdevice.class);
			example.createCriteria().andEqualTo("ownercode", inData.getOwnercode()).andEqualTo("deviceindex", 2)
					.andEqualTo("isdelete", 0);
			List<UtUnitNetdevice> list = netdeviceMapper.selectByExample(example);
			if (Util.isNotEmpty(list) && list.size() > 0) {
				if (!list.get(0).getId().equals(Long.valueOf(inData.getId()))) {
					throw new ServiceException("此设备编号下已经存在一个用户传输装置！");
				}
			}
		}

		// 判断设备是否已经存在
		Example example = new Example(UtUnitNetdevice.class);
		example.createCriteria().andEqualTo("ownercode", inData.getOwnercode())
				.andEqualTo("deviceindex", Integer.parseInt(inData.getDeviceindex()))
				.andEqualTo("deviceno", Integer.parseInt(inData.getDeviceno())).andEqualTo("isdelete", 0);
		List<UtUnitNetdevice> list = netdeviceMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			if (!list.get(0).getId().equals(Long.valueOf(inData.getId()))) {
				throw new ServiceException("保存失败，该设备信息已存在！");
			}
		}

		String id = inData.getId();
		UtUnitNetdevice netdevice = netdeviceMapper.selectByPrimaryKey(Long.valueOf(id));
		boolean isChange = netdevice.getOwnercode().equals(inData.getOwnercode())
				&& netdevice.getDeviceindex().toString().equals(inData.getDeviceindex())
				&& netdevice.getDeviceno().toString().equals(inData.getDeviceno())
				&& netdevice.getIsIndependent().equals(inData.getIsIndependent())
				&& netdevice.getName().equals(inData.getName());
		NetDeviceInData data = null;
		if (!isChange) {
			data = new NetDeviceInData();
			data.setDeviceindex(netdevice.getDeviceindex().toString());
			data.setDeviceno(netdevice.getDeviceno().toString());
			data.setOwnercode(netdevice.getOwnercode());
			data.setName(netdevice.getName());
			Json json = sendMsgDevice(data, -1);
			// HttpJson msg = sendMsgDevice(data, -1);
			// HttpJson msg2 = sendMsgDevice(inData, 0);
			Json json2 = sendMsgDevice(inData, 0);
			if (json.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse(json.getObj().toString());
				Integer code = (Integer) map.get("Code");
				String msg = (String) map.get("Msg");
				if (200 != code) {
					log.error(msg);
					throw new ServiceException(msg);
				}
				log.error(msg);
			} else {
				throw new ServiceException("同步删除联网设备接口出错！");
			}
			if (json2.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse(json2.getObj().toString());
				Integer code = (Integer) map.get("Code");
				String msg = (String) map.get("Msg");
				if (200 != code) {
					log.error(msg);
					throw new ServiceException(msg);
				}
				log.error(msg);
			} else {
				throw new ServiceException("同步设备：未找到有效接入的设备！");
			}

			BeanUtils.copyProperties(inData, netdevice);
			if (Util.isNotEmpty(inData.getDeviceindex())) {
				netdevice.setDeviceindex(Integer.parseInt(inData.getDeviceindex()));
			} else {
				netdevice.setDeviceindex(null);
			}
			if (Util.isNotEmpty(inData.getDeviceno())) {
				netdevice.setDeviceno(Integer.parseInt(inData.getDeviceno()));
			} else {
				netdevice.setDeviceno(null);
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			netdevice.setUpdatedate(format.format(date));
			netdevice.setUpdateuser(inData.getUserId());
			netdevice.setUnitid(Long.valueOf(inData.getUnitid()));
			netdevice.setEqsystemid(Long.valueOf(inData.getEqsystemid()));
			netdeviceMapper.updateByPrimaryKey(netdevice);

		}

	}

	@Override
	public PageInfo<NetDeviceOutData> list(NetDeviceInData inData, Long id) throws Exception {
		userid = id;
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<NetDeviceOutData> list = netdeviceMapper.list(inData);
		PageInfo<NetDeviceOutData> pageInfo = new PageInfo<NetDeviceOutData>(list);
		return pageInfo;
	}

	@Override
	public void delete(String id) throws Exception {
		Example example = new Example(UtEqEquipment.class);
		example.createCriteria().andEqualTo("netdeviceid", Long.valueOf(id)).andEqualTo("isdelete", 0);
		List<UtEqEquipment> list = utEqEquipmentMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			throw new ServiceException("删除失败，该设备下存在设备设施信息！");
		}

		UtUnitNetdevice netdevice = netdeviceMapper.selectByPrimaryKey(Long.valueOf(id));
		if (netdevice != null) {
			NetDeviceInData data = new NetDeviceInData();
			data.setDeviceindex(netdevice.getDeviceindex().toString());
			data.setDeviceno(netdevice.getDeviceno().toString());
			data.setOwnercode(netdevice.getOwnercode());
			data.setName(netdevice.getName());

			Json json = HttpClientUtil.doPostFour("http://221.6.68.150:4007/Configuration/DeleteOwner",netdevice.getOwnercode());

			if (json.isSuccess()) {
				Map<String,Object> map = (Map<String,Object>)JSONObject.parse(json.getObj().toString());
				Integer code = (Integer) map.get("Code");
				String msg = (String) map.get("Msg");
				if (200 != code) {
					log.error(msg);
					throw new ServiceException(msg);
				}
				log.error(msg);
			} else {
				throw new ServiceException("同步删除联网设备接口出错！");
			}
			// HttpJson msg = sendMsgDevice(data, -1);
			// Json json = sendMsgDevice(data, -1);
			// InterfaceData outData = Util.analysisXML(json);
			// if (!json.isSuccess()) {
			// log.error(json.getObj().toString());
			// throw new ServiceException("同步设备出错!");
			// }else {
			// if(!"200".equals(outData.getCode())) {
			// log.error(outData.getMsg());
			// throw new ServiceException("同步设备出错!");
			// }
			// log.error(outData.getMsg());
			// }
		}
		// netdeviceMapper.deleteByPrimaryKey(Long.valueOf(id));
		UtUnitNetdevice utUnitNetdevice = netdeviceMapper.selectByPrimaryKey(Long.valueOf(id));
		utUnitNetdevice.setIsdelete(1);
		netdeviceMapper.updateByPrimaryKey(utUnitNetdevice);
	}

	@Override
	public PageInfo<NetDeviceOutData> getImportList(String unitId) throws Exception {
		List<NetDeviceOutData> list = baseinfoRelationMapper.getImportList(Long.valueOf(unitId), databaseName);
		PageInfo<NetDeviceOutData> pageInfo = new PageInfo<NetDeviceOutData>(list);
		return pageInfo;
	}

	@Override
	@Transactional
	public void addAll(Long userId, String ids, String unitid) throws Exception {
		String[] split = ids.split(",");
		List<NetDeviceOutData> list = new ArrayList<NetDeviceOutData>();
		for (String id : split) {
			List<NetDeviceOutData> outDatas = netdeviceMapper.getDeviceListById(databaseName, id);
			list.addAll(outDatas);
		}
		for (NetDeviceOutData outData : list) {
			Example example = new Example(UtUnitNetdevice.class);
			Criteria criteria = example.createCriteria();
			criteria.andEqualTo("ownercode", outData.getOwnercode());
			criteria.andEqualTo("deviceindex", outData.getDeviceindex());
			criteria.andEqualTo("deviceno", outData.getDeviceno());
			List<UtUnitNetdevice> selectByExample = netdeviceMapper.selectByExample(example);
			if (selectByExample != null && selectByExample.size() > 0) {
				continue;
			}

			UtUnitNetdevice netdevice = new UtUnitNetdevice();
			BeanUtils.copyProperties(outData, netdevice);
			netdevice.setId(snowflakeIdWorker.nextId());
			if (Util.isNotEmpty(outData.getDevicename())) {
				netdevice.setName(outData.getDevicename());
			}
			if (Util.isNotEmpty(outData.getDeviceindex())) {
				netdevice.setDeviceindex(Integer.parseInt(outData.getDeviceindex()));
			}
			if (Util.isNotEmpty(outData.getDeviceno())) {
				netdevice.setDeviceno(Integer.parseInt(outData.getDeviceno()));
			}
			Date date = new Date();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			netdevice.setCreatedate(date);
			netdevice.setCreateuser(userId);
			netdevice.setUpdatedate(format.format(date));
			if (Util.isNotEmpty(unitid)) {
				netdevice.setUnitid(Long.valueOf(unitid));
			}
			netdeviceMapper.insert(netdevice);
		}
	}

	@Override
	public List<EqSystemOutData> getEqSystemSelect() throws Exception {
		List<EqSystemOutData> list = eqsystemMapper.getEqSystemSelect();
		return list;
	}

	@Override
	public List<NetDeviceOutData> getNetDeviceNameSelect(Long unitid) throws Exception {
		List<NetDeviceOutData> list = netdeviceMapper.getNetDeviceNameSelect(unitid);
		return list;
	}

	@Override
	public List<NetDeviceOutData> getArrayList(NetDeviceInData inData) throws Exception {
		List<NetDeviceOutData> list = netdeviceMapper.getArrayList(inData);
		if (list == null) {
			throw new ServiceException(UtilMessage.GET_MSG_ERROR);
		}
		return list;
	}

	@Override
	@Transactional
	public void importDevicesFromOthers(Long userId) throws Exception {

		Map<Long, Long> eqIds = new HashMap<Long, Long>();
		Map<String, Long> map = new HashMap<>();
		List<UtUnitBaseinfoRelation> all = baseinfoRelationMapper.selectAll();
		for (UtUnitBaseinfoRelation utUnitBaseinfoRelation : all) {
			map.put(utUnitBaseinfoRelation.getSoureaddress(), utUnitBaseinfoRelation.getUnitid());
		}
		// 导入设备(末端设备)
		List<NetDeviceOutData> netDeviceOutDatas = netdeviceMapper.getNetDeviceFromOthers(databaseName);
		for (NetDeviceOutData netDeviceOutData : netDeviceOutDatas) {
			Long netDeviceId = snowflakeIdWorker.nextId();

			UtUnitNetdevice utUnitNetdevice = new UtUnitNetdevice();
			utUnitNetdevice.setId(netDeviceId);
			utUnitNetdevice.setCreatedate(new Date());
			utUnitNetdevice.setCreateuser(userId);
			utUnitNetdevice.setOwnercode(netDeviceOutData.getOwnercode());
			utUnitNetdevice.setName(netDeviceOutData.getName());

			utUnitNetdevice.setUpdatedate("");
			utUnitNetdevice.setIsIndependent("1");
			utUnitNetdevice.setIsdelete(0);
			utUnitNetdevice.setUnitid(map.get(netDeviceOutData.getOwnercode()));
			if (Util.isNotEmpty(netDeviceOutData.getDeviceindex())) {
				utUnitNetdevice.setDeviceindex(Integer.parseInt(netDeviceOutData.getDeviceindex()));
			}
			if (Util.isNotEmpty(netDeviceOutData.getDeviceno())) {
				utUnitNetdevice.setDeviceno(Integer.parseInt(netDeviceOutData.getDeviceno()));
			}

			Example example = new Example(UtUnitBaseinfoRelation.class);
			example.createCriteria().andEqualTo("soureaddress", netDeviceOutData.getOwnercode());

			UtEqEquipment utEqEquipment = new UtEqEquipment();
			Example example1 = new Example(UtUnitBuildarea.class);
			example1.createCriteria().andEqualTo("unitid",map.get(netDeviceOutData.getOwnercode()));
			List<UtUnitBuildarea> buildArea = unitBuildareaMapper.selectByExample(example1);
			UtUnitBuildarea buildAreaOutData = buildArea.get(0);
			Long eqId = snowflakeIdWorker.nextId();
			utEqEquipment.setNetdeviceid(netDeviceId);
			//id
			utEqEquipment.setId(eqId);
			//建筑id
			utEqEquipment.setBuildid(buildAreaOutData.getBuildid());
			//建筑区域id
			utEqEquipment.setBuildareaid(buildAreaOutData.getId());
			//设备名称
			utEqEquipment.setEqname(netDeviceOutData.getName());
			utEqEquipment.setEqmodel(netDeviceOutData.getEqmodel());
			//位置
			utEqEquipment.setInstallposition(netDeviceOutData.getAddress());
			utEqEquipment.setManufacturerphone(netDeviceOutData.getPhone());
			utEqEquipment.setLastupdate(new Date());
			utEqEquipment.setIsdelete(0);
			if ("1".equals(netDeviceOutData.getDeviceindex())) {
				utUnitNetdevice.setEqsystemid(3L);
				utEqEquipment.setEqsystemid(3L);
				utEqEquipment.setEqclassid(495644241322573824L);
			} else if ("2".equals(netDeviceOutData.getDeviceindex())) {
				utUnitNetdevice.setEqsystemid(3L);
				utEqEquipment.setEqsystemid(3L);
				utEqEquipment.setEqclassid(499607740788768768L);
			}else{
				utUnitNetdevice.setEqsystemid(1L);
			}

			if (Util.isNotEmpty(netDeviceOutData.getStatus())) {
				utEqEquipment.setStatus(Integer.parseInt(netDeviceOutData.getStatus()));
			}

			List<UtUnitBaseinfoRelation> list = baseinfoRelationMapper.selectByExample(example);
			if (Util.isNotEmpty(list)) {
				UtUnitBaseinfoRelation utUnitBaseinfoRelation = list.get(0);
				utUnitNetdevice.setUnitid(utUnitBaseinfoRelation.getUnitid());
				//单位id
				utEqEquipment.setUnitid(utUnitBaseinfoRelation.getUnitid());
			}
			if (utUnitNetdevice.getDeviceindex() == 3) {
				eqIds.put(netDeviceId, eqId);
			}
			netdeviceMapper.insert(utUnitNetdevice);
			utEqEquipmentMapper.insert(utEqEquipment);
		}

		List<DeviceRelData> rtuDeviceFromOthers = netdeviceMapper.getRtuDeviceFromOthers(databaseName);
		for (DeviceRelData deviceRelData : rtuDeviceFromOthers) {
			UtEqEquipmentdetialgw utEqEquipmentdetialgw = new UtEqEquipmentdetialgw();
			BeanUtils.copyProperties(deviceRelData, utEqEquipmentdetialgw);
			utEqEquipmentdetialgw.setId(snowflakeIdWorker.nextId());
			if (Util.isNotEmpty(deviceRelData.getAnalogpara())) {
				String[] split = deviceRelData.getAnalogpara().split(",");
				try {
					if (Util.isNotEmpty(split)) {
						if (Util.isNotEmpty(split[0])) {
							utEqEquipmentdetialgw.setAnalogk(new BigDecimal(split[0]));
						}
						if (split.length > 1 && Util.isNotEmpty(split[1])) {
							utEqEquipmentdetialgw.setAnalogb(new BigDecimal(split[1]));
						}
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
			Example example = new Example(UtUnitNetdevice.class);
			example.createCriteria().andEqualTo("ownercode", deviceRelData.getOwnerCode()).andEqualTo("deviceindex", 3)
					.andEqualTo("deviceno", deviceRelData.getDeviceNo());
			List<UtUnitNetdevice> list = netdeviceMapper.selectByExample(example);
			if (Util.isNotEmpty(list)) {
				UtUnitNetdevice utUnitNetdevice = list.get(0);
				utEqEquipmentdetialgw.setNetdeviceid(utUnitNetdevice.getId());
				Long eqId = eqIds.get(utUnitNetdevice.getId());
				utEqEquipmentdetialgw.setEqid(eqId);
				UtEqEquipment utEqEquipment = utEqEquipmentMapper.selectByPrimaryKey(eqId);
				if (Util.isNotEmpty(utEqEquipment)) {
					if (Util.isNotEmpty(deviceRelData.getSystype())) {
						utEqEquipment.setEqsystemid(deviceRelData.getSystype().longValue());
					}
				}
				utEqEquipmentMapper.updateByPrimaryKey(utEqEquipment);
			}
			utEqEquipmentdetialgwMapper.insert(utEqEquipmentdetialgw);

		}

	}

	@Override
	public Boolean hasEquipments(String id) throws Exception {
		Example example = new Example(UtEqEquipment.class);
		example.createCriteria().andEqualTo("netdeviceid", Long.valueOf(id)).andNotEqualTo("isdelete", 1);
		List<UtEqEquipment> list = utEqEquipmentMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void addGateWay(String netDeviceID, String gateWayID) throws Exception {
		String errorMsg = "";
		String[] gateWayIDs = gateWayID.split(",");
		UtUnitNetdevice netDevice = netdeviceMapper.selectByPrimaryKey(Long.parseLong(netDeviceID));
		for (String id : gateWayIDs) {
			WirelessDeviceOutData well = new WirelessDeviceOutData();
			well.setOwnercode(netDevice.getOwnercode());
			well.setDeviceno(netDevice.getDeviceno());
			well.setDeviceindex(netDevice.getDeviceindex());
			well.setIsrelation(1);
			well.setId(id);
			well.setDatabase(databaseName);
			Integer flag = utHdSiterwellMapper.updateSiteWell(well);
			if (flag < 1) {
				errorMsg = "添加关联失败！";
				break;
			}
		}
		if (Util.isNotEmpty(errorMsg)) {
			throw new ServiceException(errorMsg);
		}
	}

	@Override
	public void removeGateWay(String gateWayID) throws Exception {
		String[] gateWayIDs = gateWayID.split(",");
		String errorMsg = "";
		for (String id : gateWayIDs) {
			WirelessDeviceOutData well = new WirelessDeviceOutData();
			well.setId(id);
			well.setIsrelation(0);
			well.setOwnercode("");
			well.setDeviceindex(null);
			well.setDeviceno(null);
			well.setDatabase(databaseName);// 用于传递数据库参数
			Integer flag = utHdSiterwellMapper.updateSiteWell(well);
			if (flag < 1) {
				errorMsg = "移除关联失败！";
				break;
			}
		}
		if (Util.isNotEmpty(errorMsg)) {
			throw new ServiceException(errorMsg);
		}

	}

	@Override
	public List<EquipmentNetDeviceOutData> getNetEq(String netDeviceId) {
		return netdeviceMapper.getNetEq(netDeviceId);
	}
}
