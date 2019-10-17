package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.dataCenter.data.FeedbackIndata;
import com.gw.dataCenter.data.FeedbackOutData;
import com.gw.mapper.entity.UtFeedback;

public interface UtFeedbackMapper extends BaseMapper<UtFeedback> {

	/**
	 * 反馈列表
	 * 
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	List<FeedbackOutData> getFeedbackList(FeedbackIndata indata) throws Exception;
}