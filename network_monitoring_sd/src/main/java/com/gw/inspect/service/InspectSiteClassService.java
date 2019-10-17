package com.gw.inspect.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.inspect.data.InspectSiteClassInData;
import com.gw.inspect.data.InspectSiteClassOutData;
/**
 * 巡查点位分类业务层接口
 * @author zfg
 *
 */
public interface InspectSiteClassService {

	/**
	 * 获取巡查点分类列表
	 * @param inData
	 * @return
	 */
	PageInfo<InspectSiteClassOutData> getList(InspectSiteClassInData inData)throws Exception;

	/**
	 * 添加巡查点分类
	 * @param inData
	 */
	void add(InspectSiteClassInData inData) throws Exception;

	/**
	 * 编辑巡查点分类
	 * @param inData
	 * @throws Exception
	 */
	void update(InspectSiteClassInData inData) throws Exception;

	/**
	 * 删除巡查点分类
	 * @param id
	 */
	void remove(String id) throws Exception;

	/**
	 * 获取巡查点分类列表
	 * @return 
	 * @throws Exception
	 */
	List<InspectSiteClassOutData> getArrayList(InspectSiteClassInData inData) throws Exception;

}
