package com.gw.wechat.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.EqSystemOutData;
import com.gw.front.couplet.data.*;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.FrontVideoOutData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.lookup.data.FrontLookupInData;
import com.gw.front.lookup.data.FrontLookupLogOutData;
import com.gw.front.lookup.data.FrontLookupUnitInfoData;
import com.gw.front.maintenance.data.FrontMaintenanceFireOutData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.data.FrontMaintenanceTaskOutData;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.unit.data.NetworkingUserInData;
import com.gw.unit.data.NetworkingUserOutData;
import com.gw.wechat.data.*;

public interface PhoneService {

	/**
	 * 登录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontUnitUserOutData login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception;

	/**
	 * 静默授权 获取code
	 *
	 * @throws Exception
	 */
	String getUrl() throws Exception;

	/**
	 * 获取openid
	 *
	 * @param code
	 * @return
	 * @throws Exception
	 */
	String getOpenId(String code) throws Exception;

	/**
	 * 查询告警信息
	 *
	 * @param phoneAlarmInData
	 * @return
	 * @throws Exception
	 */
	List<WechatAlarmInfoOutData> getAlarmList(PhoneAlarmInData phoneAlarmInData) throws Exception;

	/**
	 * 统计告警未处理数量
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	WeChatAlarmStatOutData getAlarmNodealStat(String id) throws Exception;

	/**
	 * 告警信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	FrontCoupletAlarmInfoOutData getAlarmInfo(String id) throws Exception;

	/**
	 * 处理告警信息
	 *
	 * @param inData
	 * @throws Exception
	 */
	void dealAlarm(PhoneAlarmInfoIndata inData) throws Exception;

	/**
	 * 获得RTU告警信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	FrontCoupletAlarmInfoOutData getRTUAlarmInfo(String id) throws Exception;

	/**
	 * 处理RTU告警
	 *
	 * @param inData
	 * @throws Exception
	 */
	void dealRTUAlarm(PhoneAlarmInfoIndata inData) throws Exception;

	/**
	 * 更新是否推送故障
	 *
	 * @param isPushFault
	 * @param account
	 * @throws Exception
	 */
	void updateIsPushFault(String isPushFault, String userId) throws Exception;

	/**
	 * 查询设备列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHisSDDeviceStatusOutData> getSDDeviceStatusList(FrontHistoryInData inData) throws Exception;

	/**
	 * 标定类型列表
	 *
	 * @return
	 * @throws Exception
	 */
	List<PhoneCalibrationOutData> calibrationList() throws Exception;

	/**
	 * 根据用户id获取单位列表
	 *
	 * @param userId
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletUnitInfo> getUnitList(String userId, String keyword) throws Exception;

	/**
	 * 根据单位id获取单位详细信息
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitInfoOutData> getUnitInfoById(String unitId) throws Exception;

	/**
	 * 查岗
	 *
	 * @param ownerCode
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	boolean lookup(String ownerCode, String userId) throws Exception;

	/**
	 * 获取查岗记录
	 *
	 * @param userId
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	List<FrontLookupLogOutData> getLookupLogs(String userId, FrontLookupInData indata) throws Exception;

	/**
	 * 维保列表
	 *
	 * @param userId
	 * @param status
	 * @return
	 */
	List<FrontMaintenanceOutData> getRepairList(String userId, Integer status) throws Exception;

	/**
	 * 根据unitId获得系统信息
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<EqSystemOutData> getEqSystemByUnitId(String unitId) throws Exception;

	/**
	 * 火灾告警历史记录列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryAlarmInfoOutData> getFireHistoryList(FrontHistoryInData inData) throws Exception;

	/**
	 * 获取接口告警历史记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryAlarmInfoOutData> getInterfaceAlarmList(FrontHistoryInData inData) throws Exception;

	/**
	 * 获取巡查任务分页列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontMaintenanceTaskOutData> getInspectTaskList(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 获取维保任务分页列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontMaintenanceOutData> getRepairPageList(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 意见反馈
	 *
	 * @param inData
	 */
	void feedback(PhoneFeedbackInData inData);

	/**
	 * 下载任务
	 *
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	List<PhoneTaskDetailOutData> getTaskList(String userid, String inspectcycletype) throws Exception;

	/**
	 * 上传任务
	 *
	 * @param tasks
	 */
	void saveTaskList(List<PhoneTaskDetailOutData> tasks);

