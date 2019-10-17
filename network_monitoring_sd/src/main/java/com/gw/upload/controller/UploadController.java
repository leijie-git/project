package com.gw.upload.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.myAnnotation.PassToken;
import com.gw.upload.service.UploadService;
import com.gw.util.UtilMessage;

@Controller
@RequestMapping("/upload")
public class UploadController extends BaseController {

	@Resource
	private UploadService pictureService;

	/**
	 * 上传图片
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@PassToken
	@RequestMapping(value = "/upLoadPic", method = RequestMethod.POST)
	public Json upLoadPic(HttpServletRequest request) throws Exception {
		Json json = new Json();
		try {
			String url = pictureService.uploadFiles(request, "imageDataList");
			json.setMsg(UtilMessage.GET_MSG_SUCCESS);
			json.setObj(url);
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 上传文件
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@ResponseBody
	@RequestMapping(value = "/upLoadFile", method = RequestMethod.POST)
	public Json upLoadFile(HttpServletRequest request) throws Exception {
		Json json = new Json();
		try {
			String url = pictureService.upLoadFile(request);
			json.setMsg(UtilMessage.GET_MSG_SUCCESS);
			json.setObj(url);
			json.setSuccess(true);
		} catch (Exception e) {
			if (e instanceof ServiceException) {
				json.setMsg(e.getMessage());
			}
			e.printStackTrace();
		}

		return json;
	}

	/**
	 * 百度编辑器图片上传用
	 * 
	 * @param request
	 * @param upfile
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping(value = "/imgUpdate")
	@ResponseBody
	public String imgUpdate(HttpServletRequest request, MultipartFile upfile) throws Exception {
		State state = new BaseState();
		try {
			String url = pictureService.imgUpdate(request, upfile);
			state.putInfo("url", url);
		} catch (Exception e) {
			state = new BaseState(false);
			if (e instanceof ServiceException) {
				e.printStackTrace();
			}

		}
		return state.toJSONString();
	}
}
