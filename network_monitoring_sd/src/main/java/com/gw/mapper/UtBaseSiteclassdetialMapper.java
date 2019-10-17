package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.inspect.data.SiteClassDetialInData;
import com.gw.inspect.data.SiteClassDetialOutData;
import com.gw.mapper.entity.UtBaseSiteclassdetial;
import org.apache.ibatis.annotations.Param;

public interface UtBaseSiteclassdetialMapper extends BaseMapper<UtBaseSiteclassdetial> {

	/**
	 * 获取分类检查项列表
	 * @param inData
	 * @return
	 */
	List<SiteClassDetialOutData> getList(SiteClassDetialInData inData);

	/**
	 * 批量插入
	 * @param list
	 * @return
	 */
	int insertDetialList(List<UtBaseSiteclassdetial > list);

	/**
	 * 根据分类ID查找检查项
	 * @param siteClassid
	 * @return
	 */
	List<SiteClassDetialOutData> getSiteclassdetialBySiteclassid(@Param("siteClassid") String siteClassid);


	//	根据检查项对象查出检查项ID
	String selectIDbyUtBaseSiteclassdetial(UtBaseSiteclassdetial utBaseSiteclassdetial );
}