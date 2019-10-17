package com.gw.front.analysis.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.front.analysis.data.FrontAnaLysisLookupOutData;
import com.gw.front.analysis.data.FrontAnalysisAlarmCountOutData;
import com.gw.front.analysis.data.FrontAnalysisAlarmStatOutData;
import com.gw.front.analysis.data.FrontAnalysisCommunicationOutData;
import com.gw.front.analysis.data.FrontAnalysisEventOutData;
import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.analysis.data.FrontAnalysisPhoneOutData;
import com.gw.front.analysis.data.FrontAnalysisUnitOutData;
import com.gw.front.analysis.service.FrontAnalysisService;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryMessageOutData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.util.UtilConst;

/**
 * 综合分析
 * 
 * @author Administrator
 *
 */
@Controller
@ResponseBody
@RequestMapping("/front/analysis")
public class FrontAnalysisController extends BaseController {

	private Logger log = LoggerFactory.getLogger(FrontAnalysisController.class);

	@Resource
	private FrontAnalysisService frontAnalysisService;

	/**
	 * 统计查岗次数
	 * 
	 * @param request
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUplookCout")
	public Json getUplookCout(HttpServletRequest request, FrontAnalysisInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			FrontCoupletCommonOutData outData = frontAnalysisService.getUplookCout(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 统计查岗记录列表
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getLookupStatList")
	public PageInfo<FrontAnaLysisLookupOutData> getLookupStatList(HttpServletRequest request,
			FrontAnalysisInData inData) throws Exception {
		PageInfo<FrontAnaLysisLookupOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontAnalysisService.getLookupStatList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出查岗统计列表
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportLookupStatList")
	public void exportLookupStatList(HttpServletRequest request, FrontAnalysisInData inData,
			HttpServletResponse response) throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontAnalysisService.exportLookupStatList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 综合分析--系统分析--单位系统分析--单位系统异常率统计
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmCout")
	public Json getAlarmCout(HttpServletRequest request, FrontAnalysisInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			FrontAnalysisAlarmStatOutData outData = frontAnalysisService.getAlarmCout(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 综合分析--系统分析--单位系统分析--单位系统异常率统计 各系统告警分类型统计(火灾、水系统。。。)
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmStatBySystem")
	public Json getAlarmStatBySystem(HttpServletRequest request, FrontAnalysisInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			FrontAnalysisAlarmCountOutData outData = frontAnalysisService.getAlarmStatBySystem(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 通讯系统分析
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getCommunicationCount")
	public Json getCommunicationCount(HttpServletRequest request, FrontAnalysisInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			FrontAnalysisCommunicationOutData outData = frontAnalysisService.getCommunicationCount(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 短信统计
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getMessageList")
	public PageInfo<FrontHistoryMessageOutData> getMessageList(HttpServletRequest request, FrontHistoryInData inData)
			throws Exception {
		PageInfo<FrontHistoryMessageOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontAnalysisService.getMessageList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出短信记录
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportMessageList")
	public void exportMessageList(HttpServletRequest request, FrontHistoryInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontAnalysisService.exportMessageList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 电话统计
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getPhoneList")
	public PageInfo<FrontAnalysisPhoneOutData> getPhoneList(HttpServletRequest request, FrontAnalysisInData inData)
			throws Exception {
		PageInfo<FrontAnalysisPhoneOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontAnalysisService.getPhoneList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出电话记录
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportPhoneList")
	public void exportPhoneList(HttpServletRequest request, FrontAnalysisInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontAnalysisService.exportPhoneList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 单位分析列表
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitList")
	public PageInfo<FrontAnalysisUnitOutData> getUnitList(HttpServletRequest request, FrontAnalysisInData inData)
			throws Exception {
		PageInfo<FrontAnalysisUnitOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontAnalysisService.getUnitList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出单位分析列表
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportUnitList")
	public void exportUnitList(HttpServletRequest request, FrontAnalysisInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontAnalysisService.exportUnitList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	/**
	 * 事件分析统计
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/eventsCount")
	public Json eventsCount(HttpServletRequest request, FrontAnalysisInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			List<FrontCoupletCommonOutData> outData = frontAnalysisService.eventsCount(inData);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 事件分析列表
	 * 
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getEventList")
	public PageInfo<FrontAnalysisEventOutData> getEventList(HttpServletRequest request, FrontAnalysisInData inData)
			throws Exception {
		PageInfo<FrontAnalysisEventOutData> pager = null;
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			pager = frontAnalysisService.getEventList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 导出事件分析列表
	 * 
	 * @param request
	 * @param inData
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/exportEventList")
	public void exportEventList(HttpServletRequest request, FrontAnalysisInData inData, HttpServletResponse response)
			throws Exception {
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			frontAnalysisService.exportEventList(inData, response);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
