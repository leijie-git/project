package com.gw.inspect.controller;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.inspect.data.InspectInData;
import com.gw.inspect.data.InspectSiteInData;
import com.gw.inspect.data.InspectSiteOutData;
import com.gw.inspect.service.InspectSiteService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.mapper.entity.UtInspectSiteOut;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.util.JwtUtil;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 巡查点位管理
 * @author zfg
 *
 */
@RestController
@RequestMapping("/inspectSite")
public class InspectSiteController extends BaseController {
	private Logger log = LoggerFactory.getLogger(InspectSiteController.class);

	@Resource
	private InspectSiteService inspectSiteService;

	
	/**
	 * 获取巡查点列表
	 * @param inData
	 * @return
	 */
	@GetMapping("/getList")
	public PageInfo<InspectSiteOutData> getList(InspectSiteInData inData){
		PageInfo<InspectSiteOutData> pager=null;
		try {
			pager = inspectSiteService.getList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	/**
	 * 获取巡查计划未拥有的点位
	 * @param inData
	 * @return
	 */
	@GetMapping("/getSiteList")
	public PageInfo<InspectSiteOutData> getSiteList(InspectSiteInData inData){
		PageInfo<InspectSiteOutData> pager=null;
		try {
			pager = inspectSiteService.getSiteList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pager;
	}
	
	/**
	 * 获取巡查点列表集合
	 * @return
	 */
	@GetMapping("/getArrayList")
	public Json getArrayList(InspectSiteInData inData){
		Json json = new Json();
		try {
			List<InspectSiteOutData> list  = inspectSiteService.getArrayList(inData);
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 添加巡查点
	 * @param inData
	 * @return
	 */
	@PostMapping("/add")
	public Json add(InspectSiteInData inData) {
		Json json = new Json();
		try {
			inspectSiteService.add(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 编辑巡查点
	 * @param inData
	 * @return
	 */
	@PostMapping("/update")
	public Json update(InspectSiteInData inData) {
		Json json = new Json();
		try {
			inspectSiteService.update(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除巡查点
	 * @param id
	 * @return
	 */
	@PostMapping("/remove")
	public Json update(@RequestParam("id") String id) {
		Json json = new Json();
		try {
			inspectSiteService.remove(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 根据计划寻找对应点位
	 * @param inData
	 * @return
	 */
	@PostMapping("/getPlanSite")
	public PageInfo<InspectSiteOutData> planSite(InspectSiteInData inData) {
		PageInfo<InspectSiteOutData> list= null;
		try {
			list = inspectSiteService.planSite(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return list;
	}
	
	/**
	 * 导入
	 *
	 * @param uploadExcel
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/importExcel", method = RequestMethod.POST)
	public Json importExcel(@RequestParam("uploadExcel") MultipartFile uploadExcel, HttpServletRequest request) {
		Json json = new Json();
		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
//获取token
		String authorize = request.getHeader("Authorization");
 	try {
			if(authorize!=null){
				long id=0;
				String Account=null;
				String UnitID=null;

//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				Account=tokenToMap.get("Account");
				UnitID=tokenToMap.get("UnitID");

				List<InspectInData> inspectInData = importExcel(uploadExcel, 0, InspectInData.class);
				inspectSiteService.importData(inspectInData, UnitID);
				json.setSuccess(true);
				json.setMsg("导入成功");
			}else {
			List<InspectInData> inspectInData = importExcel(uploadExcel, 0, InspectInData.class);
			inspectSiteService.importData(inspectInData, sessinInfo.getUnitId());
			json.setSuccess(true);
			json.setMsg("导入成功");}
		} catch (Exception e) {
			json.setSuccess(false);
			log.error(e.getMessage(), e);
			json.setMsg("导入失败");
		}
		return json;
	}



	/**
	 * 导入
	 * -查询所有点位的名称,位置,执行人,巡查频数,执行周期,周期内起止时间,
	 * 	同时根据计划ID判断点位是否已关联该计划(isPlaned),根据时间及是否关联排序
	 */
	@ResponseBody
	@RequestMapping(value = "/utInspectSiteOutList", method = RequestMethod.GET)
	public PageInfo<UtInspectSiteOut> utInspectSiteOutList(UtInspectSiteOut utInspectSiteOut) {


			PageInfo<UtInspectSiteOut> pager=null;

		try {
			pager =inspectSiteService.selectUtInspectSiteOutList(utInspectSiteOut);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}

		     return  pager;

	}

}