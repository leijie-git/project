package com.gw.inspect.service;

import com.gw.inspect.data.InspectionInData;

import java.util.List;

public interface InspectConfigService {



	/**
	 * 解析excel数据 将数据解析成对象分别存进数据库
	 * 详情： UT_Inspect_Site 检查位置
	 *        UT_Base_SiteClassBase  检查对象
	 *        UT_Base_SiteClassDetial  检查项
	 */
  int add(List<InspectionInData> list) throws Exception;


}
