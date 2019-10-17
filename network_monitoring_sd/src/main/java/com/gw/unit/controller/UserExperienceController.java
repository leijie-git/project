package com.gw.unit.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.unit.data.UserExperienceInData;
import com.gw.unit.data.UserExperienceOutData;
import com.gw.unit.data.UtUserInformationOutData;
import com.gw.unit.service.UserExperienceService;

@Controller
@RequestMapping("/userExperience")
@ResponseBody
public class UserExperienceController {

	@Autowired
	private UserExperienceService userExperienceService;

	private Logger log = LoggerFactory.getLogger(UserExperienceController.class);

	/**
	 * 分页获取行业人员列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/pageList")
	public PageInfo<UtUserInformationOutData> pageList(UserExperienceInData inData) {
		PageInfo<UtUserInformationOutData> pageList = null;
		try {
			pageList = userExperienceService.pageList(inData);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pageList;
	}

	/**
	 * 更新行业人员
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/update")
	public Json updateUserInformation(UtUserInformationOutData inData) {
		Json json = new Json();
		try {
			userExperienceService.updateUserInformation(inData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除人员经历
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete")
	public Json delete(String id) {
		Json json = new Json();
		try {
			userExperienceService.delete(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 添加人员经历
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/add")
	public Json add(UtUserInformationOutData inData) {
		Json json = new Json();
		try {
			userExperienceService.add(inData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 更新人员经历
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/updateUserExperience")
	public Json updateUserExperience(UserExperienceInData inData) {
		Json json = new Json();
		try {
			userExperienceService.updateUserExperience(inData);
			json.setSuccess(true);
			json.setMsg("更新成功");
		} catch (ServiceException e) {
			json.setMsg(e.getMessage());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 删除人员经历
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("/deleteUserExperience")
	public Json deleteUserExperience(String id) {
		Json json = new Json();
		try {
			userExperienceService.deleteUserExperience(id);
			json.setSuccess(true);
			json.setMsg("删除成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 添加人员经历
	 * 
	 * @param inData
	 * @return
	 */
	@RequestMapping("/addUserExperience")
	public Json addUserExperience(UserExperienceInData inData) {
		Json json = new Json();
		try {
			userExperienceService.addUserExperience(inData);
			json.setSuccess(true);
			json.setMsg("添加成功");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

	/**
	 * 查询人员工作经历列表
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping("/getExperienceList")
	public Json getExperienceList(String userId) {
		Json json = new Json();
		try {
			List<UserExperienceOutData> outData = userExperienceService.getExperienceList(userId);
			json.setSuccess(true);
			json.setObj(outData);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return json;
	}

}
