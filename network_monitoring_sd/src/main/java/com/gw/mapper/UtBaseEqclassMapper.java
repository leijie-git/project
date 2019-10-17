package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.device.data.EqClassOutData;
import com.gw.mapper.entity.UtBaseEqclass;

public interface UtBaseEqclassMapper extends BaseMapper<UtBaseEqclass> {

	List<EqClassOutData> getArrayList(@Param("systemID") String systemID) throws Exception;
	
	
	/**
	 * 获取设备类型分页列表
	 * @param classname
	 * @return
	 * @throws Exception
	 */
	List<EqClassOutData> getPageList(@Param("classname") String classname) throws Exception;
}