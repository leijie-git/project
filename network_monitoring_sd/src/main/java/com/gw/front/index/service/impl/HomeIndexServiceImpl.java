package com.gw.front.index.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.ExportExcel;
import com.gw.common.SnowflakeIdWorker;
import com.gw.exception.ServiceException;
import com.gw.firePower.data.FirePowerOutData;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.firePower.data.SquadronOutData;
import com.gw.front.couplet.data.FrontCoupletFireAlarmOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.*;
import com.gw.front.index.service.HomeIndexService;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.login.service.FrontLoginService;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.unit.data.MaintenanceUnitOutData;
import com.gw.util.Token;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import com.gw.util.UtilMessage;
import com.gw.wechat.data.PhoneCalibrationOutData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@Service
public class HomeIndexServiceImpl implements HomeIndexService {

	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Resource
	private UtMaintenanceUnitMapper utMaintenanceUnitMapper;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtMaintenanceLoginLogMapper utMaintenanceLoginLogMapper;
	@Resource
	private UtUnitNotifyMapper utUnitNotifyMapper;
	@Resource
	private UtUserNotifyRelMapper utUserNotifyRelMapper;
	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Resource
	private UtInspectTaskdetialMapper utInspectTaskdetialMapper;
	@Resource
	private UtUserServerMapper utUserServerMapper;
	@Resource
	private UtUserServerTradesMapper utUserServerTradesMapper;
	@Resource
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtUserDutyLogMapper utUserDutyLogMapper;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtPhoneLogMapper utPhoneLogMapper;
	@Autowired
	private UtFirePowerMapper utFirePowerMapper;
	@Resource
	private SysOperationLogMapper sysOperationLogMapper;
	@Autowired
	private UtFireStationMapper utFireStationMapper;
	@Autowired
	private UtUnitCalibrationMapper utUnitCalibrationMapper;
	@Autowired
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	@Autowired
	private UtSquadronMapper UtSquadronMapper;
	@Autowired
	private FrontLoginService frontLoginService;
	@Autowired
	private UtUnitEventMapper utUnitEventMapper;
	@Resource
	private UtUnitVideoMapper utUnitVideoMapper;
	@Resource
	private UtDevicestatusStatMapper utDevicestatusStatMapper;
	@Resource
	private PropertiesManageService propertiesManageService;
	@Autowired
	private UtBaseUserRelationMapper utBaseUserRelationMapper;

	@Value("${raw.data.database}")
	private String database;
//	@Value("${map.center}")
//	private String mapCenter;
//	@Value("${map.level}")
//	private String mapLevel;
//	@Value("${video.appkey}")
//	private String appkey;
//	@Value("${video.appsecret}")
//	private String appsecret;

	@Override
	public FrontFireOutData fireList(FrontAlarmInData inData) throws Exception {
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
//		FrontFireOutData outData = utLzBjzjalarmMapper.statFiresByBuild(inData.getUserId());
		FrontFireOutData outData = utLzBjzjalarmMapper.statFires(inData.getUserId(),"2");
//		FrontFireOutData frontFireOutData = utLzBjzjalarmMapper.statFiresByBJZJBuild(inData.getUserId());
		FrontFireOutData frontFireOutData = utLzBjzjalarmMapper.statFires(inData.getUserId(),"3");
		List<FrontFireInfoOutData> alarmListByBJZJBuild = utLzBjzjalarmMapper.getAlarmList(inData.getUserId(), "1", inData.getIsDeal(),"3");
		List<FrontFireInfoOutData> alarmListByBuild = utLzBjzjalarmMapper.getAlarmList(inData.getUserId(), "1", inData.getIsDeal(), "2");
		if (Util.isNotEmpty(userId)) {
			if (Util.isEmpty(outData)) {
//				outData = utLzBjzjalarmMapper.statFiresByBJZJBuild(inData.getUserId());
				outData = utLzBjzjalarmMapper.statFires(inData.getUserId(),"3");
				if (Util.isNotEmpty(outData)) {
					outData.setFireInfoList(alarmListByBJZJBuild);
				}
			} else {
				if (Util.isNotEmpty(frontFireOutData)) {
					Integer dealCount = frontFireOutData.getDealCount();
					Integer noDealCount = frontFireOutData.getNoDealCount();
					Integer dealCount1 = outData.getDealCount();
					Integer noDealCount1 = outData.getNoDealCount();
					Integer i = dealCount1 + dealCount;
					Integer j = noDealCount1 + noDealCount;
					alarmListByBJZJBuild.addAll(alarmListByBuild);
					Collections.sort(alarmListByBJZJBuild, new Comparator<FrontFireInfoOutData>() {
						@Override
						public int compare(FrontFireInfoOutData o1, FrontFireInfoOutData o2) {
							return o1.getTime().compareTo(o2.getTime());
						}
					});
					outData.setDealCount(i);
					outData.setNoDealCount(j);
					outData.setFireInfoList(alarmListByBJZJBuild);
				} else {
//					outData = utLzBjzjalarmMapper.statFiresByBuild(inData.getUserId());
					outData = utLzBjzjalarmMapper.statFires(inData.getUserId(),"2");
					if (Util.isNotEmpty(outData)) {
						outData.setFireInfoList(alarmListByBuild);
					}
				}
			}
		} else {
			outData = utLzBjzjalarmMapper.statFires(inData.getUserId(),"1");
			if (Util.isNotEmpty(outData)) {
				outData.setFireInfoList(utLzBjzjalarmMapper.getAlarmList(inData.getUserId(), "1", inData.getIsDeal(), "1"));
			}
		}
		return outData;
	}

