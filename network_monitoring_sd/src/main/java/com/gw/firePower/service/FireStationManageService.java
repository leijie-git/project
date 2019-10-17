package com.gw.firePower.service;

import com.github.pagehelper.PageInfo;
import com.gw.firePower.data.FireStationManageInData;
import com.gw.firePower.data.FireStationManageOutData;

public interface FireStationManageService {

	/**
	 * 新增消防站设备信息
	 * @param id
	 * @param inData
	 * @throws Exception 
	 */
	void addFireStation(Long id, FireStationManageInData inData) throws Exception;

	/**
	 * 修改消防站设备信息
	 * @param id
	 * @param inData
	 * @throws Exception 
	 */
	void updateFireStation(Long id, FireStationManageInData inData) throws Exception;

	/**
	 * 获取消防站设备信息
	 * 
	 * @param pageSize
	 * @param pageNumber
	 * @param fireStationName
	 * @param name
	 * @return
	 * @throws Exception
	 */
	PageInfo<FireStationManageOutData> getFireStationList(Integer pageNumber, Integer pageSize, String fireStationName, String name) throws Exception;

	/**
	 * 删除消防站设备信息
	 * @param id
	 * @throws Exception 
	 */
	void deleteFireStation(String id) throws Exception;

}
