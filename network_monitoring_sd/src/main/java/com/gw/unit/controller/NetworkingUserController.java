package com.gw.unit.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
import com.gw.myAnnotation.PassToken;
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
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.unit.data.BaseInfoSelectOutData;
import com.gw.unit.data.NetworkingUserInData;
import com.gw.unit.data.NetworkingUserOutData;
import com.gw.unit.service.NetworkingUserService;
import com.gw.util.UtilConst;

@Controller
@RequestMapping("/networkingUser")
@ResponseBody
public class NetworkingUserController extends BaseController{

	@Autowired
	private NetworkingUserService networkingUserService;

	private Logger log = LoggerFactory.getLogger(NetworkingUserController.class);

	/**
	 * 分页查询
	 * @param request
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/list")
	public PageInfo<NetworkingUserOutData> list(HttpServletRequest request, NetworkingUserInData inData) throws Exception{
		//获取token
		String authorize = request.getHeader("Authorization");
		try {
			if(authorize!=null){
				long id=0;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				PageInfo<NetworkingUserOutData> list = networkingUserService.list(id, inData);
				return list;
			}else {
				GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
				PageInfo<NetworkingUserOutData> list = networkingUserService.list(sessinInfo.getId(), inData);
				return list;
			}



		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}

	/**
	 * 为下拉框准备数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/typeSelect")
	public Json typeSelect() {
		Json json = new Json();
		try {
			List<BaseInfoSelectOutData> list = networkingUserService.selectUnitnameAndId();
			json.setSuccess(true);
			json.setObj(list);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 添加联网单位人员
	 * @param inData
	 * @return
	 */
	@RequestMapping("/add")
	public Json add(HttpServletRequest request, NetworkingUserInData inData) {
		Json json = new Json();
		//获取token
		String authorize = request.getHeader("Authorization");
		try {
			if(authorize!=null){
				long id=0;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				networkingUserService.addNetworkingUser(id,inData);
				json.setSuccess(true);
				json.setMsg("添加成功");
			}else {
				GetSessionInfoOutData userSession = (GetSessionInfoOutData)getSessinInfo(request,UtilConst.USER_SESSION);
				networkingUserService.addNetworkingUser(userSession.getId(),inData);
				json.setSuccess(true);
				json.setMsg("添加成功");
			}

		} catch (ServiceException se) {
			json.setMsg(se.getMessage());
			log.error(se.getMessage(),se);
		} catch (Exception e) {
			json.setMsg("添加失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("/update")
	public Json update(NetworkingUserInData inData,HttpServletRequest request){
		Json json = new Json();
		//获取token
		String authorize = request.getHeader("Authorization");
       try {
			if(authorize!=null){
				long id=0;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				networkingUserService.updateNetworkingUser(id,inData);
			json.setSuccess(true);
			json.setMsg("修改成功");}
			else {
				GetSessionInfoOutData userSession = (GetSessionInfoOutData)getSessinInfo(request,UtilConst.USER_SESSION);
				networkingUserService.updateNetworkingUser(userSession.getId(),inData);
				json.setSuccess(true);
				json.setMsg("修改成功");
			}
		} catch (ServiceException se) {
			json.setMsg(se.getMessage());
			log.error(se.getMessage(),se);
		} catch (Exception e) {
			json.setMsg("修改失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}

	@RequestMapping("/delete")
	public Json delete(String id){
		Json json = new Json();
		try {
			networkingUserService.deleteNetworkingUser(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			json.setMsg("删除失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}

	/**
	 * 重置密码
	 * @param id
	 * @return
	 */
	@RequestMapping("/resetPas")
	public Json resetPas(String id){
		Json json = new Json();
		try {
			networkingUserService.resetPas(id);
			json.setSuccess(true);
			json.setMsg("重置成功");
		} catch (Exception e) {
			json.setMsg("重置失败");
			log.error(e.getMessage(),e);
		}
		return json;
	}
}
