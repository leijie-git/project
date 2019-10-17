package com.gw.unit.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.unit.data.DangerousInData;
import com.gw.unit.data.DangerousOutData;
import com.gw.unit.service.DangerousService;

import java.util.Map;

/**
 * 单位危险品
 * @author zfg
 *
 */
@RestController
@RequestMapping("/dangerous")
public class DangerousController {
	private Logger log = LoggerFactory.getLogger(DangerousController.class);
	@Resource
	private DangerousService dangerousService;
	
	/**
	 * 获取单位危险品列表
	 * @param inData
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<DangerousOutData> getList(HttpServletRequest request, DangerousInData inData){
		PageInfo<DangerousOutData> pager = null;
		//获取token
		String authorize = request.getHeader("Authorization");
		long id=0;
		String Account=null;
		String UnitID=null;
		//解析token
		Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
		id=Long.parseLong(tokenToMap.get("id"));
		Account=tokenToMap.get("Account");
		UnitID=tokenToMap.get("UnitID");
		try {
			if(UnitID!=null){
				inData.setUnitID(UnitID);}
			pager = dangerousService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	
	/**
	 * 添加单位危险品
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(DangerousInData inData) {
		Json json = new Json();
		try {
			dangerousService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 更新单位危险品
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(DangerousInData inData) {
		Json json = new Json();
		try {
			dangerousService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除单位危险品
	 * @param inData
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(String id) {
		Json json = new Json();
		try {
			dangerousService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
