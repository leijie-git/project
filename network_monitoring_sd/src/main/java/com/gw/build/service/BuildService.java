package com.gw.build.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildInData;
import com.gw.build.data.BuildOutData;

/**
 * 建筑物业务层接口
 * @author zfg
 *
 */
public interface BuildService {

	/**
	 * 获取建筑物列表
	 * @param inData
	 * @return
	 */
	PageInfo<BuildOutData> getList(BuildInData inData)throws Exception;

	/**
	 * 获取建筑物列表（集合）
	 */
	List<BuildOutData> getArrayList(BuildInData inData) throws Exception;
	
	/**
	 * 添加建筑物
	 * @param inData
	 */
	void add(BuildInData inData) throws Exception;

	/**
	 * 编辑建筑物
	 * @param inData
	 * @throws Exception
	 */
	void update(BuildInData inData) throws Exception;

	/**
	 * 删除建筑物
	 * @param id
	 * @throws Exception
	 */
	void remove(String id) throws Exception;

}
