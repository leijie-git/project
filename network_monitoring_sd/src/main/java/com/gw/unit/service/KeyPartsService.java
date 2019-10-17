package com.gw.unit.service;


import com.github.pagehelper.PageInfo;
import com.gw.unit.data.KeyPartsInData;
import com.gw.unit.data.KeyPartsOutData;

/**
 * 重点单位业务层接口
 * @author zfg
 *
 */
public interface KeyPartsService {

	/**
	 * 获取重点单位列表
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<KeyPartsOutData> getList(KeyPartsInData inData) throws Exception;

	/**
	 * 添加重点单位
	 * @param inData
	 * @throws Exception
	 */
	void add(KeyPartsInData inData) throws Exception;

	/**
	 * 更新重点单位
	 * @param inData
	 * @throws Exception
	 */
	void update(KeyPartsInData inData) throws Exception;

	/**
	 * 删除重点单位
	 * @param id
	 * @throws Exception
	 */
	void remove(String id) throws Exception;

	
}
