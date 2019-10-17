package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.device.data.EqSystemOutData;
import com.gw.mapper.entity.UtBaseEqsystem;

public interface UtBaseEqsystemMapper extends BaseMapper<UtBaseEqsystem> {

	/**
	 * 获取系统列表
	 * 
	 * @return
	 * @throws Exception
	 */
	List<EqSystemOutData> getEqSystemSelect() throws Exception;

	/**
	 * 获取系统分页列表
	 * 
	 * @return
	 * @throws Exception
	 */
	List<EqSystemOutData> getEqSystemList(@Param("EqSystemName") String eqsystemname) throws Exception;

	/**
	 * 根据unitId获得系统信息
	 * 
	 * @param unitId
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	List<EqSystemOutData> getEqSystemByUnitId(@Param("unitId") String unitId, @Param("buildId") String buildId)
			throws Exception;

}