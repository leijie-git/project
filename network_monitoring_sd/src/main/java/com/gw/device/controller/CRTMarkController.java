package com.gw.device.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.AddressRelInData;
import com.gw.device.data.AddressRelOutData;
import com.gw.device.data.CRTInData;
import com.gw.device.data.UnitAssociatedAreaOutData;
import com.gw.device.service.CRTMarkService;

@Controller
@ResponseBody
@RequestMapping("/crtMark")
public class CRTMarkController extends BaseController {
	private Logger log = LoggerFactory.getLogger(CRTMarkController.class);

	@Autowired
	private CRTMarkService crtMarkService;

	/**
	 * 获取单位关联的区域
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getUnitAssociatedArea")
	public Json getUnitAssociatedArea(HttpServletRequest request, String unitName, String unitId) {
		Json json = new Json();
		try {
			List<UnitAssociatedAreaOutData> outData = crtMarkService.getUnitAssociatedArea(unitName,unitId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 获取区域关联的设备分页
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getAreaAssociatedEquipment")
	public PageInfo<AddressRelOutData> getAreaAssociatedEquipment(CRTInData inData) {
		PageInfo<AddressRelOutData> outData = null;
		try {
			outData = crtMarkService.getAreaAssociatedEquipment(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return outData;
	}

	/**
	 * 获取区域关联的所有设备
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getAllAreaAssociatedEquipment")
	public Json getAllAreaAssociatedEquipment(CRTInData inData) {
		Json json = new Json();
		try {
			List<AddressRelOutData> outData = crtMarkService.getAllAreaAssociatedEquipment(inData);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 更新点位信息
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/setEqPoint")
	public Json setEqPoint(AddressRelInData inData) {
		Json json = new Json();
		try {
			crtMarkService.setEqPoint(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 取消标点
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/emptyPoint")
	public Json emptyPoint(String id) {
		Json json = new Json();
		try {
			crtMarkService.emptyPoint(id);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 更新关联地址信息
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/updateEqAddressRel")
	public Json updateEqAddressRel(AddressRelInData inData) {
		Json json = new Json();
		try {
			crtMarkService.updateEqAddressRel(inData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg("更新失败");
		}
		return json;
	}
}
