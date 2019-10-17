package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtBaseSiteclassdetialbase;
import com.gw.wechat.data.PhoneSiteClassDetialBaseOutData;

public interface UtBaseSiteclassdetialbaseMapper extends BaseMapper<UtBaseSiteclassdetialbase> {

	/**
	 * 获取基本巡查点分类检查项列表
	 * @param id
	 * @return
	 */
	List<PhoneSiteClassDetialBaseOutData> getSiteClassDetailBaseList(@Param("id")String id);
}