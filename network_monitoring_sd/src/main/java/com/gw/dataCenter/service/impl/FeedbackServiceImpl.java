package com.gw.dataCenter.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.gw.dataCenter.data.FeedbackIndata;
import com.gw.dataCenter.data.FeedbackOutData;
import com.gw.dataCenter.service.FeedbackService;
import com.gw.mapper.UtFeedbackMapper;

@Service
public class FeedbackServiceImpl implements FeedbackService {

	@Resource
	private UtFeedbackMapper utFeedbackMapper;
	
	@Override
	public PageInfo<FeedbackOutData> getFeedbackList(FeedbackIndata indata) throws Exception {
		PageHelper.startPage(indata.getPageNumber(), indata.getPageSize());
		List<FeedbackOutData> list = utFeedbackMapper.getFeedbackList(indata);
		return new PageInfo<>(list);
	}

}
