package com.gw.front.analysis.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.gw.front.analysis.data.FrontAnaLysisLookupOutData;
import com.gw.front.analysis.data.FrontAnalysisAlarmCountOutData;
import com.gw.front.analysis.data.FrontAnalysisAlarmStatOutData;
import com.gw.front.analysis.data.FrontAnalysisCommunicationOutData;
import com.gw.front.analysis.data.FrontAnalysisEventOutData;
import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.analysis.data.FrontAnalysisPhoneOutData;
import com.gw.front.analysis.data.FrontAnalysisUnitOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryMessageOutData;

public interface FrontAnalysisService {

	/**
	 * 统计查岗次数
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontCoupletCommonOutData getUplookCout(FrontAnalysisInData inData) throws Exception;

	/**
	 * 统计查岗记录列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontAnaLysisLookupOutData> getLookupStatList(FrontAnalysisInData inData) throws Exception;

	/**
	 * 导出查岗统计列表
	 * 
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportLookupStatList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 单位系统异常率统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontAnalysisAlarmStatOutData getAlarmCout(FrontAnalysisInData inData) throws Exception;

	/**
	 * 各系统告警分类型统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontAnalysisAlarmCountOutData getAlarmStatBySystem(FrontAnalysisInData inData) throws Exception;

	/**
	 * 通讯系统分析
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontAnalysisCommunicationOutData getCommunicationCount(FrontAnalysisInData inData) throws Exception;

	/**
	 * 短信统计
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
	 * 电话统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontAnalysisPhoneOutData> getPhoneList(FrontAnalysisInData inData) throws Exception;

	/**
	 * 导出电话记录
	 * 
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportPhoneList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 单位分析列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontAnalysisUnitOutData> getUnitList(FrontAnalysisInData inData) throws Exception;

	/**
	 * 导出单位分析列表
	 * 
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportUnitList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 事件分析统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> eventsCount(FrontAnalysisInData inData) throws Exception;

	/**
	 * 事件分析列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontAnalysisEventOutData> getEventList(FrontAnalysisInData inData) throws Exception;

	/**
	 * 导出事件分析列表
	 * 
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportEventList(FrontAnalysisInData inData, HttpServletResponse response) throws Exception;
}
