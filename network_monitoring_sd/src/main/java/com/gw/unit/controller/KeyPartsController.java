package com.gw.unit.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildAreaOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.unit.data.KeyPartsInData;
import com.gw.unit.data.KeyPartsOutData;
import com.gw.unit.service.KeyPartsService;

import java.util.Map;

/**
 * 重点单位
 * @author zfg
 *
 */
@RestController
@RequestMapping("/keyParts")
public class KeyPartsController {
	
	private Logger log = LoggerFactory.getLogger(KeyPartsController.class);
	@Resource
	private KeyPartsService keyPartsService;
	
	/**
	 * 获取重点单位列表
	 * @param inData
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<KeyPartsOutData> getList(HttpServletRequest request, KeyPartsInData inData){
		PageInfo<KeyPartsOutData> pager = null;
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
				inData.setUnitId(UnitID);}
			pager = keyPartsService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	
	/**
	 * 添加重点单位
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(KeyPartsInData inData) {
		Json json = new Json();
		try {
			keyPartsService.add(inData);
			
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 更新重点单位
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(KeyPartsInData inData) {
		Json json = new Json();
		try {
			keyPartsService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除重点单位
	 * @param inData
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(@RequestParam("ID") String id) {
		Json json = new Json();
		try {
			keyPartsService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			
		}
		return json;
	}
	
	
}
