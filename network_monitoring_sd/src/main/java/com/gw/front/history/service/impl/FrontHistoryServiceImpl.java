package com.gw.front.history.service.impl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.gw.front.history.data.*;
import com.gw.mapper.*;
import com.gw.mapper.entity.UtBaseUseRelation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.ExportExcel;
import com.gw.device.data.EqSystemOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.history.service.FrontHistoryService;
import com.gw.mapper.entity.UtUnitNetdevice;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import tk.mybatis.mapper.entity.Example;

@Service
public class FrontHistoryServiceImpl implements FrontHistoryService {

	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Resource
	private UtLzFireequipmentalarmMapper utLzFireequipmentalarmMapper;
	@Resource
	private UtUnitVideoLogMapper utUnitVideoLogMapper;
	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Value("${raw.data.database}")
	private String database;// 源数据所在数据库
	@Resource
	private SysOperationLogMapper sysOperationLogMapper;
	@Resource
	private UtMessageLogMapper utMessageLogMapper;
	@Resource
	private UtPhoneLogMapper utPhoneLogMapper;
	@Resource
	private UtBaseEqsystemMapper utBaseEqsystemMapper;
	@Resource
	private UtUserDutyLogMapper utUserDutyLogMapper;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtUnitCalibrationMapper utUnitCalibrationMapper;
	@Autowired
	private UtBaseUserRelationMapper utBaseUserRelationMapper;

