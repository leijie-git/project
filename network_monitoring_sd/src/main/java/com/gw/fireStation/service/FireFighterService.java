package com.gw.fireStation.service;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaInData;
import com.gw.fireStation.data.FireFighterInData;
import com.gw.fireStation.data.FireFighterOutData;

/**
 * 消防人员业务层接口
 * @author zfg
 *
 */
public interface FireFighterService {

	
	/**
	 * 获取消防人员列表
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<FireFighterOutData> getList(BuildAreaInData inData) throws Exception;

	/**
	 * 添加消防人员
	 * @param inData
	 * @throws Exception 
	 */
	void add(FireFighterInData inData) throws Exception;

	/**
	 * 编辑消防人员
	 * @param inData
	 * @throws Exception 
	 */
	void update(FireFighterInData inData) throws Exception;

	/**
	 * 删除消防人员
	 * @param id
	 * @throws Exception 
	 */
	void remove(String id) throws Exception;
	

}
