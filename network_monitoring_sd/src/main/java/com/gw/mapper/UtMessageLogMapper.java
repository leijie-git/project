package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryMessageOutData;
import com.gw.mapper.entity.UtMessageLog;

public interface UtMessageLogMapper extends BaseMapper<UtMessageLog> {

	/**
	 * 短信记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryMessageOutData> getMessageList(FrontHistoryInData inData) throws Exception;

	/**
	 * 统计消息
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryMessageOutData> getMessageStatDetail(FrontHistoryInData inData) throws Exception;

	/**
	 * 短信统计列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryMessageOutData> getAnalysisMessageList(FrontHistoryInData inData) throws Exception;
}