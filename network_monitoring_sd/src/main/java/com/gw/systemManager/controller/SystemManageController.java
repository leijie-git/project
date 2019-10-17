package com.gw.systemManager.controller;

import com.gw.myAnnotation.PassToken;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/systemManage")
public class SystemManageController {

	/**
	 * 首页
	 * 
	 * @param request
	 * @return
	 */
	@PassToken
	@RequestMapping("/indexPage")
	public String indexPage(HttpServletRequest request) {
		return "index";
	}

	/**
	 * 资源列表
	 * 
	 * @param request
	 * @return
	 */
	@PassToken
	@RequestMapping("/resouceList")
	public String resouceList(HttpServletRequest request) {
		return "systemManage/resourceManage";
	}

	/**
	 * 前台资源列表
	 *
	 * @param request
	 * @return
	 */
	@PassToken
	@RequestMapping("/stagRresouceList")
	public String stagRresouceList(HttpServletRequest request) {
		return "systemManage/stageList";
	}

	/**
	 * 前台角色管理列表
	 *
	 * @param request
	 * @return
	 */
	@PassToken
	@RequestMapping("/stageRoleManage")
	public String stageRoleManage(HttpServletRequest request) {
		return "systemManage/stageRoleManage";
	}

	/**
	 * 角色列表
	 * 
	 * @param request
	 * @return
	 */
	@PassToken
	@RequestMapping("/roleList")
	public String roleList(HttpServletRequest request) {
		return "systemManage/roleManage";
	}
	
	/**
	 * 用户列表
	 * 
	 * @param request
	 * @return
	 */
	@PassToken
	@RequestMapping("/userList")
	public String userList(HttpServletRequest request) {
		return "systemManage/userManage";
	}
	
	/**
	 * 代码组列表
	 * @return
	 */
	@PassToken
	@RequestMapping("/codeGroupList")
	public String codeGroupList(){
		return "systemManage/codeGroup";
	}
	@PassToken
	@RequestMapping("/codeList")
	public String codeList(){
		return "systemManage/code";
	}
	
	/**
	 * 代码组列表
	 * @return
	 */
	@PassToken
	@RequestMapping("/proviceList")
	public String proviceList(){
		return "dataCenter/proviceManage";
	}
	
	/**
	 * 通知列表
	 * @return
	 */
	@PassToken
	@RequestMapping("/notifyList")
	public String notfiyList(){
		return "systemManage/notify";
	}
	
	/**
	 * 私钥配置
	 * @return
	 */
	@PassToken
	@RequestMapping("/unitPrivatekeyRel")
	public String unitPrivatekeyRel(){
		return "systemManage/unitPrivatekeyRel";
	}
	
	/**
	 * 配置管理
	 * @return
	 */
	@PassToken
	@RequestMapping("/propertiesManage")
	public String propertiesManage(){
		return "systemManage/propertiesManage";
	}
	
	/**
	 * 上传单位
	 * @return
	 */
	@PassToken
	@RequestMapping("/uploadUnit")
	public String uploadUnit(){
		return "uploadUnit/uploadUnit";
	}

	/**
	 * 巡查点检查项配置
	 * @return
	 */
	@PassToken
	@RequestMapping("/inspectCheckConfigure")
	public String inspectCheckConfigure(){
		return "/inspect/inspectCheckConfigure";
	}
}
