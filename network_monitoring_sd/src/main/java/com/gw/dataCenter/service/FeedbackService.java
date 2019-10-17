package com.gw.dataCenter.service;

import com.github.pagehelper.PageInfo;
import com.gw.dataCenter.data.FeedbackIndata;
import com.gw.dataCenter.data.FeedbackOutData;

public interface FeedbackService {

	/**
	 * 分页查询反馈记录
	 * @param indata 
	 * 
	 * @return
	 * @throws Exception
	 */
	PageInfo<FeedbackOutData> getFeedbackList(FeedbackIndata indata) throws Exception;

}
