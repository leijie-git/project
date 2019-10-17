package com.gw.alarm.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.alarm.data.*;
import com.gw.alarm.service.AlarmInfoService;
import com.gw.apppush.PushMsgManage;
import com.gw.common.SnowflakeIdWorker;
import com.gw.equipment.data.EquipmentFacOutData;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.service.impl.FrontCoupletServiceImpl;
import com.gw.front.index.data.FrontAlarmInData;
import com.gw.front.index.data.FrontFireInfoOutData;
import com.gw.front.index.data.FrontUnitPointOutData;
import com.gw.front.index.data.RealTimeAlarmCount;
import com.gw.front.index.service.HomeIndexService;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.data.FrontUnitCRTOutData;
import com.gw.inspect.data.InspectPlanOutData;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.thirdInterface.service.ThirdInterfaceService;
import com.gw.util.*;
import com.gw.webSocket.AlarmWebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.*;
import java.util.Map.Entry;

import static com.gw.alarm.AlarmConstants.*;

@Service
public class AlarmInfoServiceImpl implements AlarmInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoServiceImpl.class);

	@Value("${Ali.Phone.Service.Key}")
	private String accessKeyId;
	@Value("${Ali.Phone.Service.Cipter}")
	private String accessKeySecret;
	@Value("${Ali.Phone.Service.TtsCode}")
	private String ttsCode;
	@Value("${Ali.Phone.Service.Timeout}")
	private String timeout;
	@Value("${AX.status}")
	private Integer AXAlarm;
	@Value("${XMH.status}")
	private Integer XMHAlarm;
	@Value("${Ali.Phone.Service.ShowNum}")
	private String showNum;
	@Value("${Phone.Call.UnitName}")
	private String phoneUnitName;
	@Value("${raw.data.database}")
	private String database;

	@Resource
	private UtUnitBuildMapper utUnitBuildMapper;
	@Resource
	private UtUnitBuildareaMapper utUnitBuildareaMapper;
	@Resource
	private FrontCoupletServiceImpl frontCoupletServiceImpl;
	@Resource
	private PropertiesManageService propertiesManageService;
	@Resource
	private HomeIndexService homeIndexService;
	@Resource
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	@Resource
	private UtLzFireequipmentalarmHistoryMapper utLzFireequipmentalarmHistoryMapper;
	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Resource
	private UtLzBjzjalarmHistoryMapper utLzBjzjalarmHistoryMapper;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Autowired
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtMessageLogMapper utMessageLogMapper;
	@Resource
	private UtBaseEqsystemMapper utBaseEqsystemMapper;
	@Resource
	private UtBaseUserreunitMapper userreunitMapper;
	@Resource
	private ThirdInterfaceService thirdInterfaceService;
	@Resource
	private UtEqAddressRelMapper utEqAddressRelMapper;
	@Resource
	private UtEqEquipmentMapper utEqEquipmentMapper;
	@Resource
	private UtHdSiterwellMapper utHdSiterwellMapper;
	@Resource
	private UtPhoneLogMapper utPhoneLogMapper;

	@Autowired
	private UtBaseUserRelationMapper utBaseUserRelationMapper;
	private Map<String, String> sysMap;

	/**
	 * 接收RTU告警
	 */
	@Override
	public void sendRTUAlarm(HttpServletRequest request) throws Exception {

		// 解析request中的body（流读取）并 转换为对象
		String str = analysisRequest(request);
		AlarmRTUInData inData = JSONObject.parseObject(str, AlarmRTUInData.class);
		LOGGER.info("================收到RTU告警" + str);
		// 入参校验
		if (Util.isEmpty(inData.getAlarmvalue())) {
			throw new ServiceException(UtilConst.ERRO_ALARM_NULL);
		}
		// 根据设备信息查询详细信息
		AlarmRTUOutData data = utLzFireequipmentalarmMapper.getMsgByRTUDevice(inData);
		LOGGER.info("===============告警关联单位信息:" + data);
		if (data == null)
			throw new ServiceException(UtilConst.ERRO_KEYPARAM_NULL);

		//查询单位基础信息
		UtUnitBaseinfo baseInfo = utUnitBaseinfoMapper.selectByPrimaryKey(DataConvertUtil.parseLong(data.getUnitID()));
		if (baseInfo == null)
			throw new ServiceException(UtilConst.ERRO_KEYPARAM_NULL);

		//格式化告警时间
		Date alarmTime = Util.StringToDateTime(inData.getAlarmtime());

		// 查询5分钟内是否有相同告警(先查,再入库告警数据)
		List<AlarmRTUOutData> num = utLzFireequipmentalarmMapper.getRTUAlarmInfoExist(inData);

		//保存报警信息
		UtLzFireequipmentalarm alarm = saveUtLzFireequipmentalarm(inData, data);
		LOGGER.info("=============告警信息入库完成:" + alarm);
		//init sysMap
		initSysMap();
		LOGGER.info("==========生命周期测试sysMap:" + sysMap.hashCode());
		FrontUnitPointOutData alarmOutData = buildRTUAlarmOutData(inData, data, baseInfo);
		webSendAlarmMsg(alarmOutData);
		// web端推送信息
		webSendMsg();

		// 根据报警主机信息查询相关人员
		List<EmployeeForSendMsgData> employees = getEmployeeForSendMsgData(inData, data, baseInfo, alarm);
		//App推送
		pushApp(employees, ALARM_TYPE_OTHER);
		//微信推送
		pushWeChat(employees, ALARM_TYPE_OTHER);

		//打电话
		String callAlarmType = baseInfo.getCallAlarmType();
		int isautocall = baseInfo.getIsautocall() == null ? 0 : baseInfo.getIsautocall();
		if (Util.isNotEmpty(callAlarmType) &&
				callAlarmType.contains(ALARM_TYPE_OTHER) &&
				1 == isautocall &&
				num.size() == 0) { // 表示RTU告警（前端其他）
			LOGGER.info("===================RTU告警需要电话通知=======================");
			String alarmTimeFormat = UtilConv.date2Str(alarmTime, UtilConv.DATE_TIME_FULL);
			String alarmWheredesc = "传感器点位";
			String alarmSourceDesc = alarm.getEquipmentdetialname() + SPLIT_FALG;
			callPhones(alarmTimeFormat, alarmWheredesc, alarmSourceDesc, "设备故障", baseInfo);
		}

		//发短信
		String alarmTypes = baseInfo.getReceivealarmtype();
		if (Util.isNotEmpty(alarmTypes) &&
				alarmTypes.contains(ALARM_TYPE_OTHER) &&
				num.size() == 0) { // 表示RTU告警（前端其他）
			LOGGER.info("===================RTU告警需要短信通知=======================");
			SysPropertiesOutData properties = propertiesManageService.getProperties();
			String shortMsgKey = properties.getShortMessageApiKey();
			String shortMsgSecret = properties.getShortMessageApiSecret();
			String signName = properties.getSignName();
			String msg = buildRTUMsg(inData, data, baseInfo, signName);
			sendMessages(shortMsgKey, shortMsgSecret, msg, baseInfo);
		}

		// 发送新门海平台告警
		if (Util.isNotEmpty(baseInfo.getIsuploada()) &&
				baseInfo.getIsuploada() == 1 &&
				XMHAlarm == 1)
			thirdInterfaceService.xmhRtuAlarm(data, inData.getAlarmvalue(), alarmTime);

		// 发送安讯平台告警
		if (Util.isNotEmpty(baseInfo.getIsuploadb()) &&
				baseInfo.getIsuploadb() == 1) {
			String scop = "";
			if (Util.isNotEmpty(data.getAnalogdown()) &&
					Util.isNotEmpty(data.getAnalogup())) {
				scop = data.getAnalogdown() + "-" + data.getAnalogup() + "(" + data.getAnalogUnit() + ")";
			}
			String detial = Util.isEmpty(data.getEquipmentDetialName()) ? "" : data.getEquipmentDetialName();
			thirdInterfaceService.realTimeAlarmAX(data.getNetDeviceId(), data.getEqSystemID(),
					String.valueOf(alarmTime.getTime() / 1000), detial + scop);
		}
	}

	/**
	 * 接收报警主机告警
	 */
	@Override
	public void sendBJZJAlarm(HttpServletRequest request) throws Exception {

		// 解析request中的body（流读取）并转换为对象
		String str = analysisRequest(request);
		AlarmBJZJInData inData = JSONObject.parseObject(str, AlarmBJZJInData.class);
		LOGGER.info("==================接收到主机报警记录：" + str);

		//根据报警主机信息获取所属单位
		AlarmBJZJOutData data = utLzBjzjalarmMapper.getUnitIDByBJZJDeviceAndpartCode(inData);

		if (data == null) {
			data = utLzBjzjalarmMapper.getUnitIDByBJZJDevice(inData);
		}

		LOGGER.info("==================主机报警所属单位：" + data);
		if (data == null) {
			throw new ServiceException(UtilConst.ERRO_KEYPARAM_NULL);
		}


		// 查询单位表详情（用于获取推送号码，和单位需要推送的报警类型）
		UtUnitBaseinfo baseInfo = utUnitBaseinfoMapper.selectByPrimaryKey(DataConvertUtil.parseLong(data.getUnitID()));
		if (baseInfo == null)
			throw new ServiceException(UtilConst.ERRO_KEYPARAM_NULL);

		// 查询5分钟内是否有相同告警
		List<String> num = utLzBjzjalarmMapper.getAlarmInfoExist(inData);
		//保存报警信息
		UtLzBjzjalarm alarm = saveBjzjAlarmParams(inData, data);
		LOGGER.info("==================主机报警数据入库：" + alarm);
		//init sysMap
		initSysMap();

		// web端推送信息
		if (ALARM_TYPE_FIRE.equals(inData.getAlarm_Status())) {
			FrontUnitPointOutData alarmOutData = buildAlarmOutData(inData, data, baseInfo);
			LOGGER.info("==================火警推送web：" + alarmOutData);
			webSendAlarmMsg(alarmOutData);// web端推送具体的火警信息
		} else {
			LOGGER.info("==================一般主机告警推送web============");
			webSendMsg();// web端推送信息+
		}


		// 解析报警类型
		String alarmType = frontCoupletServiceImpl.dealStatus(inData.getAlarm_Status());

		// 根据报警主机信息查询相关人员
		List<EmployeeForSendMsgData> employees = getEmployeeForSendMsgData(inData, data, baseInfo, alarm, alarmType);
		String status = inData.getAlarm_Status().equals("7") ? ALARM_TYPE_OTHER : inData.getAlarm_Status();


		//app推送
		pushApp(employees, status);
		//微信推送
		pushWeChat(employees, status);

		// 打电话
		String callAlarmType = baseInfo.getCallAlarmType();
		if (Util.isNotEmpty(callAlarmType) &&
				callAlarmType.contains(inData.getAlarm_Status()) &&
				1 == baseInfo.getIsautocall() &&
				Util.isNotEmpty(baseInfo.getAutoCallNum()) &&
				num.size() == 0) {
			LOGGER.info("===================主机告警需要电话通知=======================");
			String alarmTimeFormat = UtilConv.date2Str(alarm.getTime(), UtilConv.DATE_TIME_FULL);
			String alarmWheredesc = alarm.getAlarmWheredesc();
			String alarmSourceDesc = alarm.getAlarmSourcedesc() + SPLIT_FALG;
			callPhones(alarmTimeFormat, alarmWheredesc, alarmSourceDesc, alarmType, baseInfo);
		}

		// 发短信
		String alarmTypes = baseInfo.getReceivealarmtype();
		if (Util.isNotEmpty(alarmTypes) &&
				alarmTypes.contains(inData.getAlarm_Status()) &&
				num.size() == 0) {
			LOGGER.info("===================主机告警需要短信通知=======================");
			SysPropertiesOutData properties = propertiesManageService.getProperties();
			String shortMsgKey = properties.getShortMessageApiKey();
			String shortMsgSecret = properties.getShortMessageApiSecret();
			String signName = properties.getSignName();

			String msg = buildBJZJMsg(inData, data, baseInfo, alarmType, signName);
			sendMessages(shortMsgKey, shortMsgSecret, msg, baseInfo);
		}
		// 查询真实地址(仅作为是否新增点位关联的判断)
		FrontUnitCRTOutData outData = utEqAddressRelMapper.getaddressRelByPartCode(inData.getZJID(),
				data.getEqId());
		// 报警点位没有真实地址
		if (Util.isNotEmpty(inData.getZJID()) && Util.isEmpty(outData)) {
			LOGGER.info("===================更新点位地址:" + data);
			outData = saveFrontUnitCRTOutData(inData, data);
		}

		// 发送新门海平台告警
		if (Util.isNotEmpty(baseInfo.getIsuploada()) && baseInfo.getIsuploada() == 1 && XMHAlarm == 1) {
			thirdInterfaceService.xmhBjzjAlarm(alarm, data, inData.getOnwercode(), outData);
		}

		// 发送安讯平台告警(只推送火警和故障)
		String alarm_Status = inData.getAlarm_Status();
		if (Util.isNotEmpty(baseInfo.getIsuploadb()) && 1 == AXAlarm && ("1".equals(alarm_Status) || "2".equals(alarm_Status))) {
			thirdInterfaceService.realTimeAlarmAX(data.getNetDeviceId(), alarm_Status,
					String.valueOf(Util.StringToDateTime(inData.getTime()).getTime() / 1000),
					inData.getAlarm_SourceDesc() + " " + inData.getAlarm_DeviceDesc());
		}

	}

	/**
	 * 获取需要拨打的电话
	 */
	@Override
	public List<PhoneOutData> getPhones() throws Exception {

		List<PhoneOutData> outData = new ArrayList<>();
		List<UtUnitBaseinfo> unitList = utUnitBaseinfoMapper.selectAll();
		Map<Long, UtUnitBaseinfo> map = new HashMap<>();
		for (UtUnitBaseinfo utUnitBaseinfo : unitList) {
			if (Util.isNotEmpty(utUnitBaseinfo.getIsdelete()) && utUnitBaseinfo.getIsdelete() == 1) {
				continue;
			}
			map.put(utUnitBaseinfo.getId(), utUnitBaseinfo);
		}
		long now = new Date().getTime();
		FrontAlarmInData inData = new FrontAlarmInData();
		inData.setUserId("1");
		// 获取当天未处理的告警
		//sql查找标识
		inData.setSelectType("1");
		List<FrontFireInfoOutData> alarms = utLzBjzjalarmMapper.getAllAlarms(inData);
		UtUnitBaseinfo unit = null;
		for (FrontFireInfoOutData frontFireInfoOutData : alarms) {
			String unitId = frontFireInfoOutData.getUnitId();
			if (Util.isEmpty(unitId)) {// 是否有单位
				continue;
			}
			String isPhone = frontFireInfoOutData.getIsPhone();
			if ("1".equals(isPhone)) {// 已经打电话
				continue;
			}
			unit = map.get(Long.parseLong(unitId));
			if (Util.isEmpty(unit)) {// 单位不存在
				continue;
			}
			String phone = unit.getPhone();
			if (Util.isEmpty(phone)) {// 如果电话为空
				continue;
			}
			Integer isautocall = unit.getIsautocall();
			if (isautocall == null || isautocall == 0) {// 不需要自动拨打电话
				continue;
			}
			Integer autocalldelay = unit.getAutocalldelay();
			if (Util.isEmpty(autocalldelay)) {
				autocalldelay = 10;// 默认超时10分钟打电话
			}
			String time = frontFireInfoOutData.getTime();
			if (Util.isEmpty(time)) {
				continue;
			}
			String alarmStatus = frontFireInfoOutData.getAlarmStatus();
			if (!"1".equals(alarmStatus) || "是".equals(frontFireInfoOutData.getIsTest())) {// 如果不是火警或者是测试数据
				continue;
			}
			long time2 = UtilConv.str2Date(time, UtilConv.DATE_YYYY_MM_DD_SS).getTime();
			long l = (now - time2) / 1000 / 60;
			if (l > autocalldelay && l < (15 + autocalldelay)) {// 最多拨打3次
				// 超过时间未处理的需要通知
				PhoneOutData phoneOutData = new PhoneOutData();
				phoneOutData.setMsg(unit.getUnitname() + frontFireInfoOutData.getAlarmDevicedesc() + "有未处理的火警。");
				phoneOutData.setPhone(phone);
				phoneOutData.setId(frontFireInfoOutData.getId());
				outData.add(phoneOutData);
			}
		}
		return outData;
	}

	/**
	 * 更新状态
	 *
	 * @param ids 报警主机告警信息 id 字符串
	 */
	@Override
	public void updatePhoneStatus(String ids) throws Exception {
		if (Util.isEmpty(ids)) {
			return;
		}
		String[] split = ids.split(SPLIT_FALG);
		for (String id : split) {
			if (Util.isEmpty(id)) {
				continue;
			}
			UtLzBjzjalarm utLzBjzjalarm = utLzBjzjalarmMapper.selectByPrimaryKey(Long.valueOf(id));
			utLzBjzjalarm.setIsphone("1");
			utLzBjzjalarmMapper.updateByPrimaryKeySelective(utLzBjzjalarm);
			UtLzBjzjalarmHistory utLzBjzjalarmHistory = utLzBjzjalarmHistoryMapper.selectByPrimaryKey(Long.valueOf(id));
			utLzBjzjalarmHistory.setIsphone("1");
			utLzBjzjalarmHistoryMapper.updateByPrimaryKeySelective(utLzBjzjalarmHistory);
		}
	}


    /**
     * 解析 HttpServletRequest 请求
     *
     * @param request http请求
     * @return json字符串
     */
    private String analysisRequest(HttpServletRequest request) throws IOException {
        String str = "";
        InputStream in = request.getInputStream();
        InputStreamReader reader = new InputStreamReader(in, "utf-8");
        BufferedReader bd = new BufferedReader(reader);
        String inputLine = "";
        while ((inputLine = bd.readLine()) != null) {
            str += inputLine;
        }
        return str;
    }

	/**
	 * 保存消防设备报警记录
	 *
	 * @param inData 告警信息
	 * @param data   告警设备详细信息
	 * @return 消防设备报警记录
	 */
	private UtLzFireequipmentalarm saveUtLzFireequipmentalarm(AlarmRTUInData inData, AlarmRTUOutData data) throws Exception {

		long nextId = snowflakeIdWorker.nextId();

		Date alarmTime = Util.StringToDateTime(inData.getAlarmtime());
		UtLzFireequipmentalarm alarm = new UtLzFireequipmentalarm();

		alarm.setId(nextId);
		alarm.setAlarmtime(alarmTime);
		alarm.setLastupdate(alarmTime);
		alarm.setCalibrationId(DataConvertUtil.parseLong(data.getCalibrationId()));
		alarm.setAlarmvalue(inData.getAlarmvalue());
		alarm.setEquipmentdetialname(data.getEquipmentDetialName());
		alarm.setEquipmentname(data.getEquipmentName());
		alarm.setFireequipmentdetialid(DataConvertUtil.parseLong(data.getFireEquipmentDetialID()));
		alarm.setNormalvalue(data.getNormalValue());
		alarm.setUnitid(DataConvertUtil.parseLong(data.getUnitID()));
		alarm.setAlarmStatus(DataConvertUtil.parseInt(data.getEqSystemID()));
		alarm.setBuildAreaID(DataConvertUtil.parseLong(data.getBuildAreaID()));
		UtLzFireequipmentalarmHistory alarmHistory = new UtLzFireequipmentalarmHistory();
		BeanUtils.copyProperties(alarm,alarmHistory);
		utLzFireequipmentalarmMapper.insert(alarm);
		utLzFireequipmentalarmHistoryMapper.insert(alarmHistory);
		return alarm;
	}

	/**
	 * 保存报警主机报警记录
	 *
	 * @param inData 告警信息
	 * @param data   告警设备详细信息
	 * @return 报警主机报警记录
	 */
	private UtLzBjzjalarm saveBjzjAlarmParams(AlarmBJZJInData inData, AlarmBJZJOutData data) throws Exception {
		UtLzBjzjalarm alarm = new UtLzBjzjalarm();
		long nextId = snowflakeIdWorker.nextId();
		alarm.setId(nextId);
		alarm.setCalibrationId(DataConvertUtil.parseLong(data.getCalibrationId()));
		alarm.setOnwercode(inData.getOnwercode());
		alarm.setDeviceindex(Integer.parseInt(inData.getDeviceindex()));
		alarm.setDeviceno(Integer.parseInt(inData.getDeviceno()));
		alarm.setAlarmSourcedesc(inData.getAlarm_SourceDesc().trim());
		if (Util.isNotEmpty(data.getEqAddress())) {
			alarm.setAlarmWheredesc(data.getEqAddress());
		} else {
			alarm.setAlarmWheredesc(inData.getAlarm_WhereDesc().trim());
		}
		if (Util.isNotEmpty(data.getEqDetailName())) {
			alarm.setAlarmDevicedesc(data.getEqDetailName());
		} else {
			alarm.setAlarmDevicedesc(inData.getAlarm_DeviceDesc().trim());
		}
		alarm.setAlarmStatus(DataConvertUtil.parseInt(inData.getAlarm_Status()));
		alarm.setTime(Util.StringToDateTime(inData.getAlarmtime()));
		alarm.setAlarmReserve(inData.getZJID());
		alarm.setLastupdate(Util.StringToDateTime(inData.getTime()));
		alarm.setUnitid(DataConvertUtil.parseLong(data.getUnitID()));
		alarm.setOnwercode(inData.getOnwercode());
		alarm.setJdid(inData.getJDID());
		alarm.setId(nextId);
		alarm.setDeviceindex(DataConvertUtil.parseInt(inData.getDeviceindex()));
		alarm.setDeviceno(DataConvertUtil.parseInt(inData.getDeviceno()));
		alarm.setHlid(inData.getHLID());
		alarm.setIsdeal(0);
		alarm.setIsTest(Integer.parseInt(inData.getIsTest()));
		alarm.setBuildAreaId(Long.valueOf(data.getBuildAreaID()));
		UtLzBjzjalarmHistory alarmHistory = new UtLzBjzjalarmHistory();
		BeanUtils.copyProperties(alarm,alarmHistory);
		utLzBjzjalarmMapper.insert(alarm);
		utLzBjzjalarmHistoryMapper.insert(alarmHistory);
		return alarm;
	}

	/**
	 * 保存报警点位真实地址
	 *
	 * @param inData 告警信息
	 * @param data   报警主机所属单位信息
	 * @return 点位真实店址
	 */
	private FrontUnitCRTOutData saveFrontUnitCRTOutData(AlarmBJZJInData inData, AlarmBJZJOutData data) {
		FrontUnitCRTOutData outData;
		UtEqAddressRel rel = new UtEqAddressRel();
		outData = new FrontUnitCRTOutData();
		Long relID = snowflakeIdWorker.nextId();
		rel.setId(relID);
		outData.setId(relID.toString());
		rel.setPartcode(inData.getZJID());
		outData.setPartcode(inData.getZJID());
		List<EquipmentFacOutData> eData = utEqEquipmentMapper.getListByNetDeviceid(data.getNetDeviceId());
		UtUnitBuild utUnitBuild = utUnitBuildMapper.selectByPrimaryKey(Long.valueOf(eData.get(0).getUnitid() + "1"));
		if (Util.isEmpty(utUnitBuild)) {
			UtUnitBuild build = new UtUnitBuild();
			build.setUnitid(Long.valueOf(eData.get(0).getUnitid()));
			build.setId(Long.valueOf(eData.get(0).getUnitid() + "1"));
			build.setBuildingname("默认建筑");
			utUnitBuildMapper.insert(build);
		}
		UtUnitBuildarea utUnitBuildarea = utUnitBuildareaMapper.selectByPrimaryKey(Long.valueOf(eData.get(0).getUnitid() + "2"));
		if (Util.isEmpty(utUnitBuildarea)) {
			UtUnitBuildarea utBuildarea = new UtUnitBuildarea();
			utBuildarea.setId(Long.valueOf(eData.get(0).getUnitid() + "2"));
			utBuildarea.setBuildid(Long.valueOf(eData.get(0).getUnitid() + "1"));
			utBuildarea.setUnitid(Long.valueOf(eData.get(0).getUnitid()));
			utBuildarea.setBuildareaname("默认区域");
			utUnitBuildareaMapper.insert(utBuildarea);
			rel.setBuildareaid(utBuildarea.getId());
		} else {
			rel.setBuildareaid(utUnitBuildarea.getId());
		}

		rel.setEqid(Long.parseLong(eData.get(0).getId()));
		outData.setEqid(eData.get(0).getId());
		rel.setEqsystemid(Long.parseLong(eData.get(0).getEqsystemid()));
		outData.setEqSystemID(eData.get(0).getEqsystemid());
		rel.setName("未知点位");
		rel.setUnitId(Long.parseLong(eData.get(0).getUnitid()));
		outData.setName("未知点位");
		outData.setUnitID(data.getUnitID());
		outData.setOwnercode(inData.getOnwercode());
		outData.setNetdeviceid(data.getNetDeviceId());// outData封装报警主机点位信息用于同步至新门海监控点信息
		outData.setAdress("未知地址");
		utEqAddressRelMapper.insert(rel);
		return outData;
	}

	/**
	 * 前台页面推送接口
	 */
	private void webSendMsg() throws Exception {
		// Map<String, String> userMap = new HashMap<>();
		Map<AlarmWebSocket, String> map = AlarmWebSocket.map;
		LOGGER.info("===============当前登陆用户数:" + map.size());
		if (Util.isEmpty(map)) {
			return;
		}
		for (Entry<AlarmWebSocket, String> entry : map.entrySet()) {
			String user = entry.getValue();
			String userId = user.substring(0, user.indexOf(","));
			String loginTime = user.substring(user.indexOf(",") + 1, user.length());
			LOGGER.info("================推送web的userId:" + userId);
			RealTimeAlarmCount outData = homeIndexService.getRealTimeAlarmCount(userId, loginTime);
			AlarmWebSocket webSocket = entry.getKey();
			FrontUnitUserOutData sessinInfo = new FrontUnitUserOutData();
			// if (userMap.containsKey(userId)) {
			// webSocket.sendMessage(userMap.get(userId));
			// } else {
			sessinInfo.setUserid(userId);
			sessinInfo.setLoginTime(loginTime);
			// RealTimeAlarmCount outData =
			// homeIndexService.getRealTimeAlarmCount(sessinInfo);
			webSocket.sendMessage(JSONObject.toJSONString(outData));
			// userMap.put(userId, outData + "");
			// }
		}
	}

	/**
	 * 前台页面推送接口
	 */
	private void webSendAlarmMsg(FrontUnitPointOutData alarmOutData) throws Exception {
		// Map<String, String> userMap = new HashMap<>();
		Example example = new Example(UtBaseUserreunit.class);
		example.createCriteria().andEqualTo("unitId", alarmOutData.getUnitId());
		List<UtBaseUserreunit> userReUnits = userreunitMapper.selectByExample(example);
		LOGGER.info("=========webSendMsg:报警涉及的用户个数" + userReUnits.size());
		StringBuffer sb = new StringBuffer();
		for (UtBaseUserreunit userUnit : userReUnits) {
			sb.append(userUnit.getUserId() + ",");
		}
		String userIds = sb.toString();
		Map<AlarmWebSocket, String> map = AlarmWebSocket.map;
		if (Util.isEmpty(map)) {
			LOGGER.info("=====无登陆用户");
			return;
		}
		AlarmWebSocket webSocket;
		for (Entry<AlarmWebSocket, String> entry : map.entrySet()) {
			String user = entry.getValue();
			String userId = user.substring(0, user.indexOf(","));
			if (userIds.contains(userId)) {
				webSocket = entry.getKey();
				webSocket.sendMessage(JSONObject.toJSONString(alarmOutData));
				LOGGER.info("========推送前端信息：userId:" + userId + "报警信息:" + alarmOutData.toString());
			}
		}
	}

	/**
	 * 推送消息至app
	 *
	 * @param employees 接受消息的员工列表
	 * @param status    告警类型
	 */
	private void pushApp(List<EmployeeForSendMsgData> employees, String status) throws Exception {
		LOGGER.info("===============告警推送APP相关用户数:" + employees.size());
		for (EmployeeForSendMsgData user : employees) {

			//构建推送消息
			String content = user.getUsername() + "您管理的(" + user.getAlarmArea() + " "
					+ user.getEqName() + ")设备发出报警，请登录微信绑定并查看！";

			//如果是火警，强制推送
			if ("1".equals(status) && Util.isNotEmpty(user.getChannelid())) {
				PushMsgManage.pushMsg(user.getPhoneName(), "通知", content, user.getChannelid());
			} else {
				String receiveAlarmType = user.getReceiveAlarmType();
				if (Util.isNotEmpty(receiveAlarmType) &&
						Arrays.asList(receiveAlarmType.split(SPLIT_FALG)).contains(status) && Util.isNotEmpty(user.getChannelid())) {
					PushMsgManage.pushMsg(user.getPhoneName(), "通知", content, user.getChannelid());
				}
			}
		}
	}

	/**
	 * 推送消息至微信
	 *
	 * @param employees 接受消息的员工列表
	 */
	private void pushWeChat(List<EmployeeForSendMsgData> employees, String status) throws Exception {
		LOGGER.info("===================开始微信推送:" + employees.size());
		for (EmployeeForSendMsgData user : employees) {

			//构建推送消息
			String msg = user.getUsername() + "您管理的(" + user.getAlarmArea() + " "
					+ user.getEqName() + ")设备发出报警，请登录微信绑定并查看！";

			//如果是火警，强制推送
			if ("1".equals(status)) {
				TemplateMessage templateMessage = getTemplateMessage(true, user, msg);
				if (templateMessage != null)
					PushMessage.sendTemplateMSGToUser(templateMessage, getToken());
			}

			String receiveAlarmType = user.getReceiveAlarmType();
			if (Util.isNotEmpty(receiveAlarmType) &&
					Arrays.asList(receiveAlarmType.split(SPLIT_FALG)).contains(status)) {

				// 微信推送
				TemplateMessage templateMessage = getTemplateMessage(false, user, msg);
				if (templateMessage != null)
					PushMessage.sendTemplateMSGToUser(templateMessage, getToken());
			}

		}
	}

	/**
	 * 发送短信通知告警单位相关人员
	 * 信息内容一致
	 * 记录短信发送日志
	 * 更新短信已发送数量
	 *
	 * @param shortMsgKey    短信发送key
	 * @param shortMsgSecret 短信发送密钥
	 * @param msg            信息内容
	 * @param baseInfo       单位基础信息
	 */
	private void sendMessages(String shortMsgKey, String shortMsgSecret, String msg, UtUnitBaseinfo baseInfo) throws Exception {

		Integer messageCount = baseInfo.getMessagenumber();
		messageCount = messageCount != null ? messageCount : 0;
		Integer quantitySend = baseInfo.getQuantitysent();
		quantitySend = quantitySend != null ? quantitySend : 0;

		//校验短信发送次数
		if (messageCount <= quantitySend)
			return;

		//构建短信内容
		//发送短信并记录日志
		boolean modifyFlag = false;
		List<String> phones = splitPhones(baseInfo.getMessagephone());
		for (String phone : phones) {

			//校验短信发送次数
			if (messageCount <= quantitySend)
				break;

			//发送短信
			boolean succ = PushMessage.sendShortMessage(shortMsgKey, shortMsgSecret, phone, msg);

			String status;
			if (succ) {
				quantitySend++;
				status = LOG_STATUS_SUCC;
				modifyFlag = true;
			} else {
				status = LOG_STATUS_FAIL;
			}

			//记录发送日志
			UtMessageLog log = new UtMessageLog();
			log.setId(snowflakeIdWorker.nextId());
			log.setUnitId(baseInfo.getId());
			log.setMessageType(LOG_MSGTYPE_SMS);
			log.setContent(msg);
			log.setPhone(phone);
			log.setReceiver(phone);
			log.setSendDate(new Date());
			log.setStatus(status);
			utMessageLogMapper.insert(log);
		}

		//更新短信已发送数量
		if (modifyFlag) {
			baseInfo.setQuantitysent(quantitySend);
			utUnitBaseinfoMapper.updateByPrimaryKey(baseInfo);
		}
	}

	/**
	 * 电话通知告警单位相关人员
	 * 电话内容一致
	 *
	 * @param alarmTime       告警时间
	 * @param alarmWheredesc  告警位置描述
	 * @param alarmSourceDesc 告警源描述
	 * @param baseInfo        单位信息
	 */
	private void callPhones(String alarmTime, String alarmWheredesc, String alarmSourceDesc, String alarmType, UtUnitBaseinfo baseInfo) throws ClientException {

		Integer callTotal = baseInfo.getCallTotal();
		callTotal = callTotal != null ? callTotal : 0;
		Integer callUsed = baseInfo.getCallUsed();
		callUsed = callUsed != null ? callUsed : 0;

		//校验电话拨打次数
		if (callTotal <= callUsed)
			return;

        /* 构建电话通知内容
        template:
        {"maintenanceName":"苏州思迪","unitName":"某某单位","alarmTime":"2019年5月17日11点30分",
        "alarmWheredesc":"办公楼三楼","alarmSourceDesc":"无线烟感"}*/
		JSONObject json = new JSONObject();
		json.put("maintenanceName", phoneUnitName);
		json.put("unitName", baseInfo.getUnitname());
		json.put("alarmTime", alarmTime);
		json.put("alarmWheredesc", alarmWheredesc);
		json.put("alarmType", alarmType);
		json.put("alarmSourceDesc", alarmSourceDesc);

		//拨打电话并记录日志
		boolean modifyFlag = false;
		List<String> phones = splitPhones(baseInfo.getAutoCallNum());
		List<Map<Object,Object>> list = new ArrayList<Map<Object,Object>>();
//		for (String phoneNum : phones) {
		for (int i = 0;i < phones.size(); i++) {

			//校验电话发送次数
			if (callTotal <= callUsed)
				break;

			//拨打电话
			Map map = AliPhoneServiceUtil.singleCallByTts(accessKeyId, accessKeySecret,ttsCode, timeout, phones.get(i), showNum, json);
//			boolean succ = AliPhoneServiceUtil.singleCallByTts(accessKeyId, accessKeySecret, ttsCode, timeout, phoneNum, showNum, json);

			String status;
			if ((boolean) map.get("succ")) {
				status = LOG_STATUS_SUCC;
				callUsed++;
				modifyFlag = true;
			} else {
				status = LOG_STATUS_FAIL;
			}

			//只有拨打成功后，才去修改通话时长
			//记录打电话表
			UtPhoneLog uplog = new UtPhoneLog();
			uplog.setId(snowflakeIdWorker.nextId());
			uplog.setUnitId(baseInfo.getId());
			uplog.setPhone(phones.get(i));
			uplog.setStatus(status);
			uplog.setSendDate(new Date());
			uplog.setLongTime(0);
			utPhoneLogMapper.insert(uplog);
			if ((boolean) map.get("succ")){
				Map mapTwo = new HashMap();
				mapTwo.put("id",uplog.getId());
				mapTwo.put("utitId",uplog.getUnitId());
				mapTwo.put("callid",map.get("callid"));
				mapTwo.put("type",0);
				list.add(mapTwo);
			}

			//记录发送日志    Constants
			UtMessageLog log = new UtMessageLog();
			log.setId(snowflakeIdWorker.nextId());
			log.setUnitId(baseInfo.getId());
			log.setContent(json.toJSONString());
			log.setPhone(phones.get(i));
			log.setMessageType(LOG_MSGTYPE_PHE);
			log.setReceiver(phones.get(i));
			log.setSendDate(new Date());
			log.setStatus(status);
			utMessageLogMapper.insert(log);

		}

		if (list.size() > 0){
			try{
				ReceiveAlicomMsg.voiceReturn(accessKeyId, accessKeySecret, list,json,baseInfo);
			}catch (com.aliyuncs.exceptions.ClientException e){
				e.getMessage();
			}catch (ParseException e2){
				e2.getMessage();
			}
		} else{
			try{
				//电话都不能拨打时，拨打单位电话
				dialTheUnitPhone(json,baseInfo);
			}catch (Exception e){
				e.getMessage();
			}
		}

		//更新电话已拨打数量
		if (modifyFlag) {
			baseInfo.setCallUsed(callUsed);
			utUnitBaseinfoMapper.updateByPrimaryKey(baseInfo);
		}
	}

	/**
	 * 当电话都未拨打通时，去拨打单位电话
	 */
	public void dialTheUnitPhone(JSONObject json, UtUnitBaseinfo baseInfo) throws Exception {
		//拨打单位电话
		String phone = utUnitBaseinfoMapper.selectPhone(baseInfo.getId().toString());
		LOGGER.info("拨打单位电话了==============================" + phone);
		Map map = AliPhoneServiceUtil.singleCallByTts(accessKeyId, accessKeySecret,ttsCode, timeout, phone, showNum, json);
		//只有拨打成功后，才去修改通话时长
		String statusTwo;
		int callUsed = 0;
		if ((boolean) map.get("succ")){
			callUsed++;
			statusTwo = LOG_STATUS_SUCC;
		}else{
			statusTwo = LOG_STATUS_FAIL;
		}
		//记录打电话表
		UtPhoneLog uplog = new UtPhoneLog();
		uplog.setId(snowflakeIdWorker.nextId());
		uplog.setUnitId(baseInfo.getId());
		uplog.setPhone(phone);
		uplog.setStatus(statusTwo);
		uplog.setSendDate(new Date());
		uplog.setLongTime(0);
		utPhoneLogMapper.insert(uplog);

		//记录发送日志    Constants
		UtMessageLog log = new UtMessageLog();
		log.setId(snowflakeIdWorker.nextId());
		log.setUnitId(baseInfo.getId());
		log.setContent(json.toJSONString());
		log.setPhone(phone);
		log.setMessageType(LOG_MSGTYPE_PHE);
		log.setReceiver(phone);
		log.setSendDate(new Date());
		log.setStatus(statusTwo);
		utMessageLogMapper.insert(log);
		//接收语音回执消息，没有就结束
		List list = new ArrayList<Map<Object,Object>>();
		Map mapTwo = new HashMap();
		if ((boolean) map.get("succ")){
			mapTwo.put("id",uplog.getId());
			mapTwo.put("utitId",uplog.getUnitId());
			mapTwo.put("callid",map.get("callid"));
			//该属性判断是否是单位电话
			mapTwo.put("type",1);
			list.add(mapTwo);
			try{
				ReceiveAlicomMsg.voiceReturn(accessKeyId, accessKeySecret,list,json,baseInfo);
			} catch (com.aliyuncs.exceptions.ClientException e){
				e.getMessage();
			} catch (ParseException e2){
				e2.getMessage();
			}
		}
		//更新电话已拨打数量
		if (callUsed > 0) {
			baseInfo.setCallUsed(callUsed);
			utUnitBaseinfoMapper.updateByPrimaryKey(baseInfo);
		}
	}

	//修改存储通话时长
	public void updateUnitPhone(UtPhoneLog utPhoneLog) throws Exception {
		LOGGER.info("存储数据/通话时长是================" + utPhoneLog.getLongTime());
		UtPhoneLog log = new UtPhoneLog();
		log.setId(utPhoneLog.getId());
		log.setLongTime(utPhoneLog.getLongTime());
		log.setStatus("2");
		utPhoneLogMapper.updatePhoneLog(log);
	}

	/**
	 * 获取微信消息模板
	 *
	 * @param isFire 是否为火警报警信息
	 * @param user   接受消息的员工
	 * @param msg    要发送的消息
	 * @return 微信消息模板
	 */
	private TemplateMessage getTemplateMessage(boolean isFire, EmployeeForSendMsgData user, String msg) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		String openid = user.getOpenid();
		if (Util.isEmpty(openid)) {
			LOGGER.info("===================" + user.getUseraccount() + " has no openid!");
			return null;
		}
		TemplateMessage templateMessage = new TemplateMessage();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("first", getJsonData(user.getUnitName()));
		jsonObject.put("remark", getJsonData(msg));
		String alarmtype = "0";
		if (isFire) {
			jsonObject.put("keyword1", getJsonData(user.getEqName()));
			jsonObject.put("keyword2", getJsonData(user.getAlarmTime()));
			jsonObject.put("keyword3", getJsonData(user.getAlarmType()));
			jsonObject.put("keyword4", getJsonData(user.getAlarmArea()));
			jsonObject.put("keyword5", getJsonData(user.getAlarmDetail()));
			templateMessage.setTemplateId(properties.getWxTempId());
		} else {
			jsonObject.put("keyword1", getJsonData(user.getDetialname()));
			jsonObject.put("keyword2", getJsonData(user.getDeviceno()));
			jsonObject.put("keyword3", getJsonData(user.getIoport()));
			jsonObject.put("keyword4", getJsonData(user.getScope()));
			jsonObject.put("keyword5", getJsonData(user.getAlarmvalue()));
			templateMessage.setTemplateId(properties.getWxTempRtuId());
			alarmtype = "1";
		}
		templateMessage.setClickurl(
				String.format(properties.getWxClickUrl(), openid, user.getAlarmId(), alarmtype));
		templateMessage.setTopcolor("#FF0000");
		templateMessage.setTouser(openid);
		templateMessage.setTemplateData(jsonObject);
		return templateMessage;
	}


	/**
	 * 分割手机号字符串
	 *
	 * @param phones 手机号字符串，多个用','分割，例'13112345678,13212345678'
	 * @return 手机号
	 */
	private List<String> splitPhones(String phones) {

		List<String> phoneList = new ArrayList<>();

		if (Util.isEmpty(phones))
			return phoneList;

		String[] split = phones.split(SPLIT_FALG);
		for (String phone : split) {
			if (phone.matches("^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$"))
				phoneList.add(phone);
		}
		return phoneList;
	}

	/**
	 * 初始化消防系统字典map
	 *
	 * @return map
	 */
	private Map<String, String> initSysMap() {
		if (null == sysMap) {
			List<UtBaseEqsystem> list = utBaseEqsystemMapper.selectAll();
			sysMap = new HashMap<String, String>();
			for (UtBaseEqsystem utBaseEqsystem : list) {
				sysMap.put(utBaseEqsystem.getId() + "", utBaseEqsystem.getEqsystemname());
			}
		}
		return sysMap;
	}

	/**
	 * 构建微信模板文本样式 jsonObj
	 *
	 * @param content 内容
	 * @return 带样式的 jsonObj
	 */
	private Object getJsonData(String content) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("value", Util.isEmpty(content) ? "" : content);
		jsonObject.put("color", "#173177");
		return jsonObject;
	}

	/**
	 * 获取微信 token
	 *
	 * @return token
	 */
	public String getToken() throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		return HttpGetWXToken.getToken(properties.getWxAppid(), properties.getWxSecret());
	}

	/**
	 * 构建发送的短信息内容
	 *
	 * @param signName        短信签名
	 * @param unitname        单位名称
	 * @param onwercode       设备12位编码（单位编号）
	 * @param alarmTime       报警时间
	 * @param alarmTypeName   报警类型
	 * @param alarmSourceDesc 报警描述
	 * @param eqName          设备名称
	 * @param alarmArea       报警地点
	 * @return 短信内容
	 */
	private String buildMsg(String signName, String unitname, String alarmSourceDesc, String alarmArea, String eqName, String alarmTime,
							String onwercode, String alarmTypeName) {
		return signName + unitname + "(" + onwercode
				+ ")在" + alarmTime + " 收到[" + alarmTypeName + "]信息，报警描述："
				+ alarmSourceDesc + ",报警设备：" + eqName + ",位置："
				+ alarmArea + "。";
	}

	private FrontUnitPointOutData buildAlarmOutData(AlarmBJZJInData inData, AlarmBJZJOutData data, UtUnitBaseinfo baseInfo) {
		FrontUnitPointOutData alarmOutData = new FrontUnitPointOutData();
		alarmOutData.setAlarmStatus(inData.getAlarm_Status());
		alarmOutData.setUnitId(data.getUnitID());
		alarmOutData.setUnitPoint(baseInfo.getUnitpoint());
		alarmOutData.setUnittype("1");
		alarmOutData.setVideoId(data.getVideoId());
		return alarmOutData;
	}

	private FrontUnitPointOutData buildRTUAlarmOutData(AlarmRTUInData inData, AlarmRTUOutData data, UtUnitBaseinfo baseInfo) {
		FrontUnitPointOutData alarmOutData = new FrontUnitPointOutData();
		alarmOutData.setAlarmStatus(inData.getAlarmStatus());
		alarmOutData.setUnitId(data.getUnitID());
		alarmOutData.setEqSystemID(data.getEqSystemID());
		alarmOutData.setUnitPoint(baseInfo.getUnitpoint());
		alarmOutData.setUnittype("1");
		alarmOutData.setVideoId(data.getVideoId());
		return alarmOutData;
	}

	private String buildRTUMsg(AlarmRTUInData inData, AlarmRTUOutData data, UtUnitBaseinfo baseInfo, String signName) {
		String unitname = baseInfo.getUnitname();
		String alarmSourceDesc = data.getEquipmentDetialName() + " " + data.getNormalValue()
				+ (Util.isEmpty(data.getAnalogUnit()) ? "" : data.getAnalogUnit());
		String alarmArea = data.getInstallPosition();
		String eqName = data.getEquipmentName();
		String onwercode = inData.getOnwercode();
		String alarmTypeName = sysMap.get(inData.getStatus());
		String alarmTimeStr = inData.getAlarmtime();

		return buildMsg(signName, unitname, alarmSourceDesc, alarmArea, eqName, alarmTimeStr, onwercode, alarmTypeName);
	}

	private String buildBJZJMsg(AlarmBJZJInData inData, AlarmBJZJOutData data, UtUnitBaseinfo baseInfo, String alarmStatus, String signName) {
		String unitname = baseInfo.getUnitname();
		String alarmSourceDesc = inData.getAlarm_SourceDesc() + " " + inData.getAlarm_DeviceDesc();
		String alarmArea = inData.getAlarm_WhereDesc();
		String eqName = data.getEqname();
		String onwercode = inData.getOnwercode();
		String alarmTimeStr = inData.getTime();

		return buildMsg(signName, unitname, alarmSourceDesc, alarmArea, eqName, alarmTimeStr, onwercode, alarmStatus);
	}

	private List<EmployeeForSendMsgData> getEmployeeForSendMsgData(AlarmRTUInData inData, AlarmRTUOutData data, UtUnitBaseinfo baseInfo, UtLzFireequipmentalarm alarm) throws Exception {
		List<EmployeeForSendMsgData> employees = utLzBjzjalarmMapper.getEmployeeByBJZJDeviceByBuild(data.getUnitID());
		List<EmployeeForSendMsgData> employee = new ArrayList<>();
		if (employees != null) {
			for (EmployeeForSendMsgData user : employees) {
				if (Util.isEmpty(user.getBuildAreaId()) || user.getBuildAreaId().equals(String.valueOf(alarm.getBuildAreaID()))) {
					employee.add(user);
				}
			}
		}
		for (EmployeeForSendMsgData user : employee) {
			user.setAlarmArea(data.getInstallPosition());
			// user.setAlarmDetail(alarmSourceDesc);// 当前值
			// user.setAlarmTime(inData.getDeviceno());// 子号
			// user.setAlarmType(inData.getIoport());// 端口索引
			user.setEqName(data.getEquipmentName());
			user.setUnitName(baseInfo.getUnitname());
			user.setAlarmId(Long.toString(alarm.getId()));
			user.setDeviceno(inData.getDeviceno());
			user.setAlarmvalue(inData.getAlarmvalue() + " " + inData.getAlarmtime());
			user.setIoport(inData.getIoport());
			if (Util.isNotEmpty(data.getAnalogdown()) && Util.isNotEmpty(data.getAnalogup())) {
				user.setScope(data.getAnalogdown() + "-" + data.getAnalogup() + "(" + data.getAnalogUnit() + ")");
			}
			user.setDetialname(Util.isEmpty(data.getEquipmentDetialName()) ? "" : data.getEquipmentDetialName());
		}
		return employee;
	}

	private List<EmployeeForSendMsgData> getEmployeeForSendMsgData(AlarmBJZJInData inData, AlarmBJZJOutData data, UtUnitBaseinfo baseInfo, UtLzBjzjalarm alarm, String alarmType) throws Exception {
		List<EmployeeForSendMsgData> employees = utLzBjzjalarmMapper.getEmployeeByBJZJDeviceByBuild(data.getUnitID());
		List<EmployeeForSendMsgData> employee = new ArrayList<>();
		if (employees != null) {
			for (EmployeeForSendMsgData user : employees) {
				if (Util.isEmpty(user.getBuildAreaId()) || user.getBuildAreaId().equals(String.valueOf(alarm.getBuildAreaId()))) {
					employee.add(user);
				}
			}
			for (EmployeeForSendMsgData employeeForSendMsgData : employee) {
				employeeForSendMsgData.setAlarmArea(inData.getAlarm_WhereDesc());
				employeeForSendMsgData.setAlarmDetail(inData.getAlarm_SourceDesc() + " " + inData.getAlarm_DeviceDesc());
				employeeForSendMsgData.setAlarmTime(inData.getTime());
				employeeForSendMsgData.setAlarmType(alarmType);
				employeeForSendMsgData.setEqName(data.getEqname());
				employeeForSendMsgData.setAlarmId(alarm.getId() + "");
				employeeForSendMsgData.setUnitName(baseInfo.getUnitname());
				employeeForSendMsgData.setAlarmId(alarm.getId() + "");
			}
		}
		return employee;
	}

    /**
     *@描述  根据主机ID查询当前主机的所有点位
     *@创建人 Jie.Lei
     *@参数
     *@返回值
     *@创建时间 2019/7/24
     */
    @Override
    public PageInfo<AddresselHostpointOut>  getPointByEqid(AlarmInData alarmInData) {
        PageHelper.startPage(alarmInData.getPageNumber(), alarmInData.getPageSize());
        List<AddresselHostpointOut> list = utEqAddressRelMapper.getPointByEqid(alarmInData.getId());
//        PageInfo<AddresselHostpointOut> pager = new PageInfo<InspectPlanOutData>(list);
        PageInfo<AddresselHostpointOut> pager = new PageInfo<>(list);
        return pager;
    }

}