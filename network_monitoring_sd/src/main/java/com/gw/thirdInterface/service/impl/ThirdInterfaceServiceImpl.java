package com.gw.thirdInterface.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.gw.alarm.data.AlarmBJZJOutData;
import com.gw.alarm.data.AlarmRTUOutData;
import com.gw.common.Json;
import com.gw.device.data.NetDeviceOutData;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.FrontCoupletInData;
import com.gw.front.couplet.data.FrontInterFaceStatusOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.unit.data.FrontUnitCRTOutData;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.systemManager.data.CodeOutData;
import com.gw.thirdInterface.data.*;
import com.gw.thirdInterface.service.ThirdInterfaceService;
import com.gw.unit.data.UnitBaseInfoInData;
import com.gw.unit.data.UnitBaseInfoOutData;
import com.gw.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URLEncoder;
import java.util.*;

@Service
public class ThirdInterfaceServiceImpl implements ThirdInterfaceService {
	private static Logger log = LoggerFactory.getLogger(ThirdInterfaceServiceImpl.class);
	@Value("${raw.data.database}")
	private String database;
	@Value("${AX.status}")
	private Integer status;
	// private static String XMHUrl = "http://120.55.78.242:9085/zjintertest/api";
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Resource
	private UtEqAddressRelMapper utEqAddressRelMapper;
	@Resource
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;
	@Resource
	private UtBaseProvicecityregionMapper utBaseProvicecityregionMapper;
	@Autowired
	private UtBaseCodegroupMapper utBaseCodegroupMapper;

	@Override
	public void setOrg(String unitID, String companyName) throws Exception {
		UnitBaseInfoInData data = new UnitBaseInfoInData();
		data.setId(unitID);
		List<UnitBaseInfoOutData> baseData = utUnitBaseinfoMapper.selectAllUnitBaseInfo(data);
		List<CodeOutData> codeData = utBaseCodegroupMapper.getListByGroupKey("25");// 单位类型code列表
		Map<Long, UtBaseProvicecityregion> areaMap = new HashMap<>();
		Map<String, String> areaJDMap = new HashMap<>();
		List<UtBaseProvicecityregion> areaList = utBaseProvicecityregionMapper.selectAll();
		for (UtBaseProvicecityregion utBaseProvicecityregion : areaList) {
			areaMap.put(utBaseProvicecityregion.getId(), utBaseProvicecityregion);
			if (utBaseProvicecityregion.getType() == 4) {
				areaJDMap.put(utBaseProvicecityregion.getRegionname(), utBaseProvicecityregion.getRegioncode());
			}
		}

		UtUnitBaseinfo base = null;
		for (UnitBaseInfoOutData unit : baseData) {
			base = new UtUnitBaseinfo();
			base.setId(Long.parseLong(unit.getId()));
			if ("A".equals(companyName)) {// 新门海
				base.setIsuploada(1);
				encapsulationXMHUnit(unit, areaMap, areaJDMap, unitID);
			} else {// 安讯
				base.setIsuploadb(1);
				encapsulationAXUnit(unit, unitID, areaMap, areaJDMap, codeData);
			}
			if (Util.isEmpty(unit.getIsuploada()) || Util.isEmpty(unit.getIsuploadb())) {
				utUnitBaseinfoMapper.updateByPrimaryKeySelective(base);
			}
		}
	}

