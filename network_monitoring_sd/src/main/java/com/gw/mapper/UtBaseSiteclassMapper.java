package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.inspect.data.InspectSiteClassInData;
import com.gw.inspect.data.InspectSiteClassOutData;
import com.gw.mapper.entity.UtBaseSiteclass;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UtBaseSiteclassMapper extends BaseMapper<UtBaseSiteclass> {

	/**
	 * 查询巡查点分类
	 *
	 * @param inData
	 * @return
	 */
	List<InspectSiteClassOutData> getList(InspectSiteClassInData inData) throws Exception;

	InspectSiteClassOutData getBySiteclassname(@Param("SiteClassName") String SiteClassName, @Param("UnitID") String UnitID);

	List<InspectSiteClassOutData> getBySiteclassnameAndId(@Param("SiteClassName") String SiteClassName, @Param("id") String id);

	int selectNumber(@Param("UnitID") String UnitID);

//	根据单位id 和位置查出位置ID

	String selectIDBySiteClassNameAndUnitID(Map map);
}