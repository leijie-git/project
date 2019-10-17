package com.gw.systemManager.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.systemManager.data.CodeGroupInData;
import com.gw.systemManager.data.CodeGroupOutData;

/**
 * 代码组管理接口
 * @author SY
 * @data 2018年9月14日
 */
public interface CodeGroupService {
	/**
	 * 分页查询
	 * @param pageNumber
	 * @param pageSize
	 * @param codeGroupName
	 * @param codegroupkey 
	 * @return
	 */
	PageInfo<CodeGroupOutData> list(Integer pageNumber,Integer pageSize,String codeGroupName, String codegroupkey) throws Exception;

	/**
	 * 添加
	 * @param inData
	 */
	void add(String userId, CodeGroupInData inData) throws Exception;
	
	/**
	 * 更新
	 * @param inData
	 */
	void update(String userId, CodeGroupInData inData) throws Exception;
	
	/**
	 * 删除
	 * @param codeGroupID
	 */
	void delete(Long codeGroupID) throws Exception;
	
	/**
	 * 获得codeGroup下拉框数据
	 * @param codeGroupID
	 * @return
	 */
	List<CodeGroupOutData> getCodeGroupSelectList() throws Exception;
}