	/**
	 * 根据单位id获取灭火器记录
	 *
	 * @param unitId
	 * @param keyword
	 * @return
	 */
	List<FrontMaintenanceFireOutData> getFireExtinguisherList(String unitId, String keyword, String status, String userId)
			throws Exception;

	/**
	 * 立即整改
	 *
	 * @param inData
	 * @throws Exception
	 */
	void updateTaskDetail(PhoneUploadProblem inData) throws Exception;

	/**
	 * 生成隐患
	 *
	 * @param inData
	 * @throws Exception
	 */
	void uploadDanger(PhoneUploadProblem inData) throws Exception;

	/**
	 * 维保巡查问题处理
	 *
	 * @param inData
	 */
	void dangerDeal(MaintenanceDealInData inData) throws Exception;

	/**
	 * 获取基本巡查点分类检查项列表
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<PhoneSiteClassDetialBaseOutData> getSiteClassDetailBaseList(String id) throws Exception;

	/**
	 * 提交全部巡检任务
	 *
	 * @param ids
	 * @throws Exception
	 */
	void submitAllInspectTask(String userId, String jsons) throws Exception;

	/**
	 * 查看维保详情
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	FrontRepairDetailOutData getMaintenanceDetail(String id) throws Exception;

	/**
	 * 查岗列表 获取包含用户传输装置的单位
	 *
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontLookupUnitInfoData> getUnitTransferDevice(String unitId) throws Exception;

	/**
	 * 物联--系统检测 单位水、电系统列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontInterfaceAppOutData> getWaterMoreListNew(FrontCoupletInData inData) throws Exception;


	/**
	 * 物联--系统检测 单位水、电系统列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontInterFaceStatusOutData> getWaterMoreList(FrontCoupletInData inData) throws Exception;

	/**
	 * 物联--系统检测 报警系统列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletFireAlarmOutData> getFireAlarmList(FrontCoupletInData inData) throws Exception;

	/**
	 * 获取视频详情
	 *
	 * @param videoID
	 * @return
	 */
	FrontVideoOutData getVideoDetail(String videoID) throws Exception;

	/**
	 * 微信(app)退出登录(解除关联)
	 *
	 * @param inData
	 */
	void updateLog(FrontUnitUserOutData inData, String type);

	/**
	 * 维保巡查--信息统计
	 *
	 * @param userId
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletUnitInfo> getMaintenanceStatList(String userId, String keyword) throws Exception;

	/**
	 * 查询待接单列表
	 *
	 * @param userId
	 * @return
	 */
	List<PhoneTaskDetailOutData> getReceiveTaskList(String userId, String inspectcycleType, String type);

	/**
	 * 转单
	 *
	 * @param changeUserID  //转单人id
	 * @param taskID        //任务列表
	 * @param receiveUserID ////接单人id
	 */
	void changeTask(String changeUserID, String taskID, String receiveUserID);

	/**
	 * 接单
	 *
	 * @param receiveUserID //接单人id
	 * @param taskID        //任务列表
	 */
	void receiveTask(String receiveUserID, String taskID);

	/**
	 * 撤回转单
	 *
	 * @param userId
	 * @param taskID
	 */
	void withdrawTask(String userId, String taskID);

	/**
	 * 获取该联网单位所有人员信息
	 *
	 * @param userId
	 * @return
	 */
	PageInfo<NetworkingUserOutData> getNetUserList(String userId, NetworkingUserInData inData);

	/**
	 * 获取转接单数量
	 *
	 * @param userId
	 * @return
	 */
	TaskSheetOutData getTaskCount(String userId);

	/**
	 * 退出
	 *
	 * @param openId
	 * @param type
	 * @throws Exception
	 */
	void logout(String openId, String type) throws Exception;

	/**
	 * 根据主键id获取灭火器详情
	 *
	 * @param feId 灭火器id
	 */
//	FrontMaintenanceFireOutData getFeDetailById(String userId,Long feId) throws Exception;
	FrontMaintenanceFireOutData getFeDetailById(Long feId) throws Exception;

	/**
	 * 绑定NFC卡片
	 *
	 * @param nfcCode nfc卡唯一标识
	 * @param taskID  巡查任务唯一标识
	 */
	void bindingNFC(String nfcCode, String taskID) throws Exception;

	/**
	 * 通过 NFC唯一标识 获取检查项明细数据
	 *
	 * @param nfcCode nfc卡唯一标识
	 */
	List<PhoneTaskDetailOutData> getInspectDetail(String nfcCode);
}
