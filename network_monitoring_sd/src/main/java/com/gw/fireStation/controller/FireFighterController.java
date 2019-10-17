package com.gw.fireStation.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaInData;
import com.gw.common.Json;
import com.gw.fireStation.data.FireFighterInData;
import com.gw.fireStation.data.FireFighterOutData;
import com.gw.fireStation.service.FireFighterService;

@RestController
@RequestMapping("/fireFighter")
public class FireFighterController {
	@Resource
	private FireFighterService fireFighterService;
	
	private Logger log = LoggerFactory.getLogger(FireFighterController.class);
	
	/**
	 * 获取建筑物区域列表
	 * @param inData
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<FireFighterOutData> getList(BuildAreaInData inData){
		PageInfo<FireFighterOutData> pager = null;
		try {
			pager = fireFighterService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}

	/**
	 * 添加建筑物区域
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(FireFighterInData inData) {
		Json json = new Json();
		try {
			fireFighterService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 编辑建筑物区域
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(FireFighterInData inData) {
		Json json = new Json();
		try {
			fireFighterService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除建筑物区域
	 * @param id
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			fireFighterService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
