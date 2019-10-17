package com.gw.device.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.PointVideoInData;
import com.gw.device.data.PointVideoOutData;
import com.gw.device.service.PointVideoService;
import com.gw.exception.ServiceException;

@Controller
@RequestMapping("/pointVideo")
@ResponseBody
public class PointVideoController extends BaseController {
	private Logger log = LoggerFactory.getLogger(PointVideoController.class);
	@Autowired
	private PointVideoService pointVideoService;

	/**
	 * 新增视频点位
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/addPointVideo")
	public Json addPointVideo(PointVideoInData inData) throws Exception {
		Json json = new Json();
		try {
			pointVideoService.addPointVideo(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 更新视频点位
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updatePointVideo")
	public Json updatePointVideo(PointVideoInData inData) throws Exception {
		Json json = new Json();
		try {
			pointVideoService.updatePointVideo(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取视频点位分页列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPointVideoList")
	public PageInfo<PointVideoOutData> getPointVideoList(HttpServletRequest request,PointVideoInData inData) throws Exception {
		PageInfo<PointVideoOutData> pageInfo = null;
		String authorize = request.getHeader("Authorization");
		long id = 0;
		String Account = null;
		String UnitID = null;
           if (authorize != null) {
          	//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id = Long.parseLong(tokenToMap.get("id"));
				Account = tokenToMap.get("Account");
				UnitID = tokenToMap.get("UnitID");
				if (UnitID != null) {
					inData.setUnitId(UnitID);
				}

			}



		try {
			pageInfo = pointVideoService.getPointVideoList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	/**
	 * 删除视频点位
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/deletePointVideo")
	public Json deletePointVideo(String id) throws Exception {
		Json json = new Json();
		try {
			pointVideoService.deletePointVideo(id);
			json.setSuccess(true);
		} catch (ServiceException se) {
			json.setMsg(se.getMessage());
			log.error(se.getMessage(), se);
		} catch (Exception e) {
			json.setMsg("删除失败");
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取点位视频下拉数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPointVideoSelect")
	public Json getPointVideoSelect(Long unitId) throws Exception {
		Json json = new Json();
		try {
			List<PointVideoOutData> outData = pointVideoService.getPointVideoSelect(unitId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 导出点位视频列表
	 * 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportPointVideo")
	public void exportPointVideo(HttpServletResponse response, PointVideoInData inData) throws Exception {
		try {
			pointVideoService.exportPointVideo(response,inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
