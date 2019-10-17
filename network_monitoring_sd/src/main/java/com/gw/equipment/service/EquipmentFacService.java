package com.gw.equipment.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.InterfaceOutData;
import com.gw.device.data.InterfaceOutImportData;
import com.gw.equipment.data.EquipmentFacInData;
import com.gw.equipment.data.EquipmentFacInDataUpdate;
import com.gw.equipment.data.EquipmentFacOutData;

public interface EquipmentFacService {

	/**
	 * 获取设备设施列表
	 *
	 * @param inData
	 * @return
	 */
	PageInfo<EquipmentFacOutData> getList(EquipmentFacInData inData, Long id) throws Exception;

	/**
	 * 添加设备
	 *
	 * @param inData
	 */
	void add(EquipmentFacInDataUpdate inData, HttpServletRequest request) throws Exception;

	/**
	 * 批量添加接口
	 *
	 * @param inData
	 */
	void addAll(List<EquipmentFacInData> inData, HttpServletRequest request) throws Exception;

	/**
	 * 删除设备
	 *
	 * @param inData
	 */
	void remove(String id, HttpServletRequest request) throws Exception;

	/**
	 * 修改设备
	 *
	 * @param inData
	 */
	void update(EquipmentFacInDataUpdate inData, HttpServletRequest request) throws Exception;

	void updateAll(List<EquipmentFacInData> inData, HttpServletRequest request) throws Exception;

	/**
	 * 新增或者更新控制接口
	 *
	 * @param indata
	 * @throws Exception
	 */
	void updateInterfaceOut(InterfaceOutData indata) throws Exception;

	/**
	 * 删除控制接口
	 *
	 * @param id
	 * @throws Exception
	 */
	void deleteInterfaceOut(String id) throws Exception;

	/**
	 * 导入
	 *
	 * @param interfaceImportDatas
	 * @param deviceId
	 * @throws Exception
	 */
	void importData(List<InterfaceOutImportData> interfaceImportDatas, String deviceId) throws Exception;

	/**
	 * 获取设备关联输出口
	 *
	 * @param deviceId
	 * @return
	 * @throws Exception
	 */
	PageInfo<InterfaceOutData> getInterfaceOutList(String deviceId) throws Exception;

	/**
	 * 查询设备设施平面图
	 * @param id
	 * @return
	 */
	EquipmentFacOutData getBuildImgbg(Long id);

	/**
	 * 联网设备--删除接口
	 * @param eqId
	 * @param detailid
	 * @param netDevcieId
	 */
	void delete(String eqId,String detailid,String netDevcieId) throws Exception;
}
