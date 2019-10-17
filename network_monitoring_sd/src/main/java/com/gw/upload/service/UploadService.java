package com.gw.upload.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface UploadService {

	/**
	 * 处理文件流，并且判断当前人员有没有文件空间，并且返回url
	 * 
	 * @param request
	 * @param string
	 * @param userID
	 * @param getuRole
	 * @return
	 * @throws Exception
	 */
	String uploadFiles(HttpServletRequest request, String string) throws Exception;

	/**
	 * 百度编辑器上传图片
	 * 
	 * @param request
	 * @param file
	 * @return
	 * @throws Exception
	 */
	String imgUpdate(HttpServletRequest request, MultipartFile file) throws Exception;

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String upLoadFile(HttpServletRequest request) throws Exception;
}
