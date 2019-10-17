package com.gw.equipment.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.equipment.data.EquipmentDetailOutData;
import com.gw.equipment.data.EquipmentFacInData;

public interface EquipmentDetailService {

	/**
	 * 根据设备id获取端口信息列表
	 * @param id
	 * @return
	 */
	List<EquipmentDetailOutData> getPortList(String id) throws Exception;
	
	/**
	 * 导入
	 * @param addressRelImportDatas
	 * @param importunitid
	 * @param importeqid
	 * @throws Exception 
	 */
	void importData(List<EquipmentFacInData> addressRelImportDatas, String importeqid) throws Exception;

	/**
	 * 根据设备id获取端口信息列表(分页)
	 * @param id
	 * @return
	 */
	PageInfo<EquipmentDetailOutData> getPortListPage(EquipmentFacInData inData) throws Exception;

	/**
	 * 删除端口
	 * @param id
	 */
	void deletePort(String id) throws Exception;

	/**
	 * 更新端口信息
	 * @param inData
	 * @throws Exception
	 */
	void editPort(EquipmentFacInData inData) throws Exception;

	/**
	 * 为设备设施添加端口
	 * @param inData
	 */
	void addPort(EquipmentFacInData inData) throws Exception;
	
}
