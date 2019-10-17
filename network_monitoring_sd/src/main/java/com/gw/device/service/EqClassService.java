package com.gw.device.service;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.EqClassInData;
import com.gw.device.data.EqClassOutData;
import com.gw.device.data.EqSystemOutData;
import com.gw.mapper.entity.UtBaseEqPort;

import java.util.List;

public interface EqClassService {

	/**
	 * 获取设备分类列表
	 * @return
	 * @throws Exception
	 */
	List<EqClassOutData> getArrayList(String systemID) throws Exception;

	/**
	 * 获取系统列表
	 * @return
	 * @throws Exception
	 */
	List<EqSystemOutData> getSystemList() throws Exception;
	
	/**
	 * 获取设备类型列表
	 * @param classname
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageInfo<EqClassOutData> pageList(String classname, Integer pageNumber, Integer pageSize) throws Exception;
	
	
	/**
	 * 新增设备类型
	 * @param inData
	 * @throws Exception 
	 */
	void addEqClass(EqClassInData inData) throws Exception;
	
	/**
	 * 更新设备类型
	 * @param inData
	 */
	void updateEqClass(EqClassInData inData) throws Exception;
	
	/**
	 * 删除设备类型
	 * @param id
	 */
	void deleteEqClass(String id) throws Exception;

	/**
	 * 取设备类型关联端口号
	 *
	 * @return
	 * @throws Exception
	 */
	List<UtBaseEqPort> getportNameArrayList(Integer classCode) throws Exception;
}
