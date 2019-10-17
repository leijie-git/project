package com.gw.front.unit.controller;

import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.data.UnitGradeInData;
import com.gw.front.unit.service.UnitGradeService;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/front/unitGrade")
@ResponseBody
public class UnitGradeController extends BaseController {
	private Logger log = LoggerFactory.getLogger(UnitGradeController.class);

	@Autowired
	private UnitGradeService unitGradeService;

	/**
	 *单位评分-综合评分
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getUnitGrade")
	public Json getUnitGrade(HttpServletRequest request, UnitGradeInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			json.setSuccess(true);
			json.setObj(unitGradeService.getUnitGrade(inData));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 *单位评分-告警评分
	 * @param request
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/getAlarmGrade")
	public Json getAlarmGrade(HttpServletRequest request, UnitGradeInData inData) throws Exception {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		inData.setUserId(sessinInfo.getId());
		try {
			json.setSuccess(true);
			json.setObj(unitGradeService.getAlarmGrade(inData));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
}
