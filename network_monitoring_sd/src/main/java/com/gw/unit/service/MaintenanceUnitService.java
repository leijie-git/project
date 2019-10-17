package com.gw.unit.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.MaintenanceUnitInData;
import com.gw.unit.data.MaintenanceUnitOutData;

/**
 * 维保单位管理服务层接口
 * @author SY
 *
 */	
public interface MaintenanceUnitService {
	
	/**
	 * 获取维保单位
	 * @return
	 */
	MaintenanceUnitOutData getMaintenanceUnitList() throws Exception;
	
	/**
	 * 增加维保单位
	 * @param maintenanceUnit
	 */
	void addMaintenanceUnit(MaintenanceUnitInData maintenanceUnitInData) throws Exception;
	
	/**
	 * 更新维保单位
	 * @param maintenanceUnit
	 */
	void updateMaintenanceUnit(MaintenanceUnitInData maintenanceUnitInData) throws Exception;
	
	/**
	 * 删除维保单位
	 * @param id
	 */
	void deleteMaintenanceUnit(String id) throws Exception;

	/**
	 * 获取维保单位列表
	 * @param inData
	 * @return
	 */
	PageInfo<MaintenanceUnitOutData> getMaintenanceUnitList(MaintenanceUnitInData inData);

	/**
	 * 获取维保单位下拉框数据
	 * @return
	 */
	List<MaintenanceUnitOutData> getMaintenanceUnitSelectList();

}
