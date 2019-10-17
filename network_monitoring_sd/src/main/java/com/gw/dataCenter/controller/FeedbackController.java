package com.gw.dataCenter.controller;

import javax.annotation.Resource;

import com.gw.myAnnotation.PassToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.dataCenter.data.FeedbackIndata;
import com.gw.dataCenter.data.FeedbackOutData;
import com.gw.dataCenter.service.FeedbackService;

@Controller
@RequestMapping(value = "/feedback")
public class FeedbackController {

	private Logger log = LoggerFactory.getLogger(FeedbackController.class);
	@Resource
	private FeedbackService feedbackService;

	/**
	 * 跳转到反馈页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/feedbackPage")
	public String feedbackPage() throws Exception {
		return "/dataCenter/feedback";
	}

	/**
	 * 分页查询反馈记录
	 * 
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/feedbackList")
	public PageInfo<FeedbackOutData> getFeedbackList(FeedbackIndata indata) throws Exception {
		PageInfo<FeedbackOutData> pager = null;
		try {
			pager = feedbackService.getFeedbackList(indata);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}
}
