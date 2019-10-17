package com.gw.upload.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gw.upload.service.UploadService;

@Service
public class UploadServiceImpl implements UploadService {

	@Value("${cbs.imagesPath}")
	private String mImagesPath;

	@Override
	public String uploadFiles(HttpServletRequest request, String fileProperty) throws Exception {

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		/** 构建图片保存的目录 **/
		String picPathDir = "pictures/" + dateformat.format(new Date());
		// UUID
		String uuid = UUID.randomUUID().toString().replace("-", "");
		/** 得到图片保存目录的真实路径.tomcat下webapp路径 **/
		String picRealPathDir = mImagesPath + picPathDir + "/" + uuid + "/";
		/** 根据真实路径创建目录 **/
		File picSaveFile = new File(picRealPathDir);
		if (!picSaveFile.exists())
			picSaveFile.mkdirs();
		/** 页面控件的文件流 **/

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile(fileProperty);

		// 原始图片
		String picName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = picName.substring(picName.lastIndexOf("."));

		/** 使用UUID生成文件名称 **/
		String saveFileName = new Date().getTime() + suffix;// 构建文件名称
		// String saveFileName = picName;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = picRealPathDir + saveFileName;
		File file = new File(fileName);
		String picDirPath = "/file/upload/" + picPathDir + "/" + uuid + "/";

		multipartFile.transferTo(file);

		request.getRemoteAddr();

		return picDirPath + saveFileName;
	}

	public String imgUpdate(HttpServletRequest request, MultipartFile multipartFile) throws Exception {

		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		/** 构建图片保存的目录 **/
		String picPathDir = "pictures/" + dateformat.format(new Date());
		// UUID
		String uuid = UUID.randomUUID().toString().replace("-", "");
		/** 得到图片保存目录的真实路径.tomcat下webapp路径 **/
		String picRealPathDir = mImagesPath + picPathDir + "/" + uuid + "/";
		/** 根据真实路径创建目录 **/
		File picSaveFile = new File(picRealPathDir);
		if (!picSaveFile.exists())
			picSaveFile.mkdirs();
		// 原始图片
		String picName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = picName.substring(picName.lastIndexOf("."));

		/** 使用UUID生成文件名称 **/
		String saveFileName = new Date().getTime() + suffix;// 构建文件名称
		// String saveFileName = picName;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = picRealPathDir + saveFileName;
		File file = new File(fileName);
		String picDirPath = "/file/upload/" + picPathDir + "/" + uuid + "/";

		multipartFile.transferTo(file);

		request.getRemoteAddr();

		return picDirPath + saveFileName;
	}

	@Override
	public String upLoadFile(HttpServletRequest request) throws Exception {


		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd/HH");
		/** 构建图片保存的目录 **/
		String picPathDir = "file/" + dateformat.format(new Date());
		// UUID
		String uuid = UUID.randomUUID().toString().replace("-", "");
		/** 得到图片保存目录的真实路径.tomcat下webapp路径 **/
		String picRealPathDir = mImagesPath + picPathDir + "/" + uuid + "/";
		/** 根据真实路径创建目录 **/
		File picSaveFile = new File(picRealPathDir);
		if (!picSaveFile.exists())
			picSaveFile.mkdirs();
		/** 页面控件的文件流 **/

		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile multipartFile = multipartRequest.getFile("uploadFile");

		// 原始图片
		String picName = multipartFile.getOriginalFilename();
		/** 获取文件的后缀 **/
		String suffix = picName.substring(picName.lastIndexOf("."));

		/** 使用UUID生成文件名称 **/
		String saveFileName = new Date().getTime() + suffix;// 构建文件名称
		// String saveFileName = picName;
		/** 拼成完整的文件保存路径加文件 **/
		String fileName = picRealPathDir + saveFileName;
		File file = new File(fileName);
		String picDirPath = "/file/upload/" + picPathDir + "/" + uuid + "/";

		multipartFile.transferTo(file);

		request.getRemoteAddr();

		return picDirPath + saveFileName;
	
	}

}
