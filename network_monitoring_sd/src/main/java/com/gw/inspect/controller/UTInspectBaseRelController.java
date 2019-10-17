package com.gw.inspect.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.inspect.data.InspectBaseRelInData;
import com.gw.inspect.data.InspectTaskInListData;
import com.gw.inspect.data.UTInspectBaseRelOutData;
import com.gw.inspect.service.UTInspectBaseRelService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.util.JwtUtil;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inspectBaseRel")
public class UTInspectBaseRelController extends BaseController {
	private Logger log = LoggerFactory.getLogger(UTInspectBaseRelController.class);

	@Autowired
	private UTInspectBaseRelService utInspectBaseRelService;

	/**
	 * 获取检查项配置
	 *
	 * @param inData
	 * @return
	 */
	@RequestMapping("/getList")
	public PageInfo<UTInspectBaseRelOutData> getList(InspectBaseRelInData inData) {
		PageInfo<UTInspectBaseRelOutData> pager = null;
		try {
			pager = utInspectBaseRelService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pager;
	}

	/**
	 * 检查项配置管理
	 *
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addAll")
	public Json addAll(@RequestBody List<InspectTaskInListData> inData, HttpServletRequest request) {
		Json json = new Json();
		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
		try {
			for (InspectTaskInListData inDatum : inData) {
				inDatum.setUnitId(sessinInfo.getUnitId());
			}
			utInspectBaseRelService.addAll(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	@RequestMapping("/updateAll")
	public Json updateAll(@RequestBody List<InspectTaskInListData> inData, HttpServletRequest request, String unitId) {
		Json json = new Json();
		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
		unitId = sessinInfo.getUnitId();
		//获取token
		String authorize = request.getHeader("Authorization");
		long id=0;
		String Account=null;
		String UnitID=null;
		//解析token
		Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
		id=Long.parseLong(tokenToMap.get("id"));
		Account=tokenToMap.get("Account");
		UnitID=tokenToMap.get("UnitID");
		try {
			for (InspectTaskInListData inDatum : inData) {
				if(authorize!=null){
				inDatum.setUnitId(UnitID);}else {
					inDatum.setUnitId(sessinInfo.getUnitId());
				}
			}
			utInspectBaseRelService.updateAll(inData, unitId);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除巡查检查项配置
	 *
	 * @param id
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(String id) {
		Json json = new Json();
		try {
			utInspectBaseRelService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查询已配置
	 *
	 * @param inData
	 * @return
	 */
	@PostMapping("/getRelList")
	public Json getRelList(InspectBaseRelInData inData) {
		Json json = new Json();
		try {
			json.setObj(utInspectBaseRelService.getRelList(inData));
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(), e);
		}
		return json;
	}

}
