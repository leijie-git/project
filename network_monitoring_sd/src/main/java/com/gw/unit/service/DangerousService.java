package com.gw.unit.service;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.DangerousInData;
import com.gw.unit.data.DangerousOutData;

/**
 * 单位危险品业务层接口
 * @author zfg
 *
 */
public interface DangerousService {

	/**
	 * 获取单位危险品列表
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<DangerousOutData> getList(DangerousInData inData) throws Exception;

	/**
	 * 添加单位危险品
	 * @param inData
	 */
	void add(DangerousInData inData) throws Exception;

	/**
	 * 更新单位危险品信息
	 * @param inData
	 */
	void update(DangerousInData inData) throws Exception;

	/**
	 * 删除单位危险品
	 * @param id
	 */
	void remove(String id) throws Exception;

}
