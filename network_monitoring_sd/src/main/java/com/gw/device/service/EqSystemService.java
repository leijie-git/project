package com.gw.device.service;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.EqSystemInData;
import com.gw.device.data.EqSystemOutData;

public interface EqSystemService {
	
	/**
	 * 获取消防系统分页数据
	 * @param eqsystemname
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageInfo<EqSystemOutData> pageList(String eqsystemname, Integer pageNumber, Integer pageSize) throws Exception;
	
	/**
	 * 新增消防系统
	 * @param inData
	 */
	void addEqSystem(EqSystemInData inData) throws Exception;
	
	/**
	 * 更新消防系统
	 * @param inData
	 */
	void updateEqSystem(EqSystemInData inData) throws Exception;
	
	/**
	 * 删除
	 * @param inData
	 */
	void deleteEqSystem(String id) throws Exception;

}
