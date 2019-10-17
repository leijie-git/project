package com.gw.unit.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.NetworkingUserOutData;
import com.gw.unit.data.UnitBaseInfoRelationInData;
import com.gw.unit.data.UnitBaseInfoRelationOutData;

/**
 * 联网单位编号绑定服务层接口
 * @author SY
 *
 */
public interface UnitBaseInfoRelationService {
	
	/**
	 * 查询所有单位绑定编号信息并分页
	 * @param request
	 * @param soureaddress
	 * @return
	 * @throws Exception
	 */
	PageInfo<UnitBaseInfoRelationOutData> list(HttpServletRequest request, String unitId) throws Exception;
	
	/**
	 * 添加
	 * @param inData
	 */
	void addBaseInfoRelation(UnitBaseInfoRelationInData inData) throws Exception;
	
	/**
	 * 更新
	 * @param inData
	 */
	void updateBaseInfoRelation(UnitBaseInfoRelationInData inData) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 */
	void deleteBaseInfoRelation(String id) throws Exception;
	
	/**
	 * 下拉框准备数据
	 * @param unitid
	 * @return
	 * @throws Exception
	 */
	List<UnitBaseInfoRelationOutData> soureaddressSelect(String unitid) throws Exception;

	/**
	 * 获取所有单位绑定编号数据
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<UnitBaseInfoRelationOutData> getAllUnitRel(UnitBaseInfoRelationInData inData) throws Exception;


	/**
	 * 提供根据单位查询单位用户接口
	 * @param UnitID
	 * @return
	 * @throws Exception
	 * jie
	 */
	List<NetworkingUserOutData>  selectAccountByUnitID(String UnitID,String role);
}
