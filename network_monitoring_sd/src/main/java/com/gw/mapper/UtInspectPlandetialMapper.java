package com.gw.mapper;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtInspectPlandetial;

public interface UtInspectPlandetialMapper extends BaseMapper<UtInspectPlandetial> {

	/**
	 * 根据巡查计划详情id查询巡查人
	 * @param id
	 * @return
	 */
	String selectTaskUser(String id);

	/**
	 * 根据点位id获取计划详情
	 * @param siteid
	 */
	UtInspectPlandetial getDetailBySiteID(@Param("plandetialid") Long plandetialid);

	/**
	 * 根据计划id删除计划与点位关系表
	 * @param parseLong
	 */
	void deleteByPlanID(@Param("planid") Long parseLong);

	/**删除任务同时修改已生成状态
	 * @param id
	 * @return
	 */
	Integer updateStatusById(@Param("id") Long id);
}