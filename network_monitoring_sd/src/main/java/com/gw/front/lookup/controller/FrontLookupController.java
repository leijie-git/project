package com.gw.front.lookup.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.couplet.data.FrontLookupUserInfoOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.lookup.data.FrontLookupInData;
import com.gw.front.lookup.data.FrontLookupLogOutData;
import com.gw.front.lookup.data.FrontLookupUnitInfoData;
import com.gw.front.lookup.data.FrontUserExperienceOutData;
import com.gw.front.lookup.service.FrontLookupService;
import com.gw.util.UtilConst;

@Controller
@ResponseBody
@RequestMapping("/front/lookup")
public class FrontLookupController extends BaseController {

	private Logger log = LoggerFactory.getLogger(FrontLookupController.class);

	@Resource
	private FrontLookupService frontLookupService;

	/**
	 * 查岗列表 获取包含用户传输装置的单位
	 * 
	 * @param request
	 * @param keyWord
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getTransferDeviceUnit")
	public PageInfo<FrontLookupUnitInfoData> getTransferDeviceUnit(HttpServletRequest request, String keyWord,
			Integer pageSize, Integer pageNumber) throws Exception {
		PageInfo<FrontLookupUnitInfoData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			pager = frontLookupService.getTransferDeviceUnit(sessinInfo.getId(), keyWord, pageSize, pageNumber);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 查岗
	 * 
	 * @param request
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/lookup")
	public Json lookup(HttpServletRequest request, String deviceIds, String type) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			json.setSuccess(frontLookupService.lookup(sessinInfo.getId(), deviceIds, type));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查岗记录
	 * 
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getLookupLogs")
	public PageInfo<FrontLookupLogOutData> getLookupLogs(HttpServletRequest request, FrontLookupInData indata)
			throws Exception {
		PageInfo<FrontLookupLogOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			pager = frontLookupService.getLookupLogs(sessinInfo.getId(), indata);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出查岗记录
	 * 
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportLookupLogs")
	public void exportLookupLogs(HttpServletRequest request, FrontLookupInData indata, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			frontLookupService.exportLookupLogs(sessinInfo.getId(), indata, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 导出查岗列表
	 * 
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportTransferDeviceUnit")
	public void exportTransferDeviceUnit(HttpServletRequest request, String keyWord, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			frontLookupService.exportTransferDeviceUnit(sessinInfo.getId(), keyWord, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 查岗人员信息
	 * 
	 * @param cardId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLookupUserInfo")
	public Json getLookupUserInfo(String id) throws Exception {
		Json json = new Json();
		try {
			FrontLookupUserInfoOutData outData = frontLookupService.getLookupUserInfo(id);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 工作经历
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUserExperience")
	public Json getUserExperience(String infoId) throws Exception {
		Json json = new Json();
		try {
			List<FrontUserExperienceOutData> outData = frontLookupService.getUserExperience(infoId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 导出工作记录
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportUserExperience")
	public void exportUserExperience(HttpServletResponse response, String infoId) throws Exception {
		try {
			frontLookupService.exportUserExperience(infoId, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 点名记录
	 * 
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getNamingLogs")
	public PageInfo<FrontLookupLogOutData> getNamingLogs(HttpServletRequest request, FrontLookupInData indata)
			throws Exception {
		PageInfo<FrontLookupLogOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		indata.setUserId(sessinInfo.getId());
		try {
			pager = frontLookupService.getNamingLogs(indata);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出点名记录
	 * 
	 * @param response
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportNamingLogs")
	public void exportNamingLogs(HttpServletResponse response, HttpServletRequest request, FrontLookupInData indata)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		indata.setUserId(sessinInfo.getId());
		try {
			frontLookupService.exportNamingLogs(indata, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	/**
	 * 查岗设备当前状态
	 * 
	 * @param deviceIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLookupCurrentstatus")
	public Json getLookupCurrentstatus(String deviceIds) throws Exception {
		Json json = new Json();
		try {
			List<FrontCoupletCommonOutData> outData = frontLookupService.getLookupCurrentstatus(deviceIds);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	/**
	 * 点名设备当前状态
	 * 
	 * @param deviceIds
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNamingCurrentstatus")
	public Json getNamingCurrentstatus(String deviceIds) throws Exception {
		Json json = new Json();
		try {
			List<FrontCoupletCommonOutData> outData = frontLookupService.getNamingCurrentstatus(deviceIds);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 联网单位用户 查岗列表 获取用户传输装置
	 * @param request
	 * @param keyWord
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping("/getNetworkingUnitTransferDevice")
	public PageInfo<FrontLookupUnitInfoData> getNetworkingUnitTransferDevice(HttpServletRequest request, String keyWord,
			Integer pageSize, Integer pageNumber){
		PageInfo<FrontLookupUnitInfoData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			pager = frontLookupService.getNetworkingUnitTransferDevice(sessinInfo.getUnitid(), keyWord, pageSize, pageNumber);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}
	
	/**
	 * 联网单位用户 查岗列表 导出用户传输装置
	 * @param request
	 * @param indata
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportNetworkingUnitTransferDevice")
	public void exportNetworkingUnitTransferDevice(HttpServletRequest request, String keyWord, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			frontLookupService.exportNetworkingUnitTransferDevice(sessinInfo.getUnitid(), keyWord, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 联网单位用户 查岗
	 * @param request
	 * @param deviceId
	 * @param type
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/networkingUnitLookUp")
	public Json networkingUnitLookUp(HttpServletRequest request, String deviceIds, String type) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			json.setSuccess(frontLookupService.networkingUnitLookUp(sessinInfo.getId(), deviceIds, type));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 获取联网单位用户  查岗记录
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getNetworkingUnitLookupLogs")
	public PageInfo<FrontLookupLogOutData> getNetworkingUnitLookupLogs(HttpServletRequest request, FrontLookupInData indata)
			throws Exception {
		PageInfo<FrontLookupLogOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			pager = frontLookupService.getNetworkingUnitLookupLogs(sessinInfo.getUnitid(), indata);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}
	
	/**
	 * 导出联网单位用户  查岗记录
	 * @param request
	 * @param keyWord
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/exportNetworkingUnitLookupLogs")
	public void exportNetworkingUnitLookupLogs(HttpServletRequest request, FrontLookupInData indata, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			frontLookupService.exportNetworkingUnitLookupLogs(sessinInfo.getUnitid(), indata, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 联网单位用户  点名列表
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getNetworkingUnitSDDeviceStatusList")
	public PageInfo<FrontHisSDDeviceStatusOutData> getNetworkingUnitSDDeviceStatusList(HttpServletRequest request,
			FrontHistoryInData inData) throws Exception {
		PageInfo<FrontHisSDDeviceStatusOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUnitId(sessinInfo.getUnitid());
		try {
			pager = frontLookupService.getNetworkingUnitSDDeviceStatusList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}
	
	/**
	 * 联网单位用户  导出点名列表
	 * @param request
	 * @param inData
	 * @throws Exception
	 */
	@RequestMapping("/exportNetworkingUnitSDDeviceStatusList")
	public void exportNetworkingUnitSDDeviceStatusList(HttpServletRequest request,HttpServletResponse response,
			FrontHistoryInData inData) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUnitId(sessinInfo.getUnitid());
		try {
			frontLookupService.exportNetworkingUnitSDDeviceStatusList(response, inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 获取联网单位用户 点名记录
	 * @param request
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/getNetworkingUnitNamingLogs")
	public PageInfo<FrontLookupLogOutData> getNetworkingUnitNamingLogs(HttpServletRequest request, FrontLookupInData indata)
			throws Exception {
		PageInfo<FrontLookupLogOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		indata.setUnitId(sessinInfo.getUnitid());
		try {
			pager = frontLookupService.getNetworkingUnitNamingLogs(indata);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}
	
	/**
	 * 导出 联网单位点名记录
	 * @param response
	 * @param request
	 * @param indata
	 * @throws Exception
	 */
	@RequestMapping("/exportNetworkingUnitNamingLogs")
	public void exportNetworkingUnitNamingLogs(HttpServletResponse response, HttpServletRequest request, FrontLookupInData indata)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		indata.setUnitId(sessinInfo.getUnitid());
		try {
			frontLookupService.exportNetworkingUnitNamingLogs(indata, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
	
}
