package com.gw.inspect.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.inspect.data.InspectInData;
import com.gw.inspect.data.InspectSiteInData;
import com.gw.inspect.data.InspectSiteOutData;
import com.gw.mapper.entity.UtInspectSiteOut;
import com.gw.mapper.entity.UtInspectSite;

/**
 * 巡查点管理业务层接口
 * @author zfg
 *
 */
public interface InspectSiteService {

	/**
	 * 获取巡查点列表
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<InspectSiteOutData> getList(InspectSiteInData inData) throws Exception;

	/**
	 * 添加巡查点
	 * @param inData
	 */
	void add(InspectSiteInData inData) throws Exception;

	/**
	 * 编辑巡查点
	 * @param inData
	 */
	void update(InspectSiteInData inData) throws Exception;

	/**
	 * 删除巡查点
	 * @param id
	 */
	void remove(String id) throws Exception;

	/**
	 * 获取巡查点列表集合
	 * @param inData
	 * @return
	 */
	List<InspectSiteOutData> getArrayList(InspectSiteInData inData) throws Exception;

	/**
	 * 获取计划对应的点位
	 * @param id
	 * @return
	 */
	PageInfo<InspectSiteOutData> planSite(InspectSiteInData inData) throws Exception ;

	/**
	 * 获取计划为包含的点位
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<InspectSiteOutData> getSiteList(InspectSiteInData inData) throws Exception;

	/**
	 * 导入
	 *
	 * @param interfaceImportDatas
	 * @param unitId
	 * @param
	 * @throws Exception
	 */
	void importData(List<InspectInData> interfaceImportDatas,String unitId) throws Exception;
/**
 * 导入
 * -查询所有点位的名称,位置,执行人,巡查频数,执行周期,周期内起止时间,
 * 	同时根据计划ID判断点位是否已关联该计划(isPlaned),根据时间及是否关联排序
 */

	PageInfo<UtInspectSiteOut> selectUtInspectSiteOutList(UtInspectSiteOut utInspectSiteOut);

	 boolean save(List autoBatcyList,String dao);

}
