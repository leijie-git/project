package com.gw.front.index.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.firePower.data.FirePowerOutData;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.firePower.data.SquadronOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.*;
import com.gw.front.index.service.HomeIndexService;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.maintenance.service.FrontMaintenanceService;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.unit.data.MaintenanceUnitOutData;
import com.gw.util.UtilConst;
import com.gw.wechat.data.PhoneCalibrationOutData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 首页
 * 
 * @author Administrator
 *
 */

@Controller
@ResponseBody
@RequestMapping("/front/homeIndex")
public class HomeIndexController extends BaseController {

	private Logger log = LoggerFactory.getLogger(HomeIndexController.class);

	@Resource
	private HomeIndexService homeIndexService;
	@Resource
	private FrontMaintenanceService frontMaintenanceService;

	/**
	 * 火警
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/fireList")
	public Json fireList(HttpServletRequest request,FrontAlarmInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			inData.setUserId(sessinInfo.getId());
			FrontFireOutData outData = homeIndexService.fireList(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 基础信息 查询单位信息
	 * 
	 * @param request
	 * @param unitId
	 * @return
	 * @throws Exception
	 */

	@RequestMapping("/getUnitInfo")
	public Json getUnitInfo(HttpServletRequest request, String unitId) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontUnitOutData outData = homeIndexService.getUnitInfo(sessinInfo.getUnitid(), unitId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据类型查询告警信息（故障、监管、屏蔽）
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmListByTypeCount")
	public Json getAlarmListByTypeCount(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontAlarmTypeOutData outData = homeIndexService.getAlarmListByTypeCount(sessinInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 根据类型查询告警信息（故障、监管、屏蔽）
	 * 
	 * @param request
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmListByType")
	public Json getAlarmListByType(HttpServletRequest request, String type,String isDeal) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontFireInfoOutData> outData = homeIndexService.getAlarmListByType(sessinInfo.getId(), type,isDeal);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 地图 获取单位点位-报警主机
	 *
	 * @param request
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitMapPoint")
	public Json getUnitMapPoint(HttpServletRequest request, String sysType, String alarmType,String unitName) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontUnitPointOutData> outData = homeIndexService.getUnitMapPoint(sessinInfo.getId(), sysType,
					alarmType,unitName);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 地图 获取单位点位-报警主机
	 * 
	 * @param request
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitMapPointByBJZJ")
	public Json getUnitMapPointByBJZJ(HttpServletRequest request, String sysType, String alarmType,String unitName) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontUnitPointOutData> outData = homeIndexService.getUnitMapPointByBJZJ(sessinInfo.getId(), sysType,
					alarmType,unitName);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 地图 获取单位点位-RTU
	 *
	 * @param request
	 * @param sysType
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitMapPointByRTU")
	public Json getUnitMapPointByRTU(HttpServletRequest request, String sysType, String alarmType,String unitName) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontUnitPointOutData> outData = homeIndexService.getUnitMapPointByRTU(sessinInfo.getId(), sysType,
					alarmType,unitName);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 联网单位（联网用户，在线设备，在线率）
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/netUnitAndDeviceStatus")
	public Json netUnitAndDeviceStatus(HttpServletRequest request, String startDate, String endDate) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontUnitInfoStatOutData outData = homeIndexService.netUnitAndDeviceStatus(sessinInfo.getId(), startDate,
					endDate);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 巡查异常
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPatrolAbnormal")
	public Json getPatrolAbnormal(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {

			FrontPatrolAbnormalOutData outData = homeIndexService.getPatrolAbnormal(sessinInfo.getId());

			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据单位类型统计单位
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitStatByUnitType")
	public Json getUnitStatByUnitType(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontRecordOutData> outData = homeIndexService.getUnitStatByUnitType(sessinInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据监管类型统计单位
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitStatBySuperviseType")
	public Json getUnitStatBySuperviseType(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontRecordOutData> outData = homeIndexService.getUnitStatBySuperviseType(sessinInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 站内通知列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNotifyList")
	public Json getNotifyList(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontNotifyOutData> outData = homeIndexService.getNotifyList(sessinInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查看通知内容
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNotifyInfo")
	public Json getNotifyInfo(HttpServletRequest request, String id) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontNotifyOutData outData = homeIndexService.getNotifyInfo(id);
			homeIndexService.setNotifyIsRead(id, sessinInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除通知
	 * 
	 * @param request
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deleteNotify")
	public Json deleteNotify(HttpServletRequest request, String id) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			homeIndexService.deleteNotify(id, sessinInfo.getId());
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 登录记录
	 */
	@RequestMapping("/getLoginLogs")
	public PageInfo<FrontLoginLogOutData> getLoginLogs(HttpServletRequest request, Integer pageNumber, Integer pageSize)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		PageInfo<FrontLoginLogOutData> outData = null;
		try {
			outData = homeIndexService.getLoginLogs(sessinInfo.getId(), pageNumber, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return outData;
	}

	/**
	 * 导出登录记录
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportLoginLogs")
	public void exportLoginLogs(HttpServletRequest request, HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			homeIndexService.exportLoginLogs(response, sessinInfo.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 用户所购服务
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserServer")
	public Json getUserServer(HttpServletRequest request) throws Exception {
		Json json = new Json();
		try {
			FrontUserServerOutData outData = homeIndexService.getUserServer();
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 购买记录
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserServerLogs")
	public Json getUserServerLogs(HttpServletRequest request) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitServerLogOutData> outData = homeIndexService.getUserServerLogs();
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 用户资料
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserInfo")
	public Json getUserInfo(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		json.setSuccess(true);
		json.setObj(sessinInfo);
		return json;
	}

	/**
	 * 获取实时告警信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRealTimeAlarms")
	public Json getRealTimeAlarms(HttpServletRequest request, String type) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontFireInfoOutData> outData = homeIndexService.getRealTimeAlarms(sessinInfo, type);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取实时告警数量
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRealTimeAlarmCount")
	public Json getRealTimeAlarmCount(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			RealTimeAlarmCount outData = homeIndexService.getRealTimeAlarmCount(sessinInfo.getId(),sessinInfo.getLoginTime());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取告警信息 24小时内
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmInfo")
	public Json getAlarmInfo(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontFireInfoOutData> outData = homeIndexService.getAlarmInfo(sessinInfo);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 新增交接班记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addUserDutyLog")
	public Json addUserDutyLog(HttpServletRequest request, FrontUserDutyLogInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontUnitUserOutData outData = homeIndexService.addUserDutyLog(request, inData, sessinInfo);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取实时报警信息统计结果
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getStatisticalInfo")
	public Json getStatisticalInfo(HttpServletRequest request) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontHistoryStatisticalInfoOutData outData = homeIndexService.getStatisticalInfo(sessinInfo.getId());
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据报警分类获取单位列表
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getUnitListByState")
	public Json getUnitListByState(HttpServletRequest request, FrontStatisticalInData inData) {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			List<FrontOnlineStatisticalOutData> outData = homeIndexService.getUnitListByState(inData);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据报警分类获取单位分页列表
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getUnitPageListByState")
	public PageInfo<FrontOnlineStatisticalOutData> getUnitPageListByState(HttpServletRequest request,
			FrontStatisticalInData inData) {
		PageInfo<FrontOnlineStatisticalOutData> pageInfo = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pageInfo = homeIndexService.getUnitPageListByState(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	/**
	 * 根据单位id与报警状态获取报警详情 24小时
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getRealTimeAlarmsByUnitId")
	public Json getRealTimeAlarmsByUnitId(HttpServletRequest request, FrontStatisticalInData inData) {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			List<FrontFireInfoOutData> outData = homeIndexService.getRealTimeAlarmsByUnitId(inData);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据单位id与报警状态获取报警详情 指定日期内
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getAlarmByUnitId")
	public PageInfo<FrontFireInfoOutData> getAlarmByUnitId(HttpServletRequest request, FrontStatisticalInData inData) {
		PageInfo<FrontFireInfoOutData> pageInfo = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pageInfo = homeIndexService.getAlarmByUnitId(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	/**
	 * 根据unitId获取单位信息
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/getUnitInfoById")
	public Json getUnitInfoById(HttpServletRequest request, String unitId) {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FrontUnitInfoOutData> outData = homeIndexService.getUnitInfoById(sessinInfo, unitId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据单位id和设备类型获取设备列表
	 * 
	 * @return
	 */
	@RequestMapping("/getNetDeviceListByUnitId")
	public Json getNetDeviceListByUnitId(HttpServletRequest request, FrontHistoryInData inData) {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			inData.setUserId(sessinInfo.getId());
			List<FrontHisSDDeviceStatusOutData> outDatas = homeIndexService.getNetDeviceListByUnitId(inData);
			json.setObj(outDatas);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return json;
	}

	/**
	 * 根据单位id获取单位用户列表
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/getUnitUserByUnitId")
	public Json getUnitUserByUnitId(String unitId) {
		Json json = new Json();
		try {
			List<FrontUnitUserOutData> outDatas = homeIndexService.getUnitUserByUnitId(unitId);
			json.setObj(outDatas);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取维保单位
	 * 
	 * @return
	 */
	@RequestMapping("/getMaintenanceUnit")
	public Json getMaintenanceUnit(HttpServletRequest request) {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			MaintenanceUnitOutData outData = homeIndexService.getMaintenanceUnit(sessinInfo.getUnitid());
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 取消关注
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/cancelConcern")
	public Json cancelConcern(String unitId) {
		Json json = new Json();
		try {
			homeIndexService.cancelConcern(unitId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 新增电话记录
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addPhoneLog")
	public Json addPhoneLog(HttpServletRequest request, FrontPhoneLogInData inData) {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setSender(sessinInfo.getId());
		try {
			homeIndexService.addPhoneLog(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 报警开关记录
	 * 
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/alarmSwitchLog")
	public Json alarmSwitch(HttpServletRequest request, FrontOperationLogInData inData) {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessionInfo.getId());
		inData.setAddress(sessionInfo.getCurrAddress());
		try {
			homeIndexService.alarmSwitchLog(request, inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setSuccess(false);
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 消防力量(首页)
	 * 
	 * @param type
	 * @return
	 */
	@RequestMapping("/firePower")
	public Json firePower(HttpServletRequest request, String type) {
		Json json = new Json();
		FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<FirePowerOutData> list = homeIndexService.firePower(sessionInfo.getId(), type);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 消防力量(单位)
	 * 
	 * @param unitID
	 * @return
	 */
	@RequestMapping("/fireUnitPower")
	public Json fireUnitPower(String unitID) {
		Json json = new Json();
		try {
			List<FirePowerOutData> list = homeIndexService.fireUnitPower(unitID);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 消防力量详情(消防站)
	 * 
	 * @param powerID
	 * @return
	 */
	@RequestMapping("/firePowerDetail")
	public Json firePowerDetail(String powerID) {
		Json json = new Json();
		try {
			FireStationManageOutData list = homeIndexService.firePowerDetail(powerID);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 消防力量详情(中队)
	 * 
	 * @param powerID
	 * @return
	 */
	@RequestMapping("/fireSquadronPowerDetail")
	public Json fireSquadronPowerDetail(String powerID) {
		Json json = new Json();
		try {
			SquadronOutData list = homeIndexService.fireSquadronPowerDetail(powerID);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取单位两公里/五公里以内消防力量
	 * 
	 * @param unitID
	 * @param instence
	 * @return
	 */
	@RequestMapping("/getFirePowers")
	public Json getFirePowers(String unitID, Integer instence) throws Exception {
		Json json = new Json();
		try {
			List<FirePowerOutData> list = homeIndexService.getFirePowers(unitID, instence);
			json.setObj(list);
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
	 * @param deviceId
	 * @param calibrationId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deviceCalibration")
	public Json deviceCalibration(HttpServletRequest request, String deviceId, String calibrationId) throws Exception {
		Json json = new Json();
		try {
			homeIndexService.deviceCalibration(deviceId, calibrationId);
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
			List<PhoneCalibrationOutData> outData = homeIndexService.calibrationList();
			json.setSuccess(true);
			json.setObj(outData);
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
			FrontVideoOutData outData = homeIndexService.getVideoDetail(videoID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据point查询单位
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitByUnitpoint")
	public Json getUnitByUnitpoint(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitOutData> outData = homeIndexService.getUnitByUnitpoint(unitId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 *@描述 统计当前用户的单位下的检查点的状态统计
	 *@创建人 Jie.Lei
	 *@参数
	 *@返回值
	 *@创建时间 2019/7/12
	 */
     @RequestMapping("/getUserUnitSiteStatus")
	public Json getUserUnitSiteStatus(String UnitID){
		 Json json = new Json();

		 try {
			 UserUnitIDSiteStatus userUnitSiteStatus = frontMaintenanceService.getUserUnitSiteStatus(UnitID);
			 json.setSuccess(true);
			 json.setObj(userUnitSiteStatus);
		 } catch (Exception e) {
			 log.error(e.getMessage(), e);
		 }
		 return json;
	 }

    /**
     *@描述 巡查进度统计 总的巡查个数 已完成个数
     *@创建人 Jie.Lei
     *@参数
     *@返回值
     *@创建时间 2019/7/12
     */
    @RequestMapping("/getSiteCountAndOkCheckCount")
    public Json getSiteCountAndOkCheckCount(String UnitID){
        Json json = new Json();

        try {
            UserUnitIDSiteStatus userUnitSiteStatus = frontMaintenanceService.getSiteCountAndOkCheckCount(UnitID);
            json.setSuccess(true);
            json.setObj(userUnitSiteStatus);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }


}
