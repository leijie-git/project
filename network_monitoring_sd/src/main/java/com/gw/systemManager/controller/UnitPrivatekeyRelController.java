package com.gw.systemManager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.systemManager.data.UnitPrivatekeyRelInData;
import com.gw.systemManager.data.UnitPrivatekeyRelOutData;
import com.gw.systemManager.service.UnitPrivatekeyRelService;

@Controller
@RequestMapping("/unitPrivatekeyRel")
@ResponseBody
public class UnitPrivatekeyRelController {
	private Logger log = LoggerFactory.getLogger(UnitPrivatekeyRelController.class);
	@Autowired
	private UnitPrivatekeyRelService unitPrivatekeyRelService;
	
	/**
	 * 获取所有私钥
	 * @param inData
	 * @return
	 */
	@RequestMapping("/privatekeyList")
	public PageInfo<UnitPrivatekeyRelOutData> privatekeyList(UnitPrivatekeyRelInData inData){
		PageInfo<UnitPrivatekeyRelOutData> pageInfo = null;
		try {
			pageInfo = unitPrivatekeyRelService.privatekeyList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 新增私钥
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addUnitPrivatekeyRel")
	public Json addUnitPrivatekeyRel(UnitPrivatekeyRelInData inData){
		Json json = new Json();
		try {
			unitPrivatekeyRelService.addUnitPrivatekeyRel(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除私钥
	 * @param privateKey
	 * @return
	 */
	@RequestMapping("/deleteUnitPrivatekeyRel")
	public Json deleteUnitPrivatekeyRel(String privateKey){
		Json json = new Json();
		try {
			unitPrivatekeyRelService.deleteUnitPrivatekeyRel(privateKey);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获取私钥已经关联的单位
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getAssociatedUnit")
	public PageInfo<UnitPrivatekeyRelOutData> getAssociatedUnit(UnitPrivatekeyRelInData inData){
		PageInfo<UnitPrivatekeyRelOutData> pageInfo = null;
		try {
			pageInfo = unitPrivatekeyRelService.getAssociatedUnit(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 获取私钥未关联的单位
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getUnassociatedUnit")
	public PageInfo<UnitPrivatekeyRelOutData> getUnassociatedUnit(UnitPrivatekeyRelInData inData){
		PageInfo<UnitPrivatekeyRelOutData> pageInfo = null;
		try {
			pageInfo = unitPrivatekeyRelService.getUnassociatedUnit(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 私钥绑定单位
	 * @param privateKey
	 * @return
	 */
	@RequestMapping("/bindUnit")
	public Json bindUnit(String privateKey, String unitIds){
		Json json = new Json();
		try {
			unitPrivatekeyRelService.bindUnit(privateKey, unitIds);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 私钥解绑单位
	 * @param privateKey
	 * @return
	 */
	@RequestMapping("/unbindUnit")
	public Json unbindUnit(String privateKey, String ids){
		Json json = new Json();
		try {
			unitPrivatekeyRelService.unbindUnit(privateKey, ids);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
