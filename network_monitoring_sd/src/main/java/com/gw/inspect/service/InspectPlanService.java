package com.gw.inspect.service;

import com.github.pagehelper.PageInfo;
import com.gw.inspect.data.InspectPlanInData;
import com.gw.inspect.data.InspectPlanOutData;

/**
 * 巡查计划业务层接口
 * @author zfg
 *
 */
public interface InspectPlanService {

	/**
	 * 获取巡查计划列表
	 * @param inData
	 * @return
	 */
	PageInfo<InspectPlanOutData> getList(InspectPlanInData inData)throws Exception;

	/**
	 * 添加巡查计划
	 * @param inData
	 * @throws Exception
	 */
	String add(InspectPlanInData inData) throws Exception;

	/**
	 * 编辑巡查计划
	 * @param inData
	 * @throws Exception
	 */
	void update(InspectPlanInData inData) throws Exception;

	/**
	 * 删除巡查计划
	 * @param id
	 * @throws Exception
	 */
	void remove(String id) throws Exception;

	/**
	 * 查询计划关联的点位
	 * @param id
	 * @throws Exception
	 */
	void planSite(String id) throws Exception;

	/**
	 * 查询单位id
	 * @param id
	 * @return
	 */
	String getUnitID(Long id) throws Exception;

}
