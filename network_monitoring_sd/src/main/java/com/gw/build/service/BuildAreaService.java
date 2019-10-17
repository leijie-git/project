package com.gw.build.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaInData;
import com.gw.build.data.BuildAreaOutData;

/**
 * 建筑物区域业务层接口
 * @author zfg
 *
 */
public interface BuildAreaService {

	/**
	 * 获取建筑物区域列表
	 * @param inData
	 * @return
	 */
	PageInfo<BuildAreaOutData> getList(BuildAreaInData inData) throws Exception;
	
	/**
	 * 获取建筑物列表（单纯集合）
	 * @return
	 */
	List<BuildAreaOutData> getArrayList(BuildAreaInData inData) throws Exception;
	/**
	 * 添加建筑物区域
	 * @param inData
	 */
	void add(BuildAreaInData inData)throws Exception;

	/**
	 * 编辑建筑物区域
	 * @param inData
	 * @throws Exception
	 */
	void update(BuildAreaInData inData)throws Exception;

	/**
	 * 删除建筑物区域
	 * @param id
	 * @throws Exception
	 */
	void remove(String id)throws Exception;

	/**
	 * 导出区域功能
	 * @param response
	 * @param inData 
	 * @throws Exception 
	 */
	void exportBuildArea(HttpServletResponse response, BuildAreaInData inData) throws Exception;

}
