package com.gw.front.analysis.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.ExportExcel;
import com.gw.front.analysis.data.*;
import com.gw.front.analysis.service.FrontAnalysisService;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryMessageExport;
import com.gw.front.history.data.FrontHistoryMessageOutData;
import com.gw.mapper.*;
import com.gw.mapper.entity.UtUnitCalibration;
import com.gw.util.Util;
import com.gw.util.UtilConv;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.*;
import java.util.Map.Entry;

@Service
public class FrontAnalysisServiceImpl implements FrontAnalysisService {

	@Resource
	private UtUnitLookupLogMapper utUnitLookupLogMapper;
	@Resource
	private UtLzBjzjalarmMapper utLzBjzjalarmMapper;
	@Resource
	private UtPhoneLogMapper utPhoneLogMapper;
	@Resource
	private UtMessageLogMapper utMessageLogMapper;
	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtUnitEventMapper utUnitEventMapper;
	@Resource
	private UtUnitCalibrationMapper utUnitCalibrationMapper;
	@Value("${raw.data.database}")
	private String database;// 源数据所在数据库

	@Override
	public FrontCoupletCommonOutData getUplookCout(FrontAnalysisInData inData) throws Exception {
		inData.setDatabase(database);
		return utUnitLookupLogMapper.getUplookCout(inData);
	}

	@Override
	public PageInfo<FrontAnaLysisLookupOutData> getLookupStatList(FrontAnalysisInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontAnaLysisLookupOutData> list = utUnitLookupLogMapper.getLookupStatList(inData);
		return new PageInfo<>(list);
	}

