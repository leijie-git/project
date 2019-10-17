package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.mapper.entity.UtFireStation;

public interface UtFireStationMapper extends BaseMapper<UtFireStation> {

	/**
	 * 获取消防站设备信息
	 * 
	 * @param fireStationName
	 * @param name
	 * @return
	 */
	List<FireStationManageOutData> getFireStationList(@Param("fireStationName") String fireStationName,
			@Param("name") String name) throws Exception;

	/**
	 * 根据powerID获取消防设备
	 * 
	 * @param unitID
	 * @return
	 */
	List<FireStationManageOutData> getFireEquipmentList(@Param("unitID") String unitID) throws Exception;

	/**
	 * 根据消防力量id查询消防设备详情（非中队）
	 * 
	 * @param powerID
	 * @return
	 */
	FireStationManageOutData getFirePowerDetail(@Param("powerID") String powerID) throws Exception;
}