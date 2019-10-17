package com.gw.front.couplet.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.equipment.data.EquipmentFacilitiesData;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.*;
import com.gw.front.couplet.service.FrontCoupletService;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.data.UnitGradeInterfaceOutData;
import com.gw.myAnnotation.PassToken;
import com.gw.upload.service.UploadService;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 物联网
 *
 * @author Administrator
 */
@Controller
@ResponseBody
@RequestMapping("/front/couplet")
public class FrontCoupletController extends BaseController {

	private Logger log = LoggerFactory.getLogger(FrontCoupletController.class);

	@Resource
	private FrontCoupletService frontCoupletService;
	@Resource
	private UploadService pictureService;

	/**
	 * 导出设备列表
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportDevices")
	public void exportDevices(HttpServletRequest request, FrontHistoryInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontCoupletService.exportDevices(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 物联--视频 单位列表
	 *
	 * @param request
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnits")
	public Json getUnits(HttpServletRequest request, String unitName) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontCoupletUnitInfo> outData = frontCoupletService.getUnits(sessinInfo.getId(), unitName);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--视频 单位建筑区域视频
	 *
	 * @param request
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitBuildAreas")
	@PassToken
	public Json getUnitBuildAreas(HttpServletRequest request, String unitId, String buildName) throws Exception {
		Json json = new Json();
		try {
			List<FrontCoupletBuildAreaOutData> outData = frontCoupletService.getUnitBuildAreas(unitId, buildName);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			if (e instanceof ServiceException)
				json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--视频 新增视频截图记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/addUnitVideoLog")
	public Json addUnitVideoLog(HttpServletRequest request, FrontCoupletVideoLogOutData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			frontCoupletService.addUnitVideoLog(inData, sessinInfo.getId());
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--视频 查询截图记录
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitVideoLogs")
	public PageInfo<FrontCoupletVideoLogOutData> getUnitVideoLogs(HttpServletRequest request, FrontCoupletInData inData)
			throws Exception {
		PageInfo<FrontCoupletVideoLogOutData> pager = null;
		try {
			pager = frontCoupletService.getUnitVideoLogs(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 物联--系统检测 查询水系统接口当前值
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInterfaceValueList")
	public Json getInterfaceValueList(HttpServletRequest request, FrontCoupletInData inData) throws Exception {
		Json json = new Json();
		try {
			List<FrontInterfaceDeviceOutData> outData = frontCoupletService.getInterfaceValueList(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--系统检测 获取区域列表
	 *
	 * @param request
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBuildAreaList")
	public Json getBuildAreaList(HttpServletRequest request, String unitId, String buildId) throws Exception {
		Json json = new Json();
		try {
			List<FrontCoupletCommonOutData> outData = frontCoupletService.getBuildAreaList(unitId, buildId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--系统检测 接口数据 表盘数据列表（包含电气火灾、防火分隔等）
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInterfaceDeviceList")
	public Json getInterfaceDeviceList(HttpServletRequest request, FrontCoupletInData inData) throws Exception {
		Json json = new Json();
		try {
			List<FrontInterfaceDeviceOutData> outData = frontCoupletService.getInterfaceDeviceList(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--系统检测 单位水、电系统更多列表
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getWaterMoreList")
	public PageInfo<FrontInterFaceStatusOutData> getWaterMoreList(HttpServletRequest request, FrontCoupletInData inData)
			throws Exception {
		PageInfo<FrontInterFaceStatusOutData> pager = null;
		try {
			pager = frontCoupletService.getWaterMoreList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	@PostMapping("/getAlarmCount")
	public Json getAlarmCount(HttpServletRequest request, FrontCoupletInData inData)
			throws Exception {
		Json json = new Json();
		try {
			json.setSuccess(true);
			json.setObj(frontCoupletService.getAlarmCount(inData));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--系统检测 导出单位水、电系统更多列表
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/exportWaterMoreList")
	public void exportWaterMoreList(HttpServletRequest request, FrontCoupletInData inData, HttpServletResponse response)
			throws Exception {
		try {
			frontCoupletService.exportWaterMoreList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 物联--系统检测 （水）接口数据 历史记录
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInterfaceDataHistory")
	public Json getElectricalHistory(FrontCoupletInData inData) throws Exception {
		Json json = new Json();
		try {
			List<FrontElectricalStatOutData> outData = frontCoupletService.getElectricalHistory(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--系统检测 报警系统图标列表
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireAlarmImgList")
	public Json getFireAlarmImgList(HttpServletRequest request,
															 EquipmentFacilitiesData inData) throws Exception {
		List<EquipmentFacilitiesData> list = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		Json json = new Json();
		try {
			list = frontCoupletService.getFireAlarmImgList(inData);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 物联--系统检测 根据报警系统图表加载列表
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireAlarmSelectList")
	public PageInfo<FrontCoupletFireAlarmOutData> getFireAlarmSelectList(HttpServletRequest request,
																   FrontCoupletInData inData) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		if (null != sessinInfo && Util.isEmpty(sessinInfo)){
			inData.setUserId(sessinInfo.getUserid());
		}
		PageInfo<FrontCoupletFireAlarmOutData> pager = null;
		try {
			pager = frontCoupletService.getFireAlarmSelectList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}



	/**
	 * 物联--系统检测 报警系统列表
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireAlarmList")
	public PageInfo<FrontCoupletFireAlarmOutData> getFireAlarmList(HttpServletRequest request,
																   FrontCoupletInData inData) throws Exception {
		PageInfo<FrontCoupletFireAlarmOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontCoupletService.getFireAlarmList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 物联--系统检测 导出单位报警系统列表
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportFireAlarmList")
	public void exportFireAlarmList(HttpServletRequest request, FrontCoupletInData inData, HttpServletResponse response)
			throws Exception {
		try {
			frontCoupletService.exportFireAlarmList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 获得告警信息
	 *
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmInfo")
	public Json getAlarmInfo(HttpServletRequest request, String id) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontCoupletAlarmInfoOutData outData = frontCoupletService.getAlarmInfo(id, sessinInfo.getUsername());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 处理告警
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dealAlarm")
	public Json dealAlarm(HttpServletRequest request, FrontCoupletAlarmInfoOutData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		inData.setCurrAddress(sessinInfo.getCurrAddress());
		try {
			frontCoupletService.dealAlarm(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 批量处理告警
	 *
	 * @param request
	 * @param isTest
	 * @param alarmStatus
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dealAlarmBatch")
	public Json dealAlarmBatch(HttpServletRequest request, String isTest, String alarmStatus, String id) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			frontCoupletService.dealAlarmBatch(isTest, alarmStatus, sessinInfo, id);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 批量处理告警
	 *
	 * @param request
	 * @param isTest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dealCheckAlarmBatch")
	public Json dealCheckAlarmBatch(HttpServletRequest request, String dealresult, String isTest, FrontCoupletAlarmInfoOutData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		inData.setCurrAddress(sessinInfo.getCurrAddress());
		try {
			frontCoupletService.dealCheckAlarmBatch(dealresult, isTest, inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取告警记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRTUAlarmList")
	public PageInfo<FrontCoupletRTUAlarmOutData> getRTUAlarmList(HttpServletRequest request, FrontCoupletInData inData)
			throws Exception {
		PageInfo<FrontCoupletRTUAlarmOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontCoupletService.getRTUAlarmList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出rtu告警信息
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportRTUAlarmList")
	public void exportRTUAlarmList(HttpServletRequest request, FrontCoupletInData inData, HttpServletResponse response)
			throws Exception {
		try {
			frontCoupletService.exportRTUAlarmList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 批量处理RTU告警
	 *
	 * @param request
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dealRTUAlarm")
	public Json dealRTUAlarm(HttpServletRequest request, String type, String alarmStatus) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			frontCoupletService.dealRTUAlarmBatch(type, alarmStatus, sessinInfo);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 处理RTU告警
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dealRTUAlarmBatch")
	public Json dealRTUAlarmBatch(HttpServletRequest request, FrontCoupletAlarmInfoOutData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		inData.setCurrAddress(sessinInfo.getCurrAddress());
		try {
			frontCoupletService.dealRTUAlarm(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获得RTU告警信息
	 *
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRTUAlarmInfo")
	public Json getRTUAlarmInfo(HttpServletRequest request, String id) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontCoupletAlarmInfoOutData outData = frontCoupletService.getRTUAlarmInfo(id, sessinInfo.getUsername());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取建筑列表
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getBuilds")
	public Json getBuilds(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontCoupletCommonOutData> outData = frontCoupletService.getBuilds(sessinInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 更新设备标定信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateDeviceCalibration")
	public Json updateDeviceCalibration(FrontCoupletCalibrationInData data) throws Exception {
		Json json = new Json();
		try {
			frontCoupletService.updateDeviceCalibration(data);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查看设备标定信息
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeviceCalibration")
	public Json getDeviceCalibration(String eqId) throws Exception {
		Json json = new Json();
		try {
			FrontCoupletCalibrationInData outData = frontCoupletService.getDeviceCalibration(eqId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取输出接口控制
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInterfaceOutData")
	@PassToken
	public Json getInterfaceOutData(HttpServletRequest request, FrontCoupletInData inData) throws Exception {
		Json json = new Json();
		try {
			List<FrontInterfaceOutData> outData = frontCoupletService.getInterfaceOutData(inData);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 接口控制命令
	 *
	 * @param request
	 * @param id
	 * @param status
	 * @param actiontype
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/changeRTUStatus")
	public Json changeRTUStatus(HttpServletRequest request, String id, String status, String actiontype)
			throws Exception {
		Json json = new Json();
		try {
			json.setSuccess(frontCoupletService.changeRTUStatus(id, status, actiontype));
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 接口控制命令
	 *
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCurrentStatus")
	public Json getCurrentStatus(HttpServletRequest request, String id) throws Exception {
		Json json = new Json();
		try {
			String status = frontCoupletService.getCurrentStatus(id);
			json.setObj(status);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 获取NB设备
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNBDevice")
	public PageInfo<FrontNBDeviceInfoOutData> getNBDevice(HttpServletRequest request, FrontNBDeviceInfoInData inData) throws Exception {
		PageInfo<FrontNBDeviceInfoOutData> pageInfo = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pageInfo = frontCoupletService.getNBDevice(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	/**
	 * 导出NB设备
	 *
	 * @param request
	 * @param response
	 * @param inData
	 * @throws Exception
	 */
	@RequestMapping("/exportNBDevice")
	public void exportNBDevice(HttpServletRequest request, HttpServletResponse response, FrontNBDeviceInfoInData inData) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontCoupletService.exportNBDevice(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@RequestMapping("/getAlarmStatus")
	public Json getAlarmStatus() throws Exception {
		Json json = new Json();
		try {
			json.setObj(frontCoupletService.getAlarmStatus());
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 单位评分系统异常时间统计
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEqNormal")
	public PageInfo<UnitGradeInterfaceOutData> getEqNormal(FrontCoupletInData inData) throws Exception {
		PageInfo<UnitGradeInterfaceOutData> pager = null;
		try {
			pager =frontCoupletService.getEqNormal(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}
}