	@Override
	public void exportLookupStatList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception {
		inData.setDatabase(database);
		List<FrontAnaLysisLookupExport> exportDatas = new ArrayList<>();
		List<FrontAnaLysisLookupOutData> list = utUnitLookupLogMapper.getLookupStatList(inData);
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		for (FrontAnaLysisLookupOutData frontAnaLysisLookupOutData : list) {
			FrontAnaLysisLookupExport data = new FrontAnaLysisLookupExport();
			BeanUtils.copyProperties(frontAnaLysisLookupOutData, data);
			String lookupCount = data.getLookupCount();
			String answerCount = data.getAnswerCount();
			if (Util.isNotEmpty(lookupCount) && Util.isNotEmpty(answerCount) && Integer.parseInt(lookupCount) != 0
					&& Integer.parseInt(answerCount) != 0) {
				data.setOnline(
						numberFormat.format(Float.parseFloat(answerCount) / Float.parseFloat(lookupCount) * 100));
			}
			exportDatas.add(data);
		}
		String[] header = new String[]{"单位编号", "子站号", "单位名称", "查岗次数", "应答次数", "在岗率(%)"};
		ExportExcel<FrontAnaLysisLookupExport> export = new ExportExcel<FrontAnaLysisLookupExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public FrontAnalysisAlarmStatOutData getAlarmCout(FrontAnalysisInData inData) throws Exception {
		List<FrontCoupletCommonOutData> alarmCout = utLzBjzjalarmMapper.getAlarmCout(inData);
		FrontAnalysisAlarmStatOutData outData = new FrontAnalysisAlarmStatOutData();
		setCountAlarm(alarmCout, outData);
		//查找真实火警
		inData.setSqlType("1");
		inData.setDealResult("2");
		inData.setIsDeal("1");
		List<FrontCoupletCommonOutData> alarmRealCout = utLzBjzjalarmMapper.getAlarmCout(inData);
		setCountAlarm(alarmRealCout, outData);
		//查找误报率
		inData.setSqlType("2");
		List<FrontCoupletCommonOutData> alarmDealCout = utLzBjzjalarmMapper.getAlarmCout(inData);
		setCountAlarm(alarmDealCout, outData);
		return outData;
	}

	private void setCountAlarm(List<FrontCoupletCommonOutData> alarmCout, FrontAnalysisAlarmStatOutData outData) {
		for (FrontCoupletCommonOutData frontCoupletCommonOutData : alarmCout) {
			if (Util.isEmpty(frontCoupletCommonOutData)) {
				continue;
			}
			String coupletValue = frontCoupletCommonOutData.getCoupletValue();
			String id = frontCoupletCommonOutData.getID();
			String alarmStatus = frontCoupletCommonOutData.getAlarmStatus();
			setCount(outData, coupletValue, id, alarmStatus);
		}
	}

	private void setCount(FrontAnalysisAlarmStatOutData outData, String coupletValue, String id, String alarmStatus) {
		// 1 灭火系统 2 电气火灾 3 报警系统 4 防火分离 5 气体系统 6 燃气系统 7 应急疏散 8 无线烟感 9 防排烟系统
		if (Util.isEmpty(coupletValue)) {
			outData.setFireCount(getCount(outData.getFireCount()));
			outData.setFireRealCount(getCount(outData.getFireRealCount()));
			return;
		}
		if ("1".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setWaterCount(getCount(outData.getWaterCount()));
			}
			return;
		}
		if ("2".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setEleCount(getCount(outData.getEleCount()));
			}
			return;
		}
		if ("3".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setFireCount(getCount(outData.getFireCount()));
			}
			if (Util.isNotEmpty(id)) {
				outData.setFireRealCount(getCount(outData.getFireRealCount()));
			}
			if (Util.isNotEmpty(alarmStatus)) {
				Integer count = (outData.getFireCount());
				if (count == 0) {
					outData.setFireDealCount(0);
				} else {
					outData.setFireDealCount(getCount(outData.getFireDealCount()));
				}
			}
			return;
		}
		if ("4".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setSplitCount(getCount(outData.getSplitCount()));
			}
			return;
		}
		if ("5".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setGaseousCount(getCount(outData.getGaseousCount()));
			}
			return;
		}
		if ("6".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setGasCount(getCount(outData.getGasCount()));
			}
			return;
		}
		if ("7".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setEmergencyCount(getCount(outData.getEmergencyCount()));
			}
			return;
		}
		if ("8".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setSmokeFeelingCount(getCount(outData.getSmokeFeelingCount()));
			}
			if (Util.isNotEmpty(id)) {
				outData.setSmokeFeelingRealCount(getCount(outData.getSmokeFeelingRealCount()));
			}
			if (Util.isNotEmpty(alarmStatus)) {
				Integer count = getCount(outData.getSmokeFeelingCount());
				if (count == 0) {
					outData.setSmokeFeelingDealCount(0);
				} else {
					if (Util.isNotEmpty(outData.getSmokeFeelingCount())) {
						outData.setSmokeFeelingDealCount(getCount(outData.getSmokeFeelingDealCount()));
					}
				}
			}
			return;
		}
		if ("9".equals(coupletValue)) {
			if (Util.isEmpty(id) && Util.isEmpty(alarmStatus)) {
				outData.setSmokeCount(getCount(outData.getSmokeCount()));
			}
			return;
		}
	}

	private Integer getCount(Integer count) {
		if (Util.isEmpty(count)) {
			return 1;
		}
		return count + 1;
	}

	@Override
	public FrontAnalysisAlarmCountOutData getAlarmStatBySystem(FrontAnalysisInData inData) throws Exception {
		FrontAnalysisAlarmCountOutData outData = new FrontAnalysisAlarmCountOutData();
		List<FrontCoupletCommonOutData> alarmCout = utLzBjzjalarmMapper.getAlarmCout(inData);

		Map<String, List<FrontCoupletCommonOutData>> map = new HashMap<>();
		for (FrontCoupletCommonOutData frontCoupletCommonOutData : alarmCout) {
			if (Util.isEmpty(frontCoupletCommonOutData)) {
				continue;
			}
			String coupletValue = frontCoupletCommonOutData.getCoupletValue();
			if (!map.containsKey(coupletValue)) {
				map.put(coupletValue, new ArrayList<>());
			}
			map.get(coupletValue).add(frontCoupletCommonOutData);
		}
		for (Entry<String, List<FrontCoupletCommonOutData>> entry : map.entrySet()) {
			String key = entry.getKey();
			List<FrontCoupletCommonOutData> value = entry.getValue();
			Map<String, FrontCoupletCommonOutData> map1 = new HashMap<>();
			for (FrontCoupletCommonOutData frontCoupletCommonOutData : value) {
				String coupletKey = frontCoupletCommonOutData.getCoupletKey();
				if (!map1.containsKey(coupletKey)) {
					FrontCoupletCommonOutData frontCoupletCommonOutData2 = new FrontCoupletCommonOutData();
					map1.put(coupletKey, frontCoupletCommonOutData2);
					frontCoupletCommonOutData2.setCoupletValue("1");
					frontCoupletCommonOutData2.setCoupletKey(dealStatus(coupletKey));
				} else {
					FrontCoupletCommonOutData frontCoupletCommonOutData2 = map1.get(coupletKey);
					frontCoupletCommonOutData2
							.setCoupletValue((Integer.parseInt(frontCoupletCommonOutData2.getCoupletValue()) + 1) + "");
				}
			}
			for (FrontCoupletCommonOutData frontCoupletCommonOutData : map1.values()) {
				setFrontCoupletCommonOutData(key, frontCoupletCommonOutData, outData);
			}
		}

		return outData;
	}

	private void setFrontCoupletCommonOutData(String coupletValue, FrontCoupletCommonOutData data,
											  FrontAnalysisAlarmCountOutData outData) {

		// 1 水系统 2 电气火灾 3 火灾自动报警系统 4 防火分离 5 气体系统 6 燃气系统 7 应急疏散 8 无线烟感 9 防排烟系统
		if (Util.isEmpty(coupletValue)) {
			outData.getFireCount().add(data);
			return;
		}
		if ("1".equals(coupletValue)) {
			outData.getWaterCount().add(data);
			;
			return;
		}
		if ("2".equals(coupletValue)) {
			outData.getEleCount().add(data);
			return;
		}
		if ("3".equals(coupletValue)) {
			outData.getFireCount().add(data);
			return;
		}
		if ("4".equals(coupletValue)) {
			outData.getSplitCount().add(data);
			return;
		}
		if ("5".equals(coupletValue)) {
			outData.getGaseousCount().add(data);
			return;
		}
		if ("6".equals(coupletValue)) {
			outData.getGasCount().add(data);
			return;
		}
		if ("7".equals(coupletValue)) {
			outData.getEmergencyCount().add(data);
			return;
		}
		if ("8".equals(coupletValue)) {
			outData.getSmokeFeelingCount().add(data);
			return;
		}
		if ("9".equals(coupletValue)) {
			outData.getSmokeCount().add(data);
			return;
		}

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
				return alarmStatus;
		}
	}

	@Override
	public FrontAnalysisCommunicationOutData getCommunicationCount(FrontAnalysisInData inData) throws Exception {
		inData.setDatabase(database);
		String startDate = inData.getStartDate();
		if (Util.isEmpty(startDate)) {
			startDate = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
		}
		FrontAnalysisCommunicationOutData outData = utPhoneLogMapper.getCommunicationCount(inData);
		List<FrontAnalysisCommunicationOutData> list = utPhoneLogMapper.getDataflows(inData);
		BigDecimal receive = new BigDecimal("0");
		BigDecimal send = new BigDecimal("0");
		for (FrontAnalysisCommunicationOutData frontAnalysisCommunicationOutData : list) {
			String receiveCount = frontAnalysisCommunicationOutData.getReceiveCount();
			if (Util.isNotEmpty(receiveCount)) {
				receive = receive.add(new BigDecimal(receiveCount));
			}
			String sendCount = frontAnalysisCommunicationOutData.getSendCount();
			if (Util.isNotEmpty(sendCount)) {
				send = send.add(new BigDecimal(sendCount));
			}
		}
		if (Util.isEmpty(outData)) {
			outData = new FrontAnalysisCommunicationOutData();
		}
		BigDecimal qian = new BigDecimal(1024 * 1024);
		outData.setReceiveCount(receive.divide(qian, 3, BigDecimal.ROUND_HALF_UP).toString());
		outData.setSendCount(send.divide(qian, 3, BigDecimal.ROUND_HALF_UP).toString());
		return outData;
	}

	@Override
	public PageInfo<FrontHistoryMessageOutData> getMessageList(FrontHistoryInData inData) throws Exception {
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHistoryMessageOutData> list = utMessageLogMapper.getAnalysisMessageList(inData);
		PageInfo<FrontHistoryMessageOutData> pager = new PageInfo<>(list);
		return pager;
	}

	@Override
	public void exportMessageList(FrontHistoryInData inData, HttpServletResponse response) throws Exception {
		List<FrontHistoryMessageExport> exportDatas = new ArrayList<>();
		List<FrontHistoryMessageOutData> list = utMessageLogMapper.getAnalysisMessageList(inData);
		for (FrontHistoryMessageOutData frontHistoryMessageOutData : list) {
			FrontHistoryMessageExport exportData = new FrontHistoryMessageExport();
			BeanUtils.copyProperties(frontHistoryMessageOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"单位编号", "子站号", "单位名称", "联系人", "发送手机号", "短信数"};
		ExportExcel<FrontHistoryMessageExport> export = new ExportExcel<FrontHistoryMessageExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public PageInfo<FrontAnalysisPhoneOutData> getPhoneList(FrontAnalysisInData inData) throws Exception {
		String endDate = inData.getEndDate();
		String startDate = inData.getStartDate();
		if (Util.isNotEmpty(endDate) && Util.isNotEmpty(startDate)) {
			inData.setStartDate(null);
			inData.setEndDate(startDate + "-" + endDate);
		} else {
			inData.setEndDate(null);
		}
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontAnalysisPhoneOutData> list = utPhoneLogMapper.getAnalysisPhoneList(inData);
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		for (FrontAnalysisPhoneOutData frontAnalysisPhoneOutData : list) {
			Integer phoneCount = frontAnalysisPhoneOutData.getPhoneCount();
			Integer phoneSucCount = frontAnalysisPhoneOutData.getPhoneSucCount();
			Integer phoneAnswerCount = frontAnalysisPhoneOutData.getPhoneAnswerCount();
			if (Util.isNotEmpty(phoneCount) && Util.isNotEmpty(phoneSucCount) && phoneCount != 0) {
				frontAnalysisPhoneOutData
						.setSuccessRate(numberFormat.format((float) phoneSucCount / (float) phoneCount * 100));
			}
			if (Util.isNotEmpty(phoneCount) && Util.isNotEmpty(phoneAnswerCount) && phoneCount != 0) {
				frontAnalysisPhoneOutData
						.setAnswerRate(numberFormat.format((float) phoneAnswerCount / (float) phoneCount * 100));
			}
		}
		return new PageInfo<>(list);
	}

	@Override
	public void exportPhoneList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception {
		String endDate = inData.getEndDate();
		String startDate = inData.getStartDate();
		if (Util.isNotEmpty(endDate) && Util.isNotEmpty(startDate)) {
			inData.setStartDate(null);
			inData.setEndDate(startDate + "-" + endDate);
		} else {
			inData.setEndDate(null);
		}
		List<FrontAnalysisPhoneExport> exportDatas = new ArrayList<>();
		List<FrontAnalysisPhoneOutData> list = utPhoneLogMapper.getAnalysisPhoneList(inData);
		NumberFormat numberFormat = NumberFormat.getInstance();
		numberFormat.setMaximumFractionDigits(2);
		for (FrontAnalysisPhoneOutData frontAnalysisMessageOutData : list) {
			FrontAnalysisPhoneExport exportData = new FrontAnalysisPhoneExport();
			BeanUtils.copyProperties(frontAnalysisMessageOutData, exportData);
			Integer phoneCount = exportData.getPhoneCount();
			Integer phoneSucCount = exportData.getPhoneSucCount();
			Integer phoneAnswerCount = exportData.getPhoneAnswerCount();
			if (Util.isNotEmpty(phoneCount) && Util.isNotEmpty(phoneSucCount) && phoneCount != 0) {
				exportData.setSuccessRate(numberFormat.format((float) phoneSucCount / (float) phoneCount * 100));
			}
			if (Util.isNotEmpty(phoneCount) && Util.isNotEmpty(phoneAnswerCount) && phoneCount != 0) {
				exportData.setAnswerRate(numberFormat.format((float) phoneAnswerCount / (float) phoneCount * 100));
			}
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"单位编号", "子站号", "单位名称", "接通次数", "接通时长", "电话拨打次数", "拨通次数", "通话率", "拨通率"};
		ExportExcel<FrontAnalysisPhoneExport> export = new ExportExcel<FrontAnalysisPhoneExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public PageInfo<FrontAnalysisUnitOutData> getUnitList(FrontAnalysisInData inData) throws Exception {
		if (Util.isEmpty(inData.getStartDate())) {
			inData.setStartDate(UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN));
		}
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontAnalysisUnitOutData> list = utUnitBaseinfoMapper.getUnitStatList(inData);
		return new PageInfo<>(list);
	}

	@Override
	public void exportUnitList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception {
		List<FrontAnalysisUnitExport> exportDatas = new ArrayList<>();
		List<FrontAnalysisUnitOutData> list = utUnitBaseinfoMapper.getUnitStatList(inData);
		for (FrontAnalysisUnitOutData frontAnalysisUnitOutData : list) {
			FrontAnalysisUnitExport exportData = new FrontAnalysisUnitExport();
			BeanUtils.copyProperties(frontAnalysisUnitOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[]{"单位编号", "子站号", "单位名称", "设备总数", "查岗次数", "查岗应答次数", "接通时长", "短信数量", "总流量", "发出流量",
				"接收流量"};
		ExportExcel<FrontAnalysisUnitExport> export = new ExportExcel<FrontAnalysisUnitExport>();
		export.exportExcel(response, exportDatas, header);

	}

	@Override
	public List<FrontCoupletCommonOutData> eventsCount(FrontAnalysisInData inData) throws Exception {
		inData.setDatabase(database);
		List<FrontCoupletCommonOutData> outData = utUnitEventMapper.eventsCount(inData);
		List<UtUnitCalibration> list = utUnitCalibrationMapper.selectAll();
		Map<String, String> map = new HashMap<>();
		for (UtUnitCalibration utUnitCalibration : list) {
			map.put(utUnitCalibration.getId() + "", utUnitCalibration.getRemark());
		}
		for (FrontCoupletCommonOutData frontCoupletCommonOutData : outData) {
			frontCoupletCommonOutData.setCoupletKey(dealEventType(frontCoupletCommonOutData.getCoupletKey(), map));
		}
		return outData;
	}

	private String dealEventType(String coupletKey, Map<String, String> map) {
		if (Util.isEmpty(coupletKey)) {
			return "";
		}
		String string = map.get(coupletKey);
		if (!Util.isEmpty(string)) {
			return string;
		}
		switch (coupletKey) {
			case "1000":
				return "逻辑火警";
			case "1001":
				return "潮湿引起的故障";
			case "2000":
				return "漏水逻辑";
			case "2001":
				return "破损逻辑";
			default:
				break;
		}
		return "";
	}

	@Override
	public PageInfo<FrontAnalysisEventOutData> getEventList(FrontAnalysisInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontAnalysisEventOutData> list = utUnitEventMapper.getEventList(inData);
		List<UtUnitCalibration> list1 = utUnitCalibrationMapper.selectAll();
		Map<String, String> map = new HashMap<>();
		for (UtUnitCalibration utUnitCalibration : list1) {
			map.put(utUnitCalibration.getId() + "", utUnitCalibration.getRemark());
		}
		for (FrontAnalysisEventOutData frontAnalysisEventOutData : list) {
			String eventTypeName = frontAnalysisEventOutData.getEventTypeName();
			if ("1000".equals(eventTypeName) || "1001".equals(eventTypeName) || "2000".equals(eventTypeName)
					|| "2001".equals(eventTypeName)) {
				frontAnalysisEventOutData.setEventUrl(eventTypeName);
			}
			frontAnalysisEventOutData.setEventTypeName(dealEventType(eventTypeName, map));
		}
		return new PageInfo<FrontAnalysisEventOutData>(list);
	}

	@Override
	public void exportEventList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception {
		inData.setDatabase(database);
		List<FrontAnalysisEventExport> exportDatas = new ArrayList<>();
		List<FrontAnalysisEventOutData> list = utUnitEventMapper.getEventList(inData);
		List<UtUnitCalibration> list1 = utUnitCalibrationMapper.selectAll();
		Map<String, String> map = new HashMap<>();
		for (UtUnitCalibration utUnitCalibration : list1) {
			map.put(utUnitCalibration.getId() + "", utUnitCalibration.getRemark());
		}
		for (FrontAnalysisEventOutData frontAnalysisEventOutData : list) {
			FrontAnalysisEventExport data = new FrontAnalysisEventExport();
			BeanUtils.copyProperties(frontAnalysisEventOutData, data);
			data.setEventTypeName(dealEventType(frontAnalysisEventOutData.getEventTypeName(), map));
			exportDatas.add(data);
		}
		String[] header = new String[]{"事件类型", "单位编号", "子站号", "单位名称", "事件记录时间", "事件结束时间", "结束说明"};
		ExportExcel<FrontAnalysisEventExport> export = new ExportExcel<FrontAnalysisEventExport>();
		export.exportExcel(response, exportDatas, header);
	}
}
