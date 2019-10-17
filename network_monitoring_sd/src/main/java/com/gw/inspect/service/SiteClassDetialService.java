package com.gw.inspect.service;

import com.github.pagehelper.PageInfo;
import com.gw.inspect.data.SiteClassDetialInData;
import com.gw.inspect.data.SiteClassDetialOutData;

import java.util.List;

/**
 * 巡查点分类检查项
 * @author zfg
 *
 */
public interface SiteClassDetialService {

	/**
	 * 获取分类下的检查项
	 * @param siteClassID
	 * @return
	 */
	PageInfo<SiteClassDetialOutData> getList(SiteClassDetialInData inData);

	List<SiteClassDetialOutData> getListByUnitID(SiteClassDetialInData inData);

	/**
	 * 添加检查项
	 * @param inData
	 */
	void add(SiteClassDetialInData inData) throws Exception;

	/**
	 * 编辑检查项
	 * @param inData
	 */
	void update(SiteClassDetialInData inData) throws Exception;

	/**
	 * 删除检查项
	 * @param id
	 */
	void remove(String id) throws Exception;

}
