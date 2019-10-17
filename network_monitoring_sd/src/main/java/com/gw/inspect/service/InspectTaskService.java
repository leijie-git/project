package com.gw.inspect.service;

import com.github.pagehelper.PageInfo;
import com.gw.inspect.data.DownLoadTaskInData;
import com.gw.inspect.data.InspectTaskInData;
import com.gw.inspect.data.InspectTaskOutData;

public interface InspectTaskService {

	/**
	 * 分页查询巡查任务列表
	 * @param inData
	 * @return
	 */
	PageInfo<InspectTaskOutData> getList(InspectTaskInData inData) throws Exception;

	/**
	 * 删除任务
	 * @param id
	 */
	void remove(String id) throws Exception;

	/**
	 * 根据人员下载巡查任务
	 * @param inData
	 * @throws Exception
	 */
	void getUserTask(DownLoadTaskInData inData) throws Exception;


}
