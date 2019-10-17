package com.gw.mapper;

import com.gw.build.data.BuildInData;
import com.gw.build.data.BuildOutData;
import com.gw.common.BaseMapper;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.unit.data.FrontUnitBuildOutData;
import com.gw.front.unit.data.FrontUnitInData;
import com.gw.mapper.entity.UtUnitBuild;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtUnitBuildMapper extends BaseMapper<UtUnitBuild> {

	/**
	 * 获取建筑物列表
	 * 
	 * @return
	 */
	List<BuildOutData> getList(BuildInData inData);

	/**
	 * 单位建筑列表
	 * 
	 * @param inData
	 * @return
	 */
	List<FrontUnitBuildOutData> getUnitBuilds(FrontUnitInData inData) throws Exception;

	/**
	 * 建筑信息
	 * 
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	FrontUnitBuildOutData getUnitBuildInfoById(String buildId) throws Exception;

	/**
	 * 获取建筑列表
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getBuilds(@Param("userId")String userId) throws Exception;

	/**
	 * 根据单位获取建筑列表
	 * @param valueOf
	 * @return
	 */
	List<BuildOutData> getBuildByUnitId(@Param("unitId")Long unitId);


	//	根据建筑名称查询id

	Long selectIdByBuildName(@Param("BuildingName")String BuildingName);
}