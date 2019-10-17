package com.gw.front.history.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.EqSystemOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.history.data.*;
import com.gw.front.history.service.FrontHistoryService;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 历史记录
 *
 * @author Administrator
 *
 */
@Controller
@ResponseBody
@RequestMapping("/front/history")
public class FrontHistoryController extends BaseController {

	private Logger log = LoggerFactory.getLogger(FrontHistoryController.class);

	@Resource
	private FrontHistoryService frontHistoryService;

	/**
	 * 火灾告警历史记录列表
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireHistoryList")
	public PageInfo<FrontHistoryAlarmInfoOutData> getFireHistoryList(HttpServletRequest request,
																	 FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryAlarmInfoOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getFireHistoryList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 单位报表--火灾统计
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitFireList")
	public PageInfo<FrontHistoryAlarmInfoOutData> getUnitFireList(HttpServletRequest request,
																  FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryAlarmInfoOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getUnitFireList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}
	/**
	 * 火灾告警记录列表
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireList")
	public PageInfo<FrontHistoryAlarmInfoOutData> getFireList(HttpServletRequest request,
															  FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryAlarmInfoOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getFireList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 24h火灾告警记录单位列表
	 *
	 * @param request
	 * @param inDatagetFireUnitList
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireUnitList")
	public PageInfo<FrontHistoryAlarmInfoOutData> getFireUnitList(HttpServletRequest request,
																  FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryAlarmInfoOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getFireUnitList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出火灾告警记录列表
	 * @param request
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportFireList")
	public void exportFireList(HttpServletRequest request, FrontHistoryInData inData,
							   HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportFireList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 单位报表火灾报警系统导出表格
	 * @param request
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportUnitFireList")
	public void exportUnitFireList(HttpServletRequest request, FrontHistoryInData inData,
								   HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportUnitFireList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 导出火灾告警历史记录列表
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportFireHistoryList")
	public void exportFireHistoryList(HttpServletRequest request, FrontHistoryInData inData,
									  HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportFireHistoryList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取接口告警历史记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInterfaceAlarmList")
	public PageInfo<FrontHistoryInterfaceAlarmData> getInterfaceAlarmList(HttpServletRequest request,
																		  FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryInterfaceAlarmData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getInterfaceAlarmList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出接口告警历史记录
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportInterfaceAlarmList")
	public void exportInterfaceAlarmList(HttpServletRequest request, FrontHistoryInData inData,
										 HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportInterfaceAlarmList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 *
	 * 单位报表水系统导出表格
	 * @param request
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportUnitInterfaceAlarmList")
	public void exportUnitInterfaceAlarmList(HttpServletRequest request, FrontHistoryInData inData,
											 HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportUnitInterfaceAlarmList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 查询截图历史记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getVideoLogList")
	public PageInfo<FrontHistoryVideoPicOutData> getVideoLogList(HttpServletRequest request, FrontHistoryInData inData)
			throws Exception {
		PageInfo<FrontHistoryVideoPicOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getVideoLogList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 设备系统状态记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getSDDeviceStatusList")
	public PageInfo<FrontHisSDDeviceStatusOutData> getSDDeviceStatusList(HttpServletRequest request,
																		 FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHisSDDeviceStatusOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getSDDeviceStatusList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 统计设备上传数量
	 *
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeviceUploadCount")
	public Json getDeviceUploadCount(@RequestParam("deviceId") String deviceId, @RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) throws Exception {
		Json json = new Json();
		try {
			Integer outData = frontHistoryService.getDeviceUploadCount(deviceId, startDate, endDate);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}


	/**
	 * 导出设备系统状态记录
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportSDDeviceStatusList")
	public void exportSDDeviceStatusList(HttpServletRequest request, FrontHistoryInData inData,
										 HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportSDDeviceStatusList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据id获取详情
	 *
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeviceInfo")
	public Json getDeviceInfo(HttpServletRequest request, FrontHistoryInData inData) throws Exception {
		Json json = new Json();
		try {
			FrontHisDeviceStatusOutData outData = frontHistoryService.getDeviceInfo(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取单个设备状态记录
	 *
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDeviceStatusList")
	public PageInfo<FrontCoupletCommonOutData> getDeviceStatusList(HttpServletRequest request,
																   FrontHistoryInData inData) throws Exception {
		PageInfo<FrontCoupletCommonOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getDeviceStatusList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 操作记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOperationList")
	public PageInfo<FrontHistoryOperationOutData> getOperationList(HttpServletRequest request,
																   FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryOperationOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getOperationList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出操作日志
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportOperationList")
	public void exportOperationList(HttpServletRequest request, FrontHistoryInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportOperationList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 短信历史记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMessageList")
	public PageInfo<FrontHistoryMessageOutData> getMessageList(HttpServletRequest request, FrontHistoryInData inData)
			throws Exception {
		PageInfo<FrontHistoryMessageOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getMessageList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出短信记录
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportMessageList")
	public void exportMessageList(HttpServletRequest request, FrontHistoryInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportMessageList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 短信统计详情
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMessageStatDetail")
	public PageInfo<FrontHistoryMessageOutData> getMessageStatDetail(HttpServletRequest request,
																	 FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryMessageOutData> pager = null;
		try {
			pager = frontHistoryService.getMessageStatDetail(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 电话历史记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPhoneList")
	public PageInfo<FrontHistoryMessageOutData> getPhoneList(HttpServletRequest request, FrontHistoryInData inData)
			throws Exception {
		PageInfo<FrontHistoryMessageOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getPhoneList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出电话记录
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportPhoneList")
	public void exportPhoneList(HttpServletRequest request, FrontHistoryInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportPhoneList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 电话统计详情
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPhoneStatDetail")
	public PageInfo<FrontHistoryMessageOutData> getPhoneStatDetail(HttpServletRequest request,
																   FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryMessageOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontHistoryService.getPhoneStatDetail(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 根据unitId获得系统信息
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEqSystemByUnitId")
	public Json getEqSystemByUnitId(String unitId, String buildId) throws Exception {
		Json json = new Json();
		try {
			List<EqSystemOutData> outData = frontHistoryService.getEqSystemByUnitId(unitId, buildId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取值班记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserDutyLogList")
	public PageInfo<FrontHistoryUserDutyLogOutData> getUserDutyLogList(HttpServletRequest request,
																	   FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHistoryUserDutyLogOutData> pageInfo = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pageInfo = frontHistoryService.getUserDutyLogList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	/**
	 * 导出值班记录
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportUserDutyLogList")
	public void exportUserDutyLogList(HttpServletRequest request, FrontHistoryInData inData,
									  HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportUserDutyLogList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 获取流量统计
	 *
	 * @param inData
	 * @param year
	 * @param month
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getDataFlow")
	public PageInfo<FrontHistoryDataFlowOutData> getDataFlowList(HttpServletRequest request, FrontHistoryInData inData)
			throws Exception {
		PageInfo<FrontHistoryDataFlowOutData> pageInfo = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pageInfo = frontHistoryService.getDataFlowList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;

	}

	/**
	 * 导出流量统计数据
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportDataFlow")
	public void exportDataFlowList(HttpServletRequest request, FrontHistoryInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportDataFlowList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 用户传输装置操作记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getOPDeviceHistory")
	public PageInfo<FrontTransmissionDeviceOutData> getOPDeviceHistory(HttpServletRequest request,
																	   FrontHistoryInData inData) throws Exception {
		PageInfo<FrontTransmissionDeviceOutData> pageInfo = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pageInfo = frontHistoryService.getOPDeviceHistory(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;

	}

	/**
	 * 导出传输装置操作记录
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportOPDeviceHistory")
	public void exportOPDeviceHistory(HttpServletRequest request, FrontHistoryInData inData,
									  HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontHistoryService.exportOPDeviceHistory(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}


}
