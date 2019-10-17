package com.gw.front.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.front.login.data.CheckAccountMsg;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.login.service.FrontLoginService;
import com.gw.myAnnotation.PassToken;
import com.gw.util.Util;
import com.gw.util.UtilConst;

@Controller
@ResponseBody
@RequestMapping("/front/start")
public class FrontLoginController extends BaseController {

	private Logger log = LoggerFactory.getLogger(FrontLoginController.class);

	@Resource
	private FrontLoginService frontLoginService;

	/**
	 * 登录
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@PostMapping("/login")
	public Json login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception {
		Json json = new Json();
		try {
			FrontUnitUserOutData outData = frontLoginService.login(request, inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 登录
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@PostMapping("/checkAccount")
	public Json checkAccount(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception {
		Json json = new Json();
		try {
			CheckAccountMsg outData = frontLoginService.checkAccount(request, inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 退出
	 * 
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping("/logout")
	public Json logout(HttpServletRequest request) throws Exception {
		Json json = new Json();
		HttpSession session = request.getSession();
		if (session != null) {
			// 注销session
			FrontUnitUserOutData sessionInfo = (FrontUnitUserOutData) getSessinInfo(request,
					UtilConst.FRONT_USER_SESSION);
			if (Util.isNotEmpty(sessionInfo)) {
				frontLoginService.updateLog(Long.parseLong(sessionInfo.getCurrentLogId()));
			}
			session.removeAttribute(UtilConst.FRONT_USER_SESSION);
		}
		json.setSuccess(true);
		return json;
	}

	/**
	 * 修改用户信息
	 * 
	 * @param request
	 * @param phone
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateMsg")
	public Json updatePhone(HttpServletRequest request, String phone, String oldPassword, String newPassword)
			throws Exception {
		Json json = new Json();
		FrontUnitUserOutData outData = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		setSessinInfo(request, "mobilephone", phone);
		try {
			frontLoginService.updatePhone(request, phone, outData.getId(), oldPassword, newPassword);
			json.setSuccess(true);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

}
