package com.gw.front.couplet.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaOutData;
import com.gw.common.ExportExcel;
import com.gw.common.SnowflakeIdWorker;
import com.gw.device.data.InterfaceOutData;
import com.gw.equipment.data.EquipmentFacilitiesData;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.*;
import com.gw.front.couplet.service.FrontCoupletService;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.data.FrontUnitVideoOutData;
import com.gw.front.unit.data.UnitGradeInterfaceOutData;
import com.gw.front.unit.data.UnitGradeInterfaceOutListData;
import com.gw.mapper.*;
import com.gw.mapper.entity.*;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.thirdInterface.service.ThirdInterfaceService;
import com.gw.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FrontCoupletServiceImpl implements FrontCoupletService {

	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtUnitVideoMapper utUnitVideoMapper;
	@Resource
	private UtUnitVideoLogMapper utUnitVideoLogMapper;
	@Resource
	private UtEqEquipmentdetialgwMapper utEqEquipmentdetialgwMapper;
	@Resource
	private UtUnitBuildareaMapper utUnitBuildareaMapper;
	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Resource
	private UtLzBjzjalarmHistoryMapper utLzBjzjalarmHistoryMapper;
	@Resource
	private UtBaseUserRelationMapper utBaseUserRelationMapper;
	@Value("${raw.data.database}")
	private String database;// 源数据所在数据库
	@Resource
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	@Resource
	private UtLzFireequipmentalarmHistoryMapper utLzFireequipmentalarmHistoryMapper;
	@Resource
	private UtUnitBuildMapper utUnitBuildMapper;
	@Resource
	private SysOperationLogMapper sysOperationLogMapper;

	@Resource
	private UtUnitCalibrationMapper utUnitCalibrationMapper;
	@Resource
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private UtUnitUserMapper utUnitUserMapper;
	@Resource
	private UtLzRepairMapper utLzRepairMapper;
	@Resource
	private UtUnitEventMapper utUnitEventMapper;
	@Resource
	private PropertiesManageService propertiesManageService;
	@Resource
	private UtUnitBaseinfoMapper unitBaseinfoMapper;
	@Resource
	private AlarmStatusMapper alarmStatusMapper;
	@Value("${cbs.imagesPath}")
	private String mImagesPath;
	//	@Value("${red.server_port}")
//	private String url;// 源数据所在数据库
//	@Value("${access_token}")
//	private String access_token;// 秘钥
//	@Value("${video.appkey}")
//	private String appkey;
//	@Value("${video.appsecret}")
//	private String appsecret;
	@Resource
	private ThirdInterfaceService thirdInterfaceService;
	@Value("${AX.status}")
	private Integer clearAlarm;

	@Override
	public void exportDevices(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		inData.setDatabase(database);
		List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
		List<FrontCoupletDevice2Export> exportDatas = new ArrayList<>();
		for (FrontHisSDDeviceStatusOutData frontHisSDDeviceStatusOutData : list) {
			FrontCoupletDevice2Export data = new FrontCoupletDevice2Export();
			BeanUtils.copyProperties(frontHisSDDeviceStatusOutData, data);
			exportDatas.add(data);
		}
		String[] header = new String[]{"设备类型", "设备名称", "子号", "使用时间", "设备状态", "上传数", "单位编号", "子站号", "单位名称", "异常次数",
				" 硬件版本", "软件版本", "终端IP", "设备型号", "通讯协议"};
		ExportExcel<FrontCoupletDevice2Export> export = new ExportExcel<FrontCoupletDevice2Export>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public List<FrontCoupletUnitInfo> getUnits(String userId, String unitName) throws Exception {
		return utUnitBaseinfoMapper.getUnits(userId, unitName);
	}

	@Override
	public List<FrontCoupletBuildAreaOutData> getUnitBuildAreas(String unitId, String buildName) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		List<FrontCoupletBuildAreaOutData> unitBuildAreas = utUnitVideoMapper.getUnitBuildAreas(unitId, buildName);
		UtUnitBaseinfo utUnitBaseinfo = unitBaseinfoMapper.selectByPrimaryKey(Long.valueOf(unitId));

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
		for (FrontCoupletBuildAreaOutData frontCoupletBuildAreaOutData : unitBuildAreas) {
			frontCoupletBuildAreaOutData.setAppKey(appKey);
			frontCoupletBuildAreaOutData.setToken(token);
		}
		return unitBuildAreas;
	}

	@Override
	public void addUnitVideoLog(FrontCoupletVideoLogOutData inData, String userId) throws Exception {
		String path = UtilConv.base64ToPicFile(inData.getFile(), mImagesPath);
		UtUnitVideoLog record = new UtUnitVideoLog();
		record.setCreateDate(new Date());
		record.setId(snowflakeIdWorker.nextId());
		record.setCreateUser(userId);
		record.setPath(path);
		record.setBuildArea(inData.getBuildArea());
		record.setUnitId(Long.valueOf(inData.getUnitId()));
		utUnitVideoLogMapper.insert(record);
	}

	@Override
	public PageInfo<FrontCoupletVideoLogOutData> getUnitVideoLogs(FrontCoupletInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontCoupletVideoLogOutData> outDatas = utUnitVideoLogMapper.getUnitVideoLogs(inData);
		PageInfo<FrontCoupletVideoLogOutData> pager = new PageInfo<>(outDatas);
		return pager;
	}

	@Override
	public List<FrontInterfaceDeviceOutData> getInterfaceValueList(FrontCoupletInData inData) throws Exception {
		inData.setDatabase(database);
		List<FrontInterfaceDeviceOutData> outData = new ArrayList<FrontInterfaceDeviceOutData>();
		List<FrontInterFaceStatusOutData> list = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
		List<FrontInterFaceStatusOutData> listTwo = null;
		//气体系统加载时，查询报警主机
		if (inData.getType().equals("5")) {
			listTwo = utEqEquipmentdetialgwMapper.getGasAlarmList(inData);
			list.addAll(listTwo);
		}
		Map<String, FrontInterfaceDeviceOutData> map = new HashMap<>();
		for (FrontInterFaceStatusOutData frontInterFaceStatusOutData : list) {
			String id = frontInterFaceStatusOutData.getId();
			if (!map.containsKey(id)) {
				FrontInterfaceDeviceOutData data = new FrontInterfaceDeviceOutData();
				data.setInterfaceList(new ArrayList<>());
				data.setEqName(frontInterFaceStatusOutData.getEqName());
				data.setClassCode(frontInterFaceStatusOutData.getClassCode());
				data.setId(id);
				data.setBuildareaname(frontInterFaceStatusOutData.getBuildareaname());
				data.setBuildImgbg(frontInterFaceStatusOutData.getBuildImgbg());
				data.setPointVideoId(frontInterFaceStatusOutData.getPointVideoId());
				outData.add(data);
				map.put(id, data);
			}
			map.get(id).getInterfaceList().add(frontInterFaceStatusOutData);
		}
		return outData;
	}

	@Override
	public List<FrontCoupletCommonOutData> getBuildAreaList(String unitId, String buildId) throws Exception {
		return utUnitBuildareaMapper.getBuildAreaList(unitId, buildId);
	}

	@Override
	public List<FrontInterfaceDeviceOutData> getInterfaceDeviceList(FrontCoupletInData inData) throws Exception {
		// inData.setDatabase(database);
		List<FrontInterfaceDeviceOutData> outData = new ArrayList<FrontInterfaceDeviceOutData>();
		// List<FrontInterFaceStatusOutData> list =
		// utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
		// Map<String, FrontInterfaceDeviceOutData> map = new HashMap<>();
		// for (FrontInterFaceStatusOutData frontInterFaceStatusOutData : list) {
		// String id = frontInterFaceStatusOutData.getInterfaceId();
		// if (!map.containsKey(id)) {
		// FrontInterfaceDeviceOutData data = new FrontInterfaceDeviceOutData();
		// data.setInterfaceList(new ArrayList<>());
		// data.setEqName(frontInterFaceStatusOutData.getEqName());
		// data.setClassCode(frontInterFaceStatusOutData.getClassCode());
		// data.setId(id);
		// outData.add(data);
		// map.put(id, data);
		// }
		// map.get(id).getInterfaceList().add(frontInterFaceStatusOutData);
		// }
		return outData;
	}

	// private void dealAlarmCount(Integer iOPort, List<FrontCoupletCommonOutData>
	// outData,
	// List<FrontCoupletCommonOutData> waterRecords, boolean isNumber) {
	// for (FrontCoupletCommonOutData frontCountOutData : waterRecords) {
	// String alarmCount = frontCountOutData.getCoupletValue();
	// if (Util.isEmpty(alarmCount)) {
	// continue;
	// }
	// FrontCoupletCommonOutData frontCoupletCommonOutData = new
	// FrontCoupletCommonOutData();
	// String[] split = alarmCount.split(",");
	// if (isNumber) {
	// // 如果是数字量需要把得到的数字转换为二进制然后取值
	// String strCount = "";
	// for (String string : split) {
	// if (!Util.isEmpty(string)) {
	// strCount +=
	// Util.autoGenericCode(Integer.toBinaryString(Integer.valueOf(string)), 8);
	// }
	// }
	// if (strCount.length() < iOPort) {
	// continue;
	// }
	// frontCoupletCommonOutData.setCoupletValue(strCount.substring(iOPort - 1,
	// iOPort));
	// continue;
	// }
	// if (split.length < iOPort) {
	// continue;
	// }
	// frontCoupletCommonOutData.setCoupletValue(split[iOPort - 1]);
	// outData.add(frontCoupletCommonOutData);
	// }
	// }

	@Override
	public PageInfo<FrontInterFaceStatusOutData> getWaterMoreList(FrontCoupletInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontInterFaceStatusOutData> list = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
		PageInfo<FrontInterFaceStatusOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public void exportWaterMoreList(FrontCoupletInData inData, HttpServletResponse response) throws Exception {
		inData.setDatabase(database);
		List<FrontInterFaceStatusOutData> list = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
		List<FrontInterFaceStatusExport> exportDatas = new ArrayList<>();
		for (FrontInterFaceStatusOutData frontInterFaceStatusOutData : list) {
			FrontInterFaceStatusExport exportData = new FrontInterFaceStatusExport();
			BeanUtils.copyProperties(frontInterFaceStatusOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"系统", "信号名称", "信号类型", "子号", "端口", "参考范围", "单位名称", "当前值"};
		ExportExcel<FrontInterFaceStatusExport> export = new ExportExcel<FrontInterFaceStatusExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public List<FrontElectricalStatOutData> getElectricalHistory(FrontCoupletInData inData) throws Exception {
		List<FrontElectricalStatOutData> outData = new ArrayList<>();
		List<FrontRTUHistoryInData> indatas = utEqEquipmentdetialgwMapper.getInterfaceInfoByDevice(inData);
		if (Util.isEmpty(indatas)) {
			return outData;
		}
		FrontRTUHistoryInData data = indatas.get(0);
		if (getWaterLists(data, inData.getStartDate(), inData.getEndDate(), data.getTableName()))
			return new ArrayList<FrontElectricalStatOutData>();
		// 模拟量
		List<FrontCoupletCommonOutData> analogRecords = utEqEquipmentdetialgwMapper.getWaterSystemHistory(data);

		// 数字量
		List<FrontCoupletCommonOutData> numberRecords = utEqEquipmentdetialgwMapper.getWaterSystemHistory1(data);

		List<FrontElectricalStatOutData> outDatas = getFrontElectricalStatOutData(indatas, analogRecords, numberRecords);
		return outDatas;
	}

	private List<FrontElectricalStatOutData> getFrontElectricalStatOutData(List<FrontRTUHistoryInData> indatas, List<FrontCoupletCommonOutData> analogRecords, List<FrontCoupletCommonOutData> numberRecords) {
		List<FrontElectricalStatOutData> outDatas = new ArrayList<>();
		for (FrontRTUHistoryInData frontRTUHistoryInData : indatas) {
			FrontElectricalStatOutData frontElectricalStatOutData = new FrontElectricalStatOutData();
			BeanUtils.copyProperties(frontRTUHistoryInData, frontElectricalStatOutData);
			Integer ioType = frontRTUHistoryInData.getIotype();
			Integer ioPort = frontRTUHistoryInData.getIoport();
			if (1 == ioType) {// 模拟量
				frontElectricalStatOutData.setStatData(getAlarmCount(ioPort, analogRecords, false));
			} else {
				frontElectricalStatOutData.setStatData(getAlarmCount(ioPort, numberRecords, true));
			}
			frontElectricalStatOutData
					.setAnalogdown(UtilConv.scientificToString(frontElectricalStatOutData.getAnalogdown()));
			frontElectricalStatOutData
					.setAnalogup(UtilConv.scientificToString(frontElectricalStatOutData.getAnalogup()));
			frontElectricalStatOutData.setAnalogWarningDown(
					UtilConv.scientificToString(frontElectricalStatOutData.getAnalogWarningDown()));
			frontElectricalStatOutData
					.setAnalogWarningUp(UtilConv.scientificToString(frontElectricalStatOutData.getAnalogWarningUp()));
			outDatas.add(frontElectricalStatOutData);
		}
		return outDatas;
	}

	private boolean getWaterLists(FrontRTUHistoryInData data, String startDate2, String endDate2, String tableName) throws Exception {
		String startDate = startDate2;
		String endDate = endDate2;
		if (Util.isEmpty(startDate) && Util.isEmpty(endDate)) {
			startDate = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
		}
		data.setStartDate(startDate);
		if (Util.isNotEmpty(endDate) && endDate.length() == 10) {
			endDate = endDate + " 23:59:59";
		}
		data.setEndDate(endDate);
		data.setTableName(database + ".dbo." + tableName);

		Integer count = utEqEquipmentdetialgwMapper.selectWaterHistoryCount(data);
		if (count == null || count == 0) {
			return true;
		}

		int interval = 1;
		if (count > 1000) {
			interval = count / 1000;
			if ((count % 1000) != 0) {
				interval++;
			}
		}
		data.setInterval(interval);
		return false;
	}

	private List<FrontCoupletCommonOutData> getAlarmCount(Integer ioPort, List<FrontCoupletCommonOutData> analogRecords,
														  boolean isNumber) {
		List<FrontCoupletCommonOutData> list = new ArrayList<>(analogRecords.size());
		for (FrontCoupletCommonOutData frontCountOutData : analogRecords) {
			String alarmCount = frontCountOutData.getCoupletValue();
			if (Util.isEmpty(alarmCount)) {
				continue;
			}
			String[] split = alarmCount.split(",");
			FrontCoupletCommonOutData outData = new FrontCoupletCommonOutData();
			if (isNumber) {
				// 如果是数字量需要把得到的数字转换为二进制然后取值
				String strCount = "";
				for (String string : split) {
					if (!Util.isEmpty(string)) {
						strCount += Util.autoGenericCode(Integer.toBinaryString(Integer.valueOf(string)), 8);
					}
				}
				if (strCount.length() < ioPort) {
					continue;
				}
				BeanUtils.copyProperties(frontCountOutData, outData);
				outData.setCoupletValue(strCount.substring(ioPort - 1, ioPort));
				list.add(outData);
				continue;
			}
			if (split.length < ioPort) {
				continue;
			}

			BeanUtils.copyProperties(frontCountOutData, outData);
			outData.setCoupletValue(split[ioPort - 1]);
			list.add(outData);
		}
		return list;
	}

	@Override
	public List<EquipmentFacilitiesData> getFireAlarmImgList(EquipmentFacilitiesData inData) throws Exception {
		List<EquipmentFacilitiesData> list = utLzBjzjalarmMapper.getFireAlarmImgList(inData);
		List<EquipmentFacilitiesData> listTwo = utLzBjzjalarmMapper.getFireAlarm_Relation_ImgList(inData);
		for (EquipmentFacilitiesData addressRelOutData : listTwo) {
			String buildAreaId = addressRelOutData.getBuildareaid();
			if (buildAreaId != null) {
				BuildAreaOutData unitidByBuildAreaId = utUnitBuildareaMapper.getUnitidByBuildAreaId(buildAreaId);
				addressRelOutData.setUnitID(unitidByBuildAreaId.getUnitID());
				addressRelOutData.setUnitName(unitidByBuildAreaId.getUnitName());
			}
		}
		list.addAll(listTwo);
		return list;
	}

	@Override
	public PageInfo<FrontCoupletFireAlarmOutData> getFireAlarmSelectList(FrontCoupletInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontCoupletFireAlarmOutData> list = utLzBjzjalarmMapper.getFireAlarmSelectList(inData);
		if (list.size() > 0) {
			for (FrontCoupletFireAlarmOutData frontCoupletFireAlarmOutData : list) {
				frontCoupletFireAlarmOutData.setAlarmStatus(dealStatus(frontCoupletFireAlarmOutData.getAlarmStatus()));
			}
		}
		PageInfo<FrontCoupletFireAlarmOutData> pager = new PageInfo<FrontCoupletFireAlarmOutData>(list);
		return pager;
	}

	@Override
	public PageInfo<FrontCoupletFireAlarmOutData> getFireAlarmList(FrontCoupletInData inData) throws Exception {
		List<String> statusList = new ArrayList<String>();
		if (Util.isNotEmpty(inData.getAlarmStatus())) {
			String[] statuss = inData.getAlarmStatus().split(",");
			for (String status : statuss) {
				statusList.add(status);
			}
		}
		inData.setStatusList(statusList);
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		//该项为sql查找标识
		inData.setSelectType("2");List<FrontCoupletFireAlarmOutData> list = utLzBjzjalarmMapper.getFireAlarmList(inData);
		if (Util.isEmpty(userId)) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			//该项为sql查找标识
			inData.setSelectType("1");list= utLzBjzjalarmMapper.getFireAlarmList(inData) ;
			} else {
			if (Util.isEmpty( list)) {
				PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			//该项为sql查找标识
		inData.setSelectType("3");
			list= utLzBjzjalarmMapper.getFireAlarmList(inData);
			} else {
				PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
				//该项为sql查找标识
				inData.setSelectType("3");
				List<FrontCoupletFireAlarmOutData> fireAlarmListBJZJByBuild = utLzBjzjalarmMapper.getFireAlarmList(inData);
				if (Util.isNotEmpty(fireAlarmListBJZJByBuild)) {
					PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
					list.addAll(fireAlarmListBJZJByBuild);
					Collections.sort(list, new Comparator<FrontCoupletFireAlarmOutData>() {
						@Override
						public int compare(FrontCoupletFireAlarmOutData o1, FrontCoupletFireAlarmOutData o2) {
							return o2.getLastupdate().compareTo(o1.getLastupdate());
						}
					});
				}
			}
		}for (FrontCoupletFireAlarmOutData frontCoupletFireAlarmOutData : list) {
				frontCoupletFireAlarmOutData.setAlarmStatus(dealStatus(frontCoupletFireAlarmOutData.getAlarmStatus()));

		}
		PageInfo<FrontCoupletFireAlarmOutData> pager = new PageInfo<FrontCoupletFireAlarmOutData>(list);
		return pager;
	}

	@Override
	public void exportFireAlarmList(FrontCoupletInData inData, HttpServletResponse response) throws Exception {
		List<String> statusList = new ArrayList<String>();
		if (Util.isNotEmpty(inData.getAlarmStatus())) {
			String[] statuss = inData.getAlarmStatus().split(",");
			for (String status : statuss) {
				statusList.add(status);
			}
		}
		inData.setStatusList(statusList);
		List<FrontCoupletFireAlarmExport> exportDatas = new ArrayList<>();
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		//该项为sql查找标识
		inData.setSelectType("2");
		List<FrontCoupletFireAlarmOutData> list = utLzBjzjalarmMapper.getFireAlarmList(inData);
		if (Util.isEmpty(userId)) {
			//该项为sql查找标识
			inData.setSelectType("1");
			list = utLzBjzjalarmMapper.getFireAlarmList(inData);
		} else {
			//该项为sql查找标识
			inData.setSelectType("3");
			List<FrontCoupletFireAlarmOutData> fireAlarmListBJZJByBuild = utLzBjzjalarmMapper.getFireAlarmList(inData);
			if (Util.isNotEmpty(fireAlarmListBJZJByBuild)) {
				list.addAll(fireAlarmListBJZJByBuild);
			}
		}
		for (FrontCoupletFireAlarmOutData frontCoupletFireAlarmOutData : list) {
			FrontCoupletFireAlarmExport exportData = new FrontCoupletFireAlarmExport();
			BeanUtils.copyProperties(frontCoupletFireAlarmOutData, exportData);
			exportData.setAlarmStatus(dealStatus(exportData.getAlarmStatus()));
			exportDatas.add(exportData);
		}

		String[] header = new String[]{"报警设备", "时间", "测试", "报警类型", "节点", "报警分类", "报警地点"};
		ExportExcel<FrontCoupletFireAlarmExport> export = new ExportExcel<FrontCoupletFireAlarmExport>();
		export.exportExcel(response, exportDatas, header);

	}

	public String dealStatus(String alarmStatus) {
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

	@Override
	public FrontCoupletAlarmInfoOutData getAlarmInfo(String id, String userName) throws Exception {
		FrontCoupletAlarmInfoOutData outData = utLzBjzjalarmMapper.getAlarmInfo(id);
		if (null != outData && Util.isEmpty(outData.getDealTime())) {
			outData.setDealTime(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_SS));
		}
		if (null != outData && Util.isEmpty(outData.getDealUser())) {
			outData.setDealUser(userName);
		}
		if (null != outData) {
			outData.setAlarmStatus(dealStatus(outData.getAlarmStatus()));
		}
		return outData;
	}

	@Override
	@Transactional
	public void dealAlarm(FrontCoupletAlarmInfoOutData inData) throws Exception {
		UtLzBjzjalarm bjzjalarm = utLzBjzjalarmMapper.selectByPrimaryKey(Long.valueOf(inData.getId()));
		String netDeviceId = utUnitNetdeviceMapper.getDeviceByAlarm(bjzjalarm.getOnwercode(), bjzjalarm.getDeviceindex(), bjzjalarm.getDeviceno());
		// 处理编码生成规则 F+当前时间时分秒毫秒
		String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
		if (Util.isNotEmpty(inData.getDealUser())) {
			bjzjalarm.setDealuser(inData.getDealUser());
		} else {
			bjzjalarm.setDealuser(inData.getUserId());
		}
		bjzjalarm.setDealtime(UtilConv.Dateformat(new Date(), UtilConv.DATE_YYYY_MM_DD_SS));
		bjzjalarm.setIsdeal(1);
		bjzjalarm.setDealcode(dealcode);
		bjzjalarm.setDealinfo(inData.getDealInfo());
		bjzjalarm.setDealresult(Integer.valueOf(inData.getDealResult()));
		bjzjalarm.setIsneedmaintain(Integer.valueOf(inData.getIsNeedMaintain()));
		bjzjalarm.setMaintaindesc(inData.getBadDesc());
		// bjzjalarm.setLastupdate(new Date());
		// bjzjalarm.setLastupdate(new Date());
		utLzBjzjalarmMapper.updateByPrimaryKey(bjzjalarm);
		UtLzBjzjalarmHistory alarmHistory = new UtLzBjzjalarmHistory();
		BeanUtils.copyProperties(bjzjalarm,alarmHistory);
		utLzBjzjalarmHistoryMapper.updateByPrimaryKey(alarmHistory);

		// 新增操作记录
		SysOperationLog sperationLog = new SysOperationLog();
		sperationLog.setAddress(inData.getCurrAddress());
		sperationLog.setUnitId(bjzjalarm.getUnitid());
		sperationLog.setContent(inData.getBadDesc());
		sperationLog.setCreateDate(new Date());
		sperationLog.setCurrentStatus("");
		sperationLog.setId(snowflakeIdWorker.nextId());
		sperationLog.setRemark("处理编码:" + dealcode);
		sperationLog.setUrl("/front/couplet/dealAlarm");
		sperationLog.setRecordType("1");
		sperationLog.setUserId(Long.valueOf(inData.getUserId()));
		sysOperationLogMapper.insert(sperationLog);
		UtUnitUser user = utUnitUserMapper.selectByPrimaryKey(Long.parseLong(inData.getUserId()));
		if ("1".equals(inData.getIsNeedMaintain())) {
			UtLzRepair repair = new UtLzRepair();
			repair.setId(snowflakeIdWorker.nextId());
			repair.setUnitid(bjzjalarm.getUnitid());
			repair.setBaddesc(inData.getBadDesc());
			repair.setCreatetime(new Date());
			repair.setBaseid(Long.parseLong(inData.getId()));
			repair.setDatafrom(0);
			repair.setIswbfk(1);
			repair.setWbclr(user.getUsername());
			repair.setRepairsite(bjzjalarm.getAlarmWheredesc());
			repair.setDealstatus(0);
			repair.setPicurl(inData.getPicUrl());
			Integer flag = utLzRepairMapper.insert(repair);
			if (flag < 1) {
				throw new ServiceException("生成维修失败！");
			}
		}
		if (1 == clearAlarm) {
			try {
				thirdInterfaceService.clearAlarm(netDeviceId, null);
			} catch (Exception e) {
				log.error("clearAlarm error!" + e.getMessage());
			}
		}
	}

	@Override
	@Transactional
	public void dealAlarmBatch(String isTest, String alarmStatus, FrontUnitUserOutData sessinInfo, String ids) throws Exception {
		String username = sessinInfo.getUsername();
		Date now = new Date();
		Example example = new Example(UtLzBjzjalarm.class);
		List<String> idList = new ArrayList<>(Arrays.asList(ids.split(",")));
		example.createCriteria().andIn("id", idList);
		List<UtLzBjzjalarm> bjzjalarms = utLzBjzjalarmMapper.selectByExample(example);
		for (UtLzBjzjalarm bjzjalarm : bjzjalarms) {
			bjzjalarm.setDealuser(username);
			bjzjalarm.setDealtime(now);
			bjzjalarm.setIsdeal(1);
			// 处理编码生成规则 F+当前时间时分秒毫秒
			String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
			bjzjalarm.setDealcode(dealcode);
			bjzjalarm.setDealinfo("批量处理!");
			bjzjalarm.setDealresult(1);// 批量处理的都是误报
			if ("1".equals(isTest)) {
				bjzjalarm.setCalibrationId(2L);
			}
			utLzBjzjalarmMapper.updateByPrimaryKey(bjzjalarm);
			UtLzBjzjalarmHistory alarmHistory = new UtLzBjzjalarmHistory();
			BeanUtils.copyProperties(bjzjalarm,alarmHistory);
			utLzBjzjalarmHistoryMapper.updateByPrimaryKey(alarmHistory);
			// 新增操作记录
			SysOperationLog operationLog = new SysOperationLog();
			operationLog.setUnitId(bjzjalarm.getUnitid());
			operationLog.setAddress(sessinInfo.getCurrAddress());
			operationLog.setContent("批量处理火灾告警信息！");
			operationLog.setCreateDate(new Date());
			operationLog.setCurrentStatus("");
			operationLog.setId(snowflakeIdWorker.nextId());
			operationLog.setRemark("批量处理类型:" + dealStatus(alarmStatus));
			operationLog.setUrl("/front/couplet/dealAlarmBatch");
			operationLog.setRecordType("1");
			operationLog.setUserId(Long.valueOf(sessinInfo.getId()));
			sysOperationLogMapper.insert(operationLog);
			String netDeviceId = utUnitNetdeviceMapper.getDeviceByAlarm(bjzjalarm.getOnwercode(), bjzjalarm.getDeviceindex(), bjzjalarm.getDeviceno());
			if (1 == clearAlarm) {
				try {
					thirdInterfaceService.clearAlarm(netDeviceId, null);
				} catch (Exception e) {
					log.error("clearAlarm error!" + e.getMessage());
				}
			}
		}
	}

	@Override
	@Transactional
	public void dealCheckAlarmBatch(String dealresult, String isTest, FrontCoupletAlarmInfoOutData inData) throws Exception {
		Date now = new Date();
		String[] idArr = inData.getId().split(",");
		Example example = new Example(UtLzBjzjalarm.class);
		example.createCriteria().andIn("id", Arrays.asList(idArr));
		List<UtLzBjzjalarm> utLzBjzjalarms = utLzBjzjalarmMapper.selectByExample(example);
		for (UtLzBjzjalarm bjzjalarm : utLzBjzjalarms) {
			bjzjalarm.setDealuser(inData.getDealUser());
			bjzjalarm.setDealtime(now);
			bjzjalarm.setIsdeal(1);
			// 处理编码生成规则 F+当前时间时分秒毫秒
			String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
			bjzjalarm.setDealcode(dealcode);
			bjzjalarm.setDealinfo("批量处理!");
			bjzjalarm.setDealresult(Integer.valueOf(dealresult));// 批量复选框处理
			if ("1".equals(isTest)) {
				bjzjalarm.setCalibrationId(2L);
			}
			utLzBjzjalarmMapper.updateByPrimaryKey(bjzjalarm);
			UtLzBjzjalarmHistory alarmHistory = new UtLzBjzjalarmHistory();
			BeanUtils.copyProperties(bjzjalarm,alarmHistory);
			utLzBjzjalarmHistoryMapper.updateByPrimaryKey(alarmHistory);
			// 新增操作记录
			SysOperationLog operationLog = new SysOperationLog();
			operationLog.setUnitId(bjzjalarm.getUnitid());
			operationLog.setAddress(inData.getCurrAddress());
			operationLog.setContent("批量处理火灾告警信息！");
			operationLog.setCreateDate(new Date());
			operationLog.setCurrentStatus("");
			operationLog.setId(snowflakeIdWorker.nextId());
			operationLog.setRemark("批量处理类型:" + dealStatus(inData.getAlarmStatus()));
			operationLog.setUrl("/front/couplet/dealAlarmBatch");
			operationLog.setRecordType("1");
			operationLog.setUserId(Long.valueOf(inData.getUserId()));
			sysOperationLogMapper.insert(operationLog);
			String netDeviceId = utUnitNetdeviceMapper.getDeviceByAlarm(bjzjalarm.getOnwercode(), bjzjalarm.getDeviceindex(), bjzjalarm.getDeviceno());
			if (1 == clearAlarm) {
				try {
					thirdInterfaceService.clearAlarm(netDeviceId, null);
				} catch (Exception e) {
					log.error("clearAlarm error!" + e.getMessage());
				}
			}
		}
	}

	@Override
	public PageInfo<FrontCoupletRTUAlarmOutData> getRTUAlarmList(FrontCoupletInData inData) throws Exception {
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		List<FrontCoupletRTUAlarmOutData> list = null;
		if (Util.isEmpty(userId)) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			list = utLzFireequipmentalarmMapper.getRTUAlarmList(inData);
		} else {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			list = utLzFireequipmentalarmMapper.getRTUAlarmByBuildList(inData);
		}
		PageInfo<FrontCoupletRTUAlarmOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public void exportRTUAlarmList(FrontCoupletInData inData, HttpServletResponse response) throws Exception {
		List<FrontCoupletRTUAlarmExport> exportDatas = new ArrayList<>();
		List<FrontCoupletRTUAlarmOutData> list = utLzFireequipmentalarmMapper.getRTUAlarmList(inData);//
		for (FrontCoupletRTUAlarmOutData frontCoupletRTUAlarmOutData : list) {
			FrontCoupletRTUAlarmExport exportData = new FrontCoupletRTUAlarmExport();
			BeanUtils.copyProperties(frontCoupletRTUAlarmOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"误报", "单位编号", "单位名称", "报警时间", "接口信息", "报警描述", "处理", "处理编号"};
		ExportExcel<FrontCoupletRTUAlarmExport> export = new ExportExcel<FrontCoupletRTUAlarmExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	@Transactional
	public void dealRTUAlarmBatch(String type, String alarmStatus, FrontUnitUserOutData sessinInfo) throws Exception {
		Date now = new Date();
		Example example = new Example(UtLzBjzjalarm.class);
		example.createCriteria().andEqualTo("alarmStatus", alarmStatus).andEqualTo("isdeal", 0);
		List<UtLzFireequipmentalarm> rtualarms = utLzFireequipmentalarmMapper.selectByExample(example);
		for (UtLzFireequipmentalarm rtualarm : rtualarms) {
			rtualarm.setDealuser(sessinInfo.getUsername());
			rtualarm.setDealtime(now);
			rtualarm.setIsdeal(1);
			// 处理编码生成规则 F+当前时间时分秒毫秒
			String dealcode = "F" + UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_FULL_PAT_17);
			rtualarm.setDealcode(dealcode);
			rtualarm.setDealinfo("批量处理!");
			rtualarm.setDealresult(1);// 批量处理的都是误报
			// rtualarm.setLastupdate(now);
			utLzFireequipmentalarmMapper.updateByPrimaryKey(rtualarm);
			UtLzFireequipmentalarmHistory alarmHistory = new UtLzFireequipmentalarmHistory();
			BeanUtils.copyProperties(rtualarm,alarmHistory);
			utLzFireequipmentalarmHistoryMapper.updateByPrimaryKey(alarmHistory);
			// 新增操作记录
			SysOperationLog log = new SysOperationLog();
			log.setUnitId(rtualarm.getUnitid());
			log.setAddress(sessinInfo.getCurrAddress());
			log.setContent("批量处理RTU告警信息！");
			log.setCreateDate(new Date());
			log.setCurrentStatus("");
			log.setId(snowflakeIdWorker.nextId());
			log.setRemark("批量处理类型:" + dealStatus(alarmStatus));
			log.setUrl("/front/couplet/dealRTUAlarmBatch");
			log.setRecordType("1");
			log.setUserId(Long.valueOf(sessinInfo.getId()));
			sysOperationLogMapper.insert(log);
		}
	}

	@Override
	public List<FrontCoupletCommonOutData> getBuilds(String userId) throws Exception {
		return utUnitBuildMapper.getBuilds(userId);
	}

	@Override
	@Transactional
	public void dealRTUAlarm(FrontCoupletAlarmInfoOutData inData) {
		if (Util.isNotEmpty(inData.getId())) {
			List<String> idList = new ArrayList<>(Arrays.asList(inData.getId().split(",")));
			Example example = new Example(UtLzFireequipmentalarm.class);
			example.createCriteria().andIn("id", idList);
			Date date = new Date();
			List<UtLzFireequipmentalarm> rtuAlarms = utLzFireequipmentalarmMapper.selectByExample(example);
			for (UtLzFireequipmentalarm rtuAlarm : rtuAlarms) {
				UtUnitUser user = utUnitUserMapper.selectByPrimaryKey(Long.parseLong(inData.getUserId()));
				rtuAlarm.setDealuser(user.getUsername());
				rtuAlarm.setDealtime(date);
				rtuAlarm.setIsdeal(1);
				if (Util.isEmpty(inData.getIsTest())) {
					rtuAlarm.setIsTest(null);
				} else {
					rtuAlarm.setIsTest(Integer.valueOf(inData.getIsTest()));
				}
				// 处理编码生成规则 F+当前时间时分秒毫秒
				String dealcode = "F" + UtilConv.date2Str(date, UtilConv.DATE_TIME_FULL_PAT_17);
				rtuAlarm.setDealcode(dealcode);
				rtuAlarm.setDealinfo(inData.getDealInfo());
				rtuAlarm.setDealresult(Integer.valueOf(inData.getDealResult()));
				// rtuAlarm.setLastupdate(new Date());
				rtuAlarm.setMaintaindesc(inData.getBadDesc());
				if (Util.isEmpty(inData.getIsNeedMaintain())) {
					rtuAlarm.setIsneedmaintain(null);
				} else {
					rtuAlarm.setIsneedmaintain(Integer.valueOf(inData.getIsNeedMaintain()));
				}
				utLzFireequipmentalarmMapper.updateByPrimaryKey(rtuAlarm);
				UtLzFireequipmentalarmHistory alarmHistory = new UtLzFireequipmentalarmHistory();
				BeanUtils.copyProperties(rtuAlarm,alarmHistory);
				utLzFireequipmentalarmHistoryMapper.updateByPrimaryKey(alarmHistory);
				// 新增操作记录
				SysOperationLog log = new SysOperationLog();
				log.setAddress(inData.getCurrAddress());
				log.setUnitId(rtuAlarm.getUnitid());
				log.setContent(inData.getBadDesc());
				log.setCreateDate(new Date());
				log.setCurrentStatus("");
				log.setId(snowflakeIdWorker.nextId());
				log.setRemark("处理编码:" + dealcode);
				log.setUrl("/front/couplet/dealRTUAlarm");
				log.setRecordType("1");
				log.setUserId(Long.valueOf(inData.getUserId()));
				sysOperationLogMapper.insert(log);
				if ("1".equals(inData.getIsNeedMaintain())) {
					UtLzRepair repair = new UtLzRepair();
					repair.setId(snowflakeIdWorker.nextId());
					repair.setUnitid(rtuAlarm.getUnitid());
					repair.setBaddesc(inData.getBadDesc());
					repair.setCreatetime(new Date());
					repair.setBaseid(rtuAlarm.getId());
					repair.setDatafrom(1);
					repair.setIswbfk(1);
					repair.setWbclr(user.getUsername());
					repair.setDealstatus(0);
					repair.setPicurl(inData.getPicUrl());
					Integer flag = utLzRepairMapper.insert(repair);
					if (flag < 1) {
						throw new ServiceException("生成维修失败！");
					}
				}
			}
		}
	}

	@Override
	public FrontCoupletAlarmInfoOutData getRTUAlarmInfo(String id, String username) throws Exception {
		FrontCoupletAlarmInfoOutData outData = utLzFireequipmentalarmMapper.getRTUAlarmInfo(id);
		if (Util.isEmpty(outData.getDealTime())) {
			outData.setDealTime(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_SS));
		}
		if (Util.isEmpty(outData.getDealUser())) {
			outData.setDealUser(username);
		}
		return outData;
	}

	@Override
	public void updateDeviceCalibration(FrontCoupletCalibrationInData data) throws Exception {
		UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.parseLong(data.getEqId()));
		if (Util.isNotEmpty(data.getStartDate())) {
			netdevice.setStartdate(UtilConv.str2Date(data.getStartDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
		} else {
			netdevice.setStartdate(new Date());
		}
		netdevice.setEnddate(UtilConv.str2Date(data.getEndDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
		netdevice.setCalibrationremark(data.getRemark());
		if ("5".equals(data.getCalibrationId())) {
			netdevice.setCalibrationId(null);
			netdevice.setStartdate(null);
			netdevice.setEnddate(null);
			netdevice.setCalibrationremark("");
		} else {
			netdevice.setCalibrationId(Long.parseLong(data.getCalibrationId()));
			UtUnitEvent event = new UtUnitEvent();
			event.setId(snowflakeIdWorker.nextId());
			event.setDeviceindex(netdevice.getDeviceindex());
			event.setDeviceno(netdevice.getDeviceno());
			event.setOwnercode(netdevice.getOwnercode());
			event.setStarttime(UtilConv.str2Date(data.getStartDate(), UtilConv.DATE_YYYY_MM_DD_CHN));
			event.setEventid(Long.valueOf(data.getCalibrationId()));
			event.setUnitid(netdevice.getUnitid());
			utUnitEventMapper.insert(event);
		}
		utUnitNetdeviceMapper.updateByPrimaryKey(netdevice);
	}

	@Override
	public FrontCoupletCalibrationInData getDeviceCalibration(String eqId) throws Exception {
		FrontCoupletCalibrationInData outData = utUnitNetdeviceMapper.getDeviceCalibration(eqId);
		if (Util.isEmpty(outData)) {
			outData = new FrontCoupletCalibrationInData();
		}
		return outData;
	}

	@Override
	public List<FrontUnitVideoOutData> getUnitVideoInfo(String unitId, String name) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		List<FrontUnitVideoOutData> unitVideoInfo = utUnitVideoMapper.getUnitVideoInfo(unitId, name);
		UtUnitBaseinfo utUnitBaseinfo = unitBaseinfoMapper.selectByPrimaryKey(DataConvertUtil.parseLong(unitId));

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
		for (FrontUnitVideoOutData frontUnitVideoOutData : unitVideoInfo) {
			frontUnitVideoOutData.setAppKey(appKey);
			frontUnitVideoOutData.setToken(token);
		}
		return unitVideoInfo;
	}

	@Override
	public List<FrontInterfaceOutData> getInterfaceOutData(FrontCoupletInData inData) throws Exception {
		inData.setDatabase(database);
		List<InterfaceOutData> interfaceOutDatas = utEqEquipmentdetialgwMapper.getInterfaceOutData(inData);
		if (Util.isEmpty(interfaceOutDatas)) {
			return null;
		}
		Map<String, InterfaceOutData> map = new HashMap<>();
		List<InterfaceOutData> list = utEqEquipmentdetialgwMapper.getDetialgwList(inData);
		for (InterfaceOutData interfaceOutData : list) {
			String ownerCode = interfaceOutData.getOwnerCode();
			Integer deviceNo = interfaceOutData.getDeviceNo();
			Integer ioPort = interfaceOutData.getIoPort();
			String key = ownerCode + deviceNo.toString() + ioPort.toString();
			if (map.containsKey(key)) {
				continue;
			}
			map.put(key, interfaceOutData);
		}
		List<FrontInterfaceOutData> outDatas = new ArrayList<>();
		for (InterfaceOutData interfaceOutData : interfaceOutDatas) {
			String ownerCode = interfaceOutData.getOwnerCode();
			Integer deviceNo = interfaceOutData.getDeviceNo();
			String ioPort = interfaceOutData.getReserve();
			if (Util.isEmpty(ioPort)) {
				ioPort = "";// 没有输出口
			}
			String key = ownerCode + deviceNo.toString() + ioPort;

			InterfaceOutData data = map.get(key);
			FrontInterfaceOutData outData = new FrontInterfaceOutData();
			if (Util.isNotEmpty(data)) {
				outData.setBuildareaname(data.getBuildAreaName());
				Integer currentValue = data.getCurrentValue();
				Integer digitalNormal = data.getDigitalNormal();
				outData.setStatus((Util.isNotEmpty(currentValue)) && currentValue == digitalNormal ? data.getGoodName()
						: data.getBadName());
				outData.setStatusValue(currentValue + "");
				outData.setDigitalNormal(digitalNormal + "");
			} else {
				String rtuDigitalout = utEqEquipmentdetialgwMapper.currentInterfaceStatus("D" + ownerCode, deviceNo,
						database);

				if (Util.isEmpty(rtuDigitalout)) {
					continue;// 没有输出口
				}
				String[] split = rtuDigitalout.split(",");
				// 需要把得到的数字转换为二进制然后取值
				String strCount = "";
				for (String string : split) {
					if (!Util.isEmpty(string)) {
						strCount += Util.autoGenericCode(Integer.toBinaryString(Integer.valueOf(string)), 8);
					}
				}
				Integer ioPort2 = interfaceOutData.getIoPort();
				if (strCount.length() < ioPort2) {
					continue;// 没有输出口
				}
				// 当前值
				String currentValue = strCount.substring(ioPort2 - 1, ioPort2);
				Integer digitalNormal = interfaceOutData.getDigitalNormal();// 正常值
				if (digitalNormal == Integer.parseInt(currentValue)) {
					// 上线状态
					outData.setStatus(interfaceOutData.getGoodName());
				} else {
					// 脱扣状态
					outData.setStatus(interfaceOutData.getBadName());
				}
				outData.setStatusValue(currentValue);
				outData.setDigitalNormal(digitalNormal + "");
			}
			outData.setEqName(interfaceOutData.getIoName());
			outData.setId(interfaceOutData.getId());
			outData.setBadName(interfaceOutData.getBadName());
			outData.setGoodName(interfaceOutData.getGoodName());
			outData.setOrderType(interfaceOutData.getOrderType());
			outDatas.add(outData);
		}
		return outDatas;
	}

	@Override
	public boolean changeRTUStatus(String id, String status, String actiontype) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		String deviceId = utEqEquipmentdetialgwMapper.getDeviceId(id, database);
		StringBuilder sb = new StringBuilder();

		// 调用远程接口
		String api = String.format(ReqApiConst.GET_REDSERVER_SENDCMD_API, properties.getAccessToken(), 1, deviceId, id, actiontype, status);
		sb.append(properties.getRedServerPort()).append(api);
		log.error("changeRTUStatus调用远程接口" + sb.toString());
		HttpJson httpGet = HttpClientUtil.httpGet(sb.toString());
		if (httpGet.isSuccess()) {
			String msg = httpGet.getMsg();
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse(msg);
			Integer code = (Integer) map.get("Code");
			if (200 == code) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getCurrentStatus(String id) throws Exception {
		InterfaceOutData outData = utUnitNetdeviceMapper.getCurrentStatus(id, database);
		Map<String, InterfaceOutData> map = new HashMap<>();
		FrontCoupletInData inData = new FrontCoupletInData();
		inData.setDatabase(database);
		List<InterfaceOutData> list = utEqEquipmentdetialgwMapper.getDetialgwList(inData);
		for (InterfaceOutData interfaceOutData : list) {
			String ownerCode = interfaceOutData.getOwnerCode();
			Integer deviceNo = interfaceOutData.getDeviceNo();
			Integer ioPort = interfaceOutData.getIoPort();
			String key = ownerCode + deviceNo.toString() + ioPort.toString();
			if (map.containsKey(key)) {
				continue;
			}
			map.put(key, interfaceOutData);
		}

		String ownerCode = outData.getOwnerCode();
		Integer deviceNo = outData.getDeviceNo();
		String ioPort = outData.getReserve();
		String key = ownerCode + deviceNo.toString() + ioPort;

		InterfaceOutData data = map.get(key);
		if (Util.isEmpty(data)) {
			String rtuDigitalout = utEqEquipmentdetialgwMapper.currentInterfaceStatus("D" + ownerCode, deviceNo,
					database);

			if (Util.isEmpty(rtuDigitalout)) {
				return "无";// 没有输出口
			}
			String[] split = rtuDigitalout.split(",");
			// 需要把得到的数字转换为二进制然后取值
			String strCount = "";
			for (String string : split) {
				if (!Util.isEmpty(string)) {
					strCount += Util.autoGenericCode(Integer.toBinaryString(Integer.valueOf(string)), 8);
				}
			}

			Integer ioPort2 = outData.getIoPort();
			if (strCount.length() < ioPort2) {
				return "无";// 没有输出口
			}
			// 当前值
			String currentValue = strCount.substring(ioPort2 - 1, ioPort2);
			Integer digitalNormal = outData.getDigitalNormal();// 正常值
			if (digitalNormal == Integer.parseInt(currentValue)) {
				// 上线状态
				return outData.getGoodName();
			} else {
				// 脱扣状态
				return outData.getBadName();
			}
		}
		Integer currentValue = data.getCurrentValue();
		Integer digitalNormal = data.getDigitalNormal();
		return Util.isNotEmpty(currentValue) && currentValue == digitalNormal ? data.getGoodName() : data.getBadName();

	}

	@Override
	public PageInfo<FrontNBDeviceInfoOutData> getNBDevice(FrontNBDeviceInfoInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontNBDeviceInfoOutData> list = utUnitNetdeviceMapper.getNBDevice(inData);
		PageInfo<FrontNBDeviceInfoOutData> pageInfo = new PageInfo<FrontNBDeviceInfoOutData>(list);
		return pageInfo;
	}

	@Override
	public void exportNBDevice(FrontNBDeviceInfoInData inData, HttpServletResponse response) throws Exception {
		inData.setDatabase(database);
		List<FrontNBDeviceInfoOutData> list = utUnitNetdeviceMapper.getNBDevice(inData);
		List<FrontNBDeviceInfoExport> exportList = new ArrayList<>();
		for (FrontNBDeviceInfoOutData frontNBDeviceInfoOutData : list) {
			FrontNBDeviceInfoExport export = new FrontNBDeviceInfoExport();
			BeanUtils.copyProperties(frontNBDeviceInfoOutData, export);
			exportList.add(export);
		}
		String[] header = new String[]{"设备厂家", "设备型号", "IMEI", "入网时间", "设备状态", "电量%", "压力值", "单位编号", "单位名称", "安装位置", "通知电话", "经度", "纬度"};
		ExportExcel<FrontNBDeviceInfoExport> exportExcel = new ExportExcel<FrontNBDeviceInfoExport>();
		exportExcel.exportExcel(response, exportList, header);
	}

	@Override
	public Map<String, Integer> getAlarmCount(FrontCoupletInData inData) throws Exception {
		inData.setDatabase(database);
		//取出初始数据
		List<FrontInterFaceStatusOutData> list = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
		Map<String, Integer> map = new HashMap<>();
		for (FrontInterFaceStatusOutData item : list) {
			if (null == map.get(item.getDetialName())) {
				map.put(item.getDetialName(), 1);
			} else {
				map.put(item.getDetialName(), map.get(item.getDetialName()) + 1);
			}
		}
		return map;
	}

	@Override
	public List<AlarmStatus> getAlarmStatus() throws Exception {
		return alarmStatusMapper.getAlarmStatus();
	}

	@Override
	public PageInfo<UnitGradeInterfaceOutData> getEqNormal(FrontCoupletInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		//获取单位设备信息
		inData.setDatabase(database);
		List<FrontInterFaceStatusOutData> list = utEqEquipmentdetialgwMapper.getInterfaceValueList(inData);
		//返回list
		List<UnitGradeInterfaceOutData> interfaceOutData = new ArrayList<>();
		List<UnitGradeInterfaceOutListData> interfaces = new ArrayList<>();
		FrontCoupletInData frontCoupletInData = new FrontCoupletInData();
		list = list.stream()
				.collect(Collectors.collectingAndThen
						(Collectors.toCollection(() ->
										new TreeSet<>(Comparator.comparing(t -> t.getNetDeviceId()))),
								ArrayList::new
						)
				);
		for (FrontInterFaceStatusOutData frontInterFaceStatusOutData : list) {
			//根据设备Id查找设备信息
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			frontCoupletInData.setNetDeviceId(frontInterFaceStatusOutData.getNetDeviceId());
			List<FrontRTUHistoryInData> indatas = utEqEquipmentdetialgwMapper.getInterfaceInfoByDevice(frontCoupletInData);
			indatas = indatas.stream()
					.collect(Collectors.collectingAndThen
							(Collectors.toCollection(() ->
											new TreeSet<>(Comparator.comparing(t -> t.getTableName()))),
									ArrayList::new
							)
					);
			//遍历设备信息
			for (FrontRTUHistoryInData outData : indatas) {
				//获取设备table名称
				FrontRTUHistoryInData data = new FrontRTUHistoryInData();
				data.setDeviceNo(outData.getDeviceNo());
				if (getWaterLists(data, inData.getStartDate(), inData.getEndDate(), outData.getTableName()))
					continue;
				// 模拟量
				List<FrontCoupletCommonOutData> analogRecords = utEqEquipmentdetialgwMapper.getWaterSystemHistory(data);
				// 数字量
				List<FrontCoupletCommonOutData> numberRecords = utEqEquipmentdetialgwMapper.getWaterSystemHistory1(data);
				List<FrontElectricalStatOutData> outDatas = getFrontElectricalStatOutData(indatas, analogRecords, numberRecords);
				//设备上线数值
				double analogup = Double.parseDouble(outData.getAnalogup());
				//设备下线数值
				double analogdown = Double.parseDouble(outData.getAnalogdown());
				List<FrontCoupletCommonOutData> statData = outDatas.get(0).getStatData();
				//异常开始时间
				String normalStartData = null;
				//异常结束时间
				String normalEndData = null;
				//异常时间
				Long a = 0l;
				Long b1 = 0l;
				Long b2 = 0l;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				for (int i = 0; i < statData.size(); i++) {
					//当前设备数值
					double coupletValue = Double.parseDouble(statData.get(i).getCoupletValue());
					if ((coupletValue > analogup || coupletValue < analogdown) && normalStartData == null) {
						normalStartData = statData.get(i).getCoupletKey();
					} else if (coupletValue < analogup && coupletValue > analogdown && normalEndData == null && normalStartData != null) {
						normalEndData = statData.get(i).getCoupletKey();
						//计算时间差
						//a += 时间差
						a += UtilDateTime.getDateMinusVal(sdf.parse(normalStartData), sdf.parse(normalEndData), TimeUnit.MINUTES);
						normalStartData = null;
						normalEndData = null;
					}
				}
				//如果normalStartData存在，则需要再加一段，用户选择的结束时间-normalStartData，加到a中
				if (Util.isNotEmpty(normalStartData)) {
					Date parse = sdf.parse("2019-08-14 16:46:00.000");
					a += UtilDateTime.getDateMinusVal(parse, sdf.parse(normalStartData), TimeUnit.MINUTES);
				}
				//存入返回数据
				setInterFace(interfaceOutData, interfaces, frontInterFaceStatusOutData, a, b1, b2);
			}
		}
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		PageInfo<UnitGradeInterfaceOutData> pager = new PageInfo<>(interfaceOutData);
		return pager;
	}

	private void setInterFace(List<UnitGradeInterfaceOutData> interfaceOutData, List<UnitGradeInterfaceOutListData> interfaces, FrontInterFaceStatusOutData frontInterFaceStatusOutData, Long a, Long b1, Long b2) {
		UnitGradeInterfaceOutListData unitGradeInterfaceOutListData = new UnitGradeInterfaceOutListData();
		unitGradeInterfaceOutListData.setIoType(frontInterFaceStatusOutData.getIOType1());
		unitGradeInterfaceOutListData.setInterfaceId(frontInterFaceStatusOutData.getInterfaceId());
		unitGradeInterfaceOutListData.setDetialName(frontInterFaceStatusOutData.getDetialName());
		unitGradeInterfaceOutListData.setEqSystemName(frontInterFaceStatusOutData.getEqSystemName());
		unitGradeInterfaceOutListData.setClassName(frontInterFaceStatusOutData.getClassName());
		unitGradeInterfaceOutListData.setTime(a);
		interfaces.add(unitGradeInterfaceOutListData);
		UnitGradeInterfaceOutData unitGradeInterfaceOutData = new UnitGradeInterfaceOutData();
		if ("1".equals(frontInterFaceStatusOutData.getIOType1())) {
			Long time = unitGradeInterfaceOutListData.getTime();
			b1 += time;
		} else {
			Long time = unitGradeInterfaceOutListData.getTime();
			b2 += time;
		}
		unitGradeInterfaceOutData.setSimulationTime(b1);
		unitGradeInterfaceOutData.setDigitalTime(b2);
		unitGradeInterfaceOutData.setList(interfaces);
		interfaceOutData.add(unitGradeInterfaceOutData);
	}
}

