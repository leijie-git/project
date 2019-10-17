package com.gw.mapper;

import java.util.List;
import java.util.Map;

import com.gw.common.BaseMapper;
import com.gw.inspect.data.InspectPlanInData;
import com.gw.inspect.data.InspectPlanOutData;
import com.gw.mapper.entity.UtInspectPlan;

public interface UtInspectPlanMapper extends BaseMapper<UtInspectPlan> {

	List<InspectPlanOutData> getList(InspectPlanInData inData) throws Exception;
//	<!--根据执行人查出执行人id-->
	String getDefaultUserIDByDefaultUserName(String name);

}