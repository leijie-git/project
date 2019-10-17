package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtBaseCodegroup;
import com.gw.systemManager.data.CodeGroupOutData;
import com.gw.systemManager.data.CodeOutData;

public interface UtBaseCodegroupMapper extends BaseMapper<UtBaseCodegroup> {
	/**
	 * 根据groupkey获取对应列表
	 * @param inData
	 * @return
	 */
	List<CodeOutData> getListByGroupKey(@Param("codeGroupKey") String codeGroupKey);
	
	/**
	 * 查询所有
	 * @param codeGroupName
	 * @param codegroupkey 
	 * @return
	 */
	List<CodeGroupOutData> selectAllCodeGroup(@Param("codeGroupName")String codeGroupName, @Param("codeGroupKey")String codegroupkey);
	
	/**
	 * 获得下拉框数据
	 * @param codeGroupID
	 * @return
	 */
	List<CodeGroupOutData> getCodeGroupSelectList();
}