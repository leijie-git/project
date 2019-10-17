package com.gw.wechat.controller;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gw.front.couplet.data.*;
import com.gw.myAnnotation.UserLoginToken;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.*;
import com.gw.wechat.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.service.CRTMarkService;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.service.FrontCoupletService;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.FrontVideoOutData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.lookup.data.FrontLookupInData;
import com.gw.front.lookup.data.FrontLookupLogOutData;
import com.gw.front.lookup.data.FrontLookupUnitInfoData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.data.FrontMaintenanceTaskOutData;
import com.gw.front.unit.data.FrontUnitCRTOutData;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.myAnnotation.PassToken;
import com.gw.wechat.service.PhoneService;

@Controller
@ResponseBody
@RequestMapping("/wechat")
public class WeChatController extends BaseController {

	private Logger log = LoggerFactory.getLogger(WeChatController.class);

	@Resource
	private PhoneService phoneService;
	@Resource
	private CRTMarkService cRTMarkService;
	@Resource
	private FrontCoupletService frontCoupletService;
	@Resource
	private PropertiesManageService propertiesManageService;

	@RequestMapping(value = "/getWxConfig")
	public Json getWxConfig(HttpServletRequest request, ModelMap map) throws Exception {
		Json json = new Json();
		String url = request.getParameter("url");
		url = URLDecoder.decode(url, "UTF-8");
		String ticket = HttpGetWXToken.getTicket();
		SysPropertiesOutData sysPropertiesOutData = propertiesManageService.getProperties();
		String appId = sysPropertiesOutData.getWxAppid();
		Map<String, String> sign = Sign.sign(ticket, url);
		for (Map.Entry entry : sign.entrySet()) {
			System.out.println(entry.getKey() + "," + entry.getValue());
		}
		map.put("wxConfig", sign);
		map.put("appId", appId);
		json.setSuccess(true);
		json.setObj(map);
		return json;
	}

