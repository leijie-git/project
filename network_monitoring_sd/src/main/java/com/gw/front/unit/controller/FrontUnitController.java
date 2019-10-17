package com.gw.front.unit.controller;

import java.util.List;

import javax.annotation.Resource;

import com.gw.front.unit.data.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaOutData;
import com.gw.common.Json;
import com.gw.device.service.CRTMarkService;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.service.FrontUnitService;
import com.gw.myAnnotation.PassToken;

@Controller
@ResponseBody
@RequestMapping("/front/unit")
public class FrontUnitController {

	private Logger log = LoggerFactory.getLogger(FrontUnitController.class);

	@Resource
	private FrontUnitService extinguisherService;

	@Resource
	private CRTMarkService cRTMarkService;

	/**
	 * 单位信息
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitInfoById")
	public Json getUnitInfoById(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitInfoOutData> outData = extinguisherService.getUnitInfoById(unitId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 单位建筑列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitBuilds")
	public PageInfo<FrontUnitBuildOutData> getUnitBuilds(FrontUnitInData inData) throws Exception {
		PageInfo<FrontUnitBuildOutData> pager = null;
		try {
			pager = extinguisherService.getUnitBuilds(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 单位信息
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitBuildInfoById")
	public Json getUnitBuildInfoById(String buildId) throws Exception {
		Json json = new Json();
		try {
			FrontUnitBuildOutData outData = extinguisherService.getUnitBuildInfoById(buildId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 单位视频
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitVideoInfo")
	public Json getUnitVideoInfo(String unitId,String name) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitVideoOutData> outData = extinguisherService.getUnitVideoInfo(unitId,name);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 单位重点部位
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitImport")
	public Json getUnitImport(String unitId) throws Exception {
		Json json = new Json();
		try {
			String outData = extinguisherService.getUnitImport(unitId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 单位危险品
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitDangerous")
	public Json getUnitDangerous(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<FrontCoupletCommonOutData> outData = extinguisherService.getUnitDangerous(unitId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 单位人员列表
	 * 
	 * @param unitId
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitUserList")
	public Json getUnitUserList(String unitId, String userRole) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitUserOutData> outData = extinguisherService.getUnitUserList(unitId, userRole);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 消防站信息
	 * 
	 * @param unitId
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitXFZInfo")
	public Json getUnitXFZInfo(String unitId, String userRole) throws Exception {
		Json json = new Json();
		try {
			FrontUnitXFZOutData outData = extinguisherService.getUnitXFZInfo(unitId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据单位查询区域列表
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitAreaImgList")
	public Json getUnitAreaImgList(String unitId) throws Exception {
		Json json = new Json();
		try {
			List<BuildAreaOutData> outData = extinguisherService.getUnitAreaImgList(unitId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 根据单位查询区域列表
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping("/getAreaEqSiteList")
	public Json getAreaEqSiteList(String buildAreaId) throws Exception {
		Json json = new Json();
		try {
			List<AreaAssociatedEquipmentOutData> outData = cRTMarkService.getAreaAssociatedEquipment(buildAreaId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}*/

	/**
	 * 消防力量中的消防设备
	 * 
	 * @param unitId
	 * @return
	 */
	@RequestMapping("/fireEquipment")
	public Json fireEquipment(String unitID) {
		Json json = new Json();
		try {
			List<FireStationManageOutData> list = extinguisherService.fireEquipment(unitID);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 查询CRT点位
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getCRTList")
	public Json getCRTList(String unitID) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitCRTOutData> outData = cRTMarkService.getCRTList(unitID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}
	
	
	/**
	 * 根据单位id查询建筑物和区域
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnitBuildArea")
	public Json getUnitBuildArea(String unitID) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitBuildArea> outData = cRTMarkService.getUnitBuildArea(unitID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}
	
	/**
	 * 查询单位CRT点位
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getUnitOneCRT")
	public Json getUnitOneCRT(String buildAreaID) throws Exception {
		Json json = new Json();
		try {
			List<FrontUnitCRTOutData> outData = cRTMarkService.getUnitOneCRT(buildAreaID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}
	
	/**
	 * 查询告警CRT点位
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@PassToken
	@RequestMapping(value = "/getOneCRT")
	public Json getOneCRT(String addressID) throws Exception {
		Json json = new Json();
		try {
			FrontUnitCRTOutData outData = cRTMarkService.getOneCRT(addressID);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
		}
		return json;
	}

}
