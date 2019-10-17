package com.gw.firePower.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.firePower.data.FirePowerInData;
import com.gw.firePower.data.FirePowerOutData;

public interface FirePowerManageService {

	/**
	 * 新增消防力量
	 * @param inData
	 * @throws Exception 
	 */
	void addFirePower(FirePowerInData inData) throws Exception;

	/**
	 * 更新消防力量
	 * @param inData
	 * @throws Exception 
	 */
	void updateFirePower(FirePowerInData inData) throws Exception;

	/**
	 * 获取消防力量分页数据
	 * @param pageSize 
	 * @param pageNumber 
	 * @param pageNumber
	 * @param pageSize
	 * @param name 
	 * @return
	 * @throws Exception 
	 */
	PageInfo<FirePowerOutData> getFirePowerList(Integer pageNumber, Integer pageSize, FirePowerInData inData) throws Exception;

	/**
	 * 删除消防力量
	 * @param id
	 * @throws Exception 
	 */
	void deleteFirePower(String id) throws Exception;

	/**
	 * 根据类别获取消防站名称
	 * @param type
	 * @return
	 */
	List<FirePowerOutData> getFireStationNameByType(String type) throws Exception;

}
