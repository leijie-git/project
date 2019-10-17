package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.front.lookup.data.FrontUserExperienceOutData;
import com.gw.mapper.entity.UtUnitUserExperience;
import com.gw.unit.data.UserExperienceInData;
import com.gw.unit.data.UserExperienceOutData;

public interface UtUnitUserExperienceMapper extends BaseMapper<UtUnitUserExperience> {

	/**
	 * 人员工作经历列表
	 * 
	 * @param companyName
	 * @return
	 * @throws Exception
	 */
	List<UserExperienceOutData> getUserExperienceList(UserExperienceInData inData) throws Exception;

	/**
	 * 人员工作经历
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	List<FrontUserExperienceOutData> getUserExperience(String infoId) throws Exception;

	/**
	 * 删除工作经历
	 * 
	 * @param id
	 * @throws Exception
	 */
	void deleteByUserId(String id) throws Exception;
}