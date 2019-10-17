package com.gw.login.service;

import javax.servlet.http.HttpServletRequest;

import com.gw.login.service.data.GetLoginInData;
import com.gw.login.service.data.GetSessionInfoOutData;

public interface LoginService {
	/**
	 * 登录接口
	 * 
	 * @param request
	 * @param inData
	 * @return
	 */
	GetSessionInfoOutData login(HttpServletRequest request, GetLoginInData inData) throws Exception;

	/**
	 * 修改密码
	 * 
	 * @param id
	 * @param passwordOld
	 * @param passwordNew
	 * @param request
	 * @throws Exception
	 */
	void updatePassword(Long id, String passwordNew, HttpServletRequest request)
			throws Exception;

	/**
	 * 重置密码
	 * 
	 * @param account
	 * @throws Exception
	 */
	void resetPassWord(String account) throws Exception;

}
