package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.firePower.data.SquadronOutData;
import com.gw.mapper.entity.UtSquadron;

public interface UtSquadronMapper extends BaseMapper<UtSquadron> {

	/**
	 * 获取中队配置信息
	 * @param name
	 * @param powerName 
	 * @return
	 */
	List<SquadronOutData> getSquadronList(@Param("name")String name, @Param("powerName")String powerName);

	/**
	 * 获取中队消防站详情
	 * @param powerID
	 * @return
	 */
	SquadronOutData getFireSquadronPowerDetail(@Param("powerID") String powerID)throws Exception;
}