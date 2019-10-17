package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtBaseEqPort;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtBaseEqPortMapper extends BaseMapper<UtBaseEqPort> {

	/**
	 * 获取设备类型关联端口号
	 * @param classcode
	 * @return
	 * @throws Exception
	 */
	List<UtBaseEqPort> getArrayList(@Param("classcode") Integer classcode) throws Exception;
	
}