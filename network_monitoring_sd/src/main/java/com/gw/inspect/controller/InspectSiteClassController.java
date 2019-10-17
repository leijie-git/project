package com.gw.inspect.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.inspect.data.InspectSiteClassInData;
import com.gw.inspect.data.InspectSiteClassOutData;
import com.gw.inspect.service.InspectSiteClassService;
import com.gw.systemManager.controller.SysResourceController;

/**
 * 巡查点分类
 * @author zfg
 *
 */

@RestController
@RequestMapping("/inspectSiteClass")
public class InspectSiteClassController{
	private Logger log = LoggerFactory.getLogger(InspectSiteClassController.class);
	@Resource
	private InspectSiteClassService inspectSiteClassService;
	
	/**
	 * 获取巡查点分类列表
	 * @param unitId
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<InspectSiteClassOutData> getList(InspectSiteClassInData inData) {
			PageInfo<InspectSiteClassOutData> list = null;
			try {
				list = inspectSiteClassService.getList(inData);
			} catch (Exception e) {
				log.error(e.getMessage(),e);
			}
		return list;
	}
	
	/**
	 * 添加巡查点分类
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(InspectSiteClassInData inData) {
		Json json = new Json();
		try {
			inspectSiteClassService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 编辑巡查点分类
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(InspectSiteClassInData inData) {
		Json json = new Json();
		try {
			inspectSiteClassService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除巡查点分类
	 * @param inData
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			inspectSiteClassService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 巡查点分类集合
	 * @param inData
	 * @return
	 */
	@GetMapping("/getArrayList")
	public Json getArrayList(InspectSiteClassInData inData) {
		Json json = new Json();
		try {
			List<InspectSiteClassOutData> obj = inspectSiteClassService.getArrayList(inData);
			json.setSuccess(true);
			json.setObj(obj);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
}
