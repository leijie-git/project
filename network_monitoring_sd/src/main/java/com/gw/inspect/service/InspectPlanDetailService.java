package com.gw.inspect.service;

import com.gw.inspect.data.InspectPlanDetailInData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.unit.data.PlanUserRoleLOutData;

public interface InspectPlanDetailService {

	/**
	 * 为巡查计划添加巡查点
	 * @param siteId
	 * @param planId
	 */
	void add(InspectPlanDetailInData inData) throws Exception;

	/**
	 * 删除计划中的巡查点
	 * @param siteId
	 * @throws Exception
	 */
	void remove(String siteId) throws Exception;

	/**
	 * 更新巡查计划详情表
	 * @param siteId
	 * @param taskUserId
	 */
	void update(InspectPlanDetailInData inData)throws Exception;

	/**
	 * 根据计划详情ID查询对应巡查人
	 * @param id
	 */
	PlanUserRoleLOutData getUserList(String id,String unitId,String userrole)throws Exception;

	/**
	 * 生成巡查任务
	 * @param inData
	 */
	void createTask(InspectTaskInData inData,Long userID)throws Exception;

	/**
	 * 批量生成任务
	 * @param inData
	 * @param id
	 */
	void createAllTask(String siteID,String planID, Long id) throws Exception;


}
