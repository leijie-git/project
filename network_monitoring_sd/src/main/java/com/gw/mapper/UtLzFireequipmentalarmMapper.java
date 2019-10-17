package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.alarm.data.AlarmRTUInData;
import com.gw.alarm.data.AlarmRTUOutData;
import com.gw.alarm.data.EmployeeForSendMsgData;
import com.gw.aspect.data.OriginalRtuFaultVo;
import com.gw.common.BaseMapper;
import com.gw.front.couplet.data.FrontCoupletAlarmInfoOutData;
import com.gw.front.couplet.data.FrontCoupletInData;
import com.gw.front.couplet.data.FrontCoupletRTUAlarmOutData;
import com.gw.front.couplet.data.FrontInterFaceStatusOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryInterfaceAlarmData;
import com.gw.front.index.data.FrontAlarmInData;
import com.gw.front.index.data.FrontFireInfoOutData;
import com.gw.front.index.data.FrontStatisticalInData;
import com.gw.front.index.data.RealTimeAlarmCount;
import com.gw.mapper.entity.UtLzFireequipmentalarm;
import com.gw.wechat.data.WechatAlarmInfoOutData;

public interface UtLzFireequipmentalarmMapper extends BaseMapper<UtLzFireequipmentalarm> {

	/**
	 * 接口告警历史记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自历史表UT_LZ_FireEquipmentAlarm_History
	 */
	List<FrontHistoryInterfaceAlarmData> getInterfaceAlarmList(FrontHistoryInData inData) throws Exception;

	List<FrontHistoryInterfaceAlarmData> getInterfaceAlarmBuildList(FrontHistoryInData inData) throws Exception;

	/**
	 * 根据RTU报警信息查询联网单位相关人员
	 * 
	 * @param inData
	 * @return
	 */
	List<EmployeeForSendMsgData> getLWEmployeeByRTUDevice(@Param("unitID") String unitID) throws Exception;

	/**
	 * 根据设备信息查询详细信息
	 * 
	 * @param inData
	 * @return
	 */
	AlarmRTUOutData getMsgByRTUDevice(AlarmRTUInData inData) throws Exception;

	/**
	 * 根据RTU报警信息查询维保单位相关人员
	 * 
	 * @param unitID
	 * @return
	 */
	List<EmployeeForSendMsgData> getWBEmployeeByRTUDevice(@Param("unitID") String unitID) throws Exception;

	/**
	 * 单位水系统更多列表
	 * 
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自历史表UT_LZ_FireEquipmentAlarm_History
	 */
	List<FrontInterFaceStatusOutData> getWaterMoreList(FrontCoupletInData inData) throws Exception;

	/**
	 * 微信 -- 获取告警列表（处理 未处理）
	 * 
	 * @param userId
	 * @param isDeal
	 * @return
	 * @throws Exception
	 * 数据源自临时表UT_LZ_FireEquipmentAlarm
	 */
	List<WechatAlarmInfoOutData> getAlarmList(@Param("userId") String userId, @Param("isDeal") String isDeal, @Param("alarmStatus") Integer alarmStatus)
			throws Exception;

	List<WechatAlarmInfoOutData> getAlarmByBuildList(@Param("userId") String userId, @Param("isDeal") String isDeal, @Param("alarmStatus") Integer alarmStatus)
			throws Exception;

	/**
	 * 获取RTU告警信息
	 * 
	 * @param id
	 * @return
	 * 数据源自历史表UT_LZ_FireEquipmentAlarm_History
	 */
	FrontCoupletAlarmInfoOutData getRTUAlarmInfo(String id) throws Exception;

	/**
	 * 手机端 获取rtu告警记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自历史表UT_LZ_FireEquipmentAlarm_History
	 */
	List<FrontHistoryAlarmInfoOutData> getInterfaceAlarmListForApp(FrontHistoryInData inData) throws Exception;

	List<FrontHistoryAlarmInfoOutData> getInterfaceAlarmListForAppByBuild(FrontHistoryInData inData) throws Exception;

	/**
	 * 获取rtu接入告警记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自历史表UT_LZ_FireEquipmentAlarm_History
	 */
	List<FrontCoupletRTUAlarmOutData> getRTUAlarmList(FrontCoupletInData inData) throws Exception;

	List<FrontCoupletRTUAlarmOutData> getRTUAlarmByBuildList(FrontCoupletInData inData) throws Exception;

	/**
	 * 根据不同的报警类型，以及用户id获取RTU告警记录
	 * @param userId
	 * @param lists
	 * @return
	 * 数据源自临时表UT_LZ_FireEquipmentAlarm
	 */
	List<FrontFireInfoOutData> getRealRTUAlarmList(FrontAlarmInData inData) throws Exception;

	List<FrontFireInfoOutData> getRealRTUAlarmListByBuild(FrontAlarmInData inData) throws Exception;

	/**
	 * 获取rtu各个类型实时告警数量
	 * @param userId
	 * @param loginTime
	 * @return
	 * @throws Exception
	 * 数据源自历史表UT_LZ_FireEquipmentAlarm_History
	 */
	RealTimeAlarmCount getRealTimeRTUCount(@Param("userId")String userId,@Param("loginTime")String loginTime)  throws Exception;

	RealTimeAlarmCount getRealTimeByBuildRTUCount(@Param("userId")String userId,@Param("loginTime")String loginTime)  throws Exception;

	/**
	 * 获取24小时内的RTU告警信息
	 * @param inData
	 * @return
	 * 数据源自临时表UT_LZ_FireEquipmentAlarm
	 */
	List<FrontFireInfoOutData> getAllRTUAlarmList(FrontAlarmInData inData);

	List<FrontFireInfoOutData> getAllRTUAlarmListByBuild(FrontAlarmInData inData);

	/**
	 * 根据unitId获得RTU告警信息
	 * @param inData
	 * @return
	 * 数据源自临时表UT_LZ_FireEquipmentAlarm
	 */
	List<FrontFireInfoOutData> getRTUAlarmByUnitId(FrontStatisticalInData inData);

	/**
	 * 查询5分钟内的相同告警
	 * @param inData
	 * @return
	 * 数据源自临时表UT_LZ_FireEquipmentAlarm
	 */
	List<AlarmRTUOutData> getRTUAlarmInfoExist(AlarmRTUInData inData);

    OriginalRtuFaultVo getOriginalRtuFaultById(Long id);
	/**
	 * 获取两天前数据最大的id
	 * @throws Exception
	 */
	String getUtLzFireequipmentalarmMinId() throws Exception;

	/**
	 * 根据最大的id把之前的数据删除
	 * @throws Exception
	 */
	void deleteUtLzFireequipmentalarmData(@Param("id") String id) throws Exception;

	/**
	 * 获取差集列表
	 * @return
	 * @throws Exception
	 */
	List<UtLzFireequipmentalarm> getDifference() throws Exception;

	/**
	 * 获取差集id
	 * @return
	 * @throws Exception
	 */
	List<Long> getInsertId() throws Exception;

}