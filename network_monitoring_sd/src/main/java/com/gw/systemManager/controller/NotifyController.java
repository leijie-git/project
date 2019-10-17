package com.gw.systemManager.controller;

import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.data.NotifyInData;
import com.gw.systemManager.data.NotifyOutData;
import com.gw.systemManager.data.NotifyRelUserOutData;
import com.gw.systemManager.service.NotifyService;
import com.gw.util.UtilConst;

import java.util.Map;

/**
 * 通知Controller层
 * @author SY
 * @data 2018年9月21日
 */

@RestController
@RequestMapping("/notify")
public class NotifyController extends BaseController{
	
	@Autowired
	private NotifyService notifyService;
	
	private Logger log = LoggerFactory.getLogger(NotifyController.class);
	
	/**
	 * 获取通知分页数据
	 * @param pageNumber
	 * @param pageSize
	 * @param title
	 * @return
	 */
	@RequestMapping("/pageList")
	public PageInfo<NotifyOutData> pageList(NotifyInData inData){
		PageInfo<NotifyOutData> pageInfo = null;
		try {
			pageInfo = notifyService.pageList(inData);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 新增通知
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addNotify")
	public Json addNotify(HttpServletRequest request, NotifyInData inData){
		Json json = new Json();
		String authorize = request.getHeader("Authorization");

		try {
			//获取token
			if(authorize!=null){
				long id=0;
				PageInfo<BuildOutData> pager=null;
				//解析token
				Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
				id=Long.parseLong(tokenToMap.get("id"));
				notifyService.addNotify(id,inData);
				json.setSuccess(true);
			}else {
				GetSessionInfoOutData sessinInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
			notifyService.addNotify(sessinInfo.getId(),inData);
			json.setSuccess(true);}
		}catch(ServiceException se){
			json.setMsg(se.getMessage());
			log.error(se.getMessage(),se);
		}catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	/**
	 * 更新通知
	 * @param inData
	 * @return
	 */
	@RequestMapping("/updateNotify")
	public Json updateNotify(NotifyInData inData){
		Json json = new Json();
		try {
			notifyService.updateNotify(inData);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除通知
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteNotify")
	public Json deleteNotify(String notifyId){
		Json json = new Json();
		try {
			notifyService.deleteNotify(notifyId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 获得收件人下拉框
	 * @return
	 */
	@RequestMapping("/getAllUserSelect")
	public PageInfo<NotifyRelUserOutData> getAllUserSelect(Integer pageNumber, Integer pageSize,String userIds){
		PageInfo<NotifyRelUserOutData> pageInfo = null;
		try {
			pageInfo = notifyService.getAllUserSelect(pageNumber,pageSize,userIds);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 获得和通知关联的用户
	 * @param pageNumber
	 * @param pageSize
	 * @param id
	 * @return
	 */
	@PostMapping("/getNotifyRelUser")
	public PageInfo<NotifyRelUserOutData> getNotifyRelUser(Integer pageNumber, Integer pageSize, String id, String userIds){
		PageInfo<NotifyRelUserOutData> pageInfo = null;
		try {
			pageInfo = notifyService.getNotifyRelUser(pageNumber, pageSize, id, userIds);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 获得未关联通知的用户
	 * @param pageNumber
	 * @param pageSize
	 * @param id
	 * @param userIds
	 * @return
	 */
	@PostMapping("/getAllUserDisSelect")
	public PageInfo<NotifyRelUserOutData> getAllUserDisSelect(Integer pageNumber, Integer pageSize, String id, String userIds){
		PageInfo<NotifyRelUserOutData> pageInfo = null;
		try {
			pageInfo = notifyService.getAllUserDisSelect(pageNumber, pageSize, id, userIds);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return pageInfo;
	}
	
	/**
	 * 添加新绑定的用户
	 * @return
	 */
	@RequestMapping("/addNotifyRelUser")
	public Json addNotifyRelUser(String id, String receiver){
		Json json = new Json();
		try {
			notifyService.addNotifyRelUser(id, receiver);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	
	/**
	 * 删除绑定通知的用户
	 * @param notifyId
	 * @param userId
	 * @return
	 */
	@RequestMapping("/deleteNotifyRelUser")
	public Json deleteNotifyRelUser(String notifyId,String userId){
		Json json = new Json();
		try {
			notifyService.deleteNotifyRelUser(notifyId, userId);
			json.setSuccess(true);
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return json;
	}
	

}
