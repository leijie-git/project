package com.gw.front.index.service;

import com.github.pagehelper.PageInfo;
import com.gw.firePower.data.FirePowerOutData;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.firePower.data.SquadronOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.*;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.unit.data.MaintenanceUnitOutData;
import com.gw.unit.data.UnitBaseInfoOutData;
import com.gw.wechat.data.PhoneCalibrationOutData;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;

public interface HomeIndexService {

	/**
	 * 获取单位火警信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	FrontFireOutData fireList(FrontAlarmInData inData) throws Exception;

	/**
	 * 查询单位信息
	 * 
	 * @param request
	 * @param unitid
	 * @param unitId2
	 * @return
	 * @throws Exception
	 */
	FrontUnitOutData getUnitInfo(String wbunitid, String unitId) throws Exception;

	/**
	 * 根据类型查询告警信息
	 * 
	 * @param userId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	List<FrontFireInfoOutData> getAlarmListByType(String userId, String type,String isDeal) throws Exception;

	/**
	 * 根据point查单位
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitOutData> getUnitByUnitpoint(String unitId) throws Exception;

	/**
	 * 获取单位地图点位
	 *
	 * @param userId
	 * @param sysType
	 * @param alarmType
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitPointOutData> getUnitMapPoint(String userId, String sysType, String alarmType, String unitName) throws Exception;

	/**
	 * 获取单位地图点位
	 * 
	 * @param userId
	 * @param sysType
	 * @param alarmType
	 * @param unitName 
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitPointOutData> getUnitMapPointByBJZJ(String userId, String sysType, String alarmType, String unitName) throws Exception;

	List<FrontUnitPointOutData> getUnitMapPointByRTU(String userId, String sysType, String alarmType, String unitName) throws Exception;

	/**
	 * 查询站内通知
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<FrontNotifyOutData> getNotifyList(String id) throws Exception;

	/**
	 * 登录日志
	 * 
	 * @param id
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontLoginLogOutData> getLoginLogs(String id, Integer pageNumber, Integer pageSize) throws Exception;

	/**
	 * 通知详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	FrontNotifyOutData getNotifyInfo(String id) throws Exception;

	/**
	 * 删除通知
	 * 
	 * @param id
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	void deleteNotify(String id, String userId) throws Exception;

	/**
	 * 联网单位数及设备状态数据
	 * 
	 * @param userId
	 * @param endDate 
	 * @param startDate 
	 * @return
	 * @throws Exception
	 */
	FrontUnitInfoStatOutData netUnitAndDeviceStatus(String userId, String startDate, String endDate) throws Exception;

	/**
	 * 巡查异常
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	FrontPatrolAbnormalOutData getPatrolAbnormal(String userId) throws Exception;

	/**
	 * 火警（异常）根据性质统计
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<FrontRecordOutData> getFireAlarmStatByProperty(String userId) throws Exception;

	/**
	 * 火警（异常）根据等级统计
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<FrontRecordOutData> getFireAlarmStatByLevel(String userId) throws Exception;

	/**
	 * 用户所购服务
	 * 
	 * @return
	 * @throws Exception
	 */
	FrontUserServerOutData getUserServer() throws Exception;

	/**
	 * 购买记录
	 * 
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitServerLogOutData> getUserServerLogs() throws Exception;

	/**
	 * 实时报警信息
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<FrontFireInfoOutData> getRealTimeAlarms(FrontUnitUserOutData sessinInfo, String type) throws Exception;

	/**
	 * 新增交接班记录
	 * 
	 * @param request
	 * @param inData
	 * @param sessinInfo
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	FrontUnitUserOutData addUserDutyLog(HttpServletRequest request, FrontUserDutyLogInData inData,
			FrontUnitUserOutData sessinInfo) throws Exception;

	/**
	 * 获取实时报警信息统计结果
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	FrontHistoryStatisticalInfoOutData getStatisticalInfo(String userId) throws Exception;

	/**
	 * 根据报警分类获取单位列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontOnlineStatisticalOutData> getUnitListByState(FrontStatisticalInData inData) throws Exception;

	/**
	 * 根据单位id与报警状态获取报警详情 24小时
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontFireInfoOutData> getRealTimeAlarmsByUnitId(FrontStatisticalInData inData) throws Exception;

	/**
	 * 根据unitId获取单位信息
	 * 
	 * @param sessinInfo
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitInfoOutData> getUnitInfoById(FrontUnitUserOutData sessinInfo, String unitId) throws Exception;

	/**
	 * 根据单位id和设备类型获取设备信列表
	 * 
	 * @param unitId
	 * @param deviceIndex
	 * @return
	 */
	List<FrontHisSDDeviceStatusOutData> getNetDeviceListByUnitId(FrontHistoryInData inData) throws Exception;

