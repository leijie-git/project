package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.inspect.data.DownLoadTaskInData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.inspect.data.InspectTaskOutData;
import com.gw.mapper.entity.UtInspectTask;
import com.gw.wechat.data.PhoneTaskDetailOutData;
import com.gw.wechat.data.SiteClassDetailOutData;
import com.gw.wechat.data.TaskSheetOutData;

public interface UtInspectTaskMapper extends BaseMapper<UtInspectTask> {

	/**
	 * 获取某个计划中的任务列表
	 * @param inData
	 * @return
	 */
	List<InspectTaskOutData> getList(InspectTaskInData inData);

	/**
	 * 根据人员获取任务列表
	 * @param inData
	 * @return
	 */
	List<PhoneTaskDetailOutData> getUserTaskList(DownLoadTaskInData inData);

	/**
	 * 根据点位id获取检查项
	 * @param siteID
	 * @return
	 */
	List<SiteClassDetailOutData> getSiteClassDetail(@Param("id") String id);

	/**
	 * 查询自己转接单的任务列表
	 * @param userId
	 * @return
	 */
	List<PhoneTaskDetailOutData> getReceiveTaskList(@Param("userID") String userId,@Param("inspectcycleType") String inspectcycleType,@Param("type") String type);

	/**
	 * 获取当前月的所有任务
	 * @return
	 */
	List<UtInspectTask> getNowMouthTask();
	
	/**
	 * 获取根据时间单位筛选任务
	 * @return
	 */
	List<UtInspectTask> getMouthTask(@Param("startTime") String startTime,@Param("endTime") String endTime,@Param("unitID")String unitID);

	/**
	 * 获取转接单任务数量
	 * @param userId
	 * @return
	 */
	TaskSheetOutData getTaskCount(@Param("userId") String userId);

	UtInspectTask getPlandetialidById(@Param("id") Long id);


}