package com.gw.front.lookup.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.common.ExportExcel;
import com.gw.common.SnowflakeIdWorker;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.couplet.data.FrontLookupUserInfoOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.lookup.data.*;
import com.gw.front.lookup.service.FrontLookupService;
import com.gw.mapper.UtUnitBaseinfoMapper;
import com.gw.mapper.UtUnitLookupLogMapper;
import com.gw.mapper.UtUnitNetdeviceMapper;
import com.gw.mapper.UtUnitUserExperienceMapper;
import com.gw.mapper.entity.UtUnitNetdevice;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.HttpClientUtil;
import com.gw.util.HttpJson;
import com.gw.util.ReqApiConst;
import com.gw.util.Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FrontLookupServiceImpl implements FrontLookupService {

	@Resource
	private UtUnitBaseinfoMapper utUnitBaseinfoMapper;
	@Resource
	private UtUnitNetdeviceMapper utUnitNetdeviceMapper;
	@Resource
	private UtUnitLookupLogMapper utUnitLookupLogMapper;
	@Resource
	private SnowflakeIdWorker snowflakeIdWorker;
	@Resource
	private PropertiesManageService propertiesManageService;
	@Value("${raw.data.database}")
	private String database;// 源数据所在数据库
//	@Value("${red.server_port}")
//	private String url;// 源数据所在数据库
//	@Value("${access_token}")
//	private String access_token;// 源数据所在数据库
	@Resource
	private UtUnitUserExperienceMapper utUnitUserExperienceMapper;

	@Override
	public PageInfo<FrontLookupUnitInfoData> getTransferDeviceUnit(String userId, String keyWord, Integer pageSize,
			Integer pageNumber) throws Exception {
		PageHelper.startPage(pageNumber, pageSize);
		List<FrontLookupUnitInfoData> list = utUnitBaseinfoMapper.getTransferDeviceUnit(userId, keyWord, database);
		// for (FrontLookupUnitInfoData frontLookupUnitInfoData : list) {
		// frontLookupUnitInfoData.setResult(deal(frontLookupUnitInfoData.getResult()));
		// }
		PageInfo<FrontLookupUnitInfoData> pager = new PageInfo<FrontLookupUnitInfoData>(list);
		return pager;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean lookup(String userId, String deviceIds, String type) throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		List<UtUnitNetdevice> list = new ArrayList<UtUnitNetdevice>();
		if (Util.isEmpty(deviceIds)) {
			return false;
		}
		if (deviceIds.indexOf(",") != -1) {
			List<String> list2 = Arrays.asList(deviceIds.split(","));
			for (String id : list2) {
				if (Util.isNotEmpty(id)) {
					list.add(utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(id)));
				}
			}
		} else {
			list.add(utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(deviceIds)));
		}
		boolean flag = true;
		for (UtUnitNetdevice utUnitNetdevice : list) {
			String ownercode = utUnitNetdevice.getOwnercode();
			Integer deviceno = utUnitNetdevice.getDeviceno();
			Integer deviceindex = utUnitNetdevice.getDeviceindex();
			if (deviceindex == 2) {//如果是用户传输装置则子号传1
				deviceno = 1;
			}

			StringBuilder urlSb = new StringBuilder(properties.getRedServerPort());

			// 调用远程接口
			String api = String.format(ReqApiConst.GET_REDSERVER_CHECKDEVICE_API,properties.getAccessToken(),userId,type,ownercode,deviceindex,deviceno);
			urlSb.append(api);
			log.error("调用远程接口"+urlSb.toString());
			HttpJson httpGet = HttpClientUtil.httpGet(urlSb.toString());
			if (httpGet.isSuccess()) {
				String msg = httpGet.getMsg();
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse(msg);
				Integer code = (Integer) map.get("Code");
				if (200 != code) {
					flag = false;
				}
			}
		}
		return flag;
	}

	@Override
	public PageInfo<FrontLookupLogOutData> getLookupLogs(String userId, FrontLookupInData indata) throws Exception {
		PageHelper.startPage(indata.getPageNumber(), indata.getPageSize());
		indata.setUserId(userId);
		indata.setDatabase(database);
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getLookupLogs(indata);
		PageInfo<FrontLookupLogOutData> pagers = new PageInfo<>(lookupLogs);
		return pagers;
	}

	@Override
	public void exportLookupLogs(String id, FrontLookupInData indata, HttpServletResponse response) throws Exception {
		indata.setDatabase(database);
		indata.setUserId(id);
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getLookupLogs(indata);
		List<FrontLookupLogDataExport> exportDatas = new ArrayList<>();
		for (FrontLookupLogOutData frontLookupLogOutData : lookupLogs) {
			FrontLookupLogDataExport exportData = new FrontLookupLogDataExport();
			BeanUtils.copyProperties(frontLookupLogOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[] { "单位编号", "子站号", "单位名称", "单位地址", "发送用户", "发送时间", "发送状态", "查岗状态", "回应时间", "回应人" };
		ExportExcel<FrontLookupLogDataExport> export = new ExportExcel<FrontLookupLogDataExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public void exportTransferDeviceUnit(String id, String keyWord, HttpServletResponse response) throws Exception {
		List<FrontLookupUnitInfoData> list = utUnitBaseinfoMapper.getTransferDeviceUnit(id, keyWord, database);
		List<FrontLookupUnitInfoDataExport> exportDatas = new ArrayList<>();
		for (FrontLookupUnitInfoData dataExport : list) {
			FrontLookupUnitInfoDataExport exportData = new FrontLookupUnitInfoDataExport();
			BeanUtils.copyProperties(dataExport, exportData);
			if(dataExport.getResult().equals("0")){
				exportData.setResult("设备离线");
			}
			exportDatas.add(exportData);
		}
		String[] header = new String[] { "单位编号", "子站号", "单位名称","设备编号", "单位地址", "联系电话", "结果" };
		ExportExcel<FrontLookupUnitInfoDataExport> export = new ExportExcel<FrontLookupUnitInfoDataExport>();
		export.exportExcel(response, exportDatas, header);
	}

//	// 设备状态
//	private String deal(String online) {
//		if (Util.isEmpty(online)) {
//			return "";
//		}
//		switch (online) {
//		case "0":
//			return "离线";
//		case "1":
//			return "在线";
//		case "2":
//			return "未注册";
//		case "3":
//			return "数据超时";
//		case "4":
//			return "异常";
//		case "5":
//			return "迁移";
//		case "6":
//			return "迁移失败";
//		case "7":
//			return "迁移成功";
//		case "16":
//			return "禁止报警主机接入";
//		case "17":
//			return "允许报警主机接入";
//		case "18":
//			return "报警主机正常";
//		case "19":
//			return "协议转换与传输装置通信异常";
//		case "20":
//			return "协议转换器与报警主机通信异常";
//		case "21":
//			return "关机状态";
//		case "32":
//			return "禁止RTU接入";
//		case "33":
//			return "允许RTU接入";
//		case "34":
//			return "RTU正常";
//		case "35":
//			return "RTU与传输装置通信异常";
//		case "36":
//			return "RTU与传感器故障";
//		case "48":
//			return "禁止电气火灾接入";
//		default:
//			break;
//		}
//		return "";
//	}

	@Override
	public FrontLookupUserInfoOutData getLookupUserInfo(String id) throws Exception {
		FrontLookupUserInfoOutData lookupUserInfo = utUnitLookupLogMapper.getLookupUserInfo(id, database);
		String lookupInfoPath = lookupUserInfo.getLookupInfoPath();
		int lastIndexOf = lookupInfoPath.lastIndexOf("\\");
		String substring = lookupInfoPath.substring(lastIndexOf+1, lookupInfoPath.length());
		substring = "/images/upload/" + substring;
		lookupUserInfo.setLookupInfoPath(substring);
		return lookupUserInfo;
	}

	@Override
	public List<FrontUserExperienceOutData> getUserExperience(String infoId) throws Exception {
		return utUnitUserExperienceMapper.getUserExperience(infoId);
	}

	@Override
	public void exportUserExperience(String infoId, HttpServletResponse response) throws Exception {
		List<FrontUserExperienceExport> exportData = new ArrayList<>();
		List<FrontUserExperienceOutData> userExperience = utUnitUserExperienceMapper.getUserExperience(infoId);
		for (FrontUserExperienceOutData frontUserExperienceOutData : userExperience) {
			FrontUserExperienceExport data = new FrontUserExperienceExport();
			BeanUtils.copyProperties(frontUserExperienceOutData, data);
			exportData.add(data);
		}
		String[] header = new String[] { "公司名称", "地址", "职务", "入职时间", "离职时间", "备注" };
		ExportExcel<FrontUserExperienceExport> export = new ExportExcel<FrontUserExperienceExport>();
		export.exportExcel(response, exportData, header);
	}

	@Override
	public PageInfo<FrontLookupLogOutData> getNamingLogs(FrontLookupInData indata) throws Exception {
		indata.setDatabase(database);
		PageHelper.startPage(indata.getPageNumber(), indata.getPageSize());
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getNamingLogs(indata);
		PageInfo<FrontLookupLogOutData> pagers = new PageInfo<>(lookupLogs);
		return pagers;
	}

	@Override
	public void exportNamingLogs(FrontLookupInData indata, HttpServletResponse response) throws Exception {
		indata.setDatabase(database);
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getNamingLogs(indata);
		List<FrontNamingDataExport> exportDatas = new ArrayList<>();
		for (FrontLookupLogOutData frontLookupLogOutData : lookupLogs) {
			FrontNamingDataExport exportData = new FrontNamingDataExport();
			BeanUtils.copyProperties(frontLookupLogOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[] { "单位编号", "子站号", "单位名称", "发起用户", "发起时间", "点名结果", "回应时间" };
		ExportExcel<FrontNamingDataExport> export = new ExportExcel<FrontNamingDataExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public List<FrontCoupletCommonOutData> getLookupCurrentstatus(String deviceIds) throws Exception {
		return utUnitLookupLogMapper.getLookupCurrentstatus(Arrays.asList(deviceIds.split(",")),database);
	}

	@Override
	public List<FrontCoupletCommonOutData> getNamingCurrentstatus(String deviceIds) throws Exception {
		return utUnitLookupLogMapper.getNamingCurrentstatus(Arrays.asList(deviceIds.split(",")),database);
	}

	@Override
	public PageInfo<FrontLookupUnitInfoData> getNetworkingUnitTransferDevice(String unitId, String keyWord,
			Integer pageSize, Integer pageNumber) throws Exception{
		PageHelper.startPage(pageNumber, pageSize);
		List<FrontLookupUnitInfoData> list = utUnitNetdeviceMapper.getNetworkingUnitTransferDevice(unitId, keyWord, database);
		PageInfo<FrontLookupUnitInfoData> pager = new PageInfo<FrontLookupUnitInfoData>(list);
		return pager;
	}
	
	@Override
	public void exportNetworkingUnitTransferDevice(String unitid, String keyWord,
			HttpServletResponse response) throws Exception{
		List<FrontLookupUnitInfoData> list = utUnitNetdeviceMapper.getNetworkingUnitTransferDevice(unitid, keyWord, database);
		List<FrontNetworkingUnitTransferDeviceExport> exportDatas = new ArrayList<>();
		for (FrontLookupUnitInfoData dataExport : list) {
			FrontNetworkingUnitTransferDeviceExport exportData = new FrontNetworkingUnitTransferDeviceExport();
			BeanUtils.copyProperties(dataExport, exportData);
			if(Util.isNotEmpty(dataExport.getResult())&&dataExport.getResult().equals("0")){
				exportData.setResult("设备离线");
			}
			exportDatas.add(exportData);
		}
		String[] header = new String[] { "子站号", "设备编号", "设备名称", "联系电话", "设备状态" };
		ExportExcel<FrontNetworkingUnitTransferDeviceExport> export = new ExportExcel<FrontNetworkingUnitTransferDeviceExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public boolean networkingUnitLookUp(String userId, String deviceIds, String type) throws Exception{
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		if (Util.isEmpty(deviceIds)) {
			return false;
		}
		List<UtUnitNetdevice> list = new ArrayList<UtUnitNetdevice>();
		if (deviceIds.indexOf(",") != -1) {
			List<String> list2 = Arrays.asList(deviceIds.split(","));
			for (String id : list2) {
				if (Util.isNotEmpty(id)) {
					list.add(utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(id)));
				}
			}
		} else {
			list.add(utUnitNetdeviceMapper.selectByPrimaryKey(Long.valueOf(deviceIds)));
		}
		boolean flag = true;
		for (UtUnitNetdevice utUnitNetdevice : list) {
			String ownercode = utUnitNetdevice.getOwnercode();
			Integer deviceno = utUnitNetdevice.getDeviceno();
			Integer deviceindex = utUnitNetdevice.getDeviceindex();
			if (deviceindex == 2) {//如果是用户传输装置则子号传1
				deviceno = 1;
			}

			StringBuilder sb = new StringBuilder();
			String api = String.format(ReqApiConst.GET_REDSERVER_CHECKDEVICE_API,properties.getAccessToken(),userId,type,ownercode,deviceindex,deviceno);
			// 调用远程接口
			sb.append(properties.getRedServerPort()).append(api);
			log.error("调用远程接口"+sb.toString());
			HttpJson httpGet = HttpClientUtil.httpGet(sb.toString());
			if (httpGet.isSuccess()) {
				String msg = httpGet.getMsg();
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse(msg);
				Integer code = (Integer) map.get("Code");
				if (200 != code) {
					flag = false;
				}
			}
		}
		return flag;
	}
	@Override
	public PageInfo<FrontLookupLogOutData> getNetworkingUnitLookupLogs(String unitId, FrontLookupInData indata) throws Exception{
		PageHelper.startPage(indata.getPageNumber(), indata.getPageSize());
		indata.setUnitId(unitId);
		indata.setDatabase(database);
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getNetworkingUnitLookupLogs(indata);
		PageInfo<FrontLookupLogOutData> pagers = new PageInfo<>(lookupLogs);
		return pagers;
	}

	@Override
	public void exportNetworkingUnitLookupLogs(String unitId, FrontLookupInData indata, HttpServletResponse response) throws Exception {
		indata.setDatabase(database);
		indata.setUnitId(unitId);
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getNetworkingUnitLookupLogs(indata);
		List<FrontLookupLogDataExport> exportDatas = new ArrayList<>();
		for (FrontLookupLogOutData frontLookupLogOutData : lookupLogs) {
			FrontLookupLogDataExport exportData = new FrontLookupLogDataExport();
			BeanUtils.copyProperties(frontLookupLogOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[] { "单位编号", "子站号", "单位名称", "单位地址", "发送用户", "发送时间", "发送状态", "查岗状态", "回应时间", "回应人" };
		ExportExcel<FrontLookupLogDataExport> export = new ExportExcel<FrontLookupLogDataExport>();
		export.exportExcel(response, exportDatas, header);
	}

	@Override
	public PageInfo<FrontHisSDDeviceStatusOutData> getNetworkingUnitSDDeviceStatusList(FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		PageHelper.startPage(inData.getPageNumber(), inData.getPageSize());
		List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getNetworkingUnitSDDeviceStatusList(inData);
		return new PageInfo<FrontHisSDDeviceStatusOutData>(list);
	}
	
	@Override
	public void exportNetworkingUnitSDDeviceStatusList(HttpServletResponse response, FrontHistoryInData inData) throws Exception {
		inData.setDatabase(database);
		List<FrontHisSDDeviceStatusOutData> list = utUnitNetdeviceMapper.getNetworkingUnitSDDeviceStatusList(inData);
		List<FrontNetworkingUnitSDDeviceStatusExport> exports = new ArrayList<>();
		for (FrontHisSDDeviceStatusOutData frontHisSDDeviceStatusOutData : list) {
			FrontNetworkingUnitSDDeviceStatusExport export = new FrontNetworkingUnitSDDeviceStatusExport();
			BeanUtils.copyProperties(frontHisSDDeviceStatusOutData, export);
			exports.add(export);
		}
		String[] header = new String[] { "子站号", "设备编号", "设备类型", "设备名称", "结果" };
		ExportExcel<FrontNetworkingUnitSDDeviceStatusExport> exportExcel = new ExportExcel<FrontNetworkingUnitSDDeviceStatusExport>();
		exportExcel.exportExcel(response, exports, header);
	}

	@Override
	public PageInfo<FrontLookupLogOutData> getNetworkingUnitNamingLogs(FrontLookupInData indata) throws Exception {
		indata.setDatabase(database);
		PageHelper.startPage(indata.getPageNumber(), indata.getPageSize());
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getNetworkingUnitNamingLogs(indata);
		PageInfo<FrontLookupLogOutData> pagers = new PageInfo<>(lookupLogs);
		return pagers;
	}

	@Override
	public void exportNetworkingUnitNamingLogs(FrontLookupInData indata, HttpServletResponse response) throws Exception {
		indata.setDatabase(database);
		List<FrontLookupLogOutData> lookupLogs = utUnitLookupLogMapper.getNetworkingUnitNamingLogs(indata);
		List<FrontNamingDataExport> exportDatas = new ArrayList<>();
		for (FrontLookupLogOutData frontLookupLogOutData : lookupLogs) {
			FrontNamingDataExport exportData = new FrontNamingDataExport();
			BeanUtils.copyProperties(frontLookupLogOutData, exportData);
			exportDatas.add(exportData);
		}
		String[] header = new String[] { "单位编号", "子站号", "单位名称", "发起用户", "发起时间", "点名结果", "回应时间" };
		ExportExcel<FrontNamingDataExport> export = new ExportExcel<FrontNamingDataExport>();
		export.exportExcel(response, exportDatas, header);
	}


}
