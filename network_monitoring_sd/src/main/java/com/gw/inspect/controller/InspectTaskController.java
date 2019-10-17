package com.gw.inspect.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.inspect.data.DownLoadTaskInData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.inspect.data.InspectTaskOutData;
import com.gw.inspect.service.InspectTaskService;
import com.gw.systemManager.controller.SysResourceController;

@RestController
@RequestMapping("/task")
public class InspectTaskController {

	private Logger log = LoggerFactory.getLogger(InspectTaskController.class);
	@Resource
	private InspectTaskService inspectTaskService;
	
	/**
	 * 获取某个计划中的任务列表
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getList")
	public PageInfo<InspectTaskOutData> getList(InspectTaskInData inData){
		PageInfo<InspectTaskOutData> pager=null;
		try {
			pager = inspectTaskService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	
	/**
	 * 删除计划中的任务
	 * @param id
	 * @return
	 */
	@RequestMapping("/remove")
	public Json remove(String id) {
		Json json = new Json();
		try {
			inspectTaskService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		
		return json;
	}
	
	/**
	 * 根据人员下载不同的任务
	 * @param inData
	 * @return
	 */
	@PostMapping("/getUserTask")
	public Json getUserTask(DownLoadTaskInData inData) {
		Json json = new Json();
		try {
			inspectTaskService.getUserTask(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		
		return json;
	}
}
