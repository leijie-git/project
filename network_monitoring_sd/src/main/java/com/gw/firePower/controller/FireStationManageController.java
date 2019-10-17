package com.gw.firePower.controller;

import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
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
import com.gw.exception.ServiceException;
import com.gw.firePower.data.FireStationManageInData;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.firePower.service.FireStationManageService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.util.UtilConst;

import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/fireStationManage")
public class FireStationManageController extends BaseController {
	private Logger log = LoggerFactory.getLogger(FireStationManageController.class);

	@Autowired
	private FireStationManageService fireStationManageService;

	/**
	 * 新增消防站设备信息
	 * 
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addFireStation")
	public Json addFireStation(HttpServletRequest request, FireStationManageInData inData) {
		Json json = new Json();

		//获取token
		String authorize = request.getHeader("Authorization");


		try {

			if(authorize!=null){
				long id=0;
				String Account=null;
				String UnitID=null;
				PageInfo<BuildOutData> pager=null;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				Account=tokenToMap.get("Account");
				UnitID=tokenToMap.get("UnitID");
				fireStationManageService.addFireStation(id, inData);
				json.setSuccess(true);
			}else {
				GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			fireStationManageService.addFireStation(sessinInfo.getId(), inData);
			json.setSuccess(true);}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 修改消防站设备信息
	 * 
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/updateFireStation")
	public Json updateFireStation(HttpServletRequest request, FireStationManageInData inData) {
		Json json = new Json();
		//获取token
		String authorize = request.getHeader("Authorization");

		try {if(authorize!=null){
			long id=0;
			String Account=null;
			String UnitID=null;
			PageInfo<BuildOutData> pager=null;
			//解析token
			Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
			id=Long.parseLong(tokenToMap.get("id"));
			Account=tokenToMap.get("Account");
			UnitID=tokenToMap.get("UnitID");
			fireStationManageService.updateFireStation(id, inData);
			json.setSuccess(true);
		}else {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			fireStationManageService.updateFireStation(sessinInfo.getId(), inData);
			json.setSuccess(true);}
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg("修改失败");
		}
		return json;
	}

	/**
	 * 获取消防站设备信息
	 * 
	 * @param fireStationName
	 * @return
	 */
	@RequestMapping("/getFireStationList")
	public PageInfo<FireStationManageOutData> getFireStationList(Integer pageNumber, Integer pageSize,
			String fireStationName,String name) {
		PageInfo<FireStationManageOutData> pageInfo = null;
		try {
			pageInfo = fireStationManageService.getFireStationList(pageNumber, pageSize, fireStationName,name);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	@RequestMapping("/deleteFireStation")
	public Json deleteFireStation(String id) {
		Json json = new Json();
		try {
			fireStationManageService.deleteFireStation(id);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

}
