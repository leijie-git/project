package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtUserInformation;
import com.gw.unit.data.UserExperienceInData;
import com.gw.unit.data.UtUserInformationOutData;

public interface UtUserInformationMapper extends BaseMapper<UtUserInformation> {

	/**
	 * 行业人员列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<UtUserInformationOutData> getUserInformationList(UserExperienceInData inData) throws Exception;
}