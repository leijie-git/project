package com.gw.systemManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.common.Json;
import com.gw.systemManager.data.SysPropertiesInData;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;

@Controller
@RequestMapping("/propertiesManage")
@ResponseBody
public class PropertiesManageController {
	private Logger logger = LoggerFactory.getLogger(PropertiesManageController.class);
	@Autowired
	private PropertiesManageService propertiesManageService;
	
	/**
	 * 获取properties信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getProperties")
	public Json getProperties() throws Exception{
		Json json = new Json();
		try {
			SysPropertiesOutData sysPropertiesOutData = propertiesManageService.getProperties();
			json.setObj(sysPropertiesOutData);
			json.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 更新properties信息
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateProperties")
	public Json updateProperties(SysPropertiesInData inData) throws Exception{
		Json json = new Json();
		try {
			propertiesManageService.updateProperties(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return json;
	}
}
