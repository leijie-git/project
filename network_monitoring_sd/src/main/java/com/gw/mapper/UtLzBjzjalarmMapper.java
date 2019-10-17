package com.gw.mapper;

import com.gw.alarm.data.AlarmBJZJInData;
import com.gw.alarm.data.AlarmBJZJOutData;
import com.gw.alarm.data.EmployeeForSendMsgData;
import com.gw.aspect.data.OriginalAlarmVo;
import com.gw.common.BaseMapper;
import com.gw.equipment.data.EquipmentFacilitiesData;
import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.couplet.data.FrontCoupletAlarmInfoOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.couplet.data.FrontCoupletFireAlarmOutData;
import com.gw.front.couplet.data.FrontCoupletInData;
import com.gw.front.forSDOS.FireOutData;
import com.gw.front.forSDOS.FrontSdosInData;
import com.gw.front.forSDOS.RtuCurrentOutData;
import com.gw.front.forSDOS.RtuHistoryOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.*;
import com.gw.mapper.entity.UtLzBjzjalarm;
import com.gw.openApi.common.data.out.HistoryAlarmCount;
import com.gw.wechat.data.PhoneAlarmInData;
import com.gw.wechat.data.WeChatAlarmStatOutData;
import com.gw.wechat.data.WechatAlarmInfoOutData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtLzBjzjalarmMapper extends BaseMapper<UtLzBjzjalarm> {

	/**
	 * 查询单位火警数量
	 *
	 * @param userId
	 * @param selectType 判断选择查询的sql
	 * @return
	 * 数据源自临时表UtLzBjzjalarm
	 */
	FrontFireOutData statFires(@Param("userId")String userId,@Param("selectType")String selectType) throws Exception;

//	FrontFireOutData statFiresByBuild(@Param("userId")String userId) throws Exception;

//	FrontFireOutData statFiresByBJZJBuild(@Param("userId")String userId) throws Exception;

	/**
	 * 根据类型查询单位告警信息
	 *
	 * @param userId
	 * @param type
	 * @param selectType 判断选择查询的sql
	 * @return
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<FrontFireInfoOutData> getAlarmList(@Param("userId") String userId, @Param("type") String type, @Param("isDeal") String isDeal, @Param("selectType")String selectType)
			throws Exception;

//	List<FrontFireInfoOutData> getAlarmListByBuild(@Param("userId") String userId, @Param("type") String type, @Param("isDeal") String isDeal)
//			throws Exception;
//	List<FrontFireInfoOutData> getAlarmListByBJZJBuild(@Param("userId") String userId, @Param("type") String type, @Param("isDeal") String isDeal)
//			throws Exception;
	/**
	 * 获取故障、监管、屏蔽数量
	 *
	 * @param userId
	 * @param selectType 判断选择查询的sql
	 * @return
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<FrontAlarmTypeOutData> getAlarmListCount(@Param("userId") String userId, @Param("selectType")String selectType) throws Exception;

//	List<FrontAlarmTypeOutData> getAlarmListBuildCount(@Param("userId") String userId) throws Exception;

//	List<FrontAlarmTypeOutData> getAlarmListBJZJBuildCount(@Param("userId") String userId) throws Exception;

	/**
	 * 火警（异常）根据建筑物性质、等级统计
	 *
	 * @param userId
	 * @param codeGroupKey
	 * @return
	 * @throws Exception
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<FrontRecordOutData> getFireAlarmStatByProperty(@Param("userId") String userId,
			@Param("codeGroupKey") String codeGroupKey) throws Exception;

	/**
	 * 实时报警信息
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<FrontFireInfoOutData> getRealTimeAlarms(FrontAlarmInData inData) throws Exception;

//	List<FrontFireInfoOutData> getRealTimeAlarmsByBuild(FrontAlarmInData inData) throws Exception;

//	List<FrontFireInfoOutData> getRealTimeAlarmsBJZJByBuild(FrontAlarmInData inData) throws Exception;

	/**
	 * 火警报警列表
	 *
	 * @param inData
	 *
	 * @return
	 * @throws Exception
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<FrontCoupletFireAlarmOutData> getFireAlarmList(FrontCoupletInData inData) throws Exception;

//	List<FrontCoupletFireAlarmOutData> getFireAlarmListByBuild(FrontCoupletInData inData) throws Exception;

//	List<FrontCoupletFireAlarmOutData> getFireAlarmListBJZJByBuild(FrontCoupletInData inData) throws Exception;

	List<EquipmentFacilitiesData> getFireAlarmImgList(FrontCoupletInData inData) throws Exception;

	/**
	 * 报警主机/用传图标
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<EquipmentFacilitiesData> getFireAlarmImgList(EquipmentFacilitiesData inData) throws Exception;

	/**
	 * 获取关联地址报警主机/用传图标
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<EquipmentFacilitiesData> getFireAlarm_Relation_ImgList(EquipmentFacilitiesData inData) throws Exception;

	List<FrontCoupletFireAlarmOutData> getFireAlarmSelectList(FrontCoupletInData inData) throws Exception;

	/**
	 * 查询告警详情
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	FrontCoupletAlarmInfoOutData getAlarmInfo(String id) throws Exception;

	/**
	 * 查询报警列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<FrontHistoryAlarmInfoOutData> getFireHistoryList(FrontHistoryInData inData) throws Exception;

//	List<FrontHistoryAlarmInfoOutData> getFireHistoryBuildList(FrontHistoryInData inData) throws Exception;

//	List<FrontHistoryAlarmInfoOutData> getFireHistoryBJZJBuildList(FrontHistoryInData inData) throws Exception;

	List<FrontHistoryAlarmInfoOutData> getFireUnitList(FrontHistoryInData inData) throws Exception;

	/**
	 * 单位系统异常率统计
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<FrontCoupletCommonOutData> getAlarmCout(FrontAnalysisInData inData) throws Exception;

	/**
	 * 根据报警主机信息获取所属单位
	 *
	 * @param inData
	 * @return
	 */
	AlarmBJZJOutData getUnitIDByBJZJDeviceAndpartCode(AlarmBJZJInData inData);

	AlarmBJZJOutData getUnitIDByBJZJDevice(AlarmBJZJInData inData);

	/**
	 * 根据报警主机信息查询相关人员
	 *
	 * @param unitID
	 * @return
	 */
	List<EmployeeForSendMsgData> getEmployeeByBJZJDevice(@Param("unitID") String unitID) throws Exception;

	List<EmployeeForSendMsgData> getEmployeeByBJZJDeviceByBuild(@Param("unitID") String unitID) throws Exception;


	/**
	 * 统计告警未处理数量
	 *
	 * @param userId
	 * @param selectType 判断选择查询的sql
	 * @return
	 * @throws Exception
	 * 数据源自临时表UtLzBjzjalarm
	 */
	WeChatAlarmStatOutData getAlarmNodealStat(@Param("userId") String userId,@Param("selectType")String selectType) throws Exception;

//	WeChatAlarmStatOutData getAlarmNodealByBuildStat(@Param("userId") String userId) throws Exception;

//	WeChatAlarmStatOutData getAlarmNodealByBJZJBuildStat(@Param("userId") String userId) throws Exception;

	/**
	 * 微信 -- 查询告警列表
	 *
	 * @param phoneAlarmInData
	 * @return
	 * @throws Exception
	 */
	List<WechatAlarmInfoOutData> getWechatAlarmList(PhoneAlarmInData phoneAlarmInData) throws Exception;

//	List<WechatAlarmInfoOutData> getWechatAlarmByBuildList(PhoneAlarmInData phoneAlarmInData) throws Exception;

//	List<WechatAlarmInfoOutData> getWechatAlarmByBJZJBuildList(PhoneAlarmInData phoneAlarmInData) throws Exception;

	/**
	 * 获取实时报警信息统计结果
	 *
	 * @return
	 * @throws Exception
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<FrontStatisticalOutData> getStatisticalInfo() throws Exception;

	/**
	 * 获取火灾统计信息
	 *
	 * @param userId
	 * @param alarmStatus
	 * @param unitname
	 * @return
	 * @throws Exception
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<FrontOnlineStatisticalOutData> getFireStatistical(@Param("userId") String userId,
														   @Param("alarmStatus") Integer alarmStatus, @Param("unitname") String unitname) throws Exception;

	/**
	 * 获取设备报备数量统计
	 *
	 * @param userId
	 * @param unitname
	 * @param calibrationId
	 * @return
	 * @throws Exception
	 */
	List<FrontOnlineStatisticalOutData> getCalibrationType(@Param("userId") String userId,
														   @Param("calibrationId") Integer calibrationId, @Param("unitname") String unitname) throws Exception;

	/**
	 * 根据单位id与报警状态获取报警详情
	 *
	 * @return
	 * @throws Exception
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<FrontFireInfoOutData> getRealTimeAlarmsByUnitId(FrontStatisticalInData inData) throws Exception;

	/**
	 * 获取实时告警数量
	 *
	 * @param userId
	 * @return
	 * @throws Exception
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	RealTimeAlarmCount getRealTimeAlarmCount(@Param("userId") String userId, @Param("loginTime") String loginTime, @Param("selectType")String selectType)
			throws Exception;

//	RealTimeAlarmCount getRealTimeAlarmBuildCount(@Param("userId") String userId, @Param("loginTime") String loginTime)
//			throws Exception;

//	RealTimeAlarmCount getRealTimeAlarmBJZJBuildCount(@Param("userId") String userId, @Param("loginTime") String loginTime)
//			throws Exception;

	/**
	 * 根据单位id与报警状态获取报警详情 指定日期内
	 *
	 * @param inData
	 * @return
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<FrontFireInfoOutData> getAlarmByUnitId(FrontStatisticalInData inData) throws Exception;

	/**
	 * 首页告警信息历史记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<FrontHistoryAlarmInfoOutData> getFireList(FrontHistoryInData inData) throws Exception;

//	List<FrontHistoryAlarmInfoOutData> getFireListByBuild(FrontHistoryInData inData) throws Exception;

//	List<FrontHistoryAlarmInfoOutData> getFireListBJZJByBuild(FrontHistoryInData inData) throws Exception;

//	List<FrontHistoryAlarmInfoOutData> getFireLists(FrontHistoryInData inData) throws Exception;

	/**
	 * 获取24小时内所有未处理的报警信息
	 *
	 * @param inData
	 * @return
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<FrontFireInfoOutData> getAllAlarms(FrontAlarmInData inData);

//	List<FrontFireInfoOutData> getAllAlarmsByBuild(FrontAlarmInData inData);

//	List<FrontFireInfoOutData> getAllAlarmsByBJZJBuild(FrontAlarmInData inData);

	/**
	 * 获取报警信息重复的频率
	 *
	 * @param inData
	 * @return
	 * 数据源自临时表UtLzBjzjalarm
	 */
	List<String> getAlarmInfoExist(AlarmBJZJInData inData);

	/**
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<FireOutData> getFireAlarmDatas(FrontSdosInData inData) throws Exception;

	List<RtuCurrentOutData> getRTUAlarmCurrentDatas(FrontSdosInData inData) throws Exception;

	List<RtuHistoryOutData> getRTUAlarmHistoryDatas(FrontSdosInData inData) throws Exception;

	/**
	 * 数据源自历史表UtLzBjzjalarmHistory
	 */
	List<HistoryAlarmCount> getHistoryAlarmCount(@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("userId") Long userId, @Param("keyword") String keyword);

    OriginalAlarmVo getOriginalAlarmById(Long id);
	/**
	 * 获取两天前数据最大的id
	 * @throws Exception
	 */
	String getUtLzBjzjalarmMinId() throws Exception;

	/**
	 * 根据最大的id把之前的数据删除
	 * @throws Exception
	 */
	void deleteUtLzBjzjalarmData(@Param("id") String id) throws Exception;

	/**
	 * 获取差集列表
	 * @return
	 * @throws Exception
	 */
	List<UtLzBjzjalarm> getDifference() throws Exception;

	/**
	 * 获取差集id
	 * @return
	 * @throws Exception
	 */
	List<Long> getInsertId() throws Exception;
}