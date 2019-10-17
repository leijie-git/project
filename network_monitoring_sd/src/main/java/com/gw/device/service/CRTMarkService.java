package com.gw.device.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.AddressRelInData;
import com.gw.device.data.AddressRelOutData;
import com.gw.device.data.CRTInData;
import com.gw.device.data.UnitAssociatedAreaOutData;
import com.gw.front.unit.data.FrontUnitBuildArea;
import com.gw.front.unit.data.FrontUnitCRTOutData;

public interface CRTMarkService {

	/**
	 * 获取单位关联的区域
	 * @param unitName 
	 * @param unitId 
	 * @return
	 * @throws Exception 
	 */
	List<UnitAssociatedAreaOutData> getUnitAssociatedArea(String unitName, String unitId) throws Exception;

	/**
	 * 获取区域关联的设备信息 分页
	 * @param buildAreaId
	 * @return
	 */
	PageInfo<AddressRelOutData> getAreaAssociatedEquipment(CRTInData inData) throws Exception;

	/**
	 * 更新设备点位
	 * @param inData
	 * @throws Exception
	 */
	void setEqPoint(AddressRelInData inData) throws Exception;

	/**
	 * 获取CRT点位
	 * @param unitID
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitCRTOutData> getCRTList(String unitID) throws Exception;

	/**
	 * 获取所有区域关联的设备信息
	 * @param inData
	 * @return
	 */
	List<AddressRelOutData> getAllAreaAssociatedEquipment(CRTInData inData) throws Exception;

	/**
	 * 取消标点
	 * @param id
	 */
	void emptyPoint(String id) throws Exception;

	/**
	 * 更新关联地址信息
	 * @param inData
	 * @throws Exception
	 */
	void updateEqAddressRel(AddressRelInData inData) throws Exception;

	/**
	 * 根据报警信息获取点位
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitCRTOutData> getUnitOneCRT(String buildAreaID) throws Exception;
	
	/**
	 * 根据报警信息获取点位
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontUnitCRTOutData getOneCRT(String addressID) throws Exception;

	/**
	 * 根据单位查询建筑物区域
	 * @param unitID
	 * @return
	 */
	List<FrontUnitBuildArea> getUnitBuildArea(String unitID)  throws Exception ;


}
