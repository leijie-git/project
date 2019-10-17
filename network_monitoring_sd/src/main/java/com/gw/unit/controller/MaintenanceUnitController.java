package com.gw.unit.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.unit.data.MaintenanceUnitInData;
import com.gw.unit.data.MaintenanceUnitOutData;
import com.gw.unit.service.MaintenanceUnitService;
import com.gw.util.UtilConst;

@Controller
@RequestMapping("/maintenanceunit")
@ResponseBody
public class MaintenanceUnitController extends BaseController{
	private static final Logger log = LoggerFactory.getLogger(MaintenanceUnitController.class);
	
	@Autowired
	private MaintenanceUnitService maintenanceUnitService;
	
	/**
	 * 获取维保单位列表
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getMaintenanceUnitList")
	public PageInfo<MaintenanceUnitOutData> getMaintenanceUnitList(MaintenanceUnitInData inData){
		PageInfo<MaintenanceUnitOutData> pageInfo = null;
		try {
			pageInfo = maintenanceUnitService.getMaintenanceUnitList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 新增维保单位
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addMaintenanceUnit")
	public Json addMaintenanceUnit(HttpServletRequest request, MaintenanceUnitInData inData){
//获取token
		String authorize = request.getHeader("Authorization");
		long id=0;
		String Account=null;
		String UnitID=null;
		Json json = new Json();
		if(authorize!=null){

			//解析token
			Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
			id=Long.parseLong(tokenToMap.get("id"));
			Account=tokenToMap.get("Account");
			UnitID=tokenToMap.get("UnitID");

			try {
				inData.setCreateuserid(id);
				maintenanceUnitService.addMaintenanceUnit(inData);
				json.setSuccess(true);
				json.setMsg("走的token");
			} catch (ServiceException e) {
				json.setMsg(e.getMessage());
				log.error(e.getMessage(),e);
			} catch (Exception e) {
				json.setMsg("新增失败！");
				log.error(e.getMessage(),e);
			}
			return json;
		}


		try {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);


			inData.setCreateuserid(sessinInfo.getId());
			maintenanceUnitService.addMaintenanceUnit(inData);
			json.setSuccess(true);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			json.setMsg("新增失败！");
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	
	@RequestMapping("/updateMaintenanceUnit")
	public Json update(MaintenanceUnitInData maintenanceUnitInData){
		Json json = new Json();
		try {
			maintenanceUnitService.updateMaintenanceUnit(maintenanceUnitInData);
			json.setSuccess(true);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		} catch (Exception e) {
			json.setMsg("修改失败！");
			log.error(e.getMessage(),e);
		}
		return json;
		
	}
	
	@RequestMapping("/edit")
	public Json edit(){
		Json json = new Json();
		try {
			MaintenanceUnitOutData outData = maintenanceUnitService.getMaintenanceUnitList();
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除维保单位
	 * @return
	 */
	@RequestMapping("/deleteMaintenanceUnit")
	public Json deleteMaintenanceUnit(String id){
		Json json = new Json();
		try {
			maintenanceUnitService.deleteMaintenanceUnit(id);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获取维保单位下拉框数据
	 * @return
	 */
	@RequestMapping("/getMaintenanceUnitSelectList")
	public Json getMaintenanceUnitSelectList(HttpServletRequest request){
		String authorize = request.getHeader("Authorization");
		Json json = new Json();
		try {
			List<MaintenanceUnitOutData> list = maintenanceUnitService.getMaintenanceUnitSelectList();

			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
