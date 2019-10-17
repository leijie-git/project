package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.firePower.data.FirePowerInData;
import com.gw.firePower.data.FirePowerOutData;
import com.gw.mapper.entity.UtFirePower;

public interface UtFirePowerMapper extends BaseMapper<UtFirePower> {

	/**
	 * 获取消防力量数据
	 * 
	 * @param name
	 * @return
	 */
	List<FirePowerOutData> getFirePowerList(FirePowerInData inData) throws Exception;

	/**
	 * 根据类型获取消防站名称
	 * 
	 * @param type
	 * @return
	 */
	List<FirePowerOutData> getFireStationNameByType(@Param("type") String type) throws Exception;

	/**
	 * 根据类型以及单位获取消防力量
	 * 
	 * @param unitID
	 * @param type
	 * @param userid
	 * @return
	 */
	List<FirePowerOutData> getFrontFirePowerList(@Param("unitID") String unitID, @Param("type") String type,
			@Param("userid") String userid) throws Exception;

	/**
	 * 单位获取消防力量
	 * 
	 * @param unitID
	 * @param type
	 * @return
	 */
	List<FirePowerOutData> getFrontUnitFirePowerList(@Param("unitID") String unitID) throws Exception;
}