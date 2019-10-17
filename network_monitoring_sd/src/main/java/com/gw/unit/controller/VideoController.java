package com.gw.unit.controller;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.build.service.BuildService;
import com.gw.common.Json;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.unit.data.VideoInData;
import com.gw.unit.data.VideoOutData;
import com.gw.unit.service.VideoService;

import java.util.Map;

/**
 * 视频管理
 * @author zfg
 *
 */
@RestController
@RequestMapping("/video")
public class VideoController {

	private Logger log = LoggerFactory.getLogger(VideoController.class);
	@Resource
	private VideoService videoService;
	@Resource
	private BuildService buildService;
	/**
	 * 获取视频列表
	 * @return
	 */
	//@RequestMapping("/getVideoList")
	@GetMapping("/getList")
	public PageInfo<VideoOutData> getList(HttpServletRequest request,VideoInData inData,Model model) {
		PageInfo<VideoOutData> page = null;
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
			page = videoService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return page;
		
	}
	
	/**
	 * 添加视频
	 * @param inData
	 * @return
	 */
	//@RequestMapping(value = "/addVideo",method = RequestMethod.POST)
	@PostMapping("/add")
	public Json add(VideoInData inData) {
		Json json = new Json();
		try {
			videoService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 更新视频
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(VideoInData inData) {
		Json json = new Json();
		try {
			videoService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除视频
	 * @param id
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			videoService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	
	
}