	/**
	 * 封装安迅单位信息并发送
	 *
	 * @param unit
	 * @throws Exception
	 */
	public void encapsulationAXUnit(UnitBaseInfoOutData unit, String unitID, Map<Long, UtBaseProvicecityregion> areaMap,
									Map<String, String> areaJDMap, List<CodeOutData> codeData) throws Exception {
		if (Util.isEmpty(unitID) && Util.isNotEmpty(unit.getIsuploadb())) {// 上传所有未上传的单位(或者上传单个单位)
			return;
		}
		AXUnitSynchronizationOutData outData = new AXUnitSynchronizationOutData();
		if (Util.isNotEmpty(unit.getIsuploadb())) {
			outData.setType(2);// 修改
		} else {
			outData.setType(1);// 新增
		}
		String proviceid = unit.getProviceid();
		if (Util.isNotEmpty(proviceid)) {
			outData.setProvince(areaMap.get(Long.valueOf(proviceid)).getRegioncode());
		}
		String cityid = unit.getCityid();
		if (Util.isNotEmpty(cityid)) {
			outData.setCity(areaMap.get(Long.valueOf(cityid)).getRegioncode());
		}
		String regionid = unit.getRegionid();
		if (Util.isNotEmpty(regionid)) {
			outData.setArea(areaMap.get(Long.valueOf(regionid)).getRegioncode());
		}
		String unitpoint = unit.getUnitpoint();
		outData.setLat("");
		outData.setLng("");
		if (Util.isNotEmpty(unitpoint)) {
			String[] split = unitpoint.split(",");
			outData.setLng(split[0]);
			outData.setLat(split[1]);
		}
		outData.setAddress(unit.getUnitaddress());
		outData.setName(unit.getUnitname());
		outData.setCode(unit.getId());
		for (CodeOutData code : codeData) {
			if (code.getCodeid().equals(unit.getUnittype())) {
				outData.setOrgtype(code.getCodevalue());
			}
		}
		outData.setAdminor_phone(unit.getPhone());
		outData.setAdminor(unit.getLegalpersonname());
		String token = HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL);
		log.error("同步安迅数据：" + outData.toString());
		log.error("安迅token：" + token);
		if (Util.isNotEmpty(token)) {


			Json json = HttpClientUtil.thirdPost(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/setOrg",token,
					JSONObject.toJSONString(outData, SerializerFeature.WriteMapNullValue));
			analyticalData(json, "B");
		}
	}

	/**
	 * 封装新门海单位信息
	 *
	 * @param unit
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void encapsulationXMHUnit(UnitBaseInfoOutData unit, Map<Long, UtBaseProvicecityregion> areaMap,
									 Map<String, String> areaJDMap, String unitID) throws Exception {
		if (Util.isEmpty(unitID) && Util.isNotEmpty(unit.getIsuploada())) {// 上传所有未上传的单位(或者上传单个单位)
			return;
		}
		XMHUnitSynchronizationOutData outData = new XMHUnitSynchronizationOutData();
		String proviceid = unit.getProviceid();
		outData.setFprovince_code("330000");// 浙江省
		if (Util.isNotEmpty(proviceid)) {
			outData.setFprovince_code(areaMap.get(Long.valueOf(proviceid)).getRegioncode());
		}
		String cityid = unit.getCityid();
		outData.setFcity_code("330700");// 金华市
		if (Util.isNotEmpty(cityid)) {
			outData.setFcity_code(areaMap.get(Long.valueOf(cityid)).getRegioncode());
		}
		String regionid = unit.getRegionid();
		outData.setFcounty_code("330782");// 义务市
		if (Util.isNotEmpty(regionid)) {
			outData.setFcounty_code(areaMap.get(Long.valueOf(regionid)).getRegioncode());
		}
		String unitaddress = unit.getUnitaddress();
		outData.setFaddress("");
		outData.setFtown_code("330782100000");// 佛堂镇
		if (Util.isNotEmpty(unitaddress)) {
			outData.setFaddress(unitaddress);
			Set<String> keySet = areaJDMap.keySet();
			for (String string : keySet) {
				if (unitaddress.indexOf(string) != -1) {
					outData.setFtown_code(areaJDMap.get(string));
				}
			}
		}
		outData.setFtel_no("");
		if (Util.isNotEmpty(unit.getPhone())) {
			outData.setFtel_no(unit.getPhone());
		}
		outData.setFlink_man("");
		if (Util.isNotEmpty(unit.getLegalpersonname())) {
			outData.setFlink_man(unit.getLegalpersonname());
		}
		outData.setFsocial_uuid(unit.getId());
		outData.setFsocial_name(getNullValue(unit.getUnitname()));
		String unitpoint = unit.getUnitpoint();
		outData.setFlongitude("");
		outData.setFlatitude("");
		if (Util.isNotEmpty(unitpoint)) {
			String[] split = unitpoint.split(",");
			outData.setFlongitude(split[0]);
			outData.setFlatitude(split[1]);
		}
		outData.setFunit_type("");
		if (Util.isNotEmpty(unit.getUnittypeValue())) {
			outData.setFunit_type(unit.getUnittypeValue());
		}
		outData.setFis_active("1");
		String token = HttpGetXMHToken.getToken(ReqApiConst.GET_XINMENHAI_GETTOKEN_URL);


		if (Util.isNotEmpty(token)) {
			String url = ReqApiConst.GET_XINMENHAI_GETTOKEN_URL + "?action=obj.interface&method=cagSocialInfo&token=" + token + "&transdata="
					+ URLEncoder.encode(JSONObject.toJSONString(outData, SerializerFeature.WriteMapNullValue), "UTF-8");

			HttpJson json = HttpClientUtil.doPost(url);
			if (json.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getMsg());
				String reCode = (String) map.get("ret_code");
				if (!"1".equals(reCode)) {
					log.error("同步新门海单位失败：" + (String) map.get("data"));
					throw new ServiceException((String) map.get("data"));
				}
			} else {
				log.error("同步新门海单位doPost failed!");
				throw new ServiceException("同步新门海单位doPost failed!");

			}
		}
	}

	@Override
	public void setDevice(String netDeviceID, String unitID, String companyName) throws Exception {
		FrontHistoryInData inData = new FrontHistoryInData();
		inData.setUnitId(unitID);
		inData.setDatabase(database);
		inData.setUserId("1");
		inData.setNetDeviceId(netDeviceID);
		List<FrontHisSDDeviceStatusOutData> deviceList = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
		UtUnitNetdevice utUnitNetdevice = null;
		for (FrontHisSDDeviceStatusOutData device : deviceList) {
			utUnitNetdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(device.getId()));
			if ("A".equals(companyName)) {// 新门海
				utUnitNetdevice.setIsuploada(1);
				encapsulationXMHDevice(device, netDeviceID);
			} else {// 安讯
				utUnitNetdevice.setIsuploadb(1);
				encapsulationAXDevice(device, netDeviceID);
			}
			if (Util.isEmpty(device.getIsuploadb()) || Util.isEmpty(device.getIsuploada())) {
				utUnitNetdeviceMapper.updateByPrimaryKeySelective(utUnitNetdevice);
			}
		}
	}

	/**
	 * 封装安迅设备信息并发送
	 *
	 * @param device
	 * @throws Exception
	 */
	public void encapsulationAXDevice(FrontHisSDDeviceStatusOutData device, String netDeviceID) throws Exception {
		if (Util.isEmpty(netDeviceID) && Util.isNotEmpty(device.getIsuploadb())) {// 上传所有未上传设备或者上传单个设备
			return;
		}
		AXDeviceSynchronizationOutData outData = new AXDeviceSynchronizationOutData();
		if (Util.isNotEmpty(device.getIsuploadb())) {
			outData.setType(2);// 修改
		} else {
			outData.setType(1);// 新增
		}
		outData.setUuid(device.getId());
		outData.setOrgcode(device.getUnitID());
		outData.setName(device.getDeviceName());
		if (!"3".equals(device.getDeviceIndex())) {
			outData.setCategory("alarm");
			outData.setSub_category("alarmserver");
		} else {
			outData.setCategory("unclassified");
			outData.setSub_category("unclassified");
		}
		if ("在线".equals(device.getDeviceStatus())) {
			outData.setIsonline(0);
		} else {
			outData.setIsonline(1);
		}

		String token = HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL);


		if (Util.isNotEmpty(token)) {
			Json json = HttpClientUtil.thirdPost(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/setDevice",token,
					JSONObject.toJSONString(outData, SerializerFeature.WriteMapNullValue));


			analyticalData(json, "B");
			UtUnitNetdevice devices = new UtUnitNetdevice();
			devices.setIsuploadb(1);
			devices.setId(Long.parseLong(device.getId()));
			utUnitNetdeviceMapper.updateByPrimaryKeySelective(devices);
		}
	}

	/**
	 * 封装新门海设备信息并发送
	 *
	 * @param device
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void encapsulationXMHDevice(FrontHisSDDeviceStatusOutData device, String netDeviceID) throws Exception {
		if (Util.isEmpty(netDeviceID) && Util.isNotEmpty(device.getIsuploada())) {// 上传所有未上传设备或者上传单个设备
			return;
		}
		XMHPointSynchronizationOutData outData = new XMHPointSynchronizationOutData();
		if (Util.isNotEmpty(device.getIsuploada())) {
			outData.setFlag(2);// 修改
		} else {
			outData.setFlag(1);// 新增
		}
		outData.setFsocial_uuid(device.getUnitID());
		outData.setFtransmission_id(device.getId());
		outData.setFtransmission_name(getNullValue(device.getDeviceName()));
		outData.setFcontrhost_code(getNullValue(device.getOwnerCode()));
		if (device.getDeviceType().equals("RTU")) {
			outData.setFmonitorpositon_type("G5201");
		} else {
			outData.setFmonitorpositon_type("G5207");
		}
		outData.setFtotal_high("0");
		outData.setFvalue_down("0");
		outData.setFvalue_up("0");
		// outData.setFdevice_uuid(device.getId());
		// outData.setFdevicecnname(device.getDeviceName());
		// outData.setFdeviceclass(device.getDeviceType());
		// outData.setFloop_num(device.getChildstationnum());
		// outData.setFdeviceaddress(device.getRemark());
		// outData.setFposition(device.getManufacturerCode());
		// outData.setFis_active("1");
		String token = HttpGetXMHToken.getToken(ReqApiConst.GET_XINMENHAI_GETTOKEN_URL);


		/*
		 * log.error("新门海同步设备完整请求(未编码)："+XMHUrl+
		 * "?action=obj.interface&method=cagMonitorPointInfo&token="+HttpGetXMHToken.
		 * getToken()+ "&transdata="+JSONObject.toJSONString(outData,SerializerFeature.
		 * WriteMapNullValue));
		 */
		if (Util.isNotEmpty(token)) {
			String url = ReqApiConst.GET_XINMENHAI_GETTOKEN_URL + "?action=obj.interface&method=cagMonitorPointInfo&token=" + token + "&transdata="
					+ URLEncoder.encode(JSONObject.toJSONString(outData, SerializerFeature.WriteMapNullValue), "UTF-8");


			HttpJson json = HttpClientUtil.doPost(url);
			if (json.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getMsg());
				String reCode = (String) map.get("ret_code");
				if (!"1".equals(reCode)) {
					log.error("同步新门海设备失败：" + (String) map.get("ret_msg"));
					throw new ServiceException((String) map.get("ret_msg"));
				}
			} else {
				log.error("同步新门海设备doPost failed!");
				throw new ServiceException("同步新门海设备doPost failed!");
			}
		}
	}

	@Override
	public void setPoint(String pointID, String isFire, String unitID) throws Exception {
		List<FrontUnitCRTOutData> bjzjPointList = new ArrayList<FrontUnitCRTOutData>();// 报警主机点位
		List<FrontInterFaceStatusOutData> rtuPointList = new ArrayList<FrontInterFaceStatusOutData>();// RTU点位
		if (Util.isNotEmpty(pointID)) {// 单个点位上传
			if ("1".equals(isFire)) {// 报警主机点位列表
				bjzjPointList = utEqAddressRelMapper.getCRTList(pointID, null);
			} else {// RTU报警点位
				FrontCoupletInData inData = new FrontCoupletInData();
				inData.setPointID(pointID);
				rtuPointList = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
			}
			for (FrontUnitCRTOutData point : bjzjPointList) {// 循环同步报警主机点位
				encapsulationBJZJPoint(point);
			}
			for (FrontInterFaceStatusOutData point : rtuPointList) {// 循环同步RTU点位
				encapsulationRTUPoint(point);
			}
		} else {// 上传所有设备
			if ("1".equals(isFire)) {// 报警主机点位列表
				bjzjPointList = utEqAddressRelMapper.getCRTList(null, unitID);
			} else {// RTU报警点位
				FrontCoupletInData inData = new FrontCoupletInData();
				inData.setUnitId(unitID);
				rtuPointList = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
			}
			for (FrontUnitCRTOutData point : bjzjPointList) {// 循环同步未上传的报警主机点位
				if (Util.isEmpty(point.getIsuploada())) {
					encapsulationBJZJPoint(point);
				}
			}
			for (FrontInterFaceStatusOutData point : rtuPointList) {// 循环同步未上传的RTU点位
				if (Util.isEmpty(point.getIsuploada())) {
					encapsulationRTUPoint(point);
				}
			}
		}
	}

	/**
	 * 定时同步设备上下线状态
	 *
	 * @throws Exception
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	public void setStatus() throws Exception {
		if (1 == status) {
			FrontHistoryInData inData = new FrontHistoryInData();
			inData.setDatabase(database);
			inData.setUserId("1");
			List<FrontHisSDDeviceStatusOutData> deviceList = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
			for (FrontHisSDDeviceStatusOutData device : deviceList) {
				if (1 == device.getIsuploadb()) {
					setDeviceStatus(null, device.getId());
				}
			}
		}
	}

	@Override
	public void setDeviceStatus(String unitId, String netDeviceId) throws Exception {
		List<DeviceStatusOutData> list = null;
		if (Util.isNotEmpty(netDeviceId)) {
			list = utUnitNetdeviceMapper.getNetDeviceStatus(null, netDeviceId, database);
		} else if (Util.isNotEmpty(unitId)) {
			list = utUnitNetdeviceMapper.getNetDeviceStatus(unitId, null, database);
		} else {
			list = utUnitNetdeviceMapper.getNetDeviceStatus(null, null, database);
		}
		for (DeviceStatusOutData deviceStatusOutData : list) {


			Json json = HttpClientUtil.thirdPost(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/deviceStatus", HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL),
					JSONObject.toJSONString(deviceStatusOutData, SerializerFeature.WriteMapNullValue));
			analyticalData(json, "B");
		}
	}

	/**
	 * 封装新门海点位信息并发送（报警主机）
	 *
	 * @param point
	 * @throws Exception
	 */
	////////////////////////////
	@SuppressWarnings("unchecked")
	public void encapsulationBJZJPoint(FrontUnitCRTOutData point) throws Exception {
		XMHDeviceSynchronizationOutData outData = new XMHDeviceSynchronizationOutData();
		if (Util.isNotEmpty(point.getIsuploada())) {
			outData.setFlag(2);// 修改
		} else {
			outData.setFlag(1);// 新增
		}
		outData.setFsocial_uuid(point.getUnitID());
		outData.setFdevice_uuid(point.getId());
		outData.setFdevicecnname(getNullValue(point.getName()));
		outData.setFdeviceclass("SBTYPE99");
		outData.setFtransmission_id(point.getNetdeviceid());
		outData.setFcontrhost_code(getNullValue(point.getOwnercode()));
		outData.setFloop_num(getNullValue(point.getPartcode()));
		outData.setFdeviceaddress(getNullValue(point.getPartcode()));
		outData.setFposition(getNullValue(point.getAdress()));
		outData.setFis_active("1");

		String token = HttpGetXMHToken.getToken(ReqApiConst.GET_XINMENHAI_GETTOKEN_URL);


		/*
		 * log.error("新门海同步监控点完整请求(未编码)："+XMHUrl+
		 * "?action=obj.interface&method=cagFireDeviceInfo&token="+HttpGetXMHToken.
		 * getToken()+ "&transdata="+JSONObject.toJSONString(outData,SerializerFeature.
		 * WriteMapNullValue));
		 */
		if (Util.isNotEmpty(token)) {
			String url = ReqApiConst.GET_XINMENHAI_GETTOKEN_URL + "?action=obj.interface&method=cagFireDeviceInfo&token=" + token + "&transdata="
					+ URLEncoder.encode(JSONObject.toJSONString(outData, SerializerFeature.WriteMapNullValue), "UTF-8");

			HttpJson json = HttpClientUtil.doPost(url);
			if (json.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getMsg());
				String reCode = (String) map.get("ret_code");
				if ("1".equals(reCode)) {
					// 同步点位isupload字段
					UtEqAddressRel utEqAddressRel = utEqAddressRelMapper
							.selectByPrimaryKey(Long.valueOf(point.getId()));
					utEqAddressRel.setIsuploada(1);
					utEqAddressRelMapper.updateByPrimaryKey(utEqAddressRel);
				} else {
					log.error("同步新门海监控点失败：" + (String) map.get("ret_msg"));
					throw new ServiceException((String) map.get("ret_msg"));
				}
			} else {
				log.error("同步新门海监控点doPost failed!");
				throw new ServiceException("同步新门海监控点doPost failed!");
			}
		}
	}

	/**
	 * 封装新门海点位信息并发送（RTU）
	 *
	 * @param point
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void encapsulationRTUPoint(FrontInterFaceStatusOutData point) throws Exception {
		XMHDeviceSynchronizationOutData outData = new XMHDeviceSynchronizationOutData();
		if (Util.isNotEmpty(point.getIsuploada())) {
			outData.setFlag(2);// 修改
		} else {
			outData.setFlag(1);// 新增
		}
		outData.setFsocial_uuid(point.getUnitID());
		outData.setFdevice_uuid(point.getInterfaceId());
		outData.setFdevicecnname(getNullValue(point.getDetialName()));
		outData.setFdeviceclass("SBTYPE99");
		outData.setFtransmission_id(point.getNetDeviceId());
		outData.setFcontrhost_code(getNullValue(point.getOwnercode()));
		outData.setFloop_num(getNullValue(point.getIoPort()));
		outData.setFdeviceaddress(getNullValue(point.getIoPort()));
		outData.setFposition(getNullValue(point.getInstallposition()));
		outData.setFis_active("1");

		String token = HttpGetXMHToken.getToken(ReqApiConst.GET_XINMENHAI_GETTOKEN_URL);


		/*
		 * log.error("新门海同步监控点完整请求(未编码)："+XMHUrl+
		 * "?action=obj.interface&method=cagFireDeviceInfo&token="+token+
		 * "&transdata="+JSONObject.toJSONString(outData,SerializerFeature.
		 * WriteMapNullValue));
		 */
		if (Util.isNotEmpty(token)) {
			String url = ReqApiConst.GET_XINMENHAI_GETTOKEN_URL + "?action=obj.interface&method=cagFireDeviceInfo&token=" + token + "&transdata="
					+ URLEncoder.encode(JSONObject.toJSONString(outData, SerializerFeature.WriteMapNullValue), "UTF-8");

			HttpJson json = HttpClientUtil.doPost(url);
			if (json.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getMsg());
				String reCode = (String) map.get("ret_code");
				if ("1".equals(reCode)) {
					// 同步点位isupload字段
					UtEqEquipmentdetialgw utEqEquipmentdetialgw = utEqEquipmentdetialgwMapper
							.selectByPrimaryKey(Long.valueOf(point.getInterfaceId()));
					utEqEquipmentdetialgw.setIsuploada(1);
					utEqEquipmentdetialgwMapper.updateByPrimaryKey(utEqEquipmentdetialgw);
				} else {
					log.error("同步新门海监控点失败：" + (String) map.get("ret_msg"));
					throw new ServiceException((String) map.get("ret_msg"));
				}
			} else {
				log.error("同步新门海监控点doPost failed!");
				throw new ServiceException("同步新门海监控点doPost failed!");
			}
		}
	}

	/**
	 * 处理调用接口返回信息
	 *
	 * @param json
	 * @param flag//A:新门海
	 *            B:安讯
	 */
	@SuppressWarnings("unchecked")
	public void analyticalData(Json json, String flag) throws Exception {
		Boolean index = false;
		if (json.isSuccess()) {
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());

			if ("A".equals(flag)) {// 新门海解析

				String code = (String) map.get("ret_code");
				if ("1".equals(code)) {
					index = true;
				} else {
					log.error("返回信息：" + (String) map.get("ret_msg"));
					throw new ServiceException((String) map.get("msg"));
				}
			} else {// 安迅解析

				index = (Boolean) map.get("status");
				if (!index) {
					log.error("返回信息：" + (String) map.get("msg"));
					throw new ServiceException((String) map.get("msg"));
				}
			}
		} else {
			log.error("doPost/doGet failed!");
			throw new ServiceException("doPost/doGet failed!");

		}
	}

	/**
	 * 新门海平台实时报警
	 *
	 * @param inData
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void realTimeAlarmXMH(RealTimeAlarmXMHInData inData) throws Exception {
		inData.setF_type(transferAlarmStatus(inData.getF_type()));
		String token = HttpGetXMHToken.getToken(ReqApiConst.GET_XINMENHAI_GETTOKEN_URL);


		String url = ReqApiConst.GET_XINMENHAI_GETTOKEN_URL + "?action=obj.interface&method=cabFireDeviceMonitor&token=" + token + "&transdata="
				+ URLEncoder.encode(JSONObject.toJSONString(inData, SerializerFeature.WriteMapNullValue), "UTF-8");

		HttpJson json = HttpClientUtil.doPost(url);
		if (json.isSuccess()) {
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getMsg());
			String reCode = (String) map.get("ret_code");
			if (!"1".equals(reCode)) {
				log.error("新门海平台实时报警：" + (String) map.get("ret_msg"));
			}
		} else {
			log.error("新门海平台实时报警 doPost failed!");
		}
	}



	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 安讯平台实时报警
	 */
	public void realTimeAlarmAX(String uuid, String event, String milliseconds, String address) throws Exception {
		switch (event) {
			case "1":
				event = "A00100002";
				break;
			case "2":
				event = "A20000001";
				break;
			default:
				event = "A20000001";
				break;
		}




		JSONObject jsonObject = new JSONObject();
		jsonObject.put("uuid", uuid);
		jsonObject.put("event", event);
		jsonObject.put("address", address);
		if (Util.isNotEmpty(milliseconds)) {
			jsonObject.put("timestamp", Long.valueOf(milliseconds));
		}
		Json json = HttpClientUtil.thirdPost(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/pushAlarm", HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL),
				JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
		if (!(boolean) map.get("status")) {
			throw new ServiceException((String) map.get("msg"));
		}
	}

	public String transferAlarmStatus(String alarmStatus) {
		if (Util.isEmpty(alarmStatus)) {
			return null;
		}
		switch (alarmStatus) {
			case "1":
				return "G4403";
			case "2":
				return "";
			case "3":
				return "G4408";
			case "4":
				return "G4409";
			case "5":
				return "G4407";
			case "6":
				return "G4406";
			case "7":
				return "";
			case "8":
				return "G4417";
			case "9":
				return "";
			case "11":
				return "G4487";
			case "12":
				return "G4486";
			case "13":
				return "G4429";
			case "14":
				return "G4482";
			case "15":
				return "G4484";
			case "16":
				return "G4485";
			case "20":
				return "G4425";
			case "21":
				return "G4423";
			case "25":
				return "G4421";
			default:
				break;
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public void clearAlarm(String netDeviceId, String unitId) throws Exception {
		if (Util.isNotEmpty(unitId)) {
			List<NetDeviceOutData> list = utUnitNetdeviceMapper.getNetDeviceNameSelect(Long.valueOf(unitId));
			for (NetDeviceOutData netDeviceOutData : list) {

				JSONObject jsonObject = new JSONObject();
				jsonObject.put("uuid", netDeviceOutData.getId());
				Json json = HttpClientUtil.thirdPost(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/clearAlarm", HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL),
						JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
				if (!(boolean) map.get("status")) {
					throw new ServiceException((String) map.get("msg"));
				}
			}
		} else if (Util.isNotEmpty(netDeviceId)) {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("uuid", netDeviceId);

			Json json = HttpClientUtil.thirdPost(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/clearAlarm", HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL),
					JSONObject.toJSONString(jsonObject, SerializerFeature.WriteMapNullValue));
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
			if (!(boolean) map.get("status")) {
				throw new ServiceException((String) map.get("msg"));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<DictOutData> getDict(String t) throws Exception {
		List<DictOutData> list = null;

		Json json = HttpClientUtil.thirdGet(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/getDict?t=" + t, HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL));
		if (!json.isSuccess()) {
			throw new ServiceException("连接远程端口失败！");
		}
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
		if (!(boolean) map.get("status")) {
			throw new ServiceException((String) map.get("msg"));
		}
		JSONArray array = (JSONArray) map.get("data");
		list = (List<DictOutData>) JSONArray.parseArray(array.toJSONString(), DictOutData.class);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TreeOutData> getTree(String t) throws Exception {
		List<TreeOutData> list = null;

		Json json = HttpClientUtil.thirdGet(ReqApiConst.GET_ANXUN_GETTOKEN_URL + "/dock/anxun/getTree?t=" + t, HttpGetAXToken.getToken(ReqApiConst.GET_ANXUN_GETTOKEN_URL));
		if (!json.isSuccess()) {
			throw new ServiceException("连接远程端口失败！");
		}
		Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
		if (!(boolean) map.get("status")) {
			throw new ServiceException((String) map.get("msg"));
		}
		JSONArray array = (JSONArray) map.get("data");
		list = (List<TreeOutData>) JSONArray.parseArray(array.toJSONString(), TreeOutData.class);
		for (TreeOutData data : list) {
			List<TreeChildrenData> childList = (List<TreeChildrenData>) JSONArray.parseArray(data.getChildren(),
					TreeChildrenData.class);
			data.setChildList(childList);
			log.error("treeData:" + data);
		}
		return list;
	}

	@Override
	public void xmhRtuAlarm(AlarmRTUOutData data, String alarmvalue, Date alarmTime) throws Exception {
		RealTimeAlarmXMHInData realTimeAlarmXMHInData = new RealTimeAlarmXMHInData();
		realTimeAlarmXMHInData.setF_type(getNullValue(data.getEqSystemID()));
		realTimeAlarmXMHInData.setFsocial_uuid(data.getUnitID());
		realTimeAlarmXMHInData.setFdevice_uuid(data.getFireEquipmentDetialID());
		realTimeAlarmXMHInData.setFtransmission_id(data.getNetDeviceId());// 监控点标识
		realTimeAlarmXMHInData.setFcontrhost_code(getNullValue(data.getOwnercode()));// 消控主机编号
		realTimeAlarmXMHInData.setFloop_num(getNullValue(data.getPartCode()));
		realTimeAlarmXMHInData.setFdevice_address(getNullValue(data.getInstallPosition()));
		realTimeAlarmXMHInData.setFvalue(getNullValue(alarmvalue));
		realTimeAlarmXMHInData.setFcome_time(UtilConv.date2Str(alarmTime, UtilConv.DATE_TIME_PAT_14));
		realTimeAlarmXMH(realTimeAlarmXMHInData);
	}

	@Override
	public void xmhBjzjAlarm(UtLzBjzjalarm alarm, AlarmBJZJOutData data, String ownercode, FrontUnitCRTOutData crtData)
			throws Exception {
		encapsulationBJZJPoint(crtData);
		RealTimeAlarmXMHInData realTimeAlarmXMHInData = new RealTimeAlarmXMHInData();
		realTimeAlarmXMHInData.setF_type(alarm.getAlarmStatus().toString());
		realTimeAlarmXMHInData.setFsocial_uuid(data.getUnitID());
		realTimeAlarmXMHInData.setFdevice_uuid(crtData.getId());
		realTimeAlarmXMHInData.setFtransmission_id(data.getNetDeviceId());
		realTimeAlarmXMHInData.setFcontrhost_code(getNullValue(ownercode));
		realTimeAlarmXMHInData.setFloop_num(getNullValue(data.getPartcode()));
		realTimeAlarmXMHInData.setFdevice_address(getNullValue(alarm.getAlarmWheredesc()));
		realTimeAlarmXMHInData.setFvalue("");
		realTimeAlarmXMHInData.setFcome_time(UtilConv.date2Str(alarm.getLastupdate(), UtilConv.DATE_TIME_PAT_14));
		realTimeAlarmXMH(realTimeAlarmXMHInData);
	}

	private String getNullValue(String value) {
		return Util.isEmpty(value) ? "" : value;
	}
}
