package com.gw.fireStation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.fireStation.data.XFZUserInData;
import com.gw.fireStation.data.XFZUserOutData;
import com.gw.fireStation.service.XFZUserService;
import com.gw.unit.data.BaseInfoSelectOutData;

/**
 * 消防站人员管理
 * @author SY
 *
 */

@Controller
@RequestMapping("/xfzUser")
@ResponseBody
public class UserController {
	
	@Autowired
	private XFZUserService xfzUserService;
	
	private Logger log = LoggerFactory.getLogger(UserController.class);
	
	@RequestMapping("/list")
	public PageInfo<XFZUserOutData> list(HttpServletRequest request, String username){
		try {
			PageInfo<XFZUserOutData> list = xfzUserService.list(request, username);
			return list;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	@RequestMapping("/add")
	public Json add(XFZUserInData inData){
		Json json = new Json();
		try {
			xfzUserService.add(inData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.setMsg("添加失败");
		}
		return json;
	}
	
	@RequestMapping("/update")
	public Json update(XFZUserInData inData){
		Json json = new Json();
		try {
			xfzUserService.update(inData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.setMsg("更新失败");
		}
		return json;
	}
	
	@RequestMapping("/delete")
	public Json delete(Long id){
		Json json = new Json();
		try {
			xfzUserService.delete(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			log.error(e.getMessage(),e);
			json.setMsg("删除失败");
		}
		return json;
	}
	
	@RequestMapping("/unitSelect")
	public Json unitSelect(){
		Json json = new Json();
		try {
			List<BaseInfoSelectOutData> list = xfzUserService.unitSelect();
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