	/**
	 * 处理用户点击按钮，静默授权验证
	 *
	 * @param response
	 */
	@RequestMapping("/getUrl")
	@PassToken
	public void getUrl(HttpServletResponse response) throws Exception {
		try {
			String url = phoneService.getUrl();
			log.error("url: " + url);
			response.sendRedirect(url);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
	}

	/**
	 * 处理code获取access_token和openid等参数并进入登录页
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/getOpenId")
	@PassToken
	public Json getOpenId(HttpServletRequest request) throws Exception {
		Json json = new Json();
		String code = request.getParameter("code");
		log.error("getOpenId code:" + code);
		try {
			String openId = phoneService.getOpenId(code);
			json.setObj(openId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 登录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/login")
	public Json login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception {
		Json json = new Json();
		try {
			FrontUnitUserOutData outData = phoneService.login(request, inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 退出
	 *
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/logout")
	public Json logout(HttpServletRequest request, String openId) throws Exception {
		Json json = new Json();
		phoneService.logout(openId, "wechat");
		HttpSession session = request.getSession();
		if (session != null) {
			// 注销session
			FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request,
					UtilConst.WECHAT_USER_SESSION);
			if (Util.isNotEmpty(sessionInfo)) {
				phoneService.updateLog(sessionInfo, "wechat");
			}
			session.invalidate();
		}
		json.setSuccess(true);
		return json;
	}

	/**
	 * 告警列表
	 *
	 * @param request
	 * @param phoneAlarmInData alarmType 报警类型（火灾、RTU）1 RTU
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmList")
	public Json getAlarmList(HttpServletRequest request, PhoneAlarmInData phoneAlarmInData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		phoneAlarmInData.setUserId(sessionInfo.getId());
		try {
			List<WechatAlarmInfoOutData> outData = phoneService.getAlarmList(phoneAlarmInData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 统计告警未处理数量
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmNodealStat")
	public Json getAlarmNodealStat(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		try {
			WeChatAlarmStatOutData outData = phoneService.getAlarmNodealStat(sessionInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
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
		try {
			FrontCoupletAlarmInfoOutData outData = phoneService.getAlarmInfo(id);
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
	@PassToken
	@PostMapping("/dealAlarm")
	public Json dealAlarm(HttpServletRequest request, PhoneAlarmInfoIndata inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		if (sessionInfo != null) {
			inData.setUserId(sessionInfo.getId());
		}
		try {
			phoneService.dealAlarm(inData);
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
		try {
			FrontCoupletAlarmInfoOutData outData = phoneService.getRTUAlarmInfo(id);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 处理RTU告警
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@PostMapping("/dealRTUAlarm")
	public Json dealRTUAlarm(HttpServletRequest request, PhoneAlarmInfoIndata inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		if (sessionInfo != null) {
			inData.setUserId(sessionInfo.getId());
		}
		try {
			phoneService.dealRTUAlarm(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
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
	public Json getSDDeviceStatusList(HttpServletRequest request, FrontHistoryInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		inData.setUserId(sessionInfo.getId());
		try {
			PageInfo<FrontHisSDDeviceStatusOutData> pager = phoneService.getSDDeviceStatusList(inData);
			json.setObj(pager);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 设备标定
	 *
	 * @param request
	 * @param data
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deviceCalibration")
	public Json deviceCalibration(HttpServletRequest request, FrontCoupletCalibrationInData data) throws Exception {
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
	 * 标定内容列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/calibrationList")
	public Json calibrationList() throws Exception {
		Json json = new Json();
		try {
			List<PhoneCalibrationOutData> outData = phoneService.calibrationList();
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 更新是否推送故障
	 *
	 * @param isPushFault
	 * @param isPushFault
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateIsPushFault")
	public Json updateIsPushFault(HttpServletRequest request, String isPushFault) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		try {
			phoneService.updateIsPushFault(isPushFault, userId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取单位列表
	 *
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitList")
	public Json getUnitList(HttpServletRequest request, String keyword) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		try {
			List<FrontCoupletUnitInfo> list = phoneService.getUnitList(userId, keyword);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据单位id获取单位详细信息
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/getUnitInfoById")
	public Json getUnitInfoById(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitInfoOutData> outData = phoneService.getUnitInfoById(unitId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查岗列表 获取包含用户传输装置的单位
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitTransferDevice")
	public Json getUnitTransferDevice(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<FrontLookupUnitInfoData> list = phoneService.getUnitTransferDevice(unitId);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查岗
	 *
	 * @param request
	 * @param ownerCode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lookup")
	public Json lookup(HttpServletRequest request, String ownerCode) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		try {
			json.setSuccess(phoneService.lookup(ownerCode, userId));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取查岗记录
	 *
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLookupLogs")
	public Json getLookupLogs(HttpServletRequest request, FrontLookupInData indata) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		try {
			List<FrontLookupLogOutData> list = phoneService.getLookupLogs(userId, indata);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 下载任务
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/downLoadTask")
	public Json downLoadTask(HttpServletRequest request, String inspectcycletype) {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		try {
			List<PhoneTaskDetailOutData> list = phoneService.getTaskList(userId, inspectcycletype);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 维保列表
	 *
	 * @param request
	 * @return
	 */
	@PostMapping("/getRepairList")
	public Json getRepairList(HttpServletRequest request, Integer status) {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		try {
			List<FrontMaintenanceOutData> list = phoneService.getRepairList(userId, status);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 根据unitId获得系统信息
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEqSystemByUnitId")
	public Json getEqSystemByUnitId(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<EqSystemOutData> outData = phoneService.getEqSystemByUnitId(unitId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 火灾告警历史记录列表
	 *
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireHistoryList")
	public Json getFireHistoryList(HttpServletRequest request, FrontHistoryInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		inData.setUserId(userId);
		try {
			List<FrontHistoryAlarmInfoOutData> outData = phoneService.getFireHistoryList(inData);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取接口告警历史记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInterfaceAlarmList")
	public Json getInterfaceAlarmList(HttpServletRequest request, FrontHistoryInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		inData.setUserId(userId);
		try {
			List<FrontHistoryAlarmInfoOutData> outData = phoneService.getInterfaceAlarmList(inData);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 巡查任务分页列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInspectTaskList")
	public Json getInspectTaskList(HttpServletRequest request, FrontMaintenanceInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		inData.setUserId(userId);
		try {
			PageInfo<FrontMaintenanceTaskOutData> pager = phoneService.getInspectTaskList(inData);
			json.setObj(pager);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 维保任务分页列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRepairPageList")
	public Json getRepairPageList(HttpServletRequest request, FrontMaintenanceInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		inData.setUserId(userId);
		try {
			PageInfo<FrontMaintenanceOutData> pager = phoneService.getRepairPageList(inData);
			json.setObj(pager);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 意见反馈
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/feedback")
	public Json feedback(HttpServletRequest request, PhoneFeedbackInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		inData.setCreateUser(userId);
		try {
			phoneService.feedback(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取灭火器记录
	 *
	 * @param request
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireExtinguisherList")
	public Json getFireExtinguisherList(HttpServletRequest request, String unitId, String keyword, String status) {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		String userId = sessionInfo.getId();
		try {
			json.setObj(phoneService.getFireExtinguisherList(unitId, keyword, status, userId));
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 立即整改
	 *
	 * @param files
	 * @param inData
	 * @return
	 */
	@RequestMapping("/rectification")
	public Json rectification(PhoneUploadProblem inData, CommonsMultipartFile[] files) {
		Json json = new Json();
		try {
			phoneService.updateTaskDetail(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 生成隐患
	 *
	 * @param files
	 * @param inData
	 * @return
	 */
	@RequestMapping("/uploadDanger")
	public Json uploadDanger(PhoneUploadProblem inData, CommonsMultipartFile[] files) {
		Json json = new Json();
		try {
			phoneService.uploadDanger(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 维保巡查整改
	 *
	 * @param inData
	 * @return
	 */
	@RequestMapping("/dangerDeal")
	public Json dangerDeal(MaintenanceDealInData inData) {
		Json json = new Json();
		try {
			phoneService.dangerDeal(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 获取基本巡查点分类检查项列表
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getSiteClassDetailBaseList")
	public Json getSiteClassDetailBaseList(String id) {
		Json json = new Json();
		try {
			List<PhoneSiteClassDetialBaseOutData> outData = phoneService.getSiteClassDetailBaseList(id);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 提交全部巡检任务
	 *
	 * @param token
	 * @param jsons
	 * @return
	 */
	@RequestMapping("/submitAllInspectTask")
	public Json submitAllInspectTask(@RequestHeader("token") String token, @RequestBody String jsons) {
		Json json = new Json();
		try {
			String userId = TokenUtil.getUserIdByToken(token);
			phoneService.submitAllInspectTask(userId, jsons);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return json;
	}

	/**
	 * 查看维修详情
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMaintenanceDetail")
	public Json getMaintenanceDetail(String id) throws Exception {
		Json json = new Json();
		try {
			FrontRepairDetailOutData outData = phoneService.getMaintenanceDetail(id);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查询CRT点位
	 *
	 * @param addressID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getOneCRT")
	public Json getOneCRT(String addressID) throws Exception {
		Json json = new Json();
		try {
			FrontUnitCRTOutData outData = cRTMarkService.getOneCRT(addressID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 单位水、电系统列表
	 *
	 * @param inData
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getWaterMoreList")
	public Json getWaterMoreList(FrontCoupletInData inData) throws Exception {
		Json json = new Json();
		try {
			List<FrontInterFaceStatusOutData> list = phoneService.getWaterMoreList(inData);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 报警系统列表
	 *
	 * @param request
	 * @param inData
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireAlarmList")
	public Json getFireAlarmList(HttpServletRequest request, FrontCoupletInData inData) throws Exception {
		Json json = new Json();
		try {
			List<FrontCoupletFireAlarmOutData> list = phoneService.getFireAlarmList(inData);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查询视频详细信息
	 *
	 * @param videoID
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getVideoDetail")
	public Json getVideoDetail(String videoID) throws Exception {
		Json json = new Json();
		try {
			FrontVideoOutData outData = phoneService.getVideoDetail(videoID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}

	/**
	 * 查看设备标定信息
	 *
	 * @param eqId
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
	 * 灭火器统计
	 *
	 * @param request
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMaintenanceStatList")
	public Json getMaintenanceStatList(HttpServletRequest request, String keyword) {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.WECHAT_USER_SESSION);
		try {
			List<FrontCoupletUnitInfo> list = phoneService.getMaintenanceStatList(sessionInfo.getId(), keyword);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获得告警信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getExpireExtinguisherById")
	@PassToken
	@UserLoginToken
	public Json getExpireExtinguisherById(HttpServletRequest request, Long id) throws Exception {
		Json json = new Json();
		try {
			json.setObj(phoneService.getFeDetailById(id));
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			log.error(e.getMessage(), e);
		}
		return json;
	}
}
