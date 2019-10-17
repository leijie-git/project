package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtBaseUserreunit;
import com.gw.unit.data.GetUserUnitsData;

public interface UtBaseUserreunitMapper extends BaseMapper<UtBaseUserreunit> {
	
	/**
	 * 获取维保单位用户关联单位
	 * @param id
	 * @return
	 */
	List<GetUserUnitsData> getUserReUnit(@Param("userId") String id, @Param("unitName")String unitName);
	
	/**
	 * 获取维保单位用户未关联单位
	 * @param id
	 * @param unitName 
	 * @return
	 */
	List<GetUserUnitsData> getUserNotReUnit(@Param("userId") String id, @Param("unitName")String unitName);
	
	/**
	 * 根据ID删除所有已关联单位
	 * @param userId
	 */
	void deleteByUserId(@Param("userId") String userId);
}