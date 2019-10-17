package com.gw.front.couplet.service;

import com.github.pagehelper.PageInfo;
import com.gw.equipment.data.EquipmentFacilitiesData;
import com.gw.front.couplet.data.*;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.data.FrontUnitVideoOutData;
import com.gw.front.unit.data.UnitGradeInterfaceOutData;
import com.gw.mapper.entity.AlarmStatus;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface FrontCoupletService {

	/**
	 * 导出设备
	 *
	 * @param inData
	 * @param response
	 */
	void exportDevices(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 查询单位列表
	 *
	 * @param userId
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletUnitInfo> getUnits(String userId, String unitName) throws Exception;

	/**
	 * 单位建筑区域
	 *
	 * @param unitId
	 * @param buildName
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletBuildAreaOutData> getUnitBuildAreas(String unitId, String buildName) throws Exception;

	/**
	 * 新增视频截图记录
	 *
	 * @param inData
	 * @param userId
	 * @throws Exception
	 */
	void addUnitVideoLog(FrontCoupletVideoLogOutData inData, String userId) throws Exception;

	/**
	 * 查询截图记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontCoupletVideoLogOutData> getUnitVideoLogs(FrontCoupletInData inData) throws Exception;

	/**
	 * 查询系统接口当前值
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontInterfaceDeviceOutData> getInterfaceValueList(FrontCoupletInData inData) throws Exception;

	/**
	 * 获取区域列表
	 *
	 * @param unitId
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getBuildAreaList(String unitId, String buildId) throws Exception;

	/**
	 * 物联--系统检测 接口数据 表盘数据列表（包含电气火灾、防火分隔等）
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontInterfaceDeviceOutData> getInterfaceDeviceList(FrontCoupletInData inData) throws Exception;

	/**
	 * 单位水系统更多列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontInterFaceStatusOutData> getWaterMoreList(FrontCoupletInData inData) throws Exception;

	/**
	 * 导出单位水系统更多列表
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportWaterMoreList(FrontCoupletInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 获取电气火灾系统压力 历史记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontElectricalStatOutData> getElectricalHistory(FrontCoupletInData inData) throws Exception;

	/**
	 * 火灾报警记录图标
	 */

	List<EquipmentFacilitiesData> getFireAlarmImgList(EquipmentFacilitiesData inData) throws Exception;

	/**
	 * 火灾报警记录图标对应的列表
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontCoupletFireAlarmOutData> getFireAlarmSelectList(FrontCoupletInData inData) throws Exception;

	/**
	 * 火灾报警记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontCoupletFireAlarmOutData> getFireAlarmList(FrontCoupletInData inData) throws Exception;

	/**
	 * 导出火警历史
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportFireAlarmList(FrontCoupletInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 报警信息详情
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	FrontCoupletAlarmInfoOutData getAlarmInfo(String id, String userName) throws Exception;

	/**
	 * 处理告警
	 *
	 * @param inData
	 * @throws Exception
	 */
	void dealAlarm(FrontCoupletAlarmInfoOutData inData) throws Exception;

	/**
	 * 批量处理告警
	 *
	 * @param isTest
	 * @param alarmStatus
	 * @param sessinInfo
	 * @throws Exception
	 */
	void dealAlarmBatch(String isTest, String alarmStatus, FrontUnitUserOutData sessinInfo, String ids) throws Exception;

	/**
	 * 批量处理复选框告警
	 *
	 * @param inData
	 * @throws Exception
	 */
	void dealCheckAlarmBatch(String dealresult, String isTest, FrontCoupletAlarmInfoOutData inData) throws Exception;

	/**
	 * 获取rtu接入告警记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontCoupletRTUAlarmOutData> getRTUAlarmList(FrontCoupletInData inData) throws Exception;

	/**
	 * 导出rtu告警
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportRTUAlarmList(FrontCoupletInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 批量处理RTU告警
	 *
	 * @param type
	 * @param alarmStatus
	 * @param sessinInfo
	 * @throws Exception
	 */
	void dealRTUAlarmBatch(String type, String alarmStatus, FrontUnitUserOutData sessinInfo) throws Exception;

	/**
	 * 获取建筑列表
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getBuilds(String userId) throws Exception;

	/**
	 * 处理RTU告警
	 *
	 * @param inData
	 */
	void dealRTUAlarm(FrontCoupletAlarmInfoOutData inData);

	/**
	 * 获取RTU告警详情
	 *
	 * @param id
	 * @param username
	 * @return
	 */
	FrontCoupletAlarmInfoOutData getRTUAlarmInfo(String id, String username) throws Exception;

	/**
	 * 更新或者新增设备标定
	 *
	 * @param data
	 * @return
	 */
	void updateDeviceCalibration(FrontCoupletCalibrationInData data) throws Exception;

	/**
	 * 获取标定信息详情
	 *
	 * @param eqId
	 * @return
	 */
	FrontCoupletCalibrationInData getDeviceCalibration(String eqId) throws Exception;

	/**
	 * 单位视频
	 *
	 * @param unitId
	 * @param name
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitVideoOutData> getUnitVideoInfo(String unitId, String name) throws Exception;

	/**
	 * 获取输出接口控制
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontInterfaceOutData> getInterfaceOutData(FrontCoupletInData inData) throws Exception;

	/**
	 * 接口控制命令
	 *
	 * @param id
	 * @param status
	 * @param actiontype
	 * @throws Exception
	 */
	boolean changeRTUStatus(String id, String status, String actiontype) throws Exception;

	/**
	 * 获取当前状态
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	String getCurrentStatus(String id) throws Exception;

	/**
	 * 获取NB设备信息
	 *
	 * @param inData
	 * @return
	 */
	PageInfo<FrontNBDeviceInfoOutData> getNBDevice(FrontNBDeviceInfoInData inData) throws Exception;

	/**
	 * 导出NB设备
	 *
	 * @param inData
	 * @param response
	 */
	void exportNBDevice(FrontNBDeviceInfoInData inData, HttpServletResponse response) throws Exception;

	Map<String, Integer> getAlarmCount(FrontCoupletInData inData) throws Exception;

	List<AlarmStatus> getAlarmStatus() throws Exception;

	/**
	 * 水系统 电气火灾 模拟量数字量异常时间
	 */
	PageInfo<UnitGradeInterfaceOutData>  getEqNormal(FrontCoupletInData inData) throws Exception;
}
