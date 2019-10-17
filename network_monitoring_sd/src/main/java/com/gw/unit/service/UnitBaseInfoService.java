package com.gw.unit.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.*;

/**
 * 联网单位基本信息服务接口
 *
 * @author SY
 */
public interface UnitBaseInfoService {

	/**
	 * 查询所有联网单位信息
	 *
	 * @param request
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
	PageInfo<UnitBaseInfoOutData> getBaseInfoList(UnitBaseInfoInData inData) throws Exception;

	/**
	 * 添加联网单位信息
	 *
	 * @param inData
	 * @throws Exception
	 */
	void addBaseInfo(UnitBaseInfoInData inData) throws Exception;

	/**
	 * 查询所有省
	 *
	 * @return
	 */
	List<BaseProvicecityregionOutData> getProvinceList(Integer type, String parentId) throws Exception;

	/**
	 * 根据id删除联网单位信息
	 *
	 * @param id
	 * @throws Exception
	 */
	void deleteBaseInfoById(String id) throws Exception;

	/**
	 * 更新联网单位信息
	 *
	 * @param inData
	 * @throws Exception
	 */
	void updateBaseInfo(UnitBaseInfoInData inData) throws Exception;

	/**
	 * 联网单位下拉框准备数据
	 *
	 * @return
	 * @throws Exception
	 */
	List<BaseInfoSelectOutData> baseInfoSelect() throws Exception;

	/**
	 * 获取所有联网单位信息
	 *
	 * @return
	 */
	List<BaseInfoSelectOutData> getArrayList() throws Exception;

	/**
	 * 获取联网单位关联的维保人员
	 *
	 * @return
	 * @throws Exception
	 */
	GetUnitUsersData getUnitReUser(String unitId) throws Exception;

	/**
	 * 设置联网单位关联的维保人员
	 *
	 * @param unitId
	 * @param manyUserId
	 * @throws Exception
	 */
	void setUnitReUser(String unitId, String manyUserId) throws Exception;

	/**
	 * 从外库导入所有单位数据
	 *
	 * @throws Exception
	 */
	void importAllUnits() throws Exception;

	/**
	 * 查询所有UnitBaseInfo
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	List<UnitBaseInfoOutData> selectAllUnitBaseInfoById(UnitBaseInfoInData inData) throws Exception;

	List<UnitBaseInfoOutUnitData> selectAllUnitById(UnitBaseInfoInData inData) throws Exception;
}
