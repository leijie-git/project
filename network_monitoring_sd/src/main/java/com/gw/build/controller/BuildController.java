package com.gw.build.controller;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildInData;
import com.gw.build.data.BuildOutData;
import com.gw.build.service.BuildService;
import com.gw.common.Json;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api
@RestController
@RequestMapping("/build")
public class BuildController {
	private Logger log = LoggerFactory.getLogger(BuildController.class);
	@Resource 
	private BuildService buildService;
	
	/**
	 * 获取建筑物列表
	 * @param inData
	 * @return
	 */
	@ApiOperation(value = "获取建筑物列表", notes = "获取建筑物列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNumber",value = "页码",paramType = "query"),
			@ApiImplicitParam(name = "pageSize",value = "单页条数",paramType = "query"),
			@ApiImplicitParam(name = "buildingName",value = "建筑名称（模糊匹配）",paramType = "query"),
			@ApiImplicitParam(name = "BuildingType",value = "建筑类型",paramType = "query"),
			@ApiImplicitParam(name = "unitName",value = "单位名称（模糊匹配）",paramType = "query"),
			@ApiImplicitParam(name = "UnitID",value = "单位ID",paramType = "query"),
	})
	@GetMapping("/getList")
	public PageInfo<BuildOutData> getBuildList(HttpServletRequest request, BuildInData inData){
		//获取token
		String authorize = request.getHeader("Authorization");
		long id=0;
		String Account=null;
		String UnitID=null;
		PageInfo<BuildOutData> pager=null;
		//解析token
		Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
		id=Long.parseLong(tokenToMap.get("id"));
		Account=tokenToMap.get("Account");
		UnitID=tokenToMap.get("UnitID");
		System.out.println("UnitID"+tokenToMap.get("UnitID"));
		if(UnitID!=null){
		inData.setUnitID(UnitID);}
		try {
			pager = buildService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	
	@ApiOperation(value = "获取建筑物列表（集合）", notes = "获取建筑物列表（集合）",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNumber",value = "页码",paramType = "query"),
			@ApiImplicitParam(name = "pageSize",value = "单页条数",paramType = "query"),
			@ApiImplicitParam(name = "buildingName",value = "建筑名称（模糊匹配）",paramType = "query"),
			@ApiImplicitParam(name = "BuildingType",value = "建筑类型",paramType = "query"),
			@ApiImplicitParam(name = "UnitID",value = "建筑ID",paramType = "query"),
			@ApiImplicitParam(name = "unitName",value = "单位名称（模糊匹配）",paramType = "query"),
	})
	@GetMapping("/getArrayList")
	public Json getArrayList(BuildInData inData) {
		Json json = new Json();
		List<BuildOutData> list=null;
		try {
			list = buildService.getArrayList(inData);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 新增建筑物
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(BuildInData inData) {
		Json json = new Json();
		try {
			buildService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 编辑建筑物
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(BuildInData inData) {
		Json json = new Json();
		try {
			buildService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除建筑物
	 * @param inData
	 * @return
	 */
	@PostMapping("/remove")
	public Json remove(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			buildService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
