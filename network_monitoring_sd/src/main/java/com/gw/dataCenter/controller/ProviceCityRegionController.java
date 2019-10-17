package com.gw.dataCenter.controller;

import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.dataCenter.data.ProviceCityRegionInData;
import com.gw.dataCenter.data.ProviceCityRegionOutData;
import com.gw.dataCenter.data.RegionImportVo;
import com.gw.dataCenter.service.ProviceCityRegionService;
import com.gw.exception.ServiceException;
import com.gw.myAnnotation.PassToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


@Controller
@ResponseBody
@RequestMapping(value = "/provice")
public class ProviceCityRegionController extends BaseController {

	
	private Logger log = LoggerFactory.getLogger(ProviceCityRegionController.class);
	
	@Resource 
	private ProviceCityRegionService proviceCityRegionService;
	
	/**
	 * 城市列表
	 * 
	 */
	@RequestMapping("/proviceList")
	public Json resourceList(String name) {
		Json json = new Json();
		try {
			json.setObj(proviceCityRegionService.getAllProvice(name));
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除城市
	 * 
	 */
	@RequestMapping("/delete")
	public Json delete(String id) {
		Json json = new Json();
		try {
			proviceCityRegionService.delete(id);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 添加城市信息
	 * 
	 */
	@RequestMapping("/add")
	public Json add(ProviceCityRegionInData inData) {
		Json json = new Json();
		try {
			proviceCityRegionService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 更新城市信息
	 * 
	 */
	@RequestMapping("/update")
	public Json update(ProviceCityRegionInData inData) {
		Json json = new Json();
		try {
			proviceCityRegionService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 更新城市信息
	 * 
	 */
	@RequestMapping("/edit")
	public Json edit(String id) {
		Json json = new Json();
		try {
			ProviceCityRegionOutData edit = proviceCityRegionService.edit(id);
			json.setObj(edit);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	
	/**
	 * 初始化用户拥有的权限列表
	 * 
	 */
	@RequestMapping("/listProvice")
	public Json listProvice(String type) {
		Json json = new Json();
		try {
			List<ProviceCityRegionOutData> proviceList = proviceCityRegionService.listProviceByID(type);
			json.setSuccess(true);
			json.setObj(proviceList);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	@PassToken
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Json importRegionExcel(@RequestParam("uploadExcel") MultipartFile uploadExcel) {
		List<RegionImportVo> vos = null;
		try {
			vos = proviceCityRegionService.importRegion(importExcel(uploadExcel, 0, RegionImportVo.class));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		return null;
	}
}

