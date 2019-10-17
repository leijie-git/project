package com.gw.equipment.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.InterfaceOutData;
import com.gw.device.data.InterfaceOutImportData;
import com.gw.equipment.data.*;
import com.gw.equipment.service.EquipmentFacService;
import com.gw.exception.ServiceException;
import com.gw.mapper.UtEqEquipmentMapper;
import com.gw.mapper.UtEqEquipmentdetialgwMapper;
import com.gw.mapper.UtUnitNetdeviceMapper;
import com.gw.mapper.entity.UtEqEquipment;
import com.gw.mapper.entity.UtEqEquipmentdetialgw;
import com.gw.mapper.entity.UtUnitNetdevice;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.DataConvertUtil;
import com.gw.util.HttpClientUtil;
import com.gw.util.Util;
import com.gw.util.UtilMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

@Service
public class EquipmentFacServiceImpl implements EquipmentFacService {
	//	@Value("${red.server_port}")
//	private String serverPort;
//	@Value("${access_token}")
//	private String token;
	@Resource
	private UtEqEquipmentMapper utEqEquipmentMapper;
	@Resource
	private EquipmentFacService equipmentFacService;
	@Resource
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;
	private Logger log = LoggerFactory.getLogger(EquipmentFacServiceImpl.class);
	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private PropertiesManageService propertiesManageService;

	private Long userid;

	@Value("${raw.data.database}")
	private String databaseName;

	@Override

	public PageInfo<EquipmentFacOutData> getList(EquipmentFacInData inData, Long id) throws Exception {
		userid = id;
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<EquipmentFacOutData> list = utEqEquipmentMapper.getList(inData);
		PageInfo<EquipmentFacOutData> page = new PageInfo<EquipmentFacOutData>(list);
		return page;
	}

