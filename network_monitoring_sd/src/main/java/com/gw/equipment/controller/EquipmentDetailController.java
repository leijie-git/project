package com.gw.equipment.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.equipment.data.EquipmentDetailOutData;
import com.gw.equipment.data.EquipmentFacInData;
import com.gw.equipment.service.EquipmentDetailService;
import com.gw.exception.ServiceException;
import com.gw.systemManager.controller.SysResourceController;

@RestController
@RequestMapping("/eqDetail")
public class EquipmentDetailController extends BaseController{
	
	@Resource
	private EquipmentDetailService equipmentDetailService;
	private Logger log = LoggerFactory.getLogger(EquipmentDetailController.class);
	/**
	 * 根据设备id获取端口信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPortList")
	public Json getPortList(String id) {
		Json json = new Json();
		try {
			List<EquipmentDetailOutData> list = equipmentDetailService.getPortList(id);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 根据设备id获取端口信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/getPortListPage")
	public PageInfo<EquipmentDetailOutData> getPortListPage(EquipmentFacInData inData) {
		PageInfo<EquipmentDetailOutData> pager = null;
		try {
			pager = equipmentDetailService.getPortListPage(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	
	/**
	 * 导入
	 * 
	 * @param uploadExcel
	 * @param request
	 * @param importProjectPid
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Json importExcel(@RequestParam("uploadExcel") MultipartFile uploadExcel, HttpServletRequest request,
			String eqid) {
		Json json = new Json();
		try {
			List<EquipmentFacInData> projectImportDatas = importExcel(uploadExcel, 0, EquipmentFacInData.class);
			equipmentDetailService.importData(projectImportDatas, eqid);
			json.setSuccess(true);
			json.setMsg("导入成功");
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg("导入失败");
		}
		return json;
	}
	
	/**
	 * 根据设备id获取端口信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/addPort")
	public Json addPort(EquipmentFacInData inData) {
		Json json = new Json();
		try {
			equipmentDetailService.addPort(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 根据设备id获取端口信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deletePort")
	public Json deletePort(String id) {
		Json json = new Json();
		try {
			equipmentDetailService.deletePort(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 根据设备id获取端口信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/editPort")
	public Json editPort(EquipmentFacInData inData) {
		Json json = new Json();
		try {
			equipmentDetailService.editPort(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	

}
