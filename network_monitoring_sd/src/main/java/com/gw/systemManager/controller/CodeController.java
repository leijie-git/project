package com.gw.systemManager.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.data.CodeInData;
import com.gw.systemManager.data.CodeOutData;
import com.gw.systemManager.service.CodeService;
import com.gw.util.UtilConst;

@RestController
@RequestMapping("/code")
public class CodeController extends BaseController{
	@Resource
	private CodeService codeService;
	
	private Logger logger = LoggerFactory.getLogger(CodeController.class);
	/**
	 * 根据codeGroupKey查询列表
	 * @param codeGroupKey
	 * @return
	 */
	@PostMapping("/getListByGroupKey")
	public Json getListByGroupKey(@RequestParam("codeGroupKey") String codeGroupKey){
		Json json = new Json();
		try {
			List<CodeOutData> list = codeService.getListByGroupKey(codeGroupKey);
			json.setObj(list);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 分页查询
	 * @param inData
	 * @return
	 */
	@RequestMapping("/list")
	public PageInfo<CodeOutData> list(CodeInData inData){
		try {
			PageInfo<CodeOutData> pageInfo = codeService.List(inData);
			return pageInfo;
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return null;
	}
	
	@RequestMapping("/add")
	public Json add(HttpServletRequest request, CodeInData inData){
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
				codeService.add(tokenToMap.get("id"), inData);
				json.setSuccess(true);
				json.setMsg("添加成功");
			}else {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) this.getSessinInfo(request, UtilConst.USER_SESSION);
			codeService.add(sessinInfo.getId().toString(), inData);
			json.setSuccess(true);
			json.setMsg("添加成功");}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			json.setMsg("添加失败");
		}
		return json;
	}
	
	@RequestMapping("/update")
	public Json update(HttpServletRequest request, CodeInData inData){
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
				codeService.update(tokenToMap.get("id"), inData);
				json.setSuccess(true);
				json.setMsg("更新成功");
			}else {
			GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) this.getSessinInfo(request, UtilConst.USER_SESSION);
			codeService.update(sessinInfo.getId().toString(), inData);
			json.setSuccess(true);
			json.setMsg("更新成功");}
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			json.setMsg("更新失败");
		}
		return json;
	}
	
	@RequestMapping("/delete")
	public Json delete(String codeid){
		Json json = new Json();
		try {
			codeService.delete(codeid);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			json.setMsg("删除失败");
		}
		return json;
	}
	
	@RequestMapping("/getCodeListByCodeGroupId")
	public Json getCodeListByCodeGroupId(String codeGroupId){
		Json json = new Json();
		try {
			List<CodeOutData> outData = codeService.getCodeListByCodeGroupId(codeGroupId);
			json.setObj(outData);
			json.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 从sd_transfer_device导入数据
	 * @return
	 */
	@RequestMapping("/importCodeDatas")
	public Json importCodeDatas(){
		Json json = new Json();
		try {
			codeService.importCodeDatas();
			json.setSuccess(true);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
		}
		return json;
	}
	

}
