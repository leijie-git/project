package com.gw.systemManager.controller;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildOutData;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.myAnnotation.PassToken;
import com.gw.systemManager.data.SysResourceIndata;
import com.gw.systemManager.data.SysResourceOutData;
import com.gw.systemManager.service.SysResourceService;
import com.gw.util.JwtUtil;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(value = "/resource")
public class SysResourceController extends BaseController {

	private Logger log = LoggerFactory.getLogger(SysResourceController.class);

	@Autowired
	private SysResourceService sysResourceService;

	/**
	 * 资源列表
	 *
	 * @param name
	 * @param status
	 * @param type   前后台类型 0 后台 1 前台
	 * @return
	 */
	@PassToken
	@RequestMapping("/resourceList")
	public Json resourceList (String name,String status,String type) {
		Json json = new Json();
		try {
			json.setObj(sysResourceService.getAllResource(name,status,type));
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return json;
	}


//	@RequestMapping("/resourceList")
//	public Json resourceList(HttpServletRequest request, SysResourceIndata indata) {
//		Json json = new Json();
//		try {
//			json.setObj(sysResourceService.resourceList(indata));
//			json.setSuccess(true);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
//		return json;
//	}

	/**
	 * 新增资源
	 *
	 * @param request
	 * @param sysResource
	 * @return
	 */
	@PostMapping("/addResource")
	public Json addResource(HttpServletRequest request, SysResourceIndata sysResource) {
		Json json = new Json();
		try {
			sysResourceService.addSystemResource(sysResource);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return json;
	}

	/**
	 * 编辑资源
	 *
	 * @param request
	 * @return
	 */
	@GetMapping("/editResource")
	public Json editResource(HttpServletRequest request, @RequestParam("id") String id) {
		Json json = new Json();
		try {
			SysResourceOutData resource = sysResourceService.editSystemResource(id);
			json.setObj(resource);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 删除资源
	 *
	 * @param id
	 * @return
	 */
	@GetMapping("/deleteResource")
	public Json deleteResource(@RequestParam("id") Long id) {
		Json json = new Json();
		try {
			sysResourceService.deleteSystemResource(id);
			json.setSuccess(true);
		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 更新资源
	 *
	 * @param request
	 * @param inData
	 * @return
	 */
	@PostMapping("/updateResource")
	public Json updateResource(HttpServletRequest request, SysResourceIndata inData) {

		Json json = new Json();
		//获取token
		String authorize = request.getHeader("Authorization");
		GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
		try {
			if (authorize != null) {
				long id = 0;
				String Account = null;
				String UnitID = null;
				PageInfo<BuildOutData> pager = null;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id = Long.parseLong(tokenToMap.get("id"));
				Account = tokenToMap.get("Account");
				UnitID = tokenToMap.get("UnitID");
				sysResourceService.updateSystemResource(inData, id);
				json.setSuccess(true);
			} else {
				sysResourceService.updateSystemResource(inData, sessinInfo.getId());
				json.setSuccess(true);
			}

		} catch (Exception e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage());
			e.printStackTrace();
		}
		return json;
	}

	/**
	 * 初始化用户拥有的权限列表
	 *
	 * @param request
	 * @return
	 */

	@RequestMapping("/listResource")
	public Json listResource(HttpServletRequest request, String type) {
		String authorize = request.getHeader("Authorization");
		long id = 0;
		String Account = null;
		String UnitID = null;
		if (authorize != null) {

			//解析token
			Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
			id = Long.parseLong(tokenToMap.get("id"));
			Account = tokenToMap.get("Account");
			UnitID = tokenToMap.get("UnitID");
			Json json = new Json();
			try {
				List<SysResourceOutData> resourceList = sysResourceService.listResourcesByUserId(id,
						Account, type);
				json.setSuccess(true);
				json.setObj(resourceList);
				json.setMsg("走的token");
			} catch (ServiceException e) {
				json.setMsg(e.getMessage());
				log.error(e.getMessage());
			} catch (Exception e) {
				log.error(e.getMessage());
			}
			return json;
		}


		Json json = new Json();
		GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
		try {

			List<SysResourceOutData> resourceList = sysResourceService.listResourcesByUserId(sessionInfo.getId(),
					sessionInfo.getAccount(), type);
			json.setSuccess(true);
			json.setObj(resourceList);
			json.setMsg("走的session");
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return json;
	}

	/**
	 * 初始化前台用户拥有的权限列表
	 *
	 * @param request
	 * @return
	 */
	@PassToken
	@RequestMapping("/listStageResource")
	public Json listStageResource(HttpServletRequest request, String type) {
		Json json = new Json();
		FrontUnitUserOutData sessinInfo = (FrontUnitUserOutData) getSessinInfo(request, UtilConst.FRONT_USER_SESSION);
		try {
			List<SysResourceOutData> resourceList = sysResourceService.listStageResourcesByUserId(Long.valueOf(sessinInfo.getId()),
					sessinInfo.getAccount(), type);
			json.setSuccess(true);
			json.setObj(resourceList);
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
			log.error(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return json;
	}
}
