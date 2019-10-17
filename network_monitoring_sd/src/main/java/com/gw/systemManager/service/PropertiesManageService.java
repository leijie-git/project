package com.gw.systemManager.service;

import com.gw.systemManager.data.SysPropertiesInData;
import com.gw.systemManager.data.SysPropertiesOutData;

public interface PropertiesManageService {

	/**
	 * 获取Properties信息
	 * @return
	 * @throws Exception
	 */
	SysPropertiesOutData getProperties() throws Exception;

	/**
	 * 更新Properties信息
	 * @param inData
	 * @throws Exception
	 */
	void updateProperties(SysPropertiesInData inData) throws Exception;

}