	@Override
	@Transactional
	public void add(EquipmentFacInDataUpdate inData, HttpServletRequest request) throws Exception {
		List<EquipmentDetailOutData> detail = utEqEquipmentdetialgwMapper.getListByNetDeviceid(inData.getNetdeviceid());
		if ("用户信息传输装置".equals(inData.getEqclassname())) {
			List<EquipmentFacOutData> eq = utEqEquipmentMapper.getListByNetDeviceid(inData.getNetdeviceid());
			if (Util.isNotEmpty(eq)) {
				throw new ServiceException("该联网设备已经关联过用户信息传输装置！");
			}
		}
		if ("报警主机".equals(inData.getEqclassname())) {
			List<EquipmentFacOutData> eq = utEqEquipmentMapper.getListByNetDeviceid(inData.getNetdeviceid());
			if (Util.isNotEmpty(eq)) {
				throw new ServiceException("该联网设备已经关联过报警主机！");
			}
		}
		Set<Integer> MNValue = new HashSet<Integer>();// 模拟量端口集合
		Set<Integer> SZValue = new HashSet<Integer>();// 数字量端口集合
		String errorMsg = "";// 错误信息
		if (Util.isNotEmpty(detail)) {
			for (EquipmentDetailOutData d : detail) {
				if (d.getIotype() == 1) {
					MNValue.add(d.getIoport());
				} else {
					SZValue.add(d.getIoport());
				}
			}
		}
		UtEqEquipment data = new UtEqEquipment();
		//id
		Long eqID = snowflakeIdWorker.nextId();
		data.setId(eqID);
		data.setIsdelete(0);
		System.out.println(inData.getDeviceindex()+"???");
		//设备类型
		data.setDeviceindex(inData.getDeviceindex());
		//建筑区域id
		if (inData.getBuildareaid() != null && inData.getBuildareaid() != "") {
			data.setBuildareaid(Long.parseLong(inData.getBuildareaid()));
		}
		//单位id
		if (inData.getUnitid() != null && inData.getUnitid() != "") {
			data.setUnitid(Long.parseLong(inData.getUnitid()));
		}

		//建筑id
		if (inData.getBuildid() != null && inData.getBuildid() != "") {
			data.setBuildid(Long.parseLong(inData.getBuildid()));
		}
		//类型
		if (inData.getEqclassid() != null && inData.getEqclassid() != "") {
			data.setEqclassid(Long.parseLong(inData.getEqclassid()));
		}
		if (inData.getNetdeviceid() != null && inData.getNetdeviceid() != "") {
			data.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
		}
		if (inData.getEqsystemcode() != null && inData.getEqsystemcode() != "") {
			data.setEqsystemid(Long.parseLong(inData.getEqsystemcode()));
		}
		//设备名称
		data.setEqname(inData.getEqname());
		//位置
		data.setInstallposition(inData.getInstallposition());
		data.setBuildImgbg(inData.getBuildImgbg());
		data.setPointy(inData.getPointy());
		data.setPointx(inData.getPointy());
		data.setPointVideoId(DataConvertUtil.parseLong(inData.getPointVideoId()));
		String lists = inData.getList();
		String portList = inData.getPortLists();
		Integer flags = utEqEquipmentMapper.insert(data);
		if (flags < 1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
		if (inData.getActionType() == 1) {// 故障端口（数字量）
			if (SZValue.contains(Integer.parseInt(inData.getIoport()))) {
				throw new ServiceException("数字量端口重复(" + inData.getIoport() + ")");
			}
			String name = inData.getDetialname();
			String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
			UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
			gw.setId(snowflakeIdWorker.nextId());
			gw.setEqid(eqID);
			gw.setDetialname(detialName);
			gw.setGoodname(inData.getGoodname());
			gw.setBadname(inData.getBadname());
			gw.setIoport(Integer.parseInt(inData.getIoport()));
			gw.setIotype(2);
			gw.setDigitalnormal(Integer.parseInt(inData.getDigitalnormal()));
			gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
			gw.setReserve(inData.getReserve());
			Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
			if (flag < 1) {
				throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
			}
			sendMsg1(inData, 0);
		} else if (inData.getActionType() == 2) {// 普通模拟量
			if (MNValue.contains(Integer.parseInt(inData.getIoport()))) {
				throw new ServiceException("模拟量端口重复(" + inData.getIoport() + ")");
			}
			String name = inData.getDetialname();
			String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
			UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
			gw.setId(snowflakeIdWorker.nextId());
			BigDecimal analogb = new BigDecimal(inData.getAnalogb());
			gw.setAnalogb(analogb);
			BigDecimal analogk = new BigDecimal(inData.getAnalogk());
			gw.setAnalogk(analogk);
			BigDecimal analogup = new BigDecimal(inData.getAnalogup());
			gw.setAnalogup(analogup);
			BigDecimal analogdown = new BigDecimal(inData.getAnalogdown());
			gw.setAnalogdown(analogdown);

			if ("".equals(inData.getMaxValue())) {
				BigDecimal maxValue = null;
				gw.setAnalogwarningup(maxValue);
			} else {
				BigDecimal maxValue = new BigDecimal(inData.getMaxValue());
				gw.setAnalogwarningup(maxValue);
			}
			if ("".equals(inData.getMinValue())) {
				BigDecimal minValue = null;
				gw.setAnalogwarningdown(minValue);
			} else {
				BigDecimal minValue = new BigDecimal(inData.getMinValue());
				gw.setAnalogwarningdown(minValue);
			}

			gw.setIoport(Integer.parseInt(inData.getIoport()));
			gw.setIotype(1);
			gw.setEqid(eqID);
			gw.setReserve(inData.getReserve());
			gw.setDetialname(detialName);
			gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
			gw.setAnalogunit(inData.getAnalogunit());
			Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
			if (flag < 1) {
				throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
			}
			sendMsg1(inData, 0);
		} else if (inData.getActionType() == 3) {// 泵控制柜（数字量）
			List<EquipmentRTUInData> list2 = (List<EquipmentRTUInData>) JSONArray.parseArray(lists,
					EquipmentRTUInData.class);
			Set<Integer> set = new HashSet<Integer>();
			for (EquipmentRTUInData list : list2) {
				set.add(list.getIoport());
			}
			if (set.size() != list2.size()) {
				throw new ServiceException("所填信息中有重复端口，请检查更改！");
			}
			for (EquipmentRTUInData list : list2) {
				set.add(list.getIoport());
				if (SZValue.contains(list.getIoport())) {
					errorMsg = "数字量端口重复(" + list.getIoport() + ")";
					break;
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			for (EquipmentRTUInData list : list2) {
				String name = list.getDetailname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				gw.setId(snowflakeIdWorker.nextId());
				gw.setEqid(eqID);
				gw.setDetialname(detialName);
				gw.setGoodname(list.getGoodname());
				gw.setBadname(list.getBadname());
				gw.setIoport(list.getIoport());
				gw.setIotype(2);
				gw.setDigitalnormal(list.getDigitalnormal());
				gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				gw.setReserve(list.getReserve());
				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
					break;
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			sendMsg1(inData, 0);
		} else if (inData.getActionType() == 4) {// 电气火灾
			List<EquipmentThreeInData> list = (List<EquipmentThreeInData>) JSONArray.parseArray(portList,
					EquipmentThreeInData.class);
			Set<Integer> set = new HashSet<Integer>();

			for (EquipmentThreeInData in : list) {
				set.add(in.getIoport());
			}
			if (set.size() != list.size()) {
				throw new ServiceException("所填信息中有重复端口，请检查更改！");
			}
			for (EquipmentThreeInData in : list) {
				set.add(in.getIoport());
				if (MNValue.contains(in.getIoport())) {
					errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			for (EquipmentThreeInData in : list) {
				String name = in.getDetialname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				gw.setId(snowflakeIdWorker.nextId());
				BigDecimal analogb = new BigDecimal(in.getAnalogb());
				gw.setAnalogb(analogb);
				BigDecimal analogk = new BigDecimal(in.getAnalogk());
				gw.setAnalogk(analogk);
				BigDecimal analogup = new BigDecimal(in.getAnalogup());
				gw.setAnalogup(analogup);
				BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
				gw.setAnalogdown(analogdown);
				if ("".equals(in.getMaxValue())) {
					BigDecimal maxValue = null;
					gw.setAnalogwarningup(maxValue);
				} else {
					BigDecimal maxValue = new BigDecimal(in.getMaxValue());
					gw.setAnalogwarningup(maxValue);
				}
				if ("".equals(in.getMinValue())) {
					BigDecimal minValue = null;
					gw.setAnalogwarningdown(minValue);
				} else {
					BigDecimal minValue = new BigDecimal(in.getMinValue());
					gw.setAnalogwarningdown(minValue);
				}

				gw.setIoport(in.getIoport());
				gw.setIotype(1);
				gw.setEqid(eqID);
				gw.setReserve(in.getReserve());
				gw.setDetialname(detialName);
				gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				gw.setAnalogunit(in.getAnalogunit());
				gw.setDetialname(detialName);

				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			sendMsg1(inData, 0);

		} else if (inData.getActionType() == 5) {// 其他端口
			List<EquipmentFacInData> list = (List<EquipmentFacInData>) JSONArray.parseArray(portList,
					EquipmentFacInData.class);
			Set<Integer> set = new HashSet<Integer>();
			Set<Integer> set2 = new HashSet<Integer>();
			for (EquipmentFacInData in : list) {
				if (in.getIotype().toString().equals("1")) {
					set.add(Integer.parseInt(in.getIoport()));
				} else {
					set2.add(Integer.parseInt(in.getIoport()));
				}
			}
			if ((set.size() + set2.size()) != list.size()) {
				throw new ServiceException("所填信息中有重复端口，请检查更改！");
			}
			for (EquipmentFacInData in : list) {
				if (MNValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("1")) {
					errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
					break;
				}
				if (SZValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("2")) {
					errorMsg = "数字量端口重复(" + in.getIoport() + ")";
					break;
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			for (EquipmentFacInData in : list) {
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				gw.setId(snowflakeIdWorker.nextId());
				if (Util.isNotEmpty(in.getAnalogb())) {
					BigDecimal analogb = new BigDecimal(in.getAnalogb());
					gw.setAnalogb(analogb);
				}
				if (Util.isNotEmpty(in.getAnalogk())) {
					BigDecimal analogk = new BigDecimal(in.getAnalogk());
					gw.setAnalogk(analogk);
				}
				if (Util.isNotEmpty(in.getAnalogup())) {
					BigDecimal analogup = new BigDecimal(in.getAnalogup());
					gw.setAnalogup(analogup);
				}

				if (Util.isNotEmpty(in.getAnalogdown())) {
					BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
					gw.setAnalogdown(analogdown);
				}

				if ("".equals(inData.getMaxValue())) {
					BigDecimal maxValue = null;
					gw.setAnalogwarningup(maxValue);
				} else {
					BigDecimal maxValue = new BigDecimal(inData.getMaxValue());
					gw.setAnalogwarningup(maxValue);
				}
				if ("".equals(inData.getMinValue())) {
					BigDecimal minValue = null;
					gw.setAnalogwarningdown(minValue);
				} else {
					BigDecimal minValue = new BigDecimal(inData.getMinValue());
					gw.setAnalogwarningdown(minValue);
				}
				gw.setIotype(in.getIotype());
				gw.setDetialname(in.getDetialname());
				gw.setAnalogunit(in.getAnalogunit());
				gw.setGoodname(in.getGoodname());
				gw.setBadname(in.getBadname());
				gw.setEqid(eqID);
				if (Util.isNotEmpty(in.getDigitalnormal())) {
					gw.setDigitalnormal(Integer.parseInt(in.getDigitalnormal()));
				}
				if (Util.isNotEmpty(inData.getNetdeviceid())) {
					gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				}
				gw.setReserve(in.getReserve());
				gw.setRemark(in.getRemark());
				if (Util.isNotEmpty(inData.getId())) {
					gw.setEqid(Long.parseLong(inData.getId()));
				}
				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
					break;
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
		}
	}

	/**
	 * 批量添加接口
	 *
	 * @param facInData
	 * @param request
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void addAll(List<EquipmentFacInData> facInData, HttpServletRequest request) throws Exception {
		List<EquipmentFacInData> inList = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		for (EquipmentFacInData inDatadata : facInData) {
			if (inDatadata.getId() == null) {
				StringBuffer sb = new StringBuffer();
				sb.append(inDatadata.getEqclassid());
				sb.append(inDatadata.getBuildareaid());
				sb.append(inDatadata.getNetdeviceid());
				sb.append(inDatadata.getUnitid());
				sb.append(inDatadata.getEqname());
				sb.append(inDatadata.getInstallposition());
				sb.append(inDatadata.getBuildid());
				sb.append(inDatadata.getEqsystemcode());
				if (map.get(sb.toString()) == null) {
					inList.add(inDatadata);
					map.put(sb.toString(), inList.size() - 1);//索引
				} else {
					int i = map.get(sb.toString());
					EquipmentFacInData facInData1 = inList.get(i);
					if (inDatadata.getList() != null) {
						facInData1.getList().addAll(inDatadata.getList());
					}
					if (inDatadata.getPortLists() != null) {
						facInData1.getPortLists().addAll(inDatadata.getPortLists());
					}
				}
			}
		}
		for (EquipmentFacInData inDatadata : inList) {

			List<EquipmentDetailOutData> detail = utEqEquipmentdetialgwMapper.getListByNetDeviceid(inDatadata.getNetdeviceid());
			if ("用户信息传输装置".equals(inDatadata.getEqclassname())) {
				List<EquipmentFacOutData> eq = utEqEquipmentMapper.getListByNetDeviceid(inDatadata.getNetdeviceid());
				if (Util.isNotEmpty(eq)) {
					throw new ServiceException("该联网设备已经关联过用户信息传输装置！");
				}
			}
			if ("报警主机".equals(inDatadata.getEqclassname())) {
				List<EquipmentFacOutData> eq = utEqEquipmentMapper.getListByNetDeviceid(inDatadata.getNetdeviceid());
				if (Util.isNotEmpty(eq)) {
					throw new ServiceException("该联网设备已经关联过报警主机！");
				}
			}
			Set<Integer> MNValue = new HashSet<Integer>();// 模拟量端口集合
			Set<Integer> SZValue = new HashSet<Integer>();// 数字量端口集合
			String errorMsg = "";// 错误信息
			if (Util.isNotEmpty(detail)) {
				for (EquipmentDetailOutData d : detail) {
					if (d.getIotype() == 1) {
						MNValue.add(d.getIoport());
					} else {
						SZValue.add(d.getIoport());
					}
				}
			}
			UtEqEquipment data = new UtEqEquipment();
			Long eqID = snowflakeIdWorker.nextId();
			data.setId(eqID);
			data.setIsdelete(0);
			if (inDatadata.getBuildareaid() != null && inDatadata.getBuildareaid() != "") {
				data.setBuildareaid(Long.parseLong(inDatadata.getBuildareaid()));
			}
			if (inDatadata.getUnitid() != null && inDatadata.getUnitid() != "") {
				data.setUnitid(Long.parseLong(inDatadata.getUnitid()));
			}
			if (inDatadata.getBuildid() != null && inDatadata.getBuildid() != "") {
				data.setBuildid(Long.parseLong(inDatadata.getBuildid()));
			}
			if (inDatadata.getEqclassid() != null && inDatadata.getEqclassid() != "") {
				data.setEqclassid(Long.parseLong(inDatadata.getEqclassid()));
			}
			if (inDatadata.getNetdeviceid() != null && inDatadata.getNetdeviceid() != "") {
				data.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
			}
			if (inDatadata.getEqsystemcode() != null && inDatadata.getEqsystemcode() != "") {
				data.setEqsystemid(Long.parseLong(inDatadata.getEqsystemcode()));
			}
			data.setEqname(inDatadata.getEqname());
			data.setInstallposition(inDatadata.getInstallposition());
			String portList = inDatadata.getPortList();
			Integer flags = utEqEquipmentMapper.insert(data);
			if (flags < 1) {
				throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
			}
			if (inDatadata.getActionType() == 1) {// 故障端口（数字量）
				if (SZValue.contains(Integer.parseInt(inDatadata.getIoport()))) {
					throw new ServiceException("数字量端口重复(" + inDatadata.getIoport() + ")");
				}
				String name = inDatadata.getDetialname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				gw.setId(snowflakeIdWorker.nextId());
				gw.setEqid(eqID);
				gw.setDetialname(detialName);
				gw.setGoodname(inDatadata.getGoodname());
				gw.setBadname(inDatadata.getBadname());
				gw.setIoport(Integer.parseInt(inDatadata.getIoport()));
				gw.setIotype(2);
				gw.setDigitalnormal(Integer.parseInt(inDatadata.getDigitalnormal()));
				gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
				gw.setReserve(inDatadata.getReserve());
				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
				sendMsg(inDatadata, 0);
			} else if (inDatadata.getActionType() == 2) {// 普通模拟量
				if (MNValue.contains(Integer.parseInt(inDatadata.getIoport()))) {
					throw new ServiceException("模拟量端口重复(" + inDatadata.getIoport() + ")");
				}
				String name = inDatadata.getDetialname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				gw.setId(snowflakeIdWorker.nextId());
				BigDecimal analogb = new BigDecimal(inDatadata.getAnalogb());
				gw.setAnalogb(analogb);
				BigDecimal analogk = new BigDecimal(inDatadata.getAnalogk());
				gw.setAnalogk(analogk);
				BigDecimal analogup = new BigDecimal(inDatadata.getAnalogup());
				gw.setAnalogup(analogup);
				BigDecimal analogdown = new BigDecimal(inDatadata.getAnalogdown());
				gw.setAnalogdown(analogdown);

				if ("".equals(inDatadata.getMaxValue())) {
					BigDecimal maxValue = null;
					gw.setAnalogwarningup(maxValue);
				} else {
					BigDecimal maxValue = new BigDecimal(inDatadata.getMaxValue());
					gw.setAnalogwarningup(maxValue);
				}
				if ("".equals(inDatadata.getMinValue())) {
					BigDecimal minValue = null;
					gw.setAnalogwarningdown(minValue);
				} else {
					BigDecimal minValue = new BigDecimal(inDatadata.getMinValue());
					gw.setAnalogwarningdown(minValue);
				}
				gw.setIoport(Integer.parseInt(inDatadata.getIoport()));
				gw.setIotype(1);
				gw.setEqid(eqID);
				gw.setReserve(inDatadata.getReserve());
				gw.setDetialname(detialName);
				gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
				gw.setAnalogunit(inDatadata.getAnalogunit());
				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
				sendMsg(inDatadata, 0);
			} else if (inDatadata.getActionType() == 3) {// 泵控制柜（数字量）
				Set<Integer> set = new HashSet<Integer>();
				for (EquipmentRTUInData list : inDatadata.getList()) {
					set.add(list.getIoport());
				}
				if (set.size() != inDatadata.getList().size()) {
					throw new ServiceException("所填信息中有重复端口，请检查更改！");
				}
				for (EquipmentRTUInData list : inDatadata.getList()) {
					set.add(list.getIoport());
					if (SZValue.contains(list.getIoport())) {
						errorMsg = "数字量端口重复(" + list.getIoport() + ")";
						break;
					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
				for (EquipmentRTUInData list : inDatadata.getList()) {
					String name = list.getDetailname();
					String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
					UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
					gw.setId(snowflakeIdWorker.nextId());
					gw.setEqid(eqID);
					gw.setDetialname(detialName);
					gw.setGoodname(list.getGoodname());
					gw.setBadname(list.getBadname());
					gw.setIoport(list.getIoport());
					gw.setIotype(2);
					gw.setDigitalnormal(list.getDigitalnormal());
					gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
					gw.setReserve(list.getReserve());
					Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
					if (flag < 1) {
						errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
						break;
					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
				sendMsg(inDatadata, 0);
			} else if (inDatadata.getActionType() == 4) {// 电气火灾
				Set<Integer> set = new HashSet<Integer>();
				for (EquipmentThreeInData in : inDatadata.getPortLists()) {
					set.add(in.getIoport());
				}
				if (set.size() != inDatadata.getPortLists().size()) {
					throw new ServiceException("所填信息中有重复端口，请检查更改！");
				}
				for (EquipmentThreeInData in : inDatadata.getPortLists()) {
					set.add(in.getIoport());
					if (MNValue.contains(in.getIoport())) {
						errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
				for (EquipmentThreeInData in : inDatadata.getPortLists()) {
					String name = in.getDetialname();
					String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
					UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
					gw.setId(snowflakeIdWorker.nextId());
					BigDecimal analogb = new BigDecimal(in.getAnalogb());
					gw.setAnalogb(analogb);
					BigDecimal analogk = new BigDecimal(in.getAnalogk());
					gw.setAnalogk(analogk);
					BigDecimal analogup = new BigDecimal(in.getAnalogup());
					gw.setAnalogup(analogup);
					BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
					gw.setAnalogdown(analogdown);
					if ("".equals(in.getMaxValue())) {
						BigDecimal maxValue = null;
						gw.setAnalogwarningup(maxValue);
					} else {
						BigDecimal maxValue = new BigDecimal(in.getMaxValue());
						gw.setAnalogwarningup(maxValue);
					}
					if ("".equals(in.getMinValue())) {
						BigDecimal minValue = null;
						gw.setAnalogwarningdown(minValue);
					} else {
						BigDecimal minValue = new BigDecimal(in.getMinValue());
						gw.setAnalogwarningdown(minValue);
					}

					gw.setIoport(in.getIoport());
					gw.setIotype(1);
					gw.setEqid(eqID);
					gw.setReserve(in.getReserve());
					gw.setDetialname(detialName);
					gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
					gw.setAnalogunit(in.getAnalogunit());
					gw.setDetialname(detialName);

					Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
					if (flag < 1) {
						errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
				sendMsg(inDatadata, 0);

			} else if (inDatadata.getActionType() == 5) {// 其他端口
				List<EquipmentFacInData> list = (List<EquipmentFacInData>) JSONArray.parseArray(portList,
						EquipmentFacInData.class);
				Set<Integer> set = new HashSet<Integer>();
				Set<Integer> set2 = new HashSet<Integer>();
				for (EquipmentFacInData in : list) {
					if (in.getIotype().toString().equals("1")) {
						set.add(Integer.parseInt(in.getIoport()));
					} else {
						set2.add(Integer.parseInt(in.getIoport()));
					}
				}
				if ((set.size() + set2.size()) != list.size()) {
					throw new ServiceException("所填信息中有重复端口，请检查更改！");
				}
				for (EquipmentFacInData in : list) {
					if (MNValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("1")) {
						errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
						break;
					}
					if (SZValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("2")) {
						errorMsg = "数字量端口重复(" + in.getIoport() + ")";
						break;
					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
				for (EquipmentFacInData in : list) {
					UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
					gw.setId(snowflakeIdWorker.nextId());
					if (Util.isNotEmpty(in.getAnalogb())) {
						BigDecimal analogb = new BigDecimal(in.getAnalogb());
						gw.setAnalogb(analogb);
					}
					if (Util.isNotEmpty(in.getAnalogk())) {
						BigDecimal analogk = new BigDecimal(in.getAnalogk());
						gw.setAnalogk(analogk);
					}
					if (Util.isNotEmpty(in.getAnalogup())) {
						BigDecimal analogup = new BigDecimal(in.getAnalogup());
						gw.setAnalogup(analogup);
					}

					if (Util.isNotEmpty(in.getAnalogdown())) {
						BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
						gw.setAnalogdown(analogdown);
					}

					if (Util.isNotEmpty(in.getMaxValue())) {
						BigDecimal maxValue = new BigDecimal(in.getMaxValue());
						gw.setAnalogwarningup(maxValue);
					}
					if (Util.isNotEmpty(in.getMinValue())) {
						BigDecimal minDataValue = new BigDecimal(in.getMinValue());
						gw.setAnalogwarningdown(minDataValue);
					}
					if (Util.isNotEmpty(in.getIoport())) {
						gw.setIoport(Integer.parseInt(in.getIoport()));
					}
					gw.setIotype(in.getIotype());
					gw.setDetialname(in.getDetialname());
					gw.setAnalogunit(in.getAnalogunit());
					gw.setGoodname(in.getGoodname());
					gw.setBadname(in.getBadname());
					gw.setEqid(eqID);
					if (Util.isNotEmpty(in.getDigitalnormal())) {
						gw.setDigitalnormal(Integer.parseInt(in.getDigitalnormal()));
					}
					if (Util.isNotEmpty(inDatadata.getNetdeviceid())) {
						gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
					}
					gw.setReserve(in.getReserve());
					gw.setRemark(in.getRemark());
					if (Util.isNotEmpty(inDatadata.getId())) {
						gw.setEqid(Long.parseLong(inDatadata.getId()));
					}
					Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
					if (flag < 1) {
						errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
						break;
					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
			}
		}
	}

	/**
	 * 批量修改接口
	 *
	 * @param facInData
	 * @param request
	 * @throws Exception
	 */
	@Override
	@Transactional
	public void updateAll(List<EquipmentFacInData> facInData, HttpServletRequest request) throws Exception {
		List<EquipmentFacInData> inList = new ArrayList<>();
		List<EquipmentFacInData> inDataList = new ArrayList<>();
		Map<String, Integer> map = new HashMap<>();
		for (EquipmentFacInData inDatadata : facInData) {
			if (inDatadata.getId() == null) {
				StringBuffer sb = new StringBuffer();
				sb.append(inDatadata.getEqclassid());
				sb.append(inDatadata.getBuildareaid());
				sb.append(inDatadata.getNetdeviceid());
				sb.append(inDatadata.getUnitid());
				sb.append(inDatadata.getEqname());
				sb.append(inDatadata.getInstallposition());
				sb.append(inDatadata.getBuildid());
				sb.append(inDatadata.getEqsystemcode());
				if (map.get(sb.toString()) == null) {
					inDataList.add(inDatadata);
					map.put(sb.toString(), inDataList.size() - 1);//索引
				} else {
					int i = map.get(sb.toString());
					EquipmentFacInData facInData1 = inDataList.get(i);
					if (inDatadata.getList() != null) {
						facInData1.getList().addAll(inDatadata.getList());
					}
					if (inDatadata.getPortLists() != null) {
						facInData1.getPortLists().addAll(inDatadata.getPortLists());
					}

				}
			} else {
				StringBuffer sb = new StringBuffer();
				sb.append(inDatadata.getEqclassid());
				sb.append(inDatadata.getBuildareaid());
				sb.append(inDatadata.getNetdeviceid());
				sb.append(inDatadata.getUnitid());
				sb.append(inDatadata.getEqname());
				sb.append(inDatadata.getInstallposition());
				sb.append(inDatadata.getBuildid());
				sb.append(inDatadata.getEqsystemcode());
				if (map.get(sb.toString()) == null) {
					inList.add(inDatadata);
					map.put(sb.toString(), inList.size() - 1);//索引
				} else {
					int i = map.get(sb.toString());
					EquipmentFacInData facInData1 = inList.get(i);
					if (inDatadata.getList() != null) {
						facInData1.getList().addAll(inDatadata.getList());
					}
					if (inDatadata.getPortLists() != null) {
						facInData1.getPortLists().addAll(inDatadata.getPortLists());
					}
				}
			}
		}
		equipmentFacService.addAll(inDataList, request);
		for (EquipmentFacInData inDatadata : inList) {
			List<EquipmentDetailOutData> detail = utEqEquipmentdetialgwMapper.getListByNetDeviceid(inDatadata.getNetdeviceid());
			Set<Integer> MNValue = new HashSet<Integer>();// 模拟量端口集合
			Set<Integer> SZValue = new HashSet<Integer>();// 数字量端口集合
			if (Util.isNotEmpty(detail)) {
				for (EquipmentDetailOutData d : detail) {
					if (d.getIotype() == 1) {
						MNValue.add(d.getIoport());
					} else {
						SZValue.add(d.getIoport());
					}
				}
			}
			UtEqEquipment data = new UtEqEquipment();
			if (inDatadata.getId() != null) {
				if (Util.isNotEmpty(inDatadata.getId())) {
					data.setId(Long.parseLong(inDatadata.getId()));
				}
				if (inDatadata.getBuildareaid() != null && inDatadata.getBuildareaid() != "") {
					data.setBuildareaid(Long.parseLong(inDatadata.getBuildareaid()));
				}
				if (inDatadata.getUnitid() != null && inDatadata.getUnitid() != "") {
					data.setUnitid(Long.parseLong(inDatadata.getUnitid()));
				}
				if (inDatadata.getBuildid() != null && inDatadata.getBuildid() != "") {
					data.setBuildid(Long.parseLong(inDatadata.getBuildid()));
				}
				if (inDatadata.getEqclassid() != null && inDatadata.getEqclassid() != "") {
					data.setEqclassid(Long.parseLong(inDatadata.getEqclassid()));
				}
				if (inDatadata.getNetdeviceid() != null && inDatadata.getNetdeviceid() != "") {
					data.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
				}
				if (inDatadata.getEqsystemcode() != null && inDatadata.getEqsystemcode() != "") {
					data.setEqsystemid(Long.parseLong(inDatadata.getEqsystemcode()));
				}
				data.setEqname(inDatadata.getEqname());
				data.setInstallposition(inDatadata.getInstallposition());
			}
			String portList = inDatadata.getPortList();

			if (inDatadata.getActionType() == 1) {// 故障端口（数字量）
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				if (Util.isEmpty(inDatadata.getDetailid())) {
					if (SZValue.contains(Integer.parseInt(inDatadata.getIoport()))) {
						throw new ServiceException("数字量端口重复(" + inDatadata.getIoport() + ")");
					}

						String name = inDatadata.getDetialname();
						String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
						gw.setId(snowflakeIdWorker.nextId());
						gw.setEqid(Long.parseLong(inDatadata.getId()));
						gw.setDetialname(detialName);
						gw.setGoodname(inDatadata.getGoodname());
						gw.setBadname(inDatadata.getBadname());
						gw.setIoport(Integer.parseInt(inDatadata.getIoport()));
						gw.setIotype(2);
						gw.setDigitalnormal(Integer.parseInt(inDatadata.getDigitalnormal()));
						gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
						gw.setReserve(inDatadata.getReserve());
						Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
						if (flag < 1) {
							throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);

					}
				} else {
					UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(inDatadata.getDetailid()));
					if (SZValue.contains(Integer.parseInt(inDatadata.getIoport()))) {
						if (!inDatadata.getIoport().equals(owner.getIoport().toString())) {
							throw new ServiceException("数字量端口重复(" + inDatadata.getIoport() + ")");
						}
					}

						String name = inDatadata.getDetialname();
						String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
						gw.setId(Long.parseLong(inDatadata.getDetailid()));
						gw.setEqid(Long.parseLong(inDatadata.getId()));
						gw.setDetialname(detialName);
						gw.setGoodname(inDatadata.getGoodname());
						gw.setBadname(inDatadata.getBadname());
						gw.setIoport(Integer.parseInt(inDatadata.getIoport()));
						gw.setIotype(2);
						gw.setDigitalnormal(Integer.parseInt(inDatadata.getDigitalnormal()));
						gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
						Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
						if (flag < 1) {
							throw new ServiceException(UtilMessage.UPDATE_ERROR);

					}

				}
				sendMsg(inDatadata, 0);
			} else if (inDatadata.getActionType() == 2) {// 普通模拟量
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				if (Util.isEmpty(inDatadata.getDetailid())) {
					if (MNValue.contains(Integer.parseInt(inDatadata.getIoport()))) {
						throw new ServiceException("模拟量端口重复(" + inDatadata.getIoport() + ")");
					}

						gw.setEqid(Long.parseLong(inDatadata.getId()));
						String name = inDatadata.getDetialname();
						String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
						gw.setId(snowflakeIdWorker.nextId());
						BigDecimal analogb = new BigDecimal(inDatadata.getAnalogb());
						gw.setAnalogb(analogb);
						BigDecimal analogk = new BigDecimal(inDatadata.getAnalogk());
						gw.setAnalogk(analogk);
						BigDecimal analogup = new BigDecimal(inDatadata.getAnalogup());
						gw.setAnalogup(analogup);
						BigDecimal analogdown = new BigDecimal(inDatadata.getAnalogdown());
						gw.setAnalogdown(analogdown);
						if ("".equals(inDatadata.getMaxValue())) {
							BigDecimal maxValue = null;
							gw.setAnalogwarningup(maxValue);
						} else {
							BigDecimal maxValue = new BigDecimal(inDatadata.getMaxValue());
							gw.setAnalogwarningup(maxValue);
						}
						if ("".equals(inDatadata.getMinValue())) {
							BigDecimal minValue = null;
							gw.setAnalogwarningdown(minValue);
						} else {
							BigDecimal minValue = new BigDecimal(inDatadata.getMinValue());
							gw.setAnalogwarningdown(minValue);
						}
						gw.setIoport(Integer.parseInt(inDatadata.getIoport()));
						gw.setIotype(1);
						gw.setReserve(inDatadata.getReserve());
						gw.setDetialname(detialName);
						gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
						gw.setAnalogunit(inDatadata.getAnalogunit());
						Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
						if (flag < 1) {
							throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);

					}
				} else {
					UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(inDatadata.getDetailid()));
					if (MNValue.contains(Integer.parseInt(inDatadata.getIoport()))) {
						if (!inDatadata.getIoport().equals(owner.getIoport().toString())) {
							throw new ServiceException("模拟量端口重复(" + inDatadata.getIoport() + ")");
						}
					}

						gw.setEqid(Long.parseLong(inDatadata.getId()));
						String name = inDatadata.getDetialname();
						String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
						gw.setId(Long.parseLong(inDatadata.getDetailid()));
						BigDecimal analogb = new BigDecimal(inDatadata.getAnalogb());
						gw.setAnalogb(analogb);
						BigDecimal analogk = new BigDecimal(inDatadata.getAnalogk());
						gw.setAnalogk(analogk);
						BigDecimal analogup = new BigDecimal(inDatadata.getAnalogup());
						gw.setAnalogup(analogup);
						BigDecimal analogdown = new BigDecimal(inDatadata.getAnalogdown());
						gw.setAnalogdown(analogdown);
						if ("".equals(inDatadata.getMaxValue())) {
							BigDecimal maxValue = null;
							gw.setAnalogwarningup(maxValue);
						} else {
							BigDecimal maxValue = new BigDecimal(inDatadata.getMaxValue());
							gw.setAnalogwarningup(maxValue);
						}
						if ("".equals(inDatadata.getMinValue())) {
							BigDecimal minValue = null;
							gw.setAnalogwarningdown(minValue);
						} else {
							BigDecimal minValue = new BigDecimal(inDatadata.getMinValue());
							gw.setAnalogwarningdown(minValue);
						}
						gw.setIoport(Integer.parseInt(inDatadata.getIoport()));
						gw.setIotype(1);
						gw.setReserve(inDatadata.getReserve());
						gw.setDetialname(detialName);
						gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
						gw.setAnalogunit(inDatadata.getAnalogunit());
						Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
						if (flag < 1) {
							throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
						}

				}
				sendMsg(inDatadata, 0);
			} else if (inDatadata.getActionType() == 3) {// 泵控制柜（数字量）
				Set<Integer> set = new HashSet<Integer>();
				String errorMsg = "";
				for (EquipmentRTUInData in : inDatadata.getList()) {
					set.add(in.getIoport());
				}
				if (set.size() != inDatadata.getList().size()) {
					throw new ServiceException("所填信息中有重复端口，请检查更改！");
				}

					for (EquipmentRTUInData list : inDatadata.getList()) {
						UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
						if (Util.isEmpty(list.getDetailid())) {
							if (SZValue.contains(list.getIoport())) {
								errorMsg = "数字量端口重复(" + list.getIoport() + ")";
								break;
							}
							String name = list.getDetailname();
							String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
							gw.setId(snowflakeIdWorker.nextId());
							gw.setEqid(Long.parseLong(inDatadata.getId()));
							gw.setDetialname(detialName);
							gw.setGoodname(list.getGoodname());
							gw.setBadname(list.getBadname());
							gw.setIoport(list.getIoport());
							gw.setIotype(2);
							gw.setDigitalnormal(list.getDigitalnormal());
							gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
							gw.setReserve(list.getReserve());
							Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
							if (flag < 1) {
								errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
								break;
							}
						} else {
							UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(list.getDetailid()));
							if (SZValue.contains(list.getIoport())) {
								if (!list.getIoport().equals(owner.getIoport())) {
									errorMsg = "数字量端口重复(" + list.getIoport() + ")";
									break;
								}
							}
							String name = list.getDetailname();
							String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);

								gw.setId(Long.parseLong(list.getDetailid()));
								gw.setEqid(Long.parseLong(inDatadata.getId()));
								gw.setDetialname(detialName);
								gw.setGoodname(list.getGoodname());
								gw.setBadname(list.getBadname());
								gw.setIoport(list.getIoport());
								gw.setIotype(2);
								gw.setDigitalnormal(list.getDigitalnormal());
								gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
								gw.setReserve(list.getReserve());
								Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
								if (flag < 1) {
									errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
									break;
								}

					}
				}

				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
				sendMsg(inDatadata, 0);
			} else if (inDatadata.getActionType() == 4) {// 电气火灾
				Set<Integer> set = new HashSet<Integer>();
				for (EquipmentThreeInData in : inDatadata.getPortLists()) {
					set.add(in.getIoport());
				}
				if (set.size() != inDatadata.getPortLists().size()) {
					throw new ServiceException("所填信息中有重复端口，请检查更改！");
				}
				String errorMsg = "";

					for (EquipmentThreeInData in : inDatadata.getPortLists()) {
						UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
						if (Util.isEmpty(in.getDetailid())) {
							if (MNValue.contains(in.getIoport())) {
								errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
								break;
							}
							String name = in.getDetialname();
							String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
							gw.setId(snowflakeIdWorker.nextId());
							BigDecimal analogb = new BigDecimal(in.getAnalogb());
							gw.setAnalogb(analogb);
							BigDecimal analogk = new BigDecimal(in.getAnalogk());
							gw.setAnalogk(analogk);
							BigDecimal analogup = new BigDecimal(in.getAnalogup());
							gw.setAnalogup(analogup);
							BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
							gw.setAnalogdown(analogdown);
							if ("".equals(in.getMaxValue())) {
								BigDecimal maxValue = null;
								gw.setAnalogwarningup(maxValue);
							} else {
								BigDecimal maxValue = new BigDecimal(in.getMaxValue());
								gw.setAnalogwarningup(maxValue);
							}
							if ("".equals(in.getMinValue())) {
								BigDecimal minValue = null;
								gw.setAnalogwarningdown(minValue);
							} else {
								BigDecimal minValue = new BigDecimal(in.getMinValue());
								gw.setAnalogwarningdown(minValue);
							}
							gw.setIoport(in.getIoport());
							gw.setIotype(1);
							gw.setEqid(Long.parseLong(inDatadata.getId()));
							gw.setReserve(in.getReserve());
							gw.setDetialname(detialName);
							gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
							gw.setAnalogunit(in.getAnalogunit());
							Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
							if (flag < 1) {
								errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
								break;
							}
						} else {
							UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(in.getDetailid()));
							if (MNValue.contains(in.getIoport())) {
								if (!in.getIoport().equals(owner.getIoport())) {
									errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
									break;
								}
							}
							if (inDatadata.getId() == null) {
								equipmentFacService.addAll(inList, request);
							} else {
								gw.setEqid(Long.parseLong(inDatadata.getId()));
								gw.setId(Long.parseLong(in.getDetailid()));
								BigDecimal analogb = new BigDecimal(in.getAnalogb());
								gw.setAnalogb(analogb);
								BigDecimal analogk = new BigDecimal(in.getAnalogk());
								gw.setAnalogk(analogk);
								BigDecimal analogup = new BigDecimal(in.getAnalogup());
								gw.setAnalogup(analogup);
								BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
								gw.setAnalogdown(analogdown);
								if ("".equals(in.getMaxValue())) {
									BigDecimal maxValue = null;
									gw.setAnalogwarningup(maxValue);
								} else {
									BigDecimal maxValue = new BigDecimal(in.getMaxValue());
									gw.setAnalogwarningup(maxValue);
								}
								if ("".equals(in.getMinValue())) {
									BigDecimal minValue = null;
									gw.setAnalogwarningdown(minValue);
								} else {
									BigDecimal minValue = new BigDecimal(in.getMinValue());
									gw.setAnalogwarningdown(minValue);
								}
								gw.setIoport(in.getIoport());
								gw.setIotype(1);
								gw.setReserve(in.getReserve());
								String name = in.getDetialname();
								String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
								gw.setDetialname(detialName);
								gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
								gw.setAnalogunit(in.getAnalogunit());
								Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
								if (flag < 1) {
									errorMsg = UtilMessage.UPDATE_ERROR;
									break;
								}
							}

					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
				sendMsg(inDatadata, 0);
			} else if (inDatadata.getActionType() == 5) {// 其他端口
				List<EquipmentFacInData> list = (List<EquipmentFacInData>) JSONArray.parseArray(portList,
						EquipmentFacInData.class);
				Set<Integer> set = new HashSet<Integer>();
				Set<Integer> set2 = new HashSet<Integer>();
				String errorMsg = "";
				for (EquipmentFacInData in : list) {
					if (in.getIotype().toString().equals("1")) {
						set.add(Integer.parseInt(in.getIoport()));
					} else {
						set2.add(Integer.parseInt(in.getIoport()));
					}
				}
				if ((set.size() + set2.size()) != list.size()) {
					throw new ServiceException("所填信息中有重复端口，请检查更改！");
				}
				for (EquipmentFacInData in : list) {
					if (MNValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("1")) {
						errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
						break;
					}
					if (SZValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("2")) {
						errorMsg = "数字量端口重复(" + in.getIoport() + ")";
						break;
					}
				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}

				for (EquipmentFacInData in : list) {
					UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
					gw.setEqid(Long.parseLong(inDatadata.getId()));
					gw.setId(snowflakeIdWorker.nextId());
					if (Util.isNotEmpty(in.getAnalogb())) {
						BigDecimal analogb = new BigDecimal(in.getAnalogb());
						gw.setAnalogb(analogb);
					}
					if (Util.isNotEmpty(in.getAnalogk())) {
						BigDecimal analogk = new BigDecimal(in.getAnalogk());
						gw.setAnalogk(analogk);
					}
					if (Util.isNotEmpty(in.getAnalogup())) {
						BigDecimal analogup = new BigDecimal(in.getAnalogup());
						gw.setAnalogup(analogup);
					}
					if (Util.isNotEmpty(in.getAnalogdown())) {
						BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
						gw.setAnalogdown(analogdown);
					}

						if (Util.isNotEmpty(in.getMaxValue())) {
							BigDecimal maxValue = new BigDecimal(in.getMaxValue());
							gw.setAnalogwarningup(maxValue);
						}
						if (Util.isNotEmpty(in.getMinValue())) {
							BigDecimal minDataValue = new BigDecimal(in.getMinValue());
							gw.setAnalogwarningdown(minDataValue);
						}
						if (Util.isNotEmpty(in.getIoport())) {
							gw.setIoport(Integer.parseInt(in.getIoport()));
						}
						gw.setIotype(in.getIotype());
						gw.setDetialname(in.getDetialname());
						gw.setAnalogunit(in.getAnalogunit());
						gw.setGoodname(in.getGoodname());
						gw.setBadname(in.getBadname());
						if (Util.isNotEmpty(in.getDigitalnormal())) {
							gw.setDigitalnormal(Integer.parseInt(in.getDigitalnormal()));
						}
						if (Util.isNotEmpty(inDatadata.getNetdeviceid())) {
							gw.setNetdeviceid(Long.parseLong(inDatadata.getNetdeviceid()));
						}
						gw.setReserve(in.getReserve());
						gw.setRemark(in.getRemark());
						Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
						if (flag < 1) {
							errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
							break;
						}

				}
				if (Util.isNotEmpty(errorMsg)) {
					throw new ServiceException(errorMsg);
				}
			}
			if (data.getId() != null) {
				Integer flag = utEqEquipmentMapper.updateByPrimaryKey(data);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
			}

		}
	}

	@Override
	@Transactional
	public void update(EquipmentFacInDataUpdate inData, HttpServletRequest request) throws Exception {
		List<EquipmentDetailOutData> detail = utEqEquipmentdetialgwMapper.getListByNetDeviceid(inData.getNetdeviceid());
		Set<Integer> MNValue = new HashSet<Integer>();// 模拟量端口集合
		Set<Integer> SZValue = new HashSet<Integer>();// 数字量端口集合
		if (Util.isNotEmpty(detail)) {
			for (EquipmentDetailOutData d : detail) {
				if (d.getIotype() == 1) {
					MNValue.add(d.getIoport());
				} else {
					SZValue.add(d.getIoport());
				}
			}
		}
		UtEqEquipment data = new UtEqEquipment();
		if (Util.isNotEmpty(inData.getId())) {
			data.setId(Long.parseLong(inData.getId()));
		}
		if (inData.getBuildareaid() != null && inData.getBuildareaid() != "") {
			data.setBuildareaid(Long.parseLong(inData.getBuildareaid()));
		}
		if (inData.getUnitid() != null && inData.getUnitid() != "") {
			data.setUnitid(Long.parseLong(inData.getUnitid()));
		}
		if (inData.getBuildid() != null && inData.getBuildid() != "") {
			data.setBuildid(Long.parseLong(inData.getBuildid()));
		}
		if (inData.getEqclassid() != null && inData.getEqclassid() != "") {
			data.setEqclassid(Long.parseLong(inData.getEqclassid()));
		}
		if (inData.getNetdeviceid() != null && inData.getNetdeviceid() != "") {
			data.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
		}
		if (inData.getEqsystemcode() != null && inData.getEqsystemcode() != "") {
			data.setEqsystemid(Long.parseLong(inData.getEqsystemcode()));
		}
		data.setDeviceindex(inData.getDeviceindex());
		data.setEqname(inData.getEqname());
		data.setInstallposition(inData.getInstallposition());
		data.setBuildImgbg(inData.getBuildImgbg());
		data.setPointy(inData.getPointy());
		data.setPointx(inData.getPointy());
		data.setPointVideoId(DataConvertUtil.parseLong(inData.getPointVideoId()));
		String lists = inData.getList();
		String portList = inData.getPortLists();

		if (inData.getActionType() == 1) {// 故障端口（数字量）
			UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
			if (Util.isEmpty(inData.getDetailid())) {
				if (SZValue.contains(Integer.parseInt(inData.getIoport()))) {
					throw new ServiceException("数字量端口重复(" + inData.getIoport() + ")");
				}
				String name = inData.getDetialname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				gw.setId(snowflakeIdWorker.nextId());
				gw.setEqid(Long.parseLong(inData.getId()));
				gw.setDetialname(detialName);
				gw.setGoodname(inData.getGoodname());
				gw.setBadname(inData.getBadname());
				gw.setIoport(Integer.parseInt(inData.getIoport()));
				gw.setIotype(2);
				gw.setDigitalnormal(Integer.parseInt(inData.getDigitalnormal()));
				gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				gw.setReserve(inData.getReserve());
				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
			} else {
				UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(inData.getDetailid()));
				if (SZValue.contains(Integer.parseInt(inData.getIoport()))) {
					if (!inData.getIoport().equals(owner.getIoport().toString())) {
						throw new ServiceException("数字量端口重复(" + inData.getIoport() + ")");
					}
				}
				String name = inData.getDetialname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				gw.setId(Long.parseLong(inData.getDetailid()));
				gw.setEqid(Long.parseLong(inData.getId()));
				gw.setDetialname(detialName);
				gw.setGoodname(inData.getGoodname());
				gw.setBadname(inData.getBadname());
				gw.setIoport(Integer.parseInt(inData.getIoport()));
				gw.setIotype(2);
				gw.setDigitalnormal(Integer.parseInt(inData.getDigitalnormal()));
				gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.UPDATE_ERROR);
				}
			}
			sendMsg1(inData, 0);
		} else if (inData.getActionType() == 2) {// 普通模拟量
			UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
			if (Util.isEmpty(inData.getDetailid())) {
				if (MNValue.contains(Integer.parseInt(inData.getIoport()))) {
					throw new ServiceException("模拟量端口重复(" + inData.getIoport() + ")");
				}
				String name = inData.getDetialname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				gw.setId(snowflakeIdWorker.nextId());
				BigDecimal analogb = new BigDecimal(inData.getAnalogb());
				gw.setAnalogb(analogb);
				BigDecimal analogk = new BigDecimal(inData.getAnalogk());
				gw.setAnalogk(analogk);
				BigDecimal analogup = new BigDecimal(inData.getAnalogup());
				gw.setAnalogup(analogup);
				BigDecimal analogdown = new BigDecimal(inData.getAnalogdown());
				gw.setAnalogdown(analogdown);
				BigDecimal maxValue = new BigDecimal(inData.getMaxValue());
				gw.setAnalogwarningup(maxValue);
				BigDecimal minValue = new BigDecimal(inData.getMinValue());
				gw.setAnalogwarningdown(minValue);

				gw.setIoport(Integer.parseInt(inData.getIoport()));
				gw.setIotype(1);
				gw.setEqid(Long.parseLong(inData.getId()));
				gw.setReserve(inData.getReserve());
				gw.setDetialname(detialName);
				gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				gw.setAnalogunit(inData.getAnalogunit());
				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
			} else {
				UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(inData.getDetailid()));
				if (MNValue.contains(Integer.parseInt(inData.getIoport()))) {
					if (!inData.getIoport().equals(owner.getIoport().toString())) {
						throw new ServiceException("模拟量端口重复(" + inData.getIoport() + ")");
					}
				}
				String name = inData.getDetialname();
				String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
				gw.setId(Long.parseLong(inData.getDetailid()));
				BigDecimal analogb = new BigDecimal(inData.getAnalogb());
				gw.setAnalogb(analogb);
				BigDecimal analogk = new BigDecimal(inData.getAnalogk());
				gw.setAnalogk(analogk);
				BigDecimal analogup = new BigDecimal(inData.getAnalogup());
				gw.setAnalogup(analogup);
				BigDecimal analogdown = new BigDecimal(inData.getAnalogdown());
				gw.setAnalogdown(analogdown);
				if ("".equals(inData.getMaxValue())) {
					BigDecimal maxValue = null;
					gw.setAnalogwarningup(maxValue);
				} else {
					BigDecimal maxValue = new BigDecimal(inData.getMaxValue());
					gw.setAnalogwarningup(maxValue);
				}
				if ("".equals(inData.getMinValue())) {
					BigDecimal minValue = null;
					gw.setAnalogwarningdown(minValue);
				} else {
					BigDecimal minValue = new BigDecimal(inData.getMinValue());
					gw.setAnalogwarningdown(minValue);
				}

				gw.setIoport(Integer.parseInt(inData.getIoport()));
				gw.setIotype(1);
				gw.setEqid(Long.parseLong(inData.getId()));
				gw.setReserve(inData.getReserve());
				gw.setDetialname(detialName);
				gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				gw.setAnalogunit(inData.getAnalogunit());
				Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
				if (flag < 1) {
					throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
				}
			}
			sendMsg1(inData, 0);

		} else if (inData.getActionType() == 3) {// 泵控制柜（数字量）
			List<EquipmentRTUInData> list2 = (List<EquipmentRTUInData>) JSONArray.parseArray(lists,
					EquipmentRTUInData.class);
			Set<Integer> set = new HashSet<Integer>();
			String errorMsg = "";
			for (EquipmentRTUInData in : list2) {
				set.add(in.getIoport());
			}
			if (set.size() != list2.size()) {
				throw new ServiceException("所填信息中有重复端口，请检查更改！");
			}
			for (EquipmentRTUInData list : list2) {
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				if (Util.isEmpty(list.getDetailid())) {
					if (SZValue.contains(list.getIoport())) {
						errorMsg = "数字量端口重复(" + list.getIoport() + ")";
						break;
					}
					String name = list.getDetailname();
					String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
					gw.setId(snowflakeIdWorker.nextId());
					gw.setEqid(Long.parseLong(inData.getId()));
					gw.setDetialname(detialName);
					gw.setGoodname(list.getGoodname());
					gw.setBadname(list.getBadname());
					gw.setIoport(list.getIoport());
					gw.setIotype(2);
					gw.setDigitalnormal(list.getDigitalnormal());
					gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
					gw.setReserve(list.getReserve());
					Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
					if (flag < 1) {
						errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
						break;
					}
				} else {
					if (Util.isEmpty(list.getIoport()) && Util.isEmpty(list.getIotype()) && Util.isEmpty(list.getDetailname()
					) && Util.isEmpty(list.getBadname()) && Util.isEmpty(list.getGoodname()) && Util.isEmpty(list.getDigitalnormal())
							&& Util.isEmpty(list.getEqid()) && Util.isEmpty(list.getReserve()) && Util.isEmpty(list.getRemark())) {
						Integer success = utEqEquipmentdetialgwMapper.deleteByPrimaryKey(Long.valueOf(list.getDetailid()));
						if (success < 1) {
							errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
							break;
						}
					} else {
						UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(list.getDetailid()));
						if (SZValue.contains(list.getIoport())) {
							if (!list.getIoport().equals(owner.getIoport())) {
								errorMsg = "数字量端口重复(" + list.getIoport() + ")";
								break;
							}
						}
						String name = list.getDetailname();
						String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
						gw.setId(Long.parseLong(list.getDetailid()));
						gw.setEqid(Long.parseLong(inData.getId()));
						gw.setDetialname(detialName);
						gw.setGoodname(list.getGoodname());
						gw.setBadname(list.getBadname());
						gw.setIoport(list.getIoport());
						gw.setIotype(2);
						gw.setDigitalnormal(list.getDigitalnormal());
						gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
						gw.setReserve(list.getReserve());
						Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
						if (flag < 1) {
							errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
							break;
						}

					}
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			sendMsg1(inData, 0);
		} else if (inData.getActionType() == 4) {// 电气火灾
			List<EquipmentThreeInData> list = (List<EquipmentThreeInData>) JSONArray.parseArray(portList,
					EquipmentThreeInData.class);
			Set<Integer> set = new HashSet<Integer>();
			for (EquipmentThreeInData in : list) {
				set.add(in.getIoport());
			}
			if (set.size() != list.size()) {
				throw new ServiceException("所填信息中有重复端口，请检查更改！");
			}
			String errorMsg = "";
			for (EquipmentThreeInData in : list) {
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				if (Util.isEmpty(in.getDetailid())) {
					if (MNValue.contains(in.getIoport())) {
						errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
						break;
					}
					String name = in.getDetialname();
					String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
					gw.setId(snowflakeIdWorker.nextId());
					BigDecimal analogb = new BigDecimal(in.getAnalogb());
					gw.setAnalogb(analogb);
					BigDecimal analogk = new BigDecimal(in.getAnalogk());
					gw.setAnalogk(analogk);
					BigDecimal analogup = new BigDecimal(in.getAnalogup());
					gw.setAnalogup(analogup);
					BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
					gw.setAnalogdown(analogdown);
					if ("".equals(in.getMaxValue())) {
						BigDecimal maxValue = null;
						gw.setAnalogwarningup(maxValue);
					} else {
						BigDecimal maxValue = new BigDecimal(in.getMaxValue());
						gw.setAnalogwarningup(maxValue);
					}
					if ("".equals(in.getMinValue())) {
						BigDecimal minValue = null;
						gw.setAnalogwarningdown(minValue);
					} else {
						BigDecimal minValue = new BigDecimal(in.getMinValue());
						gw.setAnalogwarningdown(minValue);
					}


					gw.setIoport(in.getIoport());
					gw.setIotype(1);
					gw.setEqid(Long.parseLong(inData.getId()));
					gw.setReserve(in.getReserve());
					gw.setDetialname(detialName);
					gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
					gw.setAnalogunit(in.getAnalogunit());
					Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
					if (flag < 1) {
						errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
						break;
					}
				} else {
					if (Util.isEmpty(in.getIoport()) && Util.isEmpty(in.getIotype()) && Util.isEmpty(in.getDetialname()
					) && Util.isEmpty(in.getMaxValue()) && Util.isEmpty(in.getMinValue()) && Util.isEmpty(in.getDigitalnormal())
							&& Util.isEmpty(in.getReserve()) && Util.isEmpty(in.getAnalogb()) && Util.isEmpty(in.getAnalogup())
							&& Util.isEmpty(in.getAnalogunit()) && Util.isEmpty(in.getAnalogk()) && Util.isEmpty(in.getAnalogdown())) {
						Integer sucess = utEqEquipmentdetialgwMapper.deleteByPrimaryKey(Long.valueOf(in.getDetailid()));
						if (sucess < 1) {
							errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
							break;
						}
					} else {
						UtEqEquipmentdetialgw owner = utEqEquipmentdetialgwMapper.selectByPrimaryKey(Long.parseLong(in.getDetailid()));
						if (MNValue.contains(in.getIoport())) {
							if (!in.getIoport().equals(owner.getIoport())) {
								errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
								break;
							}
						}

						gw.setId(Long.parseLong(in.getDetailid()));
						BigDecimal analogb = new BigDecimal(in.getAnalogb());
						gw.setAnalogb(analogb);
						BigDecimal analogk = new BigDecimal(in.getAnalogk());
						gw.setAnalogk(analogk);
						BigDecimal analogup = new BigDecimal(in.getAnalogup());
						gw.setAnalogup(analogup);
						BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
						gw.setAnalogdown(analogdown);
						if ("".equals(in.getMaxValue())) {
							BigDecimal maxValue = null;
							gw.setAnalogwarningup(maxValue);
						} else {
							BigDecimal maxValue = new BigDecimal(in.getMaxValue());
							gw.setAnalogwarningup(maxValue);
						}
						if ("".equals(in.getMinValue())) {
							BigDecimal minValue = null;
							gw.setAnalogwarningdown(minValue);
						} else {
							BigDecimal minValue = new BigDecimal(in.getMinValue());
							gw.setAnalogwarningdown(minValue);
						}

						gw.setIoport(in.getIoport());
						gw.setIotype(1);
						gw.setEqid(Long.parseLong(inData.getId()));
						gw.setReserve(in.getReserve());
						String name = in.getDetialname();
						String detialName = name.substring(name.indexOf("(") + 1, name.length() - 1);
						gw.setDetialname(detialName);
						gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
						gw.setAnalogunit(in.getAnalogunit());
						Integer flag = utEqEquipmentdetialgwMapper.updateByPrimaryKey(gw);
						if (flag < 1) {
							errorMsg = UtilMessage.UPDATE_ERROR;
							break;
						}
					}
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			sendMsg1(inData, 0);
		} else if (inData.getActionType() == 5) {// 其他端口
			List<EquipmentFacInData> list = (List<EquipmentFacInData>) JSONArray.parseArray(portList,
					EquipmentFacInData.class);
			Set<Integer> set = new HashSet<Integer>();
			Set<Integer> set2 = new HashSet<Integer>();
			String errorMsg = "";
			for (EquipmentFacInData in : list) {
				if (in.getIotype().toString().equals("1")) {
					set.add(Integer.parseInt(in.getIoport()));
				} else {
					set2.add(Integer.parseInt(in.getIoport()));
				}
			}
			if ((set.size() + set2.size()) != list.size()) {
				throw new ServiceException("所填信息中有重复端口，请检查更改！");
			}
			for (EquipmentFacInData in : list) {
				if (MNValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("1")) {
					errorMsg = "模拟量端口重复(" + in.getIoport() + ")";
					break;
				}
				if (SZValue.contains(Integer.parseInt(in.getIoport())) && in.getIotype().toString().equals("2")) {
					errorMsg = "数字量端口重复(" + in.getIoport() + ")";
					break;
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
			for (EquipmentFacInData in : list) {
				UtEqEquipmentdetialgw gw = new UtEqEquipmentdetialgw();
				gw.setId(snowflakeIdWorker.nextId());
				if (Util.isNotEmpty(in.getAnalogb())) {
					BigDecimal analogb = new BigDecimal(in.getAnalogb());
					gw.setAnalogb(analogb);
				}
				if (Util.isNotEmpty(in.getAnalogk())) {
					BigDecimal analogk = new BigDecimal(in.getAnalogk());
					gw.setAnalogk(analogk);
				}
				if (Util.isNotEmpty(in.getAnalogup())) {
					BigDecimal analogup = new BigDecimal(in.getAnalogup());
					gw.setAnalogup(analogup);
				}

				if (Util.isNotEmpty(in.getAnalogdown())) {
					BigDecimal analogdown = new BigDecimal(in.getAnalogdown());
					gw.setAnalogdown(analogdown);
				}

				if (Util.isNotEmpty(in.getMaxValue())) {
					BigDecimal maxValue = new BigDecimal(in.getMaxValue());
					gw.setAnalogwarningup(maxValue);
				}
				if (Util.isNotEmpty(in.getMinValue())) {
					BigDecimal minDataValue = new BigDecimal(in.getMinValue());
					gw.setAnalogwarningdown(minDataValue);
				}
				if (Util.isNotEmpty(in.getIoport())) {
					gw.setIoport(Integer.parseInt(in.getIoport()));
				}
				gw.setIotype(in.getIotype());
				gw.setDetialname(in.getDetialname());
				gw.setAnalogunit(in.getAnalogunit());
				gw.setGoodname(in.getGoodname());
				gw.setBadname(in.getBadname());
				if (Util.isNotEmpty(in.getDigitalnormal())) {
					gw.setDigitalnormal(Integer.parseInt(in.getDigitalnormal()));
				}
				if (Util.isNotEmpty(inData.getNetdeviceid())) {
					gw.setNetdeviceid(Long.parseLong(inData.getNetdeviceid()));
				}
				gw.setReserve(in.getReserve());
				gw.setRemark(in.getRemark());
				if (Util.isNotEmpty(inData.getId())) {
					gw.setEqid(Long.parseLong(inData.getId()));
				}
				Integer flag = utEqEquipmentdetialgwMapper.insert(gw);
				if (flag < 1) {
					errorMsg = UtilMessage.SAVE_MESSAGE_ERROR;
					break;
				}
			}
			if (Util.isNotEmpty(errorMsg)) {
				throw new ServiceException(errorMsg);
			}
		}
		Integer flag = utEqEquipmentMapper.updateByPrimaryKey(data);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.SAVE_MESSAGE_ERROR);
		}
	}

	@Override
	@Transactional
	public void remove(String id, HttpServletRequest request) throws Exception {
		// String ownercode="";
		if (id != null && id != "") {
			// List<EquipmentFacInData> list =
			// utEqEquipmentdetialgwMapper.selectDetailList(id);
			// for(EquipmentFacInData data : list) {
			// data.setStatus(-1);
			// ownercode = data.getOwnercode();
			// }
			UtEqEquipment utEqEquipment = utEqEquipmentMapper.selectByPrimaryKey(Long.valueOf(id));
			utEqEquipment.setIsdelete(1);
			utEqEquipmentMapper.updateByPrimaryKeySelective(utEqEquipment);
			utEqEquipmentdetialgwMapper.removePortByEqId(id);
			/*
			 * Integer flag = utEqEquipmentMapper.deleteByPrimaryKey(Long.parseLong(id));
			 * if(flag<1) { throw new ServiceException("删除设备失败！"); }
			 */
			/*
			 * List<EquipmentDetailOutData> portList =
			 * utEqEquipmentdetialgwMapper.getPortList(id); if(Util.isNotEmpty(portList)) {
			 * for(EquipmentDetailOutData data: portList) { Integer flag2 =
			 * utEqEquipmentdetialgwMapper.deleteByPrimaryKey(Long.parseLong(data.getId()));
			 * if(flag2<1) { throw new ServiceException("删除设备对应端口失败！"); } } }
			 */
			// JSONArray arr = JSONArray.parseArray(JSONArray.toJSONString(list));
			// HttpJson json =
			// HttpClientUtil.easemobPost2(serverPort+"?access_token="+token+"&userid="+userid+"&ownercode="+ownercode+"&cmd="+20,token,arr);
			// if(!json.isSuccess()) {
			// throw new ServiceException(json.getMsg());
			// }
		}
	}

	/**
	 * 同步端口信息
	 *
	 * @param inData
	 * @param status
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public Json sendMsg(EquipmentFacInData inData, Integer status) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		List<SdInterfaceOutData> interfaceList = new ArrayList<SdInterfaceOutData>();
		SdInterfaceOutData device = utUnitNetdeviceMapper.getDeviceMsg(inData.getNetdeviceid());
		List<EquipmentRTUInData> list2 = inData.getList();
		List<EquipmentThreeInData> portList = inData.getPortLists();
		String ownerCode = device.getOwnerCode();
		Integer deviceNo = device.getDeviceNo();
		if (inData.getActionType() == 1) {
			SdInterfaceOutData sdInterface = new SdInterfaceOutData();
			if (Util.isEmpty(inData.getDetailid())) {
				sdInterface.setOwnerCode(ownerCode);
				sdInterface.setDeviceNo(deviceNo);
				sdInterface.setIOType(2);
				sdInterface.setIOPort(Integer.parseInt(inData.getIoport()));
				sdInterface.setGoodName(inData.getGoodname());
				sdInterface.setBadName(inData.getBadname());
				sdInterface.setDigitalNormal(Integer.parseInt(inData.getDigitalnormal()));
				sdInterface.setIOName(inData.getReserve());
				sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
				sdInterface.setReserve(inData.getDetialname().substring(inData.getDetialname().indexOf("(") + 1,
						inData.getDetialname().length() - 1));
				if (status == -1) {
					sdInterface.setStatus(status);
				}
				interfaceList.add(sdInterface);
				log.error(interfaceList.toString());
			}
		}
		if (inData.getActionType() == 2) {
			SdInterfaceOutData sdInterface = new SdInterfaceOutData();
			sdInterface.setOwnerCode(ownerCode);
			sdInterface.setDeviceNo(deviceNo);
			sdInterface.setIOType(1);
			sdInterface.setIOPort(Integer.parseInt(inData.getIoport()));
			sdInterface.setAnalogUp(Double.parseDouble(inData.getAnalogup()));
			if (Util.isEmpty(inData.getAnalogk())) {
				inData.setAnalogk("1");
			}
			if (Util.isEmpty(inData.getAnalogb())) {
				inData.setAnalogb("0");
			}
			sdInterface.setAnalogPara(inData.getAnalogk() + "," + inData.getAnalogb());
			sdInterface.setAnalogDown(Double.parseDouble(inData.getAnalogdown()));
			sdInterface.setIOName(inData.getReserve());
			sdInterface.setReserve(inData.getDetialname().substring(inData.getDetialname().indexOf("(") + 1,
					inData.getDetialname().length() - 1));
			sdInterface.setAnalogUnitID(inData.getAnalogunit());
			sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
			if (status == -1) {
				sdInterface.setStatus(status);
			}
			interfaceList.add(sdInterface);
		}
		if (inData.getActionType() == 3) {
//			List<EquipmentRTUInData> list2 = (List<EquipmentRTUInData>) JSONArray.parseArray(lists,
//					EquipmentRTUInData.class);
			for (EquipmentRTUInData list : list2) {
				SdInterfaceOutData sdInterface = new SdInterfaceOutData();
				sdInterface.setOwnerCode(ownerCode);
				sdInterface.setDeviceNo(deviceNo);
				sdInterface.setIOType(2);
				sdInterface.setIOPort(list.getIoport());
				sdInterface.setGoodName(list.getGoodname());
				sdInterface.setBadName(list.getBadname());
				sdInterface.setDigitalNormal(list.getDigitalnormal());
				sdInterface.setIOName(list.getReserve());
				sdInterface.setReserve(list.getDetailname().substring(list.getDetailname().indexOf("(") + 1,
						list.getDetailname().length() - 1));
				sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
				if (status == -1) {
					sdInterface.setStatus(status);
				}
				interfaceList.add(sdInterface);
				log.error(interfaceList.toString());
			}
		}
		if (inData.getActionType() == 4) {
//			List<EquipmentThreeInData> list = (List<EquipmentThreeInData>) JSONArray.parseArray(portList,
//					EquipmentThreeInData.class);
			for (EquipmentThreeInData in : portList) {
				SdInterfaceOutData sdInterface = new SdInterfaceOutData();
				sdInterface.setOwnerCode(ownerCode);
				sdInterface.setDeviceNo(deviceNo);
				sdInterface.setIOType(1);
				sdInterface.setIOPort(in.getIoport());
				sdInterface.setAnalogUp(Double.parseDouble(in.getAnalogup()));
				if (Util.isEmpty(inData.getAnalogk())) {
					inData.setAnalogk("1");
				}
				if (Util.isEmpty(inData.getAnalogb())) {
					inData.setAnalogb("0");
				}
				sdInterface.setAnalogPara(in.getAnalogk() + "," + in.getAnalogb());
				sdInterface.setAnalogDown(Double.parseDouble(in.getAnalogdown()));
				sdInterface.setAnalogUnitID(in.getAnalogunit());
				sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
				sdInterface.setIOName(in.getReserve());
				sdInterface.setReserve(in.getDetialname().substring(in.getDetialname().indexOf("(") + 1,
						in.getDetialname().length() - 1));
				if (status == -1) {
					sdInterface.setStatus(status);
				}
				interfaceList.add(sdInterface);
				log.error(interfaceList.toString());
			}
		}
		log.error("interfaceList:" + interfaceList.toString());
		// 同步数据

		// JSONArray arr = JSONArray.parseArray(JSONArray.toJSONString(interfaceList));
		Json json = HttpClientUtil.doPost(
				properties.getRedServerPort() + "NSyncCommand?access_token=" + properties.getAccessToken() + "&userid=" + userid + "&ownercode="
						+ ownerCode + "&cmd=" + 20,
				JSONArray.toJSONString(interfaceList, SerializerFeature.WRITE_MAP_NULL_FEATURES));
		log.error(JSONArray.toJSONString(interfaceList, SerializerFeature.WRITE_MAP_NULL_FEATURES));
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
			throw new ServiceException("请求发送失败！");
		}

		return json;
	}

	@SuppressWarnings("unchecked")
	public Json sendMsg1(EquipmentFacInDataUpdate inData, Integer status) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		List<SdInterfaceOutData> interfaceList = new ArrayList<SdInterfaceOutData>();
		SdInterfaceOutData device = utUnitNetdeviceMapper.getDeviceMsg(inData.getNetdeviceid());
		String lists = inData.getList();
		String portList = inData.getPortLists();
		String ownerCode = device.getOwnerCode();
		Integer deviceNo = device.getDeviceNo();
		if (inData.getActionType() == 1) {
			SdInterfaceOutData sdInterface = new SdInterfaceOutData();
			if (Util.isEmpty(inData.getDetailid())) {
				sdInterface.setOwnerCode(ownerCode);
				sdInterface.setDeviceNo(deviceNo);
				sdInterface.setIOType(2);
				sdInterface.setIOPort(Integer.parseInt(inData.getIoport()));
				sdInterface.setGoodName(inData.getGoodname());
				sdInterface.setBadName(inData.getBadname());
				sdInterface.setDigitalNormal(Integer.parseInt(inData.getDigitalnormal()));
				sdInterface.setIOName(inData.getReserve());
				sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
				sdInterface.setReserve(inData.getDetialname().substring(inData.getDetialname().indexOf("(") + 1,
						inData.getDetialname().length() - 1));
				if (status == -1) {
					sdInterface.setStatus(status);
				}
				interfaceList.add(sdInterface);
				log.error(interfaceList.toString());
			}
		}
		if (inData.getActionType() == 2) {
			SdInterfaceOutData sdInterface = new SdInterfaceOutData();
			sdInterface.setOwnerCode(ownerCode);
			sdInterface.setDeviceNo(deviceNo);
			sdInterface.setIOType(1);
			sdInterface.setIOPort(Integer.parseInt(inData.getIoport()));
			sdInterface.setAnalogUp(Double.parseDouble(inData.getAnalogup()));
			if (Util.isEmpty(inData.getAnalogk())) {
				inData.setAnalogk("1");
			}
			if (Util.isEmpty(inData.getAnalogb())) {
				inData.setAnalogb("0");
			}
			sdInterface.setAnalogPara(inData.getAnalogk() + "," + inData.getAnalogb());
			sdInterface.setAnalogDown(Double.parseDouble(inData.getAnalogdown()));
			sdInterface.setIOName(inData.getReserve());
			sdInterface.setReserve(inData.getDetialname().substring(inData.getDetialname().indexOf("(") + 1,
					inData.getDetialname().length() - 1));
			sdInterface.setAnalogUnitID(inData.getAnalogunit());
			sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
			if (status == -1) {
				sdInterface.setStatus(status);
			}
			interfaceList.add(sdInterface);
		}
		if (inData.getActionType() == 3) {
			List<EquipmentRTUInData> list2 = (List<EquipmentRTUInData>) JSONArray.parseArray(lists,
					EquipmentRTUInData.class);
			for (EquipmentRTUInData list : list2) {
				boolean isEmpty = Util.isEmpty(list.getIoport()) && Util.isEmpty(list.getIotype()) && Util.isEmpty(list.getDetailname()
				) && Util.isEmpty(list.getBadname()) && Util.isEmpty(list.getGoodname()) && Util.isEmpty(list.getDigitalnormal())
						&& Util.isEmpty(list.getEqid()) && Util.isEmpty(list.getReserve()) && Util.isEmpty(list.getRemark());
				if (!isEmpty) {
					SdInterfaceOutData sdInterface = new SdInterfaceOutData();
					sdInterface.setOwnerCode(ownerCode);
					sdInterface.setDeviceNo(deviceNo);
					sdInterface.setIOType(2);
					sdInterface.setIOPort(list.getIoport());
					sdInterface.setGoodName(list.getGoodname());
					sdInterface.setBadName(list.getBadname());
					sdInterface.setDigitalNormal(list.getDigitalnormal());
					sdInterface.setIOName(list.getReserve());
					sdInterface.setReserve(list.getDetailname().substring(list.getDetailname().indexOf("(") + 1,
							list.getDetailname().length() - 1));
					sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
					if (status == -1) {
						sdInterface.setStatus(status);
					}
					interfaceList.add(sdInterface);
					log.error(interfaceList.toString());
				}
			}
		}
		if (inData.getActionType() == 4) {
			List<EquipmentThreeInData> list = (List<EquipmentThreeInData>) JSONArray.parseArray(portList,
					EquipmentThreeInData.class);
			for (EquipmentThreeInData in : list) {
				boolean isEmpty = Util.isEmpty(in.getIoport()) && Util.isEmpty(in.getIotype()) && Util.isEmpty(in.getDetialname()
				) && Util.isEmpty(in.getMaxValue()) && Util.isEmpty(in.getMinValue()) && Util.isEmpty(in.getDigitalnormal())
						&& Util.isEmpty(in.getReserve()) && Util.isEmpty(in.getAnalogb()) && Util.isEmpty(in.getAnalogup())
						&& Util.isEmpty(in.getAnalogunit()) && Util.isEmpty(in.getAnalogk()) && Util.isEmpty(in.getAnalogdown());
				if (!isEmpty) {
					SdInterfaceOutData sdInterface = new SdInterfaceOutData();
					sdInterface.setOwnerCode(ownerCode);
					sdInterface.setDeviceNo(deviceNo);
					sdInterface.setIOType(1);
					sdInterface.setIOPort(in.getIoport());
					sdInterface.setAnalogUp(Double.parseDouble(in.getAnalogup()));
					if (Util.isEmpty(inData.getAnalogk())) {
						inData.setAnalogk("1");
					}
					if (Util.isEmpty(inData.getAnalogb())) {
						inData.setAnalogb("0");
					}
					sdInterface.setAnalogPara(in.getAnalogk() + "," + in.getAnalogb());
					sdInterface.setAnalogDown(Double.parseDouble(in.getAnalogdown()));
					sdInterface.setAnalogUnitID(in.getAnalogunit());
					sdInterface.setSysType(Integer.parseInt(inData.getEqsystemid()));
					sdInterface.setIOName(in.getReserve());
					sdInterface.setReserve(in.getDetialname().substring(in.getDetialname().indexOf("(") + 1,
							in.getDetialname().length() - 1));
					if (status == -1) {
						sdInterface.setStatus(status);
					}
					interfaceList.add(sdInterface);
					log.error(interfaceList.toString());
				}

			}
		}
		log.error("interfaceList:" + interfaceList.toString());
		// 同步数据

		// JSONArray arr = JSONArray.parseArray(JSONArray.toJSONString(interfaceList));
		Json json = HttpClientUtil.doPost(
				properties.getRedServerPort() + "NSyncCommand?access_token=" + properties.getAccessToken() + "&userid=" + userid + "&ownercode="
						+ ownerCode + "&cmd=" + 20,
				JSONArray.toJSONString(interfaceList, SerializerFeature.WRITE_MAP_NULL_FEATURES));
		log.error(JSONArray.toJSONString(interfaceList, SerializerFeature.WRITE_MAP_NULL_FEATURES));
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
			throw new ServiceException("请求发送失败！");
		}

		return json;
	}

	@Override
	public PageInfo<InterfaceOutData> getInterfaceOutList(String deviceId) throws Exception {
		UtEqEquipment equipment = utEqEquipmentMapper.selectByPrimaryKey(Long.valueOf(deviceId));
		UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(equipment.getNetdeviceid());
		String ownercode = netdevice.getOwnercode();
		Integer deviceno = netdevice.getDeviceno();
		// String deviceIndex = "3";// RTU
		List<InterfaceOutData> list = utUnitNetdeviceMapper.getInterfaceOutList(databaseName, ownercode, deviceno);
		return new PageInfo<>(list);
	}

	@Override
	public void updateInterfaceOut(InterfaceOutData indata) throws Exception {
		indata.setDatabase(databaseName);
		String id = indata.getId();

		UtEqEquipment equipment = utEqEquipmentMapper.selectByPrimaryKey(Long.valueOf(indata.getNetDeviceId()));
		UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(equipment.getNetdeviceid());
		String ownercode = netdevice.getOwnercode();
		Integer deviceno = netdevice.getDeviceno();
		indata.setOwnerCode(ownercode);
		indata.setDeviceIndex(3);
		indata.setDeviceNo(deviceno);
		// 校验接口是否唯一
		if (!checkUnique(indata)) {
			throw new ServiceException("端口重复");
		}

		String lock = indata.getLocks();
		if (Util.isNotEmpty(lock)) {
			indata.setLocked("1".equals(lock));
		} else {
			indata.setLocked(false);
		}


		if (Util.isEmpty(id)) {// 新增
			utUnitNetdeviceMapper.addInterfaceOut(indata);
		} else {
			// 更新
			utUnitNetdeviceMapper.updateInterfaceOut(indata);
		}
	}

	private boolean checkUnique(InterfaceOutData indata) throws Exception {
		String ownercode = indata.getOwnerCode();
		Integer deviceno = indata.getDeviceNo();
		List<InterfaceOutData> list = utUnitNetdeviceMapper.getInterfaceOutList(databaseName, ownercode, deviceno);
		Integer ioPort = indata.getIoPort();
		String id = indata.getId();
		for (InterfaceOutData interfaceOutData : list) {
			Integer ioPort2 = interfaceOutData.getIoPort();
			if (ioPort.equals(ioPort2)) {
				if (Util.isEmpty(id)) {
					return false;
				}
				if (!id.equals(interfaceOutData.getId())) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public void deleteInterfaceOut(String id) throws Exception {
		utUnitNetdeviceMapper.deleteInterfaceOut(id, databaseName);

	}

	@Override
	public void importData(List<InterfaceOutImportData> interfaceImportDatas, String deviceId) throws Exception {
		for (InterfaceOutImportData interfaceOutImportData : interfaceImportDatas) {
			InterfaceOutData indata = new InterfaceOutData();
			BeanUtils.copyProperties(interfaceOutImportData, indata);
			indata.setNetDeviceId(deviceId);
			indata.setIoPort(Integer.parseInt(interfaceOutImportData.getIoPort()));
			indata.setDigitalNormal(Integer.parseInt(interfaceOutImportData.getDigitalNormal()));
			String locks = indata.getLocks();
			if (Util.isNotEmpty(locks)) {
				indata.setLocks("是".equals(locks) ? "1" : "0");
			}
			String fotActionUnit = indata.getFotActionUnit();
			if (Util.isNotEmpty(fotActionUnit)) {
				switch (fotActionUnit) {
					case "1ms":
						fotActionUnit = "1";
						break;
					case "100ms":
						fotActionUnit = "2";
						break;
					case "1s":
						fotActionUnit = "3";
					case "100s":
						fotActionUnit = "6";
						break;
					case "1min":
						fotActionUnit = "7";
						break;
					case "100min":
						fotActionUnit = "8";
						break;
					default:
						fotActionUnit = "";
						break;
				}
			}
			indata.setFotActionUnit(fotActionUnit);
			updateInterfaceOut(indata);
		}
	}

	@Override
	public EquipmentFacOutData getBuildImgbg(Long id) {
		return utEqEquipmentMapper.getBuildImgbg(id);
	}

	@Override
	public void delete(String eqId, String detailid, String netDevcieId) throws Exception {
		int i = utEqEquipmentdetialgwMapper.getPortList(eqId).size();
		if (i == 1) {
			UtEqEquipment utEqEquipment = utEqEquipmentMapper.selectByPrimaryKey(Long.valueOf(eqId));
			utEqEquipment.setIsdelete(1);
			utEqEquipmentdetialgwMapper.deleteByPrimaryKey(Long.valueOf(detailid));
			utEqEquipmentMapper.updateByPrimaryKeySelective(utEqEquipment);
		} else {
			utEqEquipmentdetialgwMapper.deleteByPrimaryKey(Long.valueOf(detailid));
		}
	}
}