	@Override
	public PageInfo<FrontHistoryAlarmInfoOutData> getFireHistoryList(FrontHistoryInData inData) throws Exception {

		List<String> statusList = new ArrayList<String>();
		if (Util.isNotEmpty(inData.getStatus())) {
			String[] statuss = inData.getStatus().split(",");
			for (String status : statuss) {
				statusList.add(status);
			}
		}
		inData.setStatusList(statusList);
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		//该项为sql查找标识
		inData.setSelectType("2");
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireHistoryList(inData);
		if (Util.isEmpty(userId)) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			//该项为sql查找标识
			inData.setSelectType("1");
			list = utLzBjzjalarmMapper.getFireHistoryList(inData);
		} else {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			//该项为sql查找标识
			inData.setSelectType("3");
			List<FrontHistoryAlarmInfoOutData> listBJZJ = utLzBjzjalarmMapper.getFireHistoryList(inData);
			if (Util.isNotEmpty(listBJZJ)) {
				list.addAll(listBJZJ);
				Collections.sort(list, new Comparator<FrontHistoryAlarmInfoOutData>() {
					@Override
					public int compare(FrontHistoryAlarmInfoOutData o1, FrontHistoryAlarmInfoOutData o2) {
						return o2.getLastupdate().compareTo(o1.getLastupdate());
					}
				});
			}
		}
		for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
			frontHistoryAlarmInfoOutData.setAlarmStatus(dealStatus(frontHistoryAlarmInfoOutData.getAlarmStatus()));
		}
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<FrontHistoryAlarmInfoOutData> getUnitFireList(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<String> statusList = new ArrayList<String>();
		if (Util.isNotEmpty(inData.getStatus())) {
			String[] statuss = inData.getStatus().split(",");
			for (String status : statuss) {
				statusList.add(status);
			}
		}
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireUnitList(inData);
		for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
			frontHistoryAlarmInfoOutData.setAlarmStatus(dealStatus(frontHistoryAlarmInfoOutData.getAlarmStatus()));
		}
		return new PageInfo<>(list);
	}

	@Override
	public void exportFireHistoryList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontHistoryAlarmInfoExport> exportDatas = new ArrayList<>();
		List<String> statusList = new ArrayList<String>();
		if (Util.isNotEmpty(inData.getStatus())) {
			String[] statuss = inData.getStatus().split(",");
			for (String status : statuss) {
				statusList.add(status);
			}
		}
		inData.setStatusList(statusList);
		//该项为sql查找标识
		inData.setSelectType("2");
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireHistoryList(inData);
		if (Util.isEmpty(list)) {
			//该项为sql查找标识
			inData.setSelectType("1");
			list = utLzBjzjalarmMapper.getFireHistoryList(inData);
		}
		for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
			FrontHistoryAlarmInfoExport exportData = new FrontHistoryAlarmInfoExport();
			BeanUtils.copyProperties(frontHistoryAlarmInfoOutData, exportData);
			exportData.setAlarmStatus(dealStatus(exportData.getAlarmStatus()));
			exportDatas.add(exportData);
		}

		String[] header = new String[]{"误报", "测试", "单位编号", "子站号", "单位名称", "报警设备", "接警时间", "主机时间", "类型", "报警源", "描述",
				"发生地点", "是否处理", "处理编号", "处理内容"};
		ExportExcel<FrontHistoryAlarmInfoExport> export = new ExportExcel<FrontHistoryAlarmInfoExport>();
		export.exportExcel(response, exportDatas, header);

	}

	private String dealStatus(String alarmStatus) {
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
	public PageInfo<FrontHistoryInterfaceAlarmData> getInterfaceAlarmList(FrontHistoryInData inData) throws Exception {
		List<FrontHistoryInterfaceAlarmData> list = null;
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		if (Util.isEmpty(userId)) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			list = utLzFireequipmentalarmMapper.getInterfaceAlarmList(inData);
		} else {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			list = utLzFireequipmentalarmMapper.getInterfaceAlarmBuildList(inData);
		}
		return new PageInfo<FrontHistoryInterfaceAlarmData>(list);
	}

	@Override
	public void exportUnitInterfaceAlarmList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontUnitInterfaceExport> exportDatas = new ArrayList<>();
		List<FrontHistoryInterfaceAlarmData> list = utLzFireequipmentalarmMapper.getInterfaceAlarmBuildList(inData);
		if (Util.isEmpty(list)) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			list = utLzFireequipmentalarmMapper.getInterfaceAlarmList(inData);
		}
		for (FrontHistoryInterfaceAlarmData frontHistoryInterfaceAlarmData : list) {
			FrontUnitInterfaceExport exportData = new FrontUnitInterfaceExport();
			BeanUtils.copyProperties(frontHistoryInterfaceAlarmData, exportData);
			exportData.setAlarmCat(frontHistoryInterfaceAlarmData.getEqName()
					+ "-" + frontHistoryInterfaceAlarmData.getNormalValue() + "-" + frontHistoryInterfaceAlarmData.getAlarmValue());
			exportDatas.add(exportData);
		}

		String[] header = new String[]{"报警设备", "首次报警时间", "描述", "次数"};
		ExportExcel<FrontUnitInterfaceExport> export = new ExportExcel<FrontUnitInterfaceExport>();
		export.exportExcel(response, exportDatas, header);

	}

	@Override
	public void exportInterfaceAlarmList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontHistoryInterfaceExport> exportDatas = new ArrayList<>();
		List<FrontHistoryInterfaceAlarmData> list = utLzFireequipmentalarmMapper.getInterfaceAlarmBuildList(inData);
		if (Util.isEmpty(list)) {
			list = utLzFireequipmentalarmMapper.getInterfaceAlarmList(inData);
		}
		for (FrontHistoryInterfaceAlarmData frontHistoryInterfaceAlarmData : list) {
			FrontHistoryInterfaceExport exportData = new FrontHistoryInterfaceExport();
			BeanUtils.copyProperties(frontHistoryInterfaceAlarmData, exportData);
			exportDatas.add(exportData);
		}

		String[] header = new String[]{"单位名称", "单位编号", "检测设备详情名称", "正常值", "报警值", "报警时间", "处理人", "处理时间", "误报", "处理编号",
				"是否处理", "处理内容"};
		ExportExcel<FrontHistoryInterfaceExport> export = new ExportExcel<FrontHistoryInterfaceExport>();
		export.exportExcel(response, exportDatas, header);

	}

	@Override
	public PageInfo<FrontHistoryVideoPicOutData> getVideoLogList(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryVideoPicOutData> list = utUnitVideoLogMapper.getVideoLogList(inData);
		return new PageInfo<FrontHistoryVideoPicOutData>(list);
	}

	@Override
	public PageInfo<FrontHisSDDeviceStatusOutData> getSDDeviceStatusList(FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
//		for (FrontHisSDDeviceStatusOutData frontHisSDDeviceStatusOutData : list) {
//			frontHisSDDeviceStatusOutData.setDeviceStatus(deal(frontHisSDDeviceStatusOutData.getDeviceStatus()));
//		}
		return new PageInfo<FrontHisSDDeviceStatusOutData>(list);
	}

	@Override
	public void exportSDDeviceStatusList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		inData.setDatabase(database);
		List<FrontHisSDDeviceStatusExport> exportDatas = new ArrayList<>();
		List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getSDDeviceStatusList(inData);
		for (FrontHisSDDeviceStatusOutData frontHisSDDeviceStatusOutData : list) {
			FrontHisSDDeviceStatusExport exportData = new FrontHisSDDeviceStatusExport();
			BeanUtils.copyProperties(frontHisSDDeviceStatusOutData, exportData);
			exportData.setDeviceStatus(deal(frontHisSDDeviceStatusOutData.getDeviceStatus()));
			exportDatas.add(exportData);
		}

		String[] header = new String[]{"设备类型", "设备名称", "子号", "使用时间", "当前状态", "上传数", "单位名称", "单位编号", "异常次数", "硬件版本",
				"软件版本"};
		ExportExcel<FrontHisSDDeviceStatusExport> export = new ExportExcel<FrontHisSDDeviceStatusExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public FrontHisDeviceStatusOutData getDeviceInfo(FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		FrontHisDeviceStatusOutData deviceInfo = utUnitNetdeviceMapper.getDeviceInfo(inData);
		List<FrontCoupletCommonOutData> deviceStatusList = utUnitNetdeviceMapper.getDeviceStatusList(inData);
		if (Util.isNotEmpty(deviceStatusList)) {
			long exceptionTime = 0l;
			for (int i = 0; i < deviceStatusList.size(); i++) {
				if (i == 0 && deviceStatusList.get(i).getCoupletKey().equals("0")) {
					Date d1 = UtilConv.str2Date(deviceStatusList.get(i).getCoupletValue(), UtilConv.DATE_YYYY_MM_DD_SS);
					Date d2 = UtilConv.Dateformat(new Date(), UtilConv.DATE_YYYY_MM_DD_SS);
					long diff = d2.getTime() - d1.getTime();
					exceptionTime += diff;
					continue;
				}
				if (i == deviceStatusList.size() - 1) {
					break;
				}
				if (deviceStatusList.get(i).getCoupletKey().equals("1") && deviceStatusList.get(i + 1).getCoupletKey().equals("0")) {
					Date d2 = UtilConv.str2Date(deviceStatusList.get(i).getCoupletValue(), UtilConv.DATE_YYYY_MM_DD_SS);
					int j = i + 1;
					if (j >= deviceStatusList.size()) {
						break;
					}
					while (deviceStatusList.get(j).getCoupletKey().equals("0")) {
						j++;
						if (j == deviceStatusList.size()) {
							break;
						}
					}
					Date d1 = UtilConv.str2Date(deviceStatusList.get(j - 1).getCoupletValue(), UtilConv.DATE_YYYY_MM_DD_SS);
					long diff = d2.getTime() - d1.getTime();
					exceptionTime += diff;
					i = j - 1;
					continue;
				}
			}
			long diffHours = exceptionTime / (60 * 60 * 1000);
			long min = ((exceptionTime / (60 * 1000)) - diffHours * 60);
			deviceInfo.setExceptionTime(diffHours + "小时" + min + "分钟");
		}
		return deviceInfo;
	}

	// 设备状态
	private String deal(String online) {
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
	public PageInfo<FrontCoupletCommonOutData> getDeviceStatusList(FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());

		List<FrontCoupletCommonOutData> deviceStatusList = utUnitNetdeviceMapper.getDeviceStatusList(inData);
		return new PageInfo<FrontCoupletCommonOutData>(deviceStatusList);
	}

	@Override
	public PageInfo<FrontHistoryOperationOutData> getOperationList(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryOperationOutData> list;
		String type = inData.getType();
		if ("1".equals(type)) {//处理记录
			list = sysOperationLogMapper.getDealOperationList(inData);
		} else {
			list = sysOperationLogMapper.getOperationList(inData);
		}
		PageInfo<FrontHistoryOperationOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public void exportOperationList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontHistoryOperationExport> exportDatas = new ArrayList<>();
		List<FrontHistoryOperationOutData> list = sysOperationLogMapper.getOperationList(inData);
		for (FrontHistoryOperationOutData frontHistoryOperationOutData : list) {
			FrontHistoryOperationExport exportData = new FrontHistoryOperationExport();
			BeanUtils.copyProperties(frontHistoryOperationOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"登录名", "用户姓名", "所属单位", "操作内容", "当前状态", "操作时间", "操作地点"};
		ExportExcel<FrontHistoryOperationExport> export = new ExportExcel<FrontHistoryOperationExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public PageInfo<FrontHistoryMessageOutData> getMessageList(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryMessageOutData> list = utMessageLogMapper.getMessageList(inData);
		PageInfo<FrontHistoryMessageOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public void exportMessageList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontHistoryMessageExport> exportDatas = new ArrayList<>();
		List<FrontHistoryMessageOutData> list = utMessageLogMapper.getMessageList(inData);
		for (FrontHistoryMessageOutData frontHistoryMessageOutData : list) {
			FrontHistoryMessageExport exportData = new FrontHistoryMessageExport();
			BeanUtils.copyProperties(frontHistoryMessageOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"单位名称", "联系人", "发送手机号", "短信数"};
		ExportExcel<FrontHistoryMessageExport> export = new ExportExcel<FrontHistoryMessageExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public PageInfo<FrontHistoryMessageOutData> getMessageStatDetail(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryMessageOutData> list = utMessageLogMapper.getMessageStatDetail(inData);
		PageInfo<FrontHistoryMessageOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public PageInfo<FrontHistoryMessageOutData> getPhoneList(FrontHistoryInData inData) throws Exception {
		String endDate = inData.getEndDate();
		String startDate = inData.getStartDate();
		if (Util.isNotEmpty(endDate) && Util.isNotEmpty(startDate)) {
			inData.setStartDate(null);
			inData.setEndDate(startDate + "-" + endDate);
		} else {
			inData.setEndDate(null);
		}
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryMessageOutData> list = utPhoneLogMapper.getPhoneList(inData);
		PageInfo<FrontHistoryMessageOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public void exportPhoneList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontHistoryMessageExport> exportDatas = new ArrayList<>();
		String endDate = inData.getEndDate();
		String startDate = inData.getStartDate();
		if (Util.isNotEmpty(endDate) && Util.isNotEmpty(startDate)) {
			inData.setStartDate(null);
			inData.setEndDate(startDate + "-" + endDate);
		} else {
			inData.setEndDate(null);
		}
		List<FrontHistoryMessageOutData> list = utPhoneLogMapper.getPhoneList(inData);
		for (FrontHistoryMessageOutData frontHistoryMessageOutData : list) {
			FrontHistoryMessageExport exportData = new FrontHistoryMessageExport();
			BeanUtils.copyProperties(frontHistoryMessageOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"单位名称", "联系人", "手机号", "拨打次数"};
		ExportExcel<FrontHistoryMessageExport> export = new ExportExcel<FrontHistoryMessageExport>();
		export.exportExcel(response, exportDatas, header);

	}

	@Override
	public PageInfo<FrontHistoryMessageOutData> getPhoneStatDetail(FrontHistoryInData inData) throws Exception {
		String endDate = inData.getEndDate();
		String startDate = inData.getStartDate();
		if (Util.isNotEmpty(endDate) && Util.isNotEmpty(startDate)) {
			inData.setStartDate(null);
			inData.setEndDate(startDate + "-" + endDate);
		} else {
			inData.setEndDate(null);
		}
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryMessageOutData> list = utPhoneLogMapper.getPhoneStatDetail(inData);
		PageInfo<FrontHistoryMessageOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public List<EqSystemOutData> getEqSystemByUnitId(String unitId, String buildId) throws Exception {
		return utBaseEqsystemMapper.getEqSystemByUnitId(unitId, buildId);
	}

	@Override
	public PageInfo<FrontHistoryUserDutyLogOutData> getUserDutyLogList(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryUserDutyLogOutData> list = utUserDutyLogMapper.getUserDutyLogList(inData);
		PageInfo<FrontHistoryUserDutyLogOutData> pageInfo = new PageInfo<FrontHistoryUserDutyLogOutData>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<FrontHistoryDataFlowOutData> getDataFlowList(FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		String startDate = inData.getStartDate();
		if (Util.isEmpty(startDate)) {
			startDate = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
		}
		List<FrontHistoryDataFlowOutData> list = utUnitBaseinfoMapper.getDataFlowList(inData);
		PageInfo<FrontHistoryDataFlowOutData> pageInfo = new PageInfo<FrontHistoryDataFlowOutData>(list);
		return pageInfo;
	}

	@Override
	public PageInfo<FrontTransmissionDeviceOutData> getOPDeviceHistory(FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontTransmissionDeviceOutData> list = utUnitNetdeviceMapper.getOPDeviceHistory(inData);
		for (FrontTransmissionDeviceOutData frontTransmissionDeviceOutData : list) {
			frontTransmissionDeviceOutData.setType(dealType(frontTransmissionDeviceOutData.getType()));
		}
		return new PageInfo<FrontTransmissionDeviceOutData>(list);
	}

	private String dealType(String type) {
		if (Util.isEmpty(type)) {
			return "";
		}
		switch (type) {
			case "1":
				return "装置开机";
			case "2":
				return "装置关机";
			case "3":
				return "二级锁开";
			case "4":
				return "二级锁关";
			case "5":
				return "手动报警";
			case "6":
				return "确认火警";
			case "7":
				return "应答查岗";
			case "8":
				return "进入自检";
			case "9":
				return "退出自检";
			case "11":
				return "退出测试";
			case "12":
				return "装置复位";
			case "13":
				return "火警延时";
			case "14":
				return "装置消音";
			case "15":
				return "解除报警";
			default:
				break;
		}
		return "";

	}

	@Override
	public void exportOPDeviceHistory(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontTransmissionDeviceExport> exportDatas = new ArrayList<>();
		List<FrontTransmissionDeviceOutData> list = utUnitNetdeviceMapper.getOPDeviceHistory(inData);
		for (FrontTransmissionDeviceOutData frontTransmissionDeviceOutData : list) {
			FrontTransmissionDeviceExport data = new FrontTransmissionDeviceExport();
			BeanUtils.copyProperties(frontTransmissionDeviceOutData, data);
			data.setType(dealType(frontTransmissionDeviceOutData.getType()));
			exportDatas.add(data);
		}
		String[] header = new String[]{"单位编号", "子站号", "单位名称", "联系电话", "操作类型", "操作时间", "接收时间"};
		ExportExcel<FrontTransmissionDeviceExport> export = new ExportExcel<FrontTransmissionDeviceExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public void exportDataFlowList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		inData.setDatabase(database);
		String startDate = inData.getStartDate();
		if (Util.isEmpty(startDate)) {
			startDate = UtilConv.date2Str(new Date(), UtilConv.YEAR_MONTH_);
		} else {
			inData.setStartDate(startDate.substring(0, startDate.lastIndexOf("-")));
		}
		String endDate = inData.getEndDate();
		if (Util.isNotEmpty(endDate)) {
			inData.setEndDate(endDate.substring(0, endDate.lastIndexOf("-")));
		}
		List<FrontHistoryDataFlowExport> exportDatas = new ArrayList<FrontHistoryDataFlowExport>();
		List<FrontHistoryDataFlowOutData> list = utUnitBaseinfoMapper.getDataFlowList(inData);
		for (FrontHistoryDataFlowOutData frontHistoryDataFlowOutData : list) {
			FrontHistoryDataFlowExport data = new FrontHistoryDataFlowExport();
			BeanUtils.copyProperties(frontHistoryDataFlowOutData, data);
			data.setAll(data.getRx() + data.getTx());
			exportDatas.add(data);
		}
		String[] header = new String[]{"单位编号", "单位名称", "联系人", "联系电话", "总流量(兆)", "发送流量(兆)", "接收流量(兆)"};
		ExportExcel<FrontHistoryDataFlowExport> export = new ExportExcel<FrontHistoryDataFlowExport>();
		export.exportExcel(response, exportDatas, header);

	}

	@Override
	public void exportUserDutyLogList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontHistoryUserDutyLogExport> exportDatas = new ArrayList<>();
		List<FrontHistoryUserDutyLogOutData> list = utUserDutyLogMapper.getUserDutyLogList(inData);
		for (FrontHistoryUserDutyLogOutData frontHistoryUserDutyLogOutData : list) {
			FrontHistoryUserDutyLogExport data = new FrontHistoryUserDutyLogExport();
			BeanUtils.copyProperties(frontHistoryUserDutyLogOutData, data);
			exportDatas.add(data);
		}
		String[] header = new String[]{"值班人", "消控室", "所属单位", "值班开始时间", "值班结束时间", "住址", "电话", "身份证"};
		ExportExcel<FrontHistoryUserDutyLogExport> export = new ExportExcel<FrontHistoryUserDutyLogExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public Integer getDeviceUploadCount(String deviceId, String startDate, String endDate) throws Exception {
		UtUnitNetdevice netdevice = utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(deviceId));
		String ownercode = netdevice.getOwnercode();
		Integer deviceIndex = netdevice.getDeviceindex();
		Integer deviceno = netdevice.getDeviceno();
		if (deviceIndex == 2) {
			deviceno = null;
		}
		String db = database + ".dbo.D" + ownercode;
		Integer outData = utUnitNetdeviceMapper.getDeviceUploadCount(db, deviceIndex, deviceno, startDate, endDate);
		return outData;
	}

	@Override
	public PageInfo<FrontHistoryAlarmInfoOutData> getFireList(FrontHistoryInData inData) throws Exception {
		Example example = new Example(UtBaseUseRelation.class);
		example.createCriteria().andEqualTo("userId", Long.valueOf(inData.getUserId()));
		List<UtBaseUseRelation> userId = utBaseUserRelationMapper.selectByExample(example);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		//sql查找标识
		inData.setSelectType("2");
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireList(inData);
		if (Util.isEmpty(userId)) {
			PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
			//sql查找标识
			inData.setSelectType("1");
			list = utLzBjzjalarmMapper.getFireList(inData);
		} else {
			if (Util.isEmpty(list)) {
				PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
				//sql查找标识
				inData.setSelectType("3");
				list = utLzBjzjalarmMapper.getFireList(inData);
			} else {
				PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
				//sql查找标识
				inData.setSelectType("3");
				List<FrontHistoryAlarmInfoOutData> listBJZJ = utLzBjzjalarmMapper.getFireList(inData);
				if (Util.isNotEmpty(listBJZJ)) {
					PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
					list.addAll(listBJZJ);
				}
			}
		}
		for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
			frontHistoryAlarmInfoOutData.setAlarmStatus(dealStatus(frontHistoryAlarmInfoOutData.getAlarmStatus()));
		}
		return new PageInfo<>(list);
	}

	@Override
	public PageInfo<FrontHistoryAlarmInfoOutData> getFireUnitList(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		inData.setSelectType("4");
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireList(inData);
		return new PageInfo<>(list);
	}

	@Override
	public void exportFireList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontFireListExport> exports = new ArrayList<>();
//		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireListByBuild(inData);
		inData.setSelectType("2");
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireList(inData);
		if (Util.isEmpty(list)) {
			inData.setSelectType("1");
			list = utLzBjzjalarmMapper.getFireList(inData);
		}
		for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
			FrontFireListExport export = new FrontFireListExport();
			BeanUtils.copyProperties(frontHistoryAlarmInfoOutData, export);
			export.setAlarmStatus(dealStatus(export.getAlarmStatus()));
			exports.add(export);
		}
		String[] header = new String[]{"单位名称", "时间", "测试", "报警类型", "节点", "报警分类", "报警地点"};
		ExportExcel<FrontFireListExport> excel = new ExportExcel<FrontFireListExport>();
		excel.exportExcel(response, exports, header);
	}

	@Override
	public void exportUnitFireList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontUnitFireListExport> exports = new ArrayList<>();
		List<FrontHistoryAlarmInfoOutData> list = utLzBjzjalarmMapper.getFireUnitList(inData);
		for (FrontHistoryAlarmInfoOutData frontHistoryAlarmInfoOutData : list) {
			FrontUnitFireListExport export = new FrontUnitFireListExport();
			BeanUtils.copyProperties(frontHistoryAlarmInfoOutData, export);
			export.setAlarmStatus(dealStatus(export.getAlarmStatus()));
			export.setCount(frontHistoryAlarmInfoOutData.getCount());
			exports.add(export);
		}
		String[] header = new String[]{"报警设备", "报警类型", "报警时间", "报警地点", "节点", "描述", "次数"};
		ExportExcel<FrontUnitFireListExport> excel = new ExportExcel<FrontUnitFireListExport>();
		excel.exportExcel(response, exports, header);
	}
}
