package com.gw.unit.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.UserExperienceInData;
import com.gw.unit.data.UserExperienceOutData;
import com.gw.unit.data.UtUserInformationOutData;

/**
 * 人员经历服务层
 * 
 * @author SY
 * @data 2018年9月20日
 */
public interface UserExperienceService {

	/**
	 * 行业人员列表
	 * 
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageInfo<UtUserInformationOutData> pageList(UserExperienceInData inData) throws Exception;

	/**
	 * 添加人员经历
	 * 
	 * @param inData
	 */
	void addUserExperience(UserExperienceInData inData) throws Exception;

	/**
	 * 更新人员经历信息
	 * 
	 * @param inData
	 */
	void updateUserExperience(UserExperienceInData inData) throws Exception;

	/**
	 * 删除人员经历
	 * 
	 * @param id
	 */
	void deleteUserExperience(String id) throws Exception;

	/**
	 * 更新行业人员
	 * 
	 * @param inData
	 * @throws Exception
	 */
	void updateUserInformation(UtUserInformationOutData inData) throws Exception;

	/**
	 * 删除行业人员
	 * 
	 * @param id
	 * @throws Exception
	 */
	void delete(String id) throws Exception;

	/**
	 * 新增行业人员
	 * 
	 * @param inData
	 * @throws Exception
	 */
	void add(UtUserInformationOutData inData) throws Exception;

	/**
	 * 查询人员工作经历列表
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<UserExperienceOutData> getExperienceList(String userId) throws Exception;
}