	/**
	 * 根据单位id获取单位用户列表
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitUserOutData> getUnitUserByUnitId(String unitId) throws Exception;

	/**
	 * 获取维保单位
	 * @param unitId 
	 * 
	 * @return
	 */
	MaintenanceUnitOutData getMaintenanceUnit(String unitId) throws Exception;

	/**
	 * 取消关注
	 * 
	 * @param unitId
	 */
	void cancelConcern(String unitId) throws Exception;

	/**
	 * 新增电话记录
	 * 
	 * @param inData
	 */
	void addPhoneLog(FrontPhoneLogInData inData) throws Exception;

	/**
	 * 新增报警开关状态记录
	 * 
	 * @param request
	 * @param switchType
	 */
	void alarmSwitchLog(HttpServletRequest request, FrontOperationLogInData inData) throws Exception;

	/**
	 * 导出登录记录
	 * 
	 * @param response
	 * @param id
	 * @throws Exception
	 */
	void exportLoginLogs(HttpServletResponse response, String id) throws Exception;

	/**
	 * 根据单位类型统计单位
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<FrontRecordOutData> getUnitStatByUnitType(String userId) throws Exception;

	/**
	 * 根据监管类型统计单位
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<FrontRecordOutData> getUnitStatBySuperviseType(String userId) throws Exception;

	/**
	 * 设置通知已读
	 * 
	 * @param id
	 * @param id2
	 */
	void setNotifyIsRead(String id, String userId) throws Exception;

	/**
	 * 消防力量(首页)
	 * 
	 * @param unitID
	 * @param type
	 * @return
	 */
	List<FirePowerOutData> firePower(String userID, String type) throws Exception;

	/**
	 * 获取实时告警数量
	 * 
	 * @param userId
	 * @param loginTime
	 * @return
	 * @throws Exception
	 */
	RealTimeAlarmCount getRealTimeAlarmCount(String userId,String loginTime) throws Exception;

	/**
	 * 根据消防力量id查询消防详情
	 * 
	 * @param powerID
	 * @return
	 */
	FireStationManageOutData firePowerDetail(String powerID) throws Exception;

	/**
	 * 获取单位两公里/五公里以内消防力量
	 * 
	 * @param unitID
	 * @param instence
	 * @return
	 * @throws Exception
	 */
	List<FirePowerOutData> getFirePowers(String unitID, Integer instence) throws Exception;

	/**
	 * 设备标定
	 * 
	 * @param deviceId
	 * @param calibrationId
	 * @throws Exception
	 */
	void deviceCalibration(String deviceId, String calibrationId) throws Exception;

	/**
	 * 标定内容列表
	 * 
	 * @return
	 * @throws Exception
	 */
	List<PhoneCalibrationOutData> calibrationList() throws Exception;

	/**
	 * 单位消防力量
	 * 
	 * @param unitID
	 * @return
	 */
	List<FirePowerOutData> fireUnitPower(String unitID) throws Exception;

	/**
	 * 获取消防站详情（中队）
	 * 
	 * @param powerID
	 * @return
	 */
	SquadronOutData fireSquadronPowerDetail(String powerID) throws Exception;

	/**
	 * 根据单位id与报警状态获取报警详情 制定日期内
	 * 
	 * @param inData
	 * @return
	 */
	PageInfo<FrontFireInfoOutData> getAlarmByUnitId(FrontStatisticalInData inData) throws Exception;

	/**
	 * 根据单位id获取单位名称
	 * 
	 * @param unitid
	 * @return
	 * @throws Exception
	 */
	String getUnitName(String unitid) throws Exception;

	/**
	 * 根据报警分类获取单位分页列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontOnlineStatisticalOutData> getUnitPageListByState(FrontStatisticalInData inData) throws Exception;

	/**
	 * 获取告警信息 24小时内
	 * 
	 * @param sessinInfo
	 * @return
	 * @throws Exception
	 */
	List<FrontFireInfoOutData> getAlarmInfo(FrontUnitUserOutData sessinInfo) throws Exception;

	/**
	 * 根据视频id获取详细信息
	 * @param videoID
	 * @return
	 */
	FrontVideoOutData getVideoDetail(String videoID) throws Exception;

	/**
	 * 获取故障、监管、屏蔽报警数量
	 * @param id
	 * @return
	 */
	FrontAlarmTypeOutData getAlarmListByTypeCount(String id) throws Exception;

}
