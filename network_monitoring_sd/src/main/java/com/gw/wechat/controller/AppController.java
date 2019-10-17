package com.gw.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.service.CRTMarkService;
import com.gw.exception.ServiceException;
import com.gw.front.couplet.data.*;
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
import com.gw.front.unit.data.FrontUnitVideoOutData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.inspect.data.InspectTaskOutData;
import com.gw.inspect.service.InspectTaskService;
import com.gw.mapper.entity.ApkInfo;
import com.gw.myAnnotation.PassToken;
import com.gw.myAnnotation.UserLoginToken;
import com.gw.unit.data.NetworkingUserInData;
import com.gw.unit.data.NetworkingUserOutData;
import com.gw.util.TokenUtil;
import com.gw.util.Util;
import com.gw.wechat.data.*;
import com.gw.wechat.service.AppService;
import com.gw.wechat.service.PhoneService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/app")
public class AppController extends BaseController {

	@Resource
	private AppService appService;
	@Resource
	private PhoneService phoneService;
	@Resource
	private FrontCoupletService frontCoupletService;
	@Resource
	private CRTMarkService cRTMarkService;
	@Resource
	private InspectTaskService inspectTaskService;

	private Logger log = LoggerFactory.getLogger(AppController.class);

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
			FrontUnitUserOutData outData = appService.login(request, inData);
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
	 * 告警列表
	 *
	 * @param request
	 * @param phoneAlarmInData alarmType 报警类型（火灾、RTU）1 RTU
	 * @param
	 * @return
	 * @throws Exception
	 */
	@UserLoginToken
	@RequestMapping("/getAlarmList")
	public Json getAlarmList(HttpServletRequest request, PhoneAlarmInData phoneAlarmInData,
							 @RequestHeader("token") String token) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		phoneAlarmInData.setUserId(userId);
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
	@PassToken
	@GetMapping("/getAlarmNodealStat")
	public Json getAlarmNodealStat(HttpServletRequest request, @RequestHeader("token") String token) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			WeChatAlarmStatOutData outData = phoneService.getAlarmNodealStat(userId);
			json.setObj(outData);
			json.setSuccess(true);
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
	@UserLoginToken
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
	public Json dealAlarm(HttpServletRequest request, PhoneAlarmInfoIndata inData, @RequestHeader("token") String token)
			throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		inData.setUserId(userId);
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
	@UserLoginToken
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
	public Json dealRTUAlarm(HttpServletRequest request, PhoneAlarmInfoIndata inData,
							 @RequestHeader("token") String token) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		inData.setUserId(userId);
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
	@UserLoginToken
	@RequestMapping("/getSDDeviceStatusList")
	public Json getSDDeviceStatusList(HttpServletRequest request, FrontHistoryInData inData,
									  @RequestHeader("token") String token) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		inData.setUserId(userId);
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
	 */
	@UserLoginToken
	@PostMapping("/deviceCalibration")
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
	@UserLoginToken
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
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@UserLoginToken
	@RequestMapping("/updateIsPushFault")
	public Json updateIsPushFault(String isPushFault, @RequestHeader("token") String token) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	 * @param token
	 * @return
	 * @throws Exception
	 */
	@UserLoginToken
	@RequestMapping("/getUnitList")
	public Json getUnitList(@RequestHeader("token") String token, String keyword) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	@UserLoginToken
	@RequestMapping("/getUnitInfoById")
	public Json getUnitInfoById(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitInfoOutData> outData = phoneService.getUnitInfoById(unitId);
			if (Util.isNotEmpty(outData)) {
				json.setObj(outData.get(0));
			}
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
	@UserLoginToken
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
	 */
	@UserLoginToken
	@RequestMapping("/lookup")
	public Json lookup(@RequestHeader("token") String token, String ownerCode) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	 * @param token
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@UserLoginToken
	@RequestMapping("/getLookupLogs")
	public Json getLookupLogs(@RequestHeader("token") String token, FrontLookupInData indata) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	@UserLoginToken
	@PostMapping("/downLoadTask")
	public Json downLoadTask(HttpServletRequest request, @RequestHeader("token") String token,
							 String inspectcycleType) {
		String userId = TokenUtil.getUserIdByToken(token);
		Json json = new Json();
		try {
			List<PhoneTaskDetailOutData> list = phoneService.getTaskList(userId, inspectcycleType);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 上传任务
	 * 已废弃
	 *
	 * @param request
	 * @return
	 */
	//@UserLoginToken
	//@PostMapping("/upLoadTask")
	public Json upLoadTask(HttpServletRequest request, @RequestHeader("token") String token,
						   String tasks) {
		String userId = TokenUtil.getUserIdByToken(token);
		List<PhoneTaskDetailOutData> taskList = JSONObject.parseArray(tasks).toJavaList(PhoneTaskDetailOutData.class);
		Json json = new Json();
		try {
			phoneService.saveTaskList(taskList);
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
	@UserLoginToken
	@PostMapping("/getRepairList")
	public Json getRepairList(HttpServletRequest request, @RequestHeader("token") String token, Integer status) {
		String userId = TokenUtil.getUserIdByToken(token);
		Json json = new Json();
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
	@UserLoginToken
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
	@UserLoginToken
	@RequestMapping("/getFireHistoryList")
	public Json getFireHistoryList(@RequestHeader("token") String token, FrontHistoryInData inData) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	@UserLoginToken
	@RequestMapping("/getInterfaceAlarmList")
	public Json getInterfaceAlarmList(@RequestHeader("token") String token, FrontHistoryInData inData)
			throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	@UserLoginToken
	@RequestMapping("/getInspectTaskList")
	public Json getInspectTaskList(@RequestHeader("token") String token, FrontMaintenanceInData inData)
			throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	 * 巡查点 , 执行人 , 是否已完成查询接口
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getTaskList")
	public Json getTaskList(InspectTaskInData inData){
		Json json = new Json();
		try {
			PageInfo<InspectTaskOutData> pager = inspectTaskService.getList(inData);
			json.setObj(pager);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 绑定NFC卡片
	 *
	 * @param nfcCode nfc卡唯一标识
	 * @param taskID  巡查任务唯一标识
	 */
	@UserLoginToken
	@PostMapping("/bindingNFC")
	public Json bindingNFC(@Param("nfcCode") String nfcCode,
						   @Param("taskID") String taskID)
			throws Exception {
		Json json = new Json();
		try {
			phoneService.bindingNFC(nfcCode, taskID);
			json.setSuccess(true);
			json.setObj(true);
		} catch (Exception e) {
			if (e instanceof ServiceException)
				json.setMsg(e.getMessage());
			json.setSuccess(false);
			json.setObj(false);
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 通过 NFC唯一标识 获取检查项明细数据
	 * 已废弃
	 *
	 * @param nfcCode nfc卡唯一标识
	 */
	//@UserLoginToken
	//@GetMapping("/inspect/getDetailByNfc")
	public Json getInspectDetail(@Param("nfcCode") String nfcCode)
			throws Exception {
		Json json = new Json();
		try {
			List<PhoneTaskDetailOutData> outData = phoneService.getInspectDetail(nfcCode);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException)
				json.setMsg(e.getMessage());
			json.setSuccess(false);
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
	@UserLoginToken
	@RequestMapping("/getRepairPageList")
	public Json getRepairPageList(@RequestHeader("token") String token, FrontMaintenanceInData inData)
			throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	 * @param token
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@UserLoginToken
	@RequestMapping("/feedback")
	public Json feedback(@RequestHeader("token") String token, PhoneFeedbackInData inData) throws Exception {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
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
	 */
	@UserLoginToken
	@RequestMapping("/getFireExtinguisherList")
	public Json getFireExtinguisherList(HttpServletRequest request, @RequestHeader("token") String token, String unitId, String keyword, String status) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			json.setObj(phoneService.getFireExtinguisherList(unitId, keyword, status, userId));
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 维保巡查整改
	 *
	 * @param token
	 * @param inData
	 * @return
	 */
	@UserLoginToken
	@RequestMapping("/dangerDeal")
	public Json dangerDeal(@RequestHeader("token") String token, MaintenanceDealInData inData) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		inData.setUserId(userId);
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
	@UserLoginToken
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
	 * @return
	 */
	@UserLoginToken
	@RequestMapping("/submitAllInspectTask")
	public Json submitAllInspectTask(@RequestHeader("token") String token, @RequestBody String jsons) {
		Json json = new Json();
		try {
			String userId = TokenUtil.getUserIdByToken(token);
			phoneService.submitAllInspectTask(userId, jsons);
			json.setSuccess(true);
			json.setMsg("成功！");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			e.printStackTrace();
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
	public Json logout(HttpServletRequest request, FrontUnitUserOutData inData) {
		Json json = new Json();
		HttpSession session = request.getSession();
		try {
			if (session != null) {
				appService.logout(inData);
				// 注销session
				session.invalidate();
			}
			json.setSuccess(true);
			json.setObj(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.setObj(false);
			json.setSuccess(false);
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
	@UserLoginToken
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
	 */
	@UserLoginToken
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
	 */
	@UserLoginToken
	@RequestMapping("/getWaterMoreList")
	public Json getWaterMoreList(FrontCoupletInData inData) throws Exception {
		Json json = new Json();
		try {
			List<FrontInterfaceAppOutData> list = phoneService.getWaterMoreListNew(inData);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 报警系统列表
	 */
	@UserLoginToken
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
	 */
	@UserLoginToken
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
	 */
	@UserLoginToken
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
	 * 单位视频
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@UserLoginToken
	@RequestMapping("/getUnitVideoInfo")
	public Json getUnitVideoInfo(String unitId, String name) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitVideoOutData> outData = frontCoupletService.getUnitVideoInfo(unitId, name);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 灭火器统计
	 */
	@UserLoginToken
	@RequestMapping("/getMaintenanceStatList")
	public Json getMaintenanceStatList(@RequestHeader("token") String token, String keyword) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			List<FrontCoupletUnitInfo> list = phoneService.getMaintenanceStatList(userId, keyword);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查询待接单列表
	 */
	@UserLoginToken
	@RequestMapping("/getReceiveTaskList")
	public Json getReceiveTaskList(@RequestHeader("token") String token, String inspectcycleType, String type) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			List<PhoneTaskDetailOutData> list = phoneService.getReceiveTaskList(userId, inspectcycleType, type);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}


	/**
	 * 转单
	 *
	 * @return
	 * @throws Exception
	 */
	@UserLoginToken
	@RequestMapping("/changeTask")
	public Json changeTask(@RequestHeader("token") String token, String taskID, String receiveUserID) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			phoneService.changeTask(userId, taskID, receiveUserID);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 接单
	 */
	@UserLoginToken
	@RequestMapping("/receiveTask")
	public Json receiveTask(@RequestHeader("token") String token, String taskID) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			phoneService.receiveTask(userId, taskID);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 撤回转单
	 */
	@UserLoginToken
	@RequestMapping("/withdrawTask")
	public Json withdrawTask(@RequestHeader("token") String token, String taskID) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			phoneService.withdrawTask(userId, taskID);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}


	/**
	 * 获取该联网单位所有人员
	 */
	@UserLoginToken
	@RequestMapping("/getNetUserList")
	public Json getNetUserList(@RequestHeader("token") String token, NetworkingUserInData inData) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			PageInfo<NetworkingUserOutData> outData = phoneService.getNetUserList(userId, inData);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 转接单数量
	 */
	@UserLoginToken
	@RequestMapping("/getTaskCount")
	public Json getTaskCount(@RequestHeader("token") String token) {
		Json json = new Json();
		String userId = TokenUtil.getUserIdByToken(token);
		try {
			TaskSheetOutData data = phoneService.getTaskCount(userId);
			json.setObj(data);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 上传apk包
	 */
	@PassToken
	@PostMapping("/apk/upload")
	public Json uploadApk(HttpServletRequest request, MultipartFile file) {
		Json json = new Json();
		try {
			String url = appService.uploadApk(file);
			json.setObj(url);
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			log.error(e.getMessage(), e);
		}

		return json;
	}

	/**
	 * 新增apk基础信息
	 */
	@PassToken
	@PostMapping("/apk/info")
	public Json addApkInfo(ApkInfo apkInfo) {
		Json json = new Json();
		try {
			appService.addApkInfo(apkInfo);
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取最新apk基础信息
	 */
	@PassToken
	@RequestMapping("/apk/getLastInfo")
	public Json getApkInfo() {
		Json json = new Json();
		try {
			ApkInfo info = appService.getLastApkInfo();
			json.setObj(info);
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取灭火器详情
	 *
	 * @param feId 灭火器id
	 */
	@PassToken
	@RequestMapping("/fe/getDetail")
	public Json getFeDetail(Long feId) {
		Json json = new Json();
		try {
			json.setObj(phoneService.getFeDetailById(feId));
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 转单
	 * @param trunSingleInData
	 */
	@PassToken
	@RequestMapping("/turnSingle")
	public Json turnSingle(TrunSingleInData trunSingleInData) {
		Json json = new Json();
		try {
			appService.turnSingle(trunSingleInData);
			json.setObj("转单成功");
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
