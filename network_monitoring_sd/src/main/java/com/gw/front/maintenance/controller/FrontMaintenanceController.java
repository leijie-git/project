package com.gw.front.maintenance.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.mapper.entity.UtLzRepair;
import com.gw.mapper.entity.UtLzRepairInData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.maintenance.data.FrontMaintenanceFireOutData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.data.FrontMaintenanceStatOutData;
import com.gw.front.maintenance.data.FrontMaintenanceTaskOutData;
import com.gw.front.maintenance.service.FrontMaintenanceService;
import com.gw.util.UtilConst;

/**
 * 维保巡查
 * 
 * @author Administrator
 *
 */
@Controller
@ResponseBody
@RequestMapping("/front/maintenance")
public class FrontMaintenanceController extends BaseController {

	private Logger log = LoggerFactory.getLogger(FrontMaintenanceController.class);

	@Resource
	private FrontMaintenanceService frontMaintenanceService;

	/**
	 * 维保巡查--信息统计
	 * 
	 * @param request
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMaintenanceStat")
	public Json getMaintenanceStat(HttpServletRequest request, String unitId) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			FrontMaintenanceStatOutData outData = frontMaintenanceService.getMaintenanceStat(unitId,
					sessinInfo.getId());
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取灭火器记录
	 *
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getFireExtinguisherList")
	public PageInfo<FrontMaintenanceFireOutData> getFireExtinguisherList(HttpServletRequest request,
			FrontMaintenanceInData inData) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		PageInfo<FrontMaintenanceFireOutData> pager = null;
		try {
			pager = frontMaintenanceService.getFireExtinguisherList(inData,sessinInfo.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出灭火器
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportFireExtinguisherList")
	public void exportFireExtinguisherList(HttpServletRequest request, FrontMaintenanceInData inData,
			HttpServletResponse response) throws Exception {
		try {
			FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
			frontMaintenanceService.exportFireExtinguisherList(inData, response,sessinInfo.getId());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 维修列表
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRepairList")
	public PageInfo<FrontMaintenanceOutData> getRepairList(HttpServletRequest request, FrontMaintenanceInData inData)
			throws Exception {
		PageInfo<FrontMaintenanceOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontMaintenanceService.getRepairList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 通过维修id查询维修流程
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getRepairDetail")
	public Json getRepairDetail(HttpServletRequest request, String repairId) {
		Json json = new Json();
		try {
			FrontMaintenanceOutData outData = frontMaintenanceService.getRepairDetail(repairId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 导出维修列表
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportRepairList")
	public void exportRepairList(HttpServletRequest request, FrontMaintenanceInData inData,
			HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontMaintenanceService.exportRepairList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 巡查任务列表
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInspectTaskList")
	public PageInfo<FrontMaintenanceTaskOutData> getInspectTaskList(HttpServletRequest request,
			FrontMaintenanceInData inData) throws Exception {
		PageInfo<FrontMaintenanceTaskOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontMaintenanceService.getInspectTaskList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 巡查快过期任务列表
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInspectEpirationTaskList")
	public PageInfo<FrontMaintenanceTaskOutData> getInspectEpirationTaskList(HttpServletRequest request,
			FrontMaintenanceInData inData) throws Exception {
		PageInfo<FrontMaintenanceTaskOutData> pager = null;
		try {
			pager = frontMaintenanceService.getInspectEpirationTaskList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出巡查快过期任务列表
	 * 
	 * @param response
	 * @param inData
	 */
	@RequestMapping("/exportInspectEpirationTaskList")
	public void exportInspectEpirationTaskList(HttpServletResponse response, FrontMaintenanceInData inData) {
		try {
			frontMaintenanceService.exportInspectEpirationTaskList(response, inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 通过任务id查询任务详情
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getInspectTaskDetail")
	public Json getInspectTaskDetail(HttpServletRequest request, String taskID) {
		Json json = new Json();
		try {
			List<FrontMaintenanceTaskOutData> outData = frontMaintenanceService.getInspectTaskDetail(taskID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 导出巡查任务列表
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportInspectTaskList")
	public void exportInspectTaskList(FrontMaintenanceInData inData,
			HttpServletResponse response) throws Exception {
		try {
			frontMaintenanceService.exportInspectTaskList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 导出巡查月度报告
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportInspectMouthReport")
	public void exportInspectMouthReport(FrontMaintenanceInData inData,
			HttpServletResponse response) throws Exception {
		try {
			frontMaintenanceService.exportInspectMouthReport(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	

	/**
	 * 导出維保月度报告
	 *
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportMaintenanceReport")
	public Json exportMaintenanceReport(HttpServletRequest request, FrontMaintenanceInData inData,
			HttpServletResponse response) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontMaintenanceService.exportMaintenanceReport(inData, response);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 *
	 *通过联网单位Id查出维保人员
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMaintenanceUserBynetworkingID")
	public Json getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData) {
		Json json = new Json();
		try {
			List<GenerateMaintenanceUserData> maintenanceUserBynetworkingID = frontMaintenanceService.getMaintenanceUserBynetworkingID(generateMaintenanceUserData);
			json.setMsg("返回成功");
			json.setSuccess(true);
			json.setObj(maintenanceUserBynetworkingID);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 *
	 *任务分配",选择维保单位执行人,维保时间区间
	 * @param
	 * @param
	 * @param
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/updateUtLzRepair",method = RequestMethod.PUT)
	public Json updateUtLzRepair(UtLzRepairInData  utLzRepair) {
		Json json = new Json();

		try {
			int flag= frontMaintenanceService.updateUtLzRepair(utLzRepair);
			json.setMsg("分配成功");
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			json.setSuccess(false);
		}
		return json;
	}
}
