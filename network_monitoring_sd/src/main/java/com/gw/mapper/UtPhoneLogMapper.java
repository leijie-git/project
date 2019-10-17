package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.front.analysis.data.FrontAnalysisCommunicationOutData;
import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.analysis.data.FrontAnalysisPhoneOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryMessageOutData;
import com.gw.mapper.entity.UtPhoneLog;

public interface UtPhoneLogMapper extends BaseMapper<UtPhoneLog> {
	/**
	 * 电话记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryMessageOutData> getPhoneList(FrontHistoryInData inData) throws Exception;

	/**
	 * 统计消息
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryMessageOutData> getPhoneStatDetail(FrontHistoryInData inData) throws Exception;

	/**
	 * 电话统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontAnalysisCommunicationOutData getCommunicationCount(FrontAnalysisInData inData) throws Exception;

	/**
	 * 电话统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontAnalysisPhoneOutData> getAnalysisPhoneList(FrontAnalysisInData inData) throws Exception;

	/**
	 * 流量列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontAnalysisCommunicationOutData> getDataflows(FrontAnalysisInData inData) throws Exception;

	/**
	 * 查询所有属性
	 * @param id
	 * @return
	 * @throws Exception
	 */
	UtPhoneLog getListLog(Long id) throws Exception;

	/**
	 * 修改电话状态及通话时长
	 * @param log
	 * @throws Exception
	 */
	void updatePhoneLog(UtPhoneLog log) throws Exception;
}