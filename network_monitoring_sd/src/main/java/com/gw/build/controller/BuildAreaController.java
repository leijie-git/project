package com.gw.build.controller;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaInData;
import com.gw.build.data.BuildAreaOutData;
import com.gw.build.data.BuildOutData;
import com.gw.build.service.BuildAreaService;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@Api
@RestController
@RequestMapping("/buildArea")
public class BuildAreaController {
	private Logger log = LoggerFactory.getLogger(BuildAreaController.class);
	@Resource
	private BuildAreaService buildAreaService;
	
	@ApiOperation(value = "获取建筑物区域列表", notes = "获取建筑物区域列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNumber",value = "页码",paramType = "query"),
			@ApiImplicitParam(name = "pageSize",value = "单页条数",paramType = "query"),
			@ApiImplicitParam(name = "buildAreaName",value = "区域名称（模糊匹配）",paramType = "query"),
			@ApiImplicitParam(name = "buildAreaSite",value = "区域地址（模糊匹配）",paramType = "query"),
			@ApiImplicitParam(name = "buildID",value = "建筑ID",paramType = "query"),
			@ApiImplicitParam(name = "unitName",value = "单位名称（模糊匹配）",paramType = "query"),
	})
	@GetMapping("/getList")
	public PageInfo<BuildAreaOutData> getList(HttpServletRequest request,BuildAreaInData inData){
		PageInfo<BuildAreaOutData> pager=null;
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
			if(UnitID!=null){
				inData.setUnitID(UnitID);}
			pager = buildAreaService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}

	@ApiOperation(value = "获取区域列表", notes = "获取区域列表",httpMethod = "GET")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "pageNumber",value = "页码",paramType = "query"),
			@ApiImplicitParam(name = "pageSize",value = "单页条数",paramType = "query"),
			@ApiImplicitParam(name = "buildAreaName",value = "区域名称（模糊匹配）",paramType = "query"),
			@ApiImplicitParam(name = "buildAreaSite",value = "区域地址（模糊匹配）",paramType = "query"),
			@ApiImplicitParam(name = "buildID",value = "建筑ID",paramType = "query"),
			@ApiImplicitParam(name = "unitName",value = "单位名称（模糊匹配）",paramType = "query"),
	})
	@GetMapping("/getArrayList")
	public Json getArratList(BuildAreaInData inData) {
		Json json = new Json();
		List<BuildAreaOutData> list;
		try {
			list = buildAreaService.getArrayList(inData);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}

    @ApiOperation(value = "添加建筑物区域", notes = "添加建筑物区域",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UnitID",value = "单位名称",paramType = "query"),
            @ApiImplicitParam(name = "BuildAreaName",value = "区域名称",paramType = "query"),
            @ApiImplicitParam(name = "BuildAreaSite",value = "区域名称（模糊匹配）",paramType = "query"),
            @ApiImplicitParam(name = "BgWidth",value = "图片宽带",paramType = "query"),
            @ApiImplicitParam(name = "BgHeight",value = "图片高度",paramType = "query"),
            @ApiImplicitParam(name = "BuildAreabg",value = "建筑图",paramType = "query"),
            @ApiImplicitParam(name = "BuildID",value = "建筑ID",paramType = "query"),
    })
	@PostMapping("/add")
	public Json add(BuildAreaInData inData) {
		Json json = new Json();
		try {
			buildAreaService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
    @ApiOperation(value = "编辑建筑物区域", notes = "编辑建筑物区域",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "UnitID",value = "单位id",paramType = "query"),
            @ApiImplicitParam(name = "BuildAreaName",value = "区域名称",paramType = "query"),
            @ApiImplicitParam(name = "BuildAreaSite",value = "区域地址",paramType = "query"),
            @ApiImplicitParam(name = "BgWidth",value = "图片宽带",paramType = "query"),
            @ApiImplicitParam(name = "BgHeight",value = "图片高度",paramType = "query"),
            @ApiImplicitParam(name = "BuildAreabg",value = "建筑图",paramType = "query"),
            @ApiImplicitParam(name = "BuildID",value = "建筑ID",paramType = "query"),
    })
	@PostMapping("/update")
	public Json update(BuildAreaInData inData) {
		Json json = new Json();
		try {
			buildAreaService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 删除建筑物区域
	 * @param id
	 * @return
	 */
    @ApiOperation(value = "删除建筑物区域", notes = "删除建筑物区域",httpMethod = "POST")
    @ApiImplicitParam(name = "id",value = "区域ID",paramType = "query")
	@PostMapping("/remove")
	public Json remove(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			buildAreaService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
    @ApiOperation(value = "导出区域功能", notes = "导出区域功能",httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BuildAreaName",value = "区域名称（模糊匹配）",paramType = "query"),
            @ApiImplicitParam(name = "BuildAreaSite",value = "区域地址（模糊匹配）",paramType = "query"),
            @ApiImplicitParam(name = "unitName",value = "单位名称（模糊匹配）",paramType = "query"),
    })
	@RequestMapping("/exportBuildArea")
	public void exportBuildArea(HttpServletResponse response, BuildAreaInData inData){
		try {
			buildAreaService.exportBuildArea(response, inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}
}
