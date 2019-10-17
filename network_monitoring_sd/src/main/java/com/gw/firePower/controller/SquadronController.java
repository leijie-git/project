package com.gw.firePower.controller;

import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.firePower.data.SquadronInData;
import com.gw.firePower.data.SquadronOutData;
import com.gw.firePower.service.SquadronService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.util.UtilConst;

import java.util.Map;

@Controller
@ResponseBody
@RequestMapping("/squadron")
public class SquadronController extends BaseController {
	private Logger log = LoggerFactory.getLogger(SquadronController.class);

	@Autowired
	private SquadronService squadronService;

	/**
	 * 获取消防中队配置数据
	 * 
	 * @param name
	 * @return
	 */
	@RequestMapping("/getSquadronList")
	public PageInfo<SquadronOutData> getSquadronList(Integer pageNumber, Integer pageSize, String name , String powerName) {
		PageInfo<SquadronOutData> pageInfo = null;
		try {
			pageInfo = squadronService.getSquadronList(pageNumber, pageSize, name, powerName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageInfo;
	}

	/**
	 * 新增中队配置
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addSquadron")
	public Json addSquadron(HttpServletRequest request, SquadronInData inData) {
		Json json = new Json();

		//获取token
		String authorize = request.getHeader("Authorization");
		try {
			if(authorize!=null){
				long id=0;
				String Account=null;
				String UnitID=null;
				PageInfo<BuildOutData> pager=null;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				Account=tokenToMap.get("Account");
				UnitID=tokenToMap.get("UnitID");
				squadronService.addSquadron(id, inData);
				json.setSuccess(true);
			}else {
				GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			squadronService.addSquadron(sessinInfo.getId(), inData);
			json.setSuccess(true);}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
	
	/**
	 * 修改消防中队信息
	 * @param request
	 * @param inData
	 * @return
	 */
	@RequestMapping("/updateSquadron")
	public Json updateSquadron(HttpServletRequest request, SquadronInData inData) {
		Json json = new Json();

		//获取token
		String authorize = request.getHeader("Authorization");
		try {
			if(authorize!=null){
				long id=0;
				String Account=null;
				String UnitID=null;
				PageInfo<BuildOutData> pager=null;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				Account=tokenToMap.get("Account");
				UnitID=tokenToMap.get("UnitID");
				squadronService.updateSquadron(id, inData);
				json.setSuccess(true);
			}else {
				GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			squadronService.updateSquadron(sessinInfo.getId(), inData);
			json.setSuccess(true);}
		} catch (ServiceException se) {
			log.error(se.getMessage(), se);
			json.setMsg(se.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			json.setMsg("修改失败");
		}
		return json;
	}
	
	/**
	 * 删除中队配置信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteSquadron")
	public Json deleteSquadron(String id) {
		Json json = new Json();
		try {
			squadronService.deleteSquadron(id);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}
}
