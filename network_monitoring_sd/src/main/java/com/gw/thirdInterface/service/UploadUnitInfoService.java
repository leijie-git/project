package com.gw.thirdInterface.service;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.NetDeviceInData;
import com.gw.device.data.NetDeviceOutData;
import com.gw.unit.data.UnitBaseInfoInData;
import com.gw.unit.data.UnitBaseInfoOutData;
import com.gw.upload.data.UploadEquipmentOutData;

public interface UploadUnitInfoService {

	/**
	 * 获取所有单位
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<UnitBaseInfoOutData> getAllUnitInfo(UnitBaseInfoInData inData) throws Exception;

	/**
	 * 获取设备
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<NetDeviceOutData> getUploadDevice(NetDeviceInData inData) throws Exception;

	/**
	 * 获取报警主机和RTU
	 * @param unitid
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws Exception 
	 */
	PageInfo<UploadEquipmentOutData> getUploadEquipment(String unitid, Integer pageNumber, Integer pageSize) throws Exception;

}
