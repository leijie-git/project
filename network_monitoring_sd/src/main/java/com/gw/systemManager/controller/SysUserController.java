package com.gw.systemManager.controller;

import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.login.controller.LoginController;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysRoleAuthorizeInData;
import com.gw.systemManager.data.SysUserInData;
import com.gw.systemManager.data.SysUserOutData;
import com.gw.systemManager.service.SysUserService;
import com.gw.util.UtilConst;

import java.util.Map;

@Controller
@RequestMapping("/user")
@ResponseBody
public class SysUserController extends BaseController{
	private static final Logger log = LoggerFactory.getLogger(SysUserController.class);
	
	@Autowired
	private SysUserService sysUserService;
	
	

	/**
	 * 获取所有用户列表
	 * @param request
	 * @return
	 */
	@GetMapping("/userList")
	public PageInfo<SysUserOutData> userList(HttpServletRequest request,@RequestParam("userName") String userName){
		PageInfo<SysUserOutData> pager = null;
		try {
			pager = sysUserService.getUserList(userName, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pager;
	}
	
	/**
	 * 添加用户
	 * @param sysUserInData
	 * @param request
	 * @return
	 */
	@PostMapping("/addUser")
	public Json addUser(SysUserInData sysUserInData,HttpServletRequest request) {
		Json json = new Json();
		//获取token
		String authorize = request.getHeader("Authorization");

		if(authorize!=null){
			//解析token
			Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
			sysUserInData.setCreateUser(tokenToMap.get("id"));
		}else {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			sysUserInData.setCreateUser(sessinInfo.getId().toString());
		}

		try {
			sysUserService.addUser(sysUserInData,request);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (ServiceException se) {
			se.printStackTrace();
			json.setMsg(se.getMessage());
			log.error(se.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteUser")
	public Json deleteUser(Long id) {
		Json json = new Json();
		try {
			sysUserService.deleteUser(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 批量删除用户
	 * @param id
	 * @return
	 */
	@PostMapping("/deleteManyUser")
	public Json deleteManyUser(@RequestParam("id") String id){
		Json json = new Json();
		try {
			sysUserService.deleteManyUser(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 更新用户信息
	 * @param sysUserInData
	 * @return
	 */
	@PostMapping("/updateUser")
	public Json updateUser(SysUserInData sysUserInData) {
		Json json = new Json();
		try {
			sysUserService.updateUser(sysUserInData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 设置用户角色
	 * @param sysRoleAuthorizeInData
	 * @return
	 */
	@PostMapping("/setUserRole")
	public Json setUserRole(SysRoleAuthorizeInData sysRoleAuthorizeInData) {
		Json json = new Json();
		try {
			sysUserService.setUserRole(sysRoleAuthorizeInData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
			e.printStackTrace();
		}
		return json;
	}
	/**
	 * 获取用户拥有和未拥有的角色
	 * @param id
	 * @return
	 */
	@GetMapping("/getUserRole")
	public Json getUserRole(@RequestParam("id") Long id,@RequestParam("type")String type) {
		Json json = new Json();
		try {
			GetUserRolesData userRole = sysUserService.getUserRole(id,type);
			json.setObj(userRole);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 维保用户重置密码
	 * @param id
	 * @return
	 */
	@RequestMapping("/resetPas")
	public Json resetPas(String id){
		Json json = new Json();
		try {
			sysUserService.resetPas(id);
			json.setSuccess(true);
			json.setMsg("重置成功");
		} catch (Exception e) {
			json.setMsg("重置失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}

}
