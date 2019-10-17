package com.gw.equipment.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.NetDeviceOutData;
import com.gw.equipment.data.WirelessDeviceImportData;
import com.gw.equipment.data.WirelessDeviceInData;
import com.gw.equipment.data.WirelessDeviceOutData;

public interface WirelessDeviceService {

	/**
	 * 分页查询无线设备列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageInfo<WirelessDeviceOutData> getList(WirelessDeviceInData inData) throws Exception;
	
	/**
	 * 获取联网设备
	 * @return
	 * @throws Exception
	 */
	List<NetDeviceOutData> getNetDevice() throws Exception;

	/**
	 * 更新无线设备信息
	 * @param inData
	 */
	void edit(WirelessDeviceInData inData) throws Exception;

	/**
	 * 导入无线设备数据
	 * @param inData
	 * @throws Exception
	 */
	void importData(List<WirelessDeviceImportData> inData,String usingtype,String heartbeats) throws Exception;

	/**
	 * 删除无线设备信息
	 * @param id
	 */
	void delelte(String id) throws Exception;

	/**
	 * 分页查询该设备关联的无线设备列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageInfo<WirelessDeviceOutData> getAssociatedList(WirelessDeviceInData inData) throws Exception;

	
	/**
	 * 分页查询该设备未关联的无线设备列表
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	PageInfo<WirelessDeviceOutData> getUnrelatedList(WirelessDeviceInData inData) throws Exception;

	

}