	@Override
	public FrontUnitOutData getUnitInfo(String wbunitid, String unitId) throws Exception {
		FrontUnitOutData outData = new FrontUnitOutData();
		if (Util.isEmpty(unitId)) {
			UtMaintenanceUnit unit = utMaintenanceUnitMapper.selectByPrimaryKey(Long.valueOf(wbunitid));
			if (Util.isNotEmpty(unit)) {
				outData.setId(String.valueOf(unit.getId()));
				outData.setAddress(unit.getAddress());
				outData.setPhone(unit.getTelephone());
				outData.setContacts(unit.getContacts());
				outData.setUnitName(unit.getUnitname());
				outData.setUnitCode(unit.getUnitcode());
				outData.setUnitPoint(unit.getPointx() + "," + unit.getPointy());
				outData.setUnitType("0");
			}
			return outData;
		}
		UtUnitBaseinfo baseinfo = utUnitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(unitId));
		outData.setId(String.valueOf(baseinfo.getId()));
		outData.setAddress(baseinfo.getUnitaddress());
		outData.setPhone(baseinfo.getPhone());
		outData.setContacts(baseinfo.getSaferesponsiblepersonname());
		outData.setUnitName(baseinfo.getUnitname());
		outData.setUnitCode(baseinfo.getUnitcode());
		outData.setUnitPoint(baseinfo.getUnitpoint());
		outData.setUnitType("1");
		return outData;
	}

	@Override
	public List<FrontFireInfoOutData> getAlarmListByType(String userId, String type, String isDeal) throws Exception {
		List<FrontFireInfoOutData> alarmList = utLzBjzjalarmMapper.getAlarmList(userId, type, isDeal, "2");
		if (Util.isEmpty(alarmList)) {
			alarmList = utLzBjzjalarmMapper.getAlarmList(userId, type, isDeal, "1");
		}
		for (FrontFireInfoOutData frontFireInfoOutData : alarmList) {
			frontFireInfoOutData.setAlarmStatus(deal(frontFireInfoOutData.getAlarmStatus()));
		}
		return alarmList;
	}

	@Override
	public List<FrontUnitPointOutData> getUnitMapPoint(String userId, String sysType, String alarmType, String unitName)
			throws Exception {
		List<FrontUnitPointOutData> outData = utUnitBaseinfoMapper.getUnitMapPoint(userId, sysType, alarmType,
				unitName,"1");
		return outData;
	}

	@Override
	public List<FrontUnitPointOutData> getUnitMapPointByBJZJ(String userId, String sysType, String alarmType, String unitName)
			throws Exception {
		List<FrontUnitPointOutData> outData = utUnitBaseinfoMapper.getUnitMapPoint(userId, sysType, alarmType,
				unitName,"2");
		return outData;
	}

	@Override
	public List<FrontUnitPointOutData> getUnitMapPointByRTU(String userId, String sysType, String alarmType, String unitName)
			throws Exception {
		List<FrontUnitPointOutData> outData = utUnitBaseinfoMapper.getUnitMapPointByRTU(userId, sysType, alarmType,
				unitName);
		return outData;
	}

	@Override
	public List<FrontNotifyOutData> getNotifyList(String userId) throws Exception {
		return utUnitNotifyMapper.getNotifyList(userId);
	}

	@Override
	public PageInfo<FrontLoginLogOutData> getLoginLogs(String userId, Integer pageNumber, Integer pageSize)
			throws Exception {
		PageHelper.startPage(pageNumber, pageSize);
		List<FrontLoginLogOutData> list = utMaintenanceLoginLogMapper.getLoginLogs(userId, "1");
		for (FrontLoginLogOutData frontLoginLogOutData : list) {
			if (Util.isEmpty(frontLoginLogOutData.getLoginDate())
					|| Util.isEmpty(frontLoginLogOutData.getLoginOutDate())) {
				continue;
			}
			Date loginDate = UtilConv.str2Date(frontLoginLogOutData.getLoginDate(), UtilConv.DATE_YYYY_MM_DD_SS);
			Date logoutDate = UtilConv.str2Date(frontLoginLogOutData.getLoginOutDate(), UtilConv.DATE_YYYY_MM_DD_SS);
			long time = logoutDate.getTime() - loginDate.getTime();
			long hours = time / (1000 * 60 * 60);
			long mins = time / (1000 * 60) - hours * 60;
			long seconds = time / 1000 - mins * 60 - hours * 60 * 60;
			frontLoginLogOutData.setOnlineTime(hours + "时" + mins + "分" + seconds + "秒");
		}
		PageInfo<FrontLoginLogOutData> pageInfo = new PageInfo<FrontLoginLogOutData>(list);
		return pageInfo;
	}

	@Override
	public FrontNotifyOutData getNotifyInfo(String id) throws Exception {
		return utUnitNotifyMapper.getNotifyInfoById(id);
	}

	@Override
	public void deleteNotify(String id, String userId) throws Exception {
		UtUserNotifyRel rel = new UtUserNotifyRel();
		rel.setNotifyId(Long.valueOf(id));
		rel.setUnitUserId(Long.valueOf(userId));
		utUserNotifyRelMapper.delete(rel);
	}

	@Override
	public FrontUnitInfoStatOutData netUnitAndDeviceStatus(String userId, String startDate, String endDate)
			throws Exception {

		// 平台设备在线离线情况
		FrontUnitInfoStatOutData outData = utUnitNetdeviceMapper.getUnitNetdeviceList(userId, database);
		if (outData == null) {
			outData = new FrontUnitInfoStatOutData();
			outData.setOfflineDeviceCount(0);
			outData.setOnlineDeviceCount(0);
		}
		int onlineCount = 0;
		int offlineCount = 0;
		Map<String, Integer> nbStatusCount = utUnitNetdeviceMapper.getNBStatusCount(userId, database);
		if (null != nbStatusCount) {
			onlineCount = nbStatusCount.get("onlineCount");
			offlineCount = nbStatusCount.get("offlineCount");
		}
		outData.setOfflineDeviceCount(outData.getOfflineDeviceCount() + offlineCount);
		outData.setOnlineDeviceCount(outData.getOnlineDeviceCount() + onlineCount);
		// 查询联网单位数量
		Integer count = utUnitBaseinfoMapper.getNetUnitCount(userId);
		outData.setUnitUserCount(count);

		if (Util.isEmpty(startDate)) {
			startDate = UtilConv.date2Str(DateUtils.addDays(new Date(), -30), UtilConv.DATE_YYYY_MM_DD_CHN);
		}
		List<FrontUtDevicestatusStatOutData> Records = utDevicestatusStatMapper.getdeviceStatList(userId, startDate,
				endDate);
		List<FrontRecordOutData> onlineRecords = new ArrayList<>();
		List<FrontRecordOutData> offLineRecords = new ArrayList<>();
		for (FrontUtDevicestatusStatOutData frontUtDevicestatusStatOutData : Records) {
			FrontRecordOutData onlinedata = new FrontRecordOutData();
			onlinedata.setRecordCount(frontUtDevicestatusStatOutData.getOnline() + onlineCount);
			onlinedata.setRecordKey(frontUtDevicestatusStatOutData.getCreateDate());
			onlineRecords.add(onlinedata);
			FrontRecordOutData offLinedata = new FrontRecordOutData();
			offLinedata.setRecordKey(frontUtDevicestatusStatOutData.getCreateDate());
			offLinedata.setRecordCount(frontUtDevicestatusStatOutData.getOutline() + offlineCount);
			offLineRecords.add(offLinedata);
		}
		outData.setOffLineRecords(offLineRecords);
		outData.setOnlineRecords(onlineRecords);
		return outData;
	}

	@Override
	public FrontPatrolAbnormalOutData getPatrolAbnormal(String userId) throws Exception {
		return utInspectTaskdetialMapper.getPatrolAbnormal(userId);
	}

	@Override
	public List<FrontRecordOutData> getFireAlarmStatByProperty(String userId) throws Exception {
		return utLzBjzjalarmMapper.getFireAlarmStatByProperty(userId, "UseNature");// 使用性质
	}

	@Override
	public List<FrontRecordOutData> getFireAlarmStatByLevel(String userId) throws Exception {
		return utLzBjzjalarmMapper.getFireAlarmStatByProperty(userId, "NHDJ");// 耐火等级
	}

	@Override
	public FrontUserServerOutData getUserServer() throws Exception {
		List<UtUserServer> list = utUserServerMapper.selectAll();
		FrontUserServerOutData outData = new FrontUserServerOutData();
		if (Util.isNotEmpty(list)) {
			UtUserServer utUserServer = list.get(0);
			BeanUtils.copyProperties(utUserServer, outData);
			outData.setReleaseDate(UtilConv.date2Str(utUserServer.getReleaseDate(), UtilConv.DATE_YYYY_MM_DD_SS));
			return outData;
		}
		return outData;
	}

	@Override
	public List<FrontUnitServerLogOutData> getUserServerLogs() throws Exception {
		List<UtUserServerTrades> list = utUserServerTradesMapper.selectAll();
		List<FrontUnitServerLogOutData> outDatas = new ArrayList<>();
		for (UtUserServerTrades utUserServerTrades : list) {
			FrontUnitServerLogOutData outData = new FrontUnitServerLogOutData();
			BeanUtils.copyProperties(utUserServerTrades, outData);
			outDatas.add(outData);
		}
		return outDatas;
	}

	@Override
	public List<FrontFireInfoOutData> getRealTimeAlarms(FrontUnitUserOutData sessinInfo, String type) throws Exception {
		// 分析告警类型
		List<String> lists = new ArrayList<String>();
		List<String> RTUlist = new ArrayList<String>();
		FrontAlarmInData inData = new FrontAlarmInData();
		lists.add("1");
		lists.add("22");
		lists.add("23");
		lists.add("24");
		lists.add("25");
		lists.add("26");
		// 告警必然查询火灾报警
		if (Util.isNotEmpty(type)) {
			String[] data = type.split(",");
			for (String s : data) {
				lists.add(s);
				if ("200".equals(s)) {
					RTUlist.add("1");
					RTUlist.add("3");
					RTUlist.add("4");
					RTUlist.add("5");
					RTUlist.add("6");
					RTUlist.add("7");
					RTUlist.add("8");
					RTUlist.add("9");
				} else if ("300".equals(s)) {
					RTUlist.add("2");
				} else if ("810".equals(s)) {
//					inData.setCalibrationCode("1");
					RTUlist.add("810");
				}
			}
		}
		inData.setLoginTime(sessinInfo.getLoginTime());
		log.info("loginTime:" + sessinInfo.getLoginTime());
		inData.setUserId(sessinInfo.getId());
		inData.setTypes(lists);
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(sessinInfo.getId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		//sql查找标识
		inData.setSelectType("2");
		List<FrontFireInfoOutData> realTimeAlarms = utLzBjzjalarmMapper.getRealTimeAlarms(inData);
		if (Util.isEmpty(userId)) {
			inData.setSelectType("1");
			realTimeAlarms = utLzBjzjalarmMapper.getRealTimeAlarms(inData);
			if (Util.isNotEmpty(realTimeAlarms)) {
				for (FrontFireInfoOutData frontFireInfoOutData : realTimeAlarms) {
					frontFireInfoOutData.setAlarmStatus(deal(frontFireInfoOutData.getAlarmStatus()));
				}
			} else {
				realTimeAlarms = new ArrayList<>();
			}
			List<FrontFireInfoOutData> rtualarms = null;
			if (Util.isNotEmpty(RTUlist)) {
				inData.setTypes(RTUlist);
				rtualarms = utLzFireequipmentalarmMapper.getRealRTUAlarmList(inData);
				realTimeAlarms.addAll(rtualarms);
			}
		} else {
			if (Util.isEmpty(realTimeAlarms)) {
				inData.setSelectType("3");
				realTimeAlarms = utLzBjzjalarmMapper.getRealTimeAlarms(inData);
			} else {
				inData.setSelectType("3");
				List<FrontFireInfoOutData> realTimeBJZJAlarms = utLzBjzjalarmMapper.getRealTimeAlarms(inData);
				if (Util.isNotEmpty(realTimeBJZJAlarms)) {
					realTimeAlarms.addAll(realTimeBJZJAlarms);
					Collections.sort(realTimeAlarms, new Comparator<FrontFireInfoOutData>() {
						@Override
						public int compare(FrontFireInfoOutData o1, FrontFireInfoOutData o2) {
							return o2.getLastupdate().compareTo(o1.getLastupdate());
						}
					});
				}
			}
			if (Util.isNotEmpty(realTimeAlarms)) {
				for (FrontFireInfoOutData frontFireInfoOutData : realTimeAlarms) {
					frontFireInfoOutData.setAlarmStatus(deal(frontFireInfoOutData.getAlarmStatus()));
				}
			}
			List<FrontFireInfoOutData> rtualarms = null;
			if (Util.isNotEmpty(RTUlist)) {
				inData.setTypes(RTUlist);
				rtualarms = utLzFireequipmentalarmMapper.getRealRTUAlarmListByBuild(inData);
				if (Util.isNotEmpty(rtualarms)) {
					realTimeAlarms.addAll(rtualarms);
				}
			}
		}
		Collections.sort(realTimeAlarms, new Comparator<FrontFireInfoOutData>() {
			public int compare(FrontFireInfoOutData arg0, FrontFireInfoOutData arg1) {
				return -1 * arg0.getTime().compareTo(arg1.getTime());
			}
		});
		return realTimeAlarms;
	}

	// 告警类型
	private String deal(String alarmStatus) {
		if (Util.isEmpty(alarmStatus)) {
			return "";
		}
		switch (alarmStatus) {
			case "0":
				return "手动报警";
			case "1":
				return "火警";
			case "2":
				return "故障";
			case "3":
				return "启动";
			case "4":
				return "反馈";
			case "5":
				return "监管";
			case "6":
				return "屏蔽";
			case "7":
				return "恢复";
			case "8":
				return "复位";
			case "9":
				return "用户传输装置操作";
			case "11":
				return "火警恢复";
			case "12":
				return "故障恢复";
			case "13":
				return "停止";
			case "14":
				return "反馈撤销";
			case "15":
				return "监管撤销";
			case "16":
				return "屏蔽撤销";
			case "20":
				return "消音";
			case "21":
				return "解除报警";
			case "22":
				return "自动火警有人";
			case "23":
				return "自动火警超时";
			case "24":
				return "自动火警无人";
			case "25":
				return "确认火警";
			case "26":
				return "紧急火警";
			case "27":
				return "预警";
			case "28":
				return "报警";
			case "30":
				return "传输装置开机";
			case "31":
				return "传输装置关机";
			case "32":
				return "控制器开机";
			case "33":
				return "控制器关机";
			case "34":
				return "RTU开机";
			case "35":
				return "RTU关机";
			default:
				break;
		}
		return "";
	}

	// 设备状态
	private String dealDeviceStatus(String online) {
		if (Util.isEmpty(online)) {
			return "";
		}
		switch (online) {
			case "0":
				return "离线";
			case "1":
				return "在线";
			case "2":
				return "未注册";
			case "3":
				return "数据超时";
			case "4":
				return "异常";
			case "5":
				return "迁移";
			case "6":
				return "迁移失败";
			case "7":
				return "迁移成功";
			case "16":
				return "禁止报警主机接入";
			case "17":
				return "允许报警主机接入";
			case "18":
				return "报警主机正常";
			case "19":
				return "协议转换与传输装置通信异常";
			case "20":
				return "协议转换器与报警主机通信异常";
			case "21":
				return "关机状态";
			case "32":
				return "禁止RTU接入";
			case "33":
				return "允许RTU接入";
			case "34":
				return "RTU正常";
			case "35":
				return "RTU与传输装置通信异常";
			case "36":
				return "RTU与传感器故障";
			case "48":
				return "禁止电气火灾接入";
			default:
				break;
		}
		return "";
	}

	@Override
	@Transactional
	public FrontUnitUserOutData addUserDutyLog(HttpServletRequest request, FrontUserDutyLogInData inData,
											   FrontUnitUserOutData sessinInfo) throws Exception {
		String shiftUnitID = "";// 当班用户单位
		String dutyUnitID = "";// 接班用户单位
		Example example = new Example(UtUnitUser.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("account", inData.getShiftUserAccount()).andEqualTo("password",
				DigestUtils.md5Hex(inData.getShiftUserPassword()));
		List<UtUnitUser> shiftUser = utUnitUserMapper.selectByExample(example);
		if (Util.isEmpty(shiftUser)) {
			throw new ServiceException("当班用户名或密码错误！");
		} else {
			for (UtUnitUser user : shiftUser) {
				shiftUnitID = user.getUnitid().toString();
			}
		}
		Example dutyexample = new Example(UtUnitUser.class);
		Criteria dutycriteria = dutyexample.createCriteria();
		dutycriteria.andEqualTo("account", inData.getDutyUserAccount());
		List<UtUnitUser> dutyUser = utUnitUserMapper.selectByExample(dutyexample);
		if (Util.isNotEmpty(dutyUser)) {
			for (UtUnitUser user : dutyUser) {
				dutyUnitID = user.getUnitid().toString();
			}
		}
		if (!shiftUnitID.equals(dutyUnitID)) {
			throw new ServiceException("交接班用户不是同一单位！");
		}
		// 更新退出记录
		frontLoginService.updateLog(Long.parseLong(sessinInfo.getCurrentLogId()));
		FrontUnitUserOutData loginData = new FrontUnitUserOutData();
		loginData.setAccount(inData.getDutyUserAccount());
		loginData.setPassword(inData.getDutyUserPassword());
		loginData.setCurrAddress(sessinInfo.getCurrAddress());
		loginData.setIp(sessinInfo.getIp());
		FrontUnitUserOutData data;
		try {
			data = frontLoginService.login(request, loginData);
		} catch (ServiceException e) {
			throw new ServiceException("接班用户名或密码错误！");
		}

		// 新增交接班记录
		UtUserDutyLog utUserDutyLog = new UtUserDutyLog();
		BeanUtils.copyProperties(inData, utUserDutyLog);
		utUserDutyLog.setId(snowflakeIdWorker.nextId());
		utUserDutyLog.setShiftUser(shiftUser.get(0).getId());
		utUserDutyLog.setDutyUser(Long.valueOf(data.getId()));
		UtUserDutyLog lastLog = utUserDutyLogMapper.getLastLog();
		Date date = new Date();
		if (Util.isNotEmpty(lastLog)) {
			lastLog.setEndDate(date);
			utUserDutyLogMapper.updateByPrimaryKey(lastLog);
		}
		utUserDutyLog.setStartDate(date);
		utUserDutyLogMapper.insert(utUserDutyLog);
		return data;
	}

	@Override
	public FrontHistoryStatisticalInfoOutData getStatisticalInfo(String userId) throws Exception {
		FrontHistoryStatisticalInfoOutData outData = new FrontHistoryStatisticalInfoOutData();
		outData.setTotal(utUnitBaseinfoMapper.getTotal(userId, database, null).size());
		outData.setOnLine(utUnitBaseinfoMapper.getOnline(userId, database, null).size());
		outData.setFireAlarm(utLzBjzjalarmMapper.getFireStatistical(userId, 1, null).size());
		outData.setFault(utLzBjzjalarmMapper.getFireStatistical(userId, 2, null).size());
		outData.setStartUp(utLzBjzjalarmMapper.getFireStatistical(userId, 3, null).size());
		outData.setFeedBack(utLzBjzjalarmMapper.getFireStatistical(userId, 4, null).size());
		outData.setSupervise(utLzBjzjalarmMapper.getFireStatistical(userId, 5, null).size());
		outData.setShield(utLzBjzjalarmMapper.getFireStatistical(userId, 6, null).size());
		outData.setReset(utLzBjzjalarmMapper.getFireStatistical(userId, 7, null).size());

		outData.setConcern(utLzBjzjalarmMapper.getCalibrationType(userId, 4, null).size());
		outData.setReport(utLzBjzjalarmMapper.getCalibrationType(userId, 1, null).size());
		outData.setTest(utLzBjzjalarmMapper.getCalibrationType(userId, 2, null).size());
		return outData;

		// List<FrontStatisticalOutData> list =
		// utLzBjzjalarmMapper.getStatisticalInfo();
		// Map<String, FrontHistoryStatisticalInfoOutData> map = new
		// HashMap<String,
		// FrontHistoryStatisticalInfoOutData>();
		// int onLine = 0;
		// int total = 0;
		// int fireAlarm = 0;
		// int concern = 0;
		// int report = 0;
		// int test = 0;
		// int fault = 0;
		// int startUp = 0;
		// int feedBack = 0;
		// int supervise = 0;
		// int shield = 0;
		// int reset = 0;
		// for (int i=0;i<list.size();i++) {
		// if(i==0){
		// map.put(list.get(i).getUnitId(), new
		// FrontHistoryStatisticalInfoOutData());
		// }else{
		// if(list.get(i).getUnitId().equals(list.get(i-1).getUnitId())){
		// continue;
		// }
		// map.put(list.get(i).getUnitId(), new
		// FrontHistoryStatisticalInfoOutData());
		// }
		// }
		// for (FrontStatisticalOutData frontStatisticalOutData : list) {
		// if(Util.isNotEmpty(frontStatisticalOutData.getDeviceStatus())){
		// if(frontStatisticalOutData.getDeviceStatus()==1){
		// map.get(frontStatisticalOutData.getUnitId()).setOnLine(1);
		// }
		// }
		// if(Util.isNotEmpty(frontStatisticalOutData.getAlarmStatus())){
		// if(frontStatisticalOutData.getAlarmStatus()==2){
		// map.get(frontStatisticalOutData.getUnitId()).setFireAlarm(1);
		// }
		// }
		// if(Util.isNotEmpty(frontStatisticalOutData.getCalibrationType())){
		// if(frontStatisticalOutData.getCalibrationType().equals("0")){
		// map.get(frontStatisticalOutData.getUnitId()).setTest(1);
		// }else if(frontStatisticalOutData.getCalibrationType().equals("1")){
		// map.get(frontStatisticalOutData.getUnitId()).setReport(1);
		// }
		// }
		// }
		// for(FrontHistoryStatisticalInfoOutData value : map.values()){
		// if(value.getOnLine()==1){
		// onLine++;
		// }
		// if(value.getFireAlarm()==1){
		// fireAlarm++;
		// }
		// if(value.getTest()==1)
		// test++;
		// if(value.getReport()==1)
		// report++;
		// total++;
		// }
		// outData.setConcern(concern);
		// outData.setFault(fault);
		// outData.setFeedBack(feedBack);
		// outData.setFireAlarm(fireAlarm);
		// outData.setOnLine(onLine);
		// outData.setReport(report);
		// outData.setReset(reset);
		// outData.setShield(shield);
		// outData.setStartUp(startUp);
		// outData.setSupervise(supervise);
		// outData.setTest(test);
		// outData.setTotal(total);
		//
		// return outData;

	}

	@Override
	public List<FrontOnlineStatisticalOutData> getUnitListByState(FrontStatisticalInData inData) throws Exception {
		inData.setDatabase(database);
		if (Util.isNotEmpty(inData.getAlarmStatus())) {
			List<FrontOnlineStatisticalOutData> outData = utLzBjzjalarmMapper.getFireStatistical(inData.getUserId(),
					inData.getAlarmStatus(), null);
			return outData;
		}
		if (Util.isNotEmpty(inData.getDeviceStatus())) {
			List<FrontOnlineStatisticalOutData> outData = utUnitBaseinfoMapper.getOnline(inData.getUserId(),
					inData.getDatabase(), null);
			return outData;
		}
		if (Util.isNotEmpty(inData.getCalibrationType())) {
			List<FrontOnlineStatisticalOutData> outData = utLzBjzjalarmMapper.getCalibrationType(inData.getUserId(),
					inData.getCalibrationType(), null);
			return outData;
		}
		return null;
	}

	@Override
	public List<FrontFireInfoOutData> getRealTimeAlarmsByUnitId(FrontStatisticalInData inData) throws Exception {
		return utLzBjzjalarmMapper.getRealTimeAlarmsByUnitId(inData);
	}

	@Override
	public List<FrontUnitInfoOutData> getUnitInfoById(FrontUnitUserOutData sessinInfo, String unitId) throws Exception {
		if ("0".equals(sessinInfo.getUsertype())) {
			return utMaintenanceUnitMapper.getMaintenanceUnitById(unitId);
		} else {
			return utUnitBaseinfoMapper.getUnitInfoById(unitId);
		}
	}

	@Override
	public List<FrontHisSDDeviceStatusOutData> getNetDeviceListByUnitId(FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getNetDevicesByUnitId(inData);
		for (FrontHisSDDeviceStatusOutData frontHisSDDeviceStatusOutData : list) {
			frontHisSDDeviceStatusOutData
					.setDeviceStatus(dealDeviceStatus(frontHisSDDeviceStatusOutData.getDeviceStatus()));
		}
		return list;
	}

	@Override
	public List<FrontUnitUserOutData> getUnitUserByUnitId(String unitId) throws Exception {
		return utUnitUserMapper.getUserListByUnitId(unitId);
	}

	@Override
	public MaintenanceUnitOutData getMaintenanceUnit(String unitId) throws Exception {
		return utMaintenanceUnitMapper.getMaintenanceUnit(unitId);
	}

	@Override
	@Transactional
	public void cancelConcern(String unitId) throws Exception {
		UtUnitBaseinfo utUnitBaseinfo = utUnitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(unitId));
		utUnitBaseinfo.setCalibrationId(null);
		utUnitBaseinfoMapper.updateByPrimaryKey(utUnitBaseinfo);

		Example example = new Example(UtUnitNetdevice.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("unitid", Long.valueOf(unitId));
		List<UtUnitNetdevice> list = utUnitNetdeviceMapper.selectByExample(example);
		if (Util.isNotEmpty(list)) {
			list.get(0).setCalibrationId(null);
			utUnitNetdeviceMapper.updateByPrimaryKey(list.get(0));
		}
	}

	@Override
	public void addPhoneLog(FrontPhoneLogInData inData) throws Exception {
		UtPhoneLog utPhoneLog = new UtPhoneLog();
		BeanUtils.copyProperties(inData, utPhoneLog, "longTime");
		utPhoneLog.setId(snowflakeIdWorker.nextId());
		if (Util.isNotEmpty(inData.getUnitId())) {
			utPhoneLog.setUnitId(Long.valueOf(inData.getUnitId()));
		}
		if (Util.isNotEmpty(inData.getSender())) {
			utPhoneLog.setSender(Long.valueOf(inData.getSender()));
		}
		if (Util.isNotEmpty(inData.getLongTime())) {
			utPhoneLog.setLongTime(inData.getLongTime());
		}
		utPhoneLog.setSendDate(new Date());
		utPhoneLogMapper.insert(utPhoneLog);
	}

	@Override
	public void alarmSwitchLog(HttpServletRequest request, FrontOperationLogInData inData) throws Exception {
		SysOperationLog log = new SysOperationLog();
		log.setId(snowflakeIdWorker.nextId());
		if (Util.isNotEmpty(inData.getUserId())) {
			log.setUserId(Long.parseLong(inData.getUserId()));
		}
		log.setAddress(inData.getAddress());
		log.setCreateDate(new Date());
		log.setRecordType("0");
		log.setRemark(inData.getMsgFrom());
		log.setUrl("/front/homeIndex/alarmSwitchLog");
		String currentStatus = inData.getCurrentStatus();
		log.setCurrentStatus(currentStatus);
		if ("0".equals(currentStatus)) {
			log.setContent("关闭声音开关");
		} else {
			log.setContent("开启声音开关");
		}
		Integer flag = sysOperationLogMapper.insert(log);
		if (flag < 1) {
			throw new ServiceException(UtilMessage.ADD_ERROR);
		}
	}

	@Override
	public void exportLoginLogs(HttpServletResponse response, String id) throws Exception {
		List<FrontLoginLogExport> exportDatas = new ArrayList<FrontLoginLogExport>();
		List<FrontLoginLogOutData> list = utMaintenanceLoginLogMapper.getLoginLogs(id, "1");
		for (FrontLoginLogOutData frontLoginLogOutData : list) {
			if (Util.isNotEmpty(frontLoginLogOutData.getLoginDate())
					&& Util.isNotEmpty(frontLoginLogOutData.getLoginOutDate())) {
				Date loginDate = UtilConv.str2Date(frontLoginLogOutData.getLoginDate(), UtilConv.DATE_YYYY_MM_DD_SS);
				Date logoutDate = UtilConv.str2Date(frontLoginLogOutData.getLoginOutDate(),
						UtilConv.DATE_YYYY_MM_DD_SS);
				long time = logoutDate.getTime() - loginDate.getTime();
				long hours = time / (1000 * 60 * 60);
				long mins = time / (1000 * 60) - hours * 60;
				long seconds = time / 1000 - mins * 60 - hours * 60 * 60;
				frontLoginLogOutData.setOnlineTime(hours + "时" + mins + "分" + seconds + "秒");
			}
			FrontLoginLogExport data = new FrontLoginLogExport();
			BeanUtils.copyProperties(frontLoginLogOutData, data);
			exportDatas.add(data);
		}
		String[] header = new String[]{"登录名称", "登录时间", "登录地点", "登录IP", "退出时间", "在线时长", "来源"};
		ExportExcel<FrontLoginLogExport> export = new ExportExcel<FrontLoginLogExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public List<FrontRecordOutData> getUnitStatByUnitType(String userId) throws Exception {
		return utUnitBaseinfoMapper.getUnitStatByUnitType(userId);
	}

	@Override
	public List<FrontRecordOutData> getUnitStatBySuperviseType(String userId) throws Exception {
		return utUnitBaseinfoMapper.getUnitStatBySuperviseType(userId);
	}

	@Override
	public void setNotifyIsRead(String id, String userId) throws Exception {
		Example example = new Example(UtUserNotifyRel.class);
		example.createCriteria().andEqualTo("unitUserId", Long.valueOf(userId)).andEqualTo("notifyId",
				Long.valueOf(id));
		UtUserNotifyRel utUserNotifyRel = new UtUserNotifyRel();
		utUserNotifyRel.setIsRead("1");
		utUserNotifyRelMapper.updateByExampleSelective(utUserNotifyRel, example);
	}

	@Override
	public List<FirePowerOutData> firePower(String userID, String type) throws Exception {
		return utFirePowerMapper.getFrontFirePowerList(null, type, userID);
	}

	@Override
	public RealTimeAlarmCount getRealTimeAlarmCount(String userId, String loginTime) throws Exception {
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(userId));
		List<UtBaseUseRelation> userIds = utBaseUserRelationMapper.selectByExample(example);
//		RealTimeAlarmCount bjzjAlarm = utLzBjzjalarmMapper.getRealTimeAlarmBuildCount(userId, loginTime, "2");
		RealTimeAlarmCount bjzjAlarm = utLzBjzjalarmMapper.getRealTimeAlarmCount(userId, loginTime, "2");
		RealTimeAlarmCount rtuAlarm = null;
		if (Util.isEmpty(userIds)) {
			bjzjAlarm = utLzBjzjalarmMapper.getRealTimeAlarmCount(userId, loginTime, "1");
			rtuAlarm = utLzFireequipmentalarmMapper.getRealTimeRTUCount(userId,
					loginTime);
		} else {
//			RealTimeAlarmCount bjzjBuildAlarm = utLzBjzjalarmMapper.getRealTimeAlarmBJZJBuildCount(userId, loginTime);
			RealTimeAlarmCount bjzjBuildAlarm = utLzBjzjalarmMapper.getRealTimeAlarmCount(userId, loginTime, "3");
			if (Util.isEmpty(bjzjAlarm)) {
				bjzjAlarm = bjzjBuildAlarm;
			} else {
				if (Util.isNotEmpty(bjzjBuildAlarm)) {
					int alarmCount = bjzjBuildAlarm.getAlarmCount();
					int electricAlarmCount = bjzjBuildAlarm.getElectricAlarmCount();
					int extingAlarmCount = bjzjBuildAlarm.getExtingAlarmCount();
					int faultAlarmCount = bjzjBuildAlarm.getFaultAlarmCount();
					int inforAlarmCount = bjzjBuildAlarm.getInforAlarmCount();
					int onlineAlarmCount = bjzjBuildAlarm.getOnlineAlarmCount();
					int otherAlarmCount = bjzjBuildAlarm.getOtherAlarmCount();
					int shieldAlarmCount = bjzjBuildAlarm.getShieldAlarmCount();
					int testAlarmCount = bjzjBuildAlarm.getTestAlarmCount();
					int waterAlarmCount = bjzjBuildAlarm.getWaterAlarmCount();
					int alarmCount1 = bjzjAlarm.getAlarmCount();
					int electricAlarmCount1 = bjzjAlarm.getElectricAlarmCount();
					int extingAlarmCount1 = bjzjAlarm.getExtingAlarmCount();
					int faultAlarmCount1 = bjzjAlarm.getFaultAlarmCount();
					int inforAlarmCount1 = bjzjAlarm.getInforAlarmCount();
					int onlineAlarmCount1 = bjzjAlarm.getOnlineAlarmCount();
					int otherAlarmCount1 = bjzjAlarm.getOtherAlarmCount();
					int shieldAlarmCount1 = bjzjAlarm.getShieldAlarmCount();
					int testAlarmCount1 = bjzjAlarm.getTestAlarmCount();
					int waterAlarmCount1 = bjzjAlarm.getWaterAlarmCount();
					int a = alarmCount + alarmCount1;
					int b = electricAlarmCount + electricAlarmCount1;
					int c = extingAlarmCount + extingAlarmCount1;
					int d = faultAlarmCount + faultAlarmCount1;
					int e = inforAlarmCount + inforAlarmCount1;
					int f = onlineAlarmCount + onlineAlarmCount1;
					int g = otherAlarmCount + otherAlarmCount1;
					int h = shieldAlarmCount + shieldAlarmCount1;
					int j = testAlarmCount + testAlarmCount1;
					int k = waterAlarmCount + waterAlarmCount1;
					bjzjAlarm.setAlarmCount(a);
					bjzjAlarm.setElectricAlarmCount(b);
					bjzjAlarm.setExtingAlarmCount(c);
					bjzjAlarm.setFaultAlarmCount(d);
					bjzjAlarm.setInforAlarmCount(e);
					bjzjAlarm.setOnlineAlarmCount(f);
					bjzjAlarm.setOtherAlarmCount(g);
					bjzjAlarm.setShieldAlarmCount(h);
					bjzjAlarm.setTestAlarmCount(j);
					bjzjAlarm.setWaterAlarmCount(k);
				}
			}
			rtuAlarm = utLzFireequipmentalarmMapper.getRealTimeByBuildRTUCount(userId,
					loginTime);
		}
		RealTimeAlarmCount count = new RealTimeAlarmCount();
		if (Util.isNotEmpty(bjzjAlarm)) {
			count.setTestAlarmCount(bjzjAlarm.getTestAlarmCount());
			count.setAlarmCount(bjzjAlarm.getAlarmCount());
			count.setFaultAlarmCount(bjzjAlarm.getFaultAlarmCount());
			count.setInforAlarmCount(bjzjAlarm.getInforAlarmCount());
			count.setOnlineAlarmCount(bjzjAlarm.getOnlineAlarmCount());
			count.setOtherAlarmCount(bjzjAlarm.getOtherAlarmCount());
			count.setShieldAlarmCount(bjzjAlarm.getShieldAlarmCount());
		}
		if (Util.isNotEmpty(rtuAlarm)) {
			count.setElectricAlarmCount(rtuAlarm.getElectricAlarmCount());
			count.setExtingAlarmCount(rtuAlarm.getExtingAlarmCount());
			count.setWaterAlarmCount(rtuAlarm.getWaterAlarmCount());
		}
		return count;
	}

	@Override
	public FireStationManageOutData firePowerDetail(String powerID) throws Exception {
		return utFireStationMapper.getFirePowerDetail(powerID);
	}

	@Override
	public List<FirePowerOutData> getFirePowers(String unitID, Integer instence) throws Exception {
		List<FirePowerOutData> outData = new ArrayList<>();
		UtUnitBaseinfo unit = utUnitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(unitID));
		if (Util.isNotEmpty(unit)) {
			String unitpoint = unit.getUnitpoint();
			if (Util.isEmpty(unitpoint)) {
				return outData;
			}
			String[] split = unitpoint.split(",");
			double lat1 = Double.valueOf(split[0]);
			double lng1 = Double.valueOf(split[1]);
			List<FirePowerOutData> powerList = utFirePowerMapper.getFrontFirePowerList(unitID, null, null);
			for (FirePowerOutData firePowerOutData : powerList) {
				String pointX = firePowerOutData.getPointX();
				String pointY = firePowerOutData.getPointY();
				if (Util.isEmpty(pointX) || Util.isEmpty(pointY)) {
					continue;
				}
				double distance = UtilConv.getDistance(lat1, lng1, Double.valueOf(pointX), Double.valueOf(pointY));
				if (instence * 1000 > distance) {
					outData.add(firePowerOutData);
				}
			}
		} else {
			List<FrontUnitOutData> byMaintenanceUnit = utUnitBaseinfoMapper.getByMaintenanceUnit(unitID);
			for (FrontUnitOutData frontUnitOutData : byMaintenanceUnit) {
				String unitpoint = frontUnitOutData.getUnitPoint();
				if (Util.isEmpty(unitpoint)) {
					return outData;
				}
				String[] split = unitpoint.split(",");
				double lat1 = Double.valueOf(split[0]);
				double lng1 = Double.valueOf(split[1]);
				List<FirePowerOutData> powerList = utFirePowerMapper.getFrontFirePowerList(unitID, null, null);
				for (FirePowerOutData firePowerOutData : powerList) {
					String pointX = firePowerOutData.getPointX();
					String pointY = firePowerOutData.getPointY();
					if (Util.isEmpty(pointX) || Util.isEmpty(pointY)) {
						continue;
					}
					double distance = UtilConv.getDistance(lat1, lng1, Double.valueOf(pointX), Double.valueOf(pointY));
					if (instence * 1000 > distance) {
						outData.add(firePowerOutData);
					}
				}
			}
		}
		return outData;
	}

	@Override
	public void deviceCalibration(String deviceId, String calibrationId) throws Exception {
		UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(deviceId));
		netdevice.setCalibrationId(Long.valueOf(calibrationId));
		UtUnitEvent event = new UtUnitEvent();
		event.setId(snowflakeIdWorker.nextId());
		event.setDeviceindex(netdevice.getDeviceindex());
		event.setDeviceno(netdevice.getDeviceno());
		event.setOwnercode(netdevice.getOwnercode());
		event.setStarttime(new Date());
		event.setEventid(Long.valueOf(calibrationId));
		utUnitEventMapper.insert(event);
		utUnitNetdeviceMapper.updateByPrimaryKey(netdevice);
	}

	@Override
	public List<PhoneCalibrationOutData> calibrationList() throws Exception {
		List<PhoneCalibrationOutData> outData = new ArrayList<>();
		List<UtUnitCalibration> list = utUnitCalibrationMapper.selectAll();
		for (UtUnitCalibration utUnitCalibration : list) {
			PhoneCalibrationOutData data = new PhoneCalibrationOutData();
			BeanUtils.copyProperties(utUnitCalibration, data);
			data.setId(utUnitCalibration.getId() + "");
			outData.add(data);
		}
		return outData;
	}

	@Override
	public List<FirePowerOutData> fireUnitPower(String unitID) throws Exception {
		return utFirePowerMapper.getFrontUnitFirePowerList(unitID);
	}

	@Override
	public SquadronOutData fireSquadronPowerDetail(String powerID) throws Exception {
		return UtSquadronMapper.getFireSquadronPowerDetail(powerID);
	}

	@Override
	public PageInfo<FrontFireInfoOutData> getAlarmByUnitId(FrontStatisticalInData inData) throws Exception {
		// 分析告警类型
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontFireInfoOutData> realTimeAlarms = utLzBjzjalarmMapper.getAlarmByUnitId(inData);
		if (Util.isNotEmpty(realTimeAlarms)) {
			for (FrontFireInfoOutData frontFireInfoOutData : realTimeAlarms) {
				frontFireInfoOutData.setAlarmStatus(deal(frontFireInfoOutData.getAlarmStatus()));
			}
		}
		PageInfo<FrontFireInfoOutData> pageInfo = new PageInfo<FrontFireInfoOutData>(realTimeAlarms);
		return pageInfo;
	}

	@Override
	public String getUnitName(String unitid) throws Exception {
		if (Util.isEmpty(unitid)) {
			return "";
		}
		UtUnitBaseinfo unit = utUnitBaseinfoMapper.selectByPrimaryKey(Long.parseLong(unitid));
		if (Util.isEmpty(unit)) {
			return "";
		}
		return unit.getUnitname();
	}

	@Override
	public PageInfo<FrontOnlineStatisticalOutData> getUnitPageListByState(FrontStatisticalInData inData)
			throws Exception {
		inData.setDatabase(database);
		if (Util.isNotEmpty(inData.getAlarmStatus())) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			List<FrontOnlineStatisticalOutData> list = utLzBjzjalarmMapper.getFireStatistical(inData.getUserId(),
					inData.getAlarmStatus(), inData.getUnitname());
			return new PageInfo<FrontOnlineStatisticalOutData>(list);
		} else if (Util.isNotEmpty(inData.getCalibrationType())) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			List<FrontOnlineStatisticalOutData> list = utLzBjzjalarmMapper.getCalibrationType(inData.getUserId(),
					inData.getCalibrationType(), inData.getUnitname());
			return new PageInfo<FrontOnlineStatisticalOutData>(list);
		} else {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			List<FrontOnlineStatisticalOutData> list = utUnitBaseinfoMapper.getTotal(inData.getUserId(),
					inData.getDatabase(), inData.getUnitname());
			return new PageInfo<FrontOnlineStatisticalOutData>(list);
		}
	}

	@Override
	public List<FrontFireInfoOutData> getAlarmInfo(FrontUnitUserOutData sessinInfo) throws Exception {
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(sessinInfo.getId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		// 分析告警类型
		FrontAlarmInData inData = new FrontAlarmInData();
		inData.setUserId(sessinInfo.getId());
		List<FrontFireInfoOutData> realTimeAlarms = null;
		List<FrontFireInfoOutData> rtualarms = null;
		if (Util.isEmpty(userId)) {
			//sql查找标识
			inData.setSelectType("1");
			realTimeAlarms = utLzBjzjalarmMapper.getAllAlarms(inData);
			rtualarms = utLzFireequipmentalarmMapper.getAllRTUAlarmList(inData);
			if (Util.isNotEmpty(realTimeAlarms)) {
				for (FrontFireInfoOutData frontFireInfoOutData : realTimeAlarms) {
					frontFireInfoOutData.setAlarmStatus(deal(frontFireInfoOutData.getAlarmStatus()));
				}
			}
			realTimeAlarms.addAll(rtualarms);
		} else {
			// 分析告警类型
			//sql查找标识
			inData.setSelectType("2");
			realTimeAlarms = utLzBjzjalarmMapper.getAllAlarms(inData);
			if(Util.isEmpty(realTimeAlarms)) {
				//sql查找标识
				inData.setSelectType("3");
				realTimeAlarms = utLzBjzjalarmMapper.getAllAlarms(inData);
			}
			rtualarms = utLzFireequipmentalarmMapper.getAllRTUAlarmListByBuild(inData);
			if (Util.isNotEmpty(realTimeAlarms)) {
				for (FrontFireInfoOutData frontFireInfoOutData : realTimeAlarms) {
					frontFireInfoOutData.setAlarmStatus(deal(frontFireInfoOutData.getAlarmStatus()));
				}
			}
			realTimeAlarms.addAll(rtualarms);
			Collections.sort(realTimeAlarms, new Comparator<FrontFireInfoOutData>() {
				@Override
				public int compare(FrontFireInfoOutData o1, FrontFireInfoOutData o2) {
					return o2.getLastupdate().compareTo(o1.getLastupdate());
				}
			});
		}

		return realTimeAlarms;
	}

	@Override
	public FrontVideoOutData getVideoDetail(String videoID) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		FrontVideoOutData video = utUnitVideoMapper.getVideoDetail(videoID);

		UtUnitBaseinfo utUnitBaseinfo = utUnitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(video.getUnitId()));
		String token;
		String appKey;
		if (Util.isNotEmpty(utUnitBaseinfo) &&
				Util.isNotEmpty(utUnitBaseinfo.getVideoAppkey()) &&
				Util.isNotEmpty(utUnitBaseinfo.getVideoAppsecret())) {
			appKey = utUnitBaseinfo.getVideoAppkey();
			token = Token.getToken(appKey, utUnitBaseinfo.getVideoAppsecret());
		} else {
			appKey = properties.getVideoAppkey();
			token = Token.getToken(appKey, properties.getVideoAppsecret());
		}
		video.setAppKey(appKey);
		video.setToken(token);
		return video;
	}

	@Override
	public FrontAlarmTypeOutData getAlarmListByTypeCount(String id) throws Exception {
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(id));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		FrontAlarmTypeOutData outData = new FrontAlarmTypeOutData();
		List<FrontAlarmTypeOutData> dataCount = utLzBjzjalarmMapper.getAlarmListCount(id,"2");
		if (Util.isEmpty(userId)) {
			dataCount = utLzBjzjalarmMapper.getAlarmListCount(id,"1");
		} else {
			if (Util.isEmpty(dataCount)) {
				dataCount = utLzBjzjalarmMapper.getAlarmListCount(id,"3");
			} else {
				List<FrontAlarmTypeOutData> dataBJZJCount = utLzBjzjalarmMapper.getAlarmListCount(id,"3");
				if (Util.isNotEmpty(dataBJZJCount)) {
					dataCount.addAll(dataBJZJCount);
				}
			}
		}
		if (Util.isNotEmpty(dataCount)) {
			for (FrontAlarmTypeOutData data : dataCount) {
				if (!"0".equals(data.getFaultCount())) {
					outData.setFaultCount(data.getFaultCount());
				}
				if (!"0".equals(data.getShieldCount())) {
					outData.setShieldCount(data.getShieldCount());
				}
				if (!"0".equals(data.getSuperviseCount())) {
					outData.setSuperviseCount(data.getSuperviseCount());
				}
			}
		}

		return outData;
	}

	@Override
	public List<FrontUnitOutData> getUnitByUnitpoint(String unitId) throws Exception {
		UtUnitBaseinfo baseinfo = utUnitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(unitId));
		List<FrontUnitOutData> unitBaseInfoOutData = new ArrayList<>();
		if (Util.isNotEmpty(baseinfo)) {
			if (Util.isNotEmpty(baseinfo.getUnitpoint())) {
				List<FrontUnitOutData> unitpoint = utUnitBaseinfoMapper.getByUnitpoint(baseinfo.getUnitpoint());
				for (FrontUnitOutData data : unitpoint) {
					unitBaseInfoOutData.add(data);
				}
			}
		} else {
			UtMaintenanceUnit utMaintenanceUnit = utMaintenanceUnitMapper.selectByPrimaryKey(Long.valueOf(unitId));
			if (Util.isNotEmpty(utMaintenanceUnit)) {
				List<FrontUnitOutData> unitPoint = utUnitBaseinfoMapper.getByMaintenanceUnit(unitId);
				for (FrontUnitOutData data : unitPoint) {
					unitBaseInfoOutData.add(data);
				}
			}
		}
		return unitBaseInfoOutData;
	}
}
