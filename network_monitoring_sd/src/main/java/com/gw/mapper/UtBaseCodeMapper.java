package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtBaseCode;
import com.gw.systemManager.data.CodeInData;
import com.gw.systemManager.data.CodeOutData;

public interface UtBaseCodeMapper extends BaseMapper<UtBaseCode> {

	/**
	 * 根据groupkey获取对应列表
	 * @param inData
	 * @return
	 */
	List<CodeOutData> getListByGroupKey(CodeInData inData);
	
	/**
	 * 分页查询所有
	 * @param codeName
	 * @return
	 */
	List<CodeOutData> getAllList(CodeInData inData);

	/**
	 * 根据codeGroupId获得code
	 * @param codeGroupId
	 * @return 
	 */
	List<CodeOutData> getCodeListByCodeGroupId(@Param("codeGroupId") String codeGroupId);

	/**
	 * 获取需要导入的数据
	 * @param database 
	 * @return
	 */
	List<CodeOutData> getImportDatas(@Param("database")String database);
}