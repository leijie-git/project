package com.gw.wechat.service;


import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.mapper.entity.ApkInfo;
import com.gw.wechat.data.TrunSingleInData;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

public interface AppService {

	/**
	 * app登录
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontUnitUserOutData login(HttpServletRequest request, FrontUnitUserOutData inData) throws Exception;

	void logout(FrontUnitUserOutData inData) throws Exception;

	/**
	 * apk上传
	 * @param file	文件
	 * @return url
	 */
	String uploadApk(MultipartFile file) throws Exception;

	/**
	 * 新增apkInfo
     * @param apkInfo apk基础信息
     */
	void addApkInfo(ApkInfo apkInfo) throws Exception;

	/**
	 * 获取最新apk基础信息
	 * @return apk基础信息
	 */
	ApkInfo getLastApkInfo() throws Exception;

	/**
	 * 转单流程
	 * @param trunSingleInData
	 * @throws Exception
	 */
	void turnSingle(TrunSingleInData trunSingleInData) throws Exception;


}
