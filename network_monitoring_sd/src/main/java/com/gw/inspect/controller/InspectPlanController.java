package com.gw.inspect.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.inspect.data.InspectPlanInData;
import com.gw.inspect.data.InspectPlanOutData;
import com.gw.inspect.service.InspectPlanService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.util.UtilConst;

import java.util.Map;

/**
 * 巡查计划
 * @author zfg
 *
 */
@RestController
@RequestMapping("/inspectPlan")
public class InspectPlanController extends BaseController{
	private Logger log = LoggerFactory.getLogger(InspectPlanController.class);
	@Resource
	private InspectPlanService inspectPlanService;
	
	/**
	 * 获取巡查计划
	 * @param inData
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<InspectPlanOutData> getList(InspectPlanInData inData){
		PageInfo<InspectPlanOutData> pager = null;
		try {
			pager = inspectPlanService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	
	/**
	 * 根据登录用户查询所属单位
	 * @param request
	 * @return
	 */
	@GetMapping("/getUnitID")
	public Json getUnitID(HttpServletRequest request) {

		Json json = new Json();
		//获取token
		String authorize = request.getHeader("Authorization");
		try {
		if(authorize!=null){
			long id=0;
			//解析token
			Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
			id=Long.parseLong(tokenToMap.get("id"));
			String unitID = inspectPlanService.getUnitID(id);
			json.setObj(unitID);
			json.setSuccess(true);}
		else {
			GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			String unitID = inspectPlanService.getUnitID(sessionInfo.getId());
			json.setObj(unitID);
			json.setSuccess(true);
		}
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 添加巡查计划
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(InspectPlanInData inData) {
		Json json = new Json();
		try {
			String id = inspectPlanService.add(inData);
			json.setObj(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 编辑巡查计划
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(InspectPlanInData inData) {
		Json json = new Json();
		try {
			inspectPlanService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除巡查计划
	 * @param
	 * @return
	 */
	@PostMapping("/remove")
	public Json update(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			inspectPlanService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	
	

	

}
