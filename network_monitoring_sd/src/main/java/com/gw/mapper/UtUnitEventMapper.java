package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.front.analysis.data.FrontAnalysisEventOutData;
import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.mapper.entity.UtUnitEvent;

public interface UtUnitEventMapper extends BaseMapper<UtUnitEvent> {

	/**
	 * 事件统计
	 * 
	 * @param inData
	 * @return
	 */
	List<FrontCoupletCommonOutData> eventsCount(FrontAnalysisInData inData) throws Exception;

	/**
	 * 事件分析列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontAnalysisEventOutData> getEventList(FrontAnalysisInData inData) throws Exception;
}