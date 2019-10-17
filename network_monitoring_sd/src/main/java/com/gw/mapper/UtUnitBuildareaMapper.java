package com.gw.mapper;

import java.util.List;

import com.gw.front.unit.data.FrontUnitBuildPOutData;
import org.apache.ibatis.annotations.Param;

import com.gw.build.data.BuildAreaInData;
import com.gw.build.data.BuildAreaOutData;
import com.gw.common.BaseMapper;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.unit.data.FrontUnitBuildArea;
import com.gw.mapper.entity.UtUnitBuildarea;

public interface UtUnitBuildareaMapper extends BaseMapper<UtUnitBuildarea> {

	/**
	 * 获取建筑物区域列表
	 * 
	 * @return
	 */
	List<BuildAreaOutData> getList(BuildAreaInData inData) throws Exception;

	/**
	 * 获取单位区域列表
	 * 
	 * @param unitId
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getBuildAreaList(@Param("unitId") String unitId, @Param("buildId") String buildId)
			throws Exception;

	/**
	 * 根据单位ID获取区域列表
	 * 
	 * @param unitId
	 * @return
	 */
	List<BuildAreaOutData> getBuildAreaByUnitId(@Param("unitId") String unitId) throws Exception;
	
	/**
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<BuildAreaOutData> getBuildAreaByBuildId(@Param("buildId") Long buildId) throws Exception;

	/**
	 * 获取区域导出的数据
	 * @param inData 
	 * @return
	 */
	List<BuildAreaOutData> getBuildAreaExportData(BuildAreaInData inData) throws Exception;

	/**
	 * 根据单位id获取建筑物区域
	 * @param unitID
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitBuildArea> getUnitBuildArea(@Param("unitID") String unitID) throws Exception;

	/**
	 * 根据单位id获取建筑物区域Pid
	 * @param unitID
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitBuildPOutData> getUnitBuildAndArea(@Param("unitID") String unitID) throws Exception;

	List<FrontUnitBuildPOutData> getUserBuildAndArea(@Param("userId") String userId) throws Exception;

	/**
	 * 根据区域id查单位名称
	 * @return
	 */
	BuildAreaOutData getUnitidByBuildAreaId(@Param("buildAreaId") String buildAreaId);




	/**
	 * 根据区域名称查询id
	 *
	 *
	 */
	Long selectIDByBuildAreaName(@Param("BuildAreaName") String BuildAreaName);



}