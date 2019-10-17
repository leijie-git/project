package com.gw.front.login.service;

import javax.servlet.http.HttpServletRequest;

import com.gw.front.login.data.CheckAccountMsg;
import com.gw.front.login.data.FrontUnitUserOutData;

public interface FrontLoginService {

	/**
	 * 前端用户登录验证
	 * 
	 * @param request
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontUnitUserOutData login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception;

	/**
	 * 更新登录日志的退出时间
	 * 
	 * @param currentLogId
	 * @throws Exception
	 */
	void updateLog(Long currentLogId) throws Exception;

	/**
	 * 修改电话
	 * 
	 * @param request
	 * @param phone
	 * @param id
	 * @param newPassword 
	 * @param oldPassword 
	 * @throws Exception
	 */
	void updatePhone(HttpServletRequest request, String phone, String id, String oldPassword, String newPassword) throws Exception;

	/**
	 * 验证账号信息
	 * @param request
	 * @param inData
	 * @return
	 */
	CheckAccountMsg checkAccount(HttpServletRequest request, FrontUnitUserOutData inData);

}
