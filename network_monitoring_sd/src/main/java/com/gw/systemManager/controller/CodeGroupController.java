package com.gw.systemManager.controller;

import java.util.List;
import java.util.Map;

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
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.data.CodeGroupInData;
import com.gw.systemManager.data.CodeGroupOutData;
import com.gw.systemManager.service.CodeGroupService;
import com.gw.util.UtilConst;

/**
 * 代码组管理Controller
 * @author SY
 * @data 2018年9月14日
 */
@Controller
@RequestMapping("/codeGroup")
@ResponseBody
public class CodeGroupController extends BaseController{
	private Logger log = LoggerFactory.getLogger(CodeGroupController.class);
	
	@Autowired
	private CodeGroupService codeGroupService;
	
	@RequestMapping("/list")
	public PageInfo<CodeGroupOutData> list(Integer pageNumber, Integer pageSize, String codegroupname,String codegroupkey){
		try {
			PageInfo<CodeGroupOutData> pageInfo = codeGroupService.list(pageNumber, pageSize, codegroupname,codegroupkey);
			return pageInfo;
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
	
	@RequestMapping("/add")
	public Json add(HttpServletRequest request, CodeGroupInData inData){
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
				codeGroupService.add(tokenToMap.get("id"), inData);
				json.setSuccess(true);
				json.setMsg("添加成功");
			}else {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) this.getSessinInfo(request, UtilConst.USER_SESSION);
			codeGroupService.add(sessinInfo.getId().toString(), inData);
			json.setSuccess(true);
			json.setMsg("添加成功");}
		} catch (Exception e) {
			json.setMsg("添加失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/update")
	public Json update(HttpServletRequest request, CodeGroupInData inData){
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
					codeGroupService.update(tokenToMap.get("id"), inData);
					json.setSuccess(true);
					json.setMsg("修改成功");
				}else {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) this.getSessinInfo(request, UtilConst.USER_SESSION);
			codeGroupService.update(sessinInfo.getId().toString(), inData);
			json.setSuccess(true);
			json.setMsg("修改成功");}
		} catch (Exception e) {
			json.setMsg("修改失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/delete")
	public Json delete(Long codegroupid){
		Json json = new Json();
		try {
			codeGroupService.delete(codegroupid);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			json.setMsg("删除失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	@RequestMapping("/getCodeGroupSelectList")
	public Json getCodeGroupSelectList(){
		Json json = new Json();
		try {
			List<CodeGroupOutData> list = codeGroupService.getCodeGroupSelectList();
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
}
