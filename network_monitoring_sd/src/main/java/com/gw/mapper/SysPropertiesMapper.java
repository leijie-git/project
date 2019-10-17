package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.SysProperties;
import com.gw.systemManager.data.SysPropertiesOutData;

public interface SysPropertiesMapper extends BaseMapper<SysProperties> {

	/**
	 * 获取properties信息
	 * @return
	 * @throws Exception
	 */
	SysPropertiesOutData getProperties() throws Exception;
}