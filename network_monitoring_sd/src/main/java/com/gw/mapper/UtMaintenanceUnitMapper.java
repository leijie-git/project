package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.mapper.entity.UtMaintenanceUnit;
import com.gw.unit.data.MaintenanceUnitInData;
import com.gw.unit.data.MaintenanceUnitOutData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtMaintenanceUnitMapper extends BaseMapper<UtMaintenanceUnit> {
	
	/**
	 * 编辑维保单位回显数据
	 * @return
	 */
	MaintenanceUnitOutData edit() throws Exception;
	
	/**
	 * 获取维保单位
	 * @param unitId 
	 * @return
	 */
	MaintenanceUnitOutData getMaintenanceUnit(@Param("unitId") String unitId) throws Exception;
	
	
	/**
	 * 根据单位id获取维保单位
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitInfoOutData> getMaintenanceUnitById(@Param("id")String id) throws Exception;

	/**
	 * 获取维保单位列表
	 * @param inData
	 * @return
	 */
	List<MaintenanceUnitOutData> getMaintenanceUnitList(MaintenanceUnitInData inData);

	/**
	 * 获取维保单位下拉框数据
	 * @return
	 */
	List<MaintenanceUnitOutData> getMaintenanceUnitSelectList();
	
	int selectSystemNumber();


	//    通过单位名称查询单位编号
	String selectUnitIDByUnitName(@Param("UnitName") String UnitName);

}