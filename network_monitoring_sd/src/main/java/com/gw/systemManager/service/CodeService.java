package com.gw.systemManager.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.systemManager.data.CodeInData;
import com.gw.systemManager.data.CodeOutData;

public interface CodeService {

	/**
	 * 根据groupKey类别查询对应列表
	 * @param inData
	 * @return
	 */
	List<CodeOutData> getListByGroupKey(String codeGroupKey) throws Exception;


	/**
	 * 分页查询
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<CodeOutData> List(CodeInData inData) throws Exception;
	
	/**
	 * 新增
	 * @param inData
	 */
	void add(String adder, CodeInData inData) throws Exception;
	
	/**
	 * 更新
	 * @param inData
	 */
	void update(String updater, CodeInData inData) throws Exception;
	
	/**
	 * 删除
	 * @param codeid
	 */
	void delete(String codeid) throws Exception;

	/**
	 * 根据codeGroupId获得code
	 * @param codeGroupId
	 * @return 
	 */
	List<CodeOutData> getCodeListByCodeGroupId(String codeGroupId) throws Exception;

	/**
	 * 从sd_transfer_device导入数据
	 */
	void importCodeDatas();
	

}
