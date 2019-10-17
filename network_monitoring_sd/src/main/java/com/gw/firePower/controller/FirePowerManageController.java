package com.gw.firePower.controller;

import java.util.List;
import java.util.Map;

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
import com.gw.firePower.data.FirePowerInData;
import com.gw.firePower.data.FirePowerOutData;
import com.gw.firePower.service.FirePowerManageService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.util.UtilConst;

@Controller
@ResponseBody
@RequestMapping("/firePowerManage")
public class FirePowerManageController extends BaseController{
	private Logger log = LoggerFactory.getLogger(FirePowerManageController.class);
	@Autowired
	private FirePowerManageService firePowerManageService;
	
	/**
	 * 获取消防力量分页数据
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/getFirePowerList")
	public PageInfo<FirePowerOutData> getFirePowerList(Integer pageNumber, Integer pageSize, FirePowerInData inData){
		PageInfo<FirePowerOutData> pageInfo = null;
		try {
			pageInfo = firePowerManageService.getFirePowerList(pageNumber,pageSize,inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 新增消防力量
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addFirePower")
	public Json addFirePower(HttpServletRequest request, FirePowerInData inData){
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
			inData.setCreateUser(tokenToMap.get("id"));
			firePowerManageService.addFirePower(inData);
			json.setSuccess(true);
		}else {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			inData.setCreateUser(sessinInfo.getId().toString());
			firePowerManageService.addFirePower(inData);
			json.setSuccess(true);}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 更新消防力量
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/updateFirePower")
	public Json updateFirePower(HttpServletRequest request, FirePowerInData inData){
		Json json = new Json();
		//获取token
		String authorize = request.getHeader("Authorization");

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
				inData.setUpdateUser(tokenToMap.get("id"));
			}else {

		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
		inData.setUpdateUser(sessinInfo.getId().toString());}
		try {
			firePowerManageService.updateFirePower(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除消防力量
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteFirePower")
	public Json deleteFirePower(String id){
		Json json = new Json();
		try {
			firePowerManageService.deleteFirePower(id);
			json.setSuccess(true);
		} catch (ServiceException se) {
			log.error(se.getMessage(),se);
			json.setMsg(se.getMessage());
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 根据类别获取消防站名称
	 * @param type
	 * @return
	 */
	@RequestMapping("/getFireStationName")
	public Json getFireStationNameByType(String type){
		Json json = new Json();
		try {
			List<FirePowerOutData> outData = firePowerManageService.getFireStationNameByType(type);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
}
