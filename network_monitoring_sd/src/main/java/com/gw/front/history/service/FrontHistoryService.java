package com.gw.front.history.service;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.EqSystemOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.history.data.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface FrontHistoryService {

	/**
	 * 查询报警主机历史记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryAlarmInfoOutData> getFireHistoryList(FrontHistoryInData inData) throws Exception;

	/**
	 * 单位报表火灾报警系统
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryAlarmInfoOutData> getUnitFireList(FrontHistoryInData inData) throws Exception;

	/**
	 * 导出火灾历史记录
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportFireHistoryList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 获取接口告警历史记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryInterfaceAlarmData> getInterfaceAlarmList(FrontHistoryInData inData) throws Exception;

	/**
	 * 导出接口告警历史记录
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportInterfaceAlarmList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	void exportUnitInterfaceAlarmList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 获取截图历史记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryVideoPicOutData> getVideoLogList(FrontHistoryInData inData) throws Exception;

	/**
	 * 设备系统状态记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHisSDDeviceStatusOutData> getSDDeviceStatusList(FrontHistoryInData inData) throws Exception;

	/**
	 * 导出设备系统状态记录
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportSDDeviceStatusList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 根据id获取详情
	 *
	 * @param id
	 * @param unitId
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	FrontHisDeviceStatusOutData getDeviceInfo(FrontHistoryInData inData) throws Exception;

	/**
	 * 设备状态记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontCoupletCommonOutData> getDeviceStatusList(FrontHistoryInData inData) throws Exception;

	/**
	 * 操作记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryOperationOutData> getOperationList(FrontHistoryInData inData) throws Exception;

	/**
	 * 导出操作日志
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportOperationList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 短信发送记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryMessageOutData> getMessageList(FrontHistoryInData inData) throws Exception;

	/**
	 * 导出短信记录
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportMessageList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 手机号短信发送记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryMessageOutData> getMessageStatDetail(FrontHistoryInData inData) throws Exception;

	/**
	 * 电话拨打记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryMessageOutData> getPhoneList(FrontHistoryInData inData) throws Exception;

	/**
	 * 导出电话记录
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportPhoneList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 电话拨打记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryMessageOutData> getPhoneStatDetail(FrontHistoryInData inData) throws Exception;

	/**
	 * 根据unitId获得系统信息
	 *
	 * @param unitId
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	List<EqSystemOutData> getEqSystemByUnitId(String unitId, String buildId) throws Exception;

	/**
	 * 获取值班记录
	 *
	 * @param inData
	 * @return
	 */
	PageInfo<FrontHistoryUserDutyLogOutData> getUserDutyLogList(FrontHistoryInData inData) throws Exception;

	/**
	 * 获取流量统计
	 *
	 * @param inData
	 * @param year
	 * @param month
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryDataFlowOutData> getDataFlowList(FrontHistoryInData inData) throws Exception;

	/**
	 * 用户传输装置操作记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontTransmissionDeviceOutData> getOPDeviceHistory(FrontHistoryInData inData) throws Exception;

	/**
	 * 导出用户传输装置操作记录
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportOPDeviceHistory(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 导出流量统计详情
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportDataFlowList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 导出值班记录
	 *
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportUserDutyLogList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 统计设备上传数量
	 *
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	Integer getDeviceUploadCount(String deviceId, String startDate, String endDate) throws Exception;

	/**
	 * 首页页面历史记录
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryAlarmInfoOutData> getFireList(FrontHistoryInData inData)throws Exception;

	/**
	 * 首页24h火灾告警记录单位列表
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontHistoryAlarmInfoOutData> getFireUnitList(FrontHistoryInData inData)throws Exception;

	/**
	 * 导出火灾告警记录列表
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportFireList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;

	void exportUnitFireList(FrontHistoryInData inData, HttpServletResponse response) throws Exception;


}
