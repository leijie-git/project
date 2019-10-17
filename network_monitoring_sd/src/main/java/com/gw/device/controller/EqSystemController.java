package com.gw.device.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.device.data.EqSystemInData;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.service.EqSystemService;

@Controller
@RequestMapping("/eqSystem")
@ResponseBody
public class EqSystemController {
	
	private Logger log = LoggerFactory.getLogger(EqSystemController.class);
	@Autowired
	private EqSystemService eqSystemService;
	
	@RequestMapping("/pageList")
	public PageInfo<EqSystemOutData> pageList(String eqsystemname, Integer pageNumber, Integer pageSize){
		PageInfo<EqSystemOutData> pageInfo = null;
		try {
			pageInfo = eqSystemService.pageList(eqsystemname, pageNumber, pageSize);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	@RequestMapping("/addEqSystem")
	public Json addEqSystem(EqSystemInData inData){
		Json json = new Json();
		try {
			eqSystemService.addEqSystem(inData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/updateEqSystem")
	public Json updateEqSystem(EqSystemInData inData){
		Json json = new Json();
		try {
			eqSystemService.updateEqSystem(inData);
			json.setSuccess(true);
			json.setMsg("修改成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/deleteEqSystem")
	public Json deleteEqSystem(String id){
		Json json = new Json();
		try {
			eqSystemService.deleteEqSystem(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
