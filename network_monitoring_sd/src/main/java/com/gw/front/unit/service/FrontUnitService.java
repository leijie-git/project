package com.gw.front.unit.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.build.data.BuildAreaOutData;
import com.gw.device.data.AreaAssociatedEquipmentOutData;
import com.gw.firePower.data.FireStationManageOutData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.front.unit.data.*;

/**
 * 灭火器接口
 * 
 * @author SY
 *
 */
public interface FrontUnitService {

	/**
	 * 获取单位详情
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitInfoOutData> getUnitInfoById(String unitId) throws Exception;

	/**
	 * 单位建筑列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontUnitBuildOutData> getUnitBuilds(FrontUnitInData inData) throws Exception;

	/**
	 * 建筑详情
	 * 
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	FrontUnitBuildOutData getUnitBuildInfoById(String buildId) throws Exception;

	/**
	 * 单位视频
	 * 
	 * @param unitId
	 * @param name 
	 * @return
	 */
	List<FrontUnitVideoOutData> getUnitVideoInfo(String unitId, String name) throws Exception;

	/**
	 * 单位重点部位
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	String getUnitImport(String unitId) throws Exception;

	/**
	 * 单位危险品
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getUnitDangerous(String unitId) throws Exception;

	/**
	 * 单位用户
	 * 
	 * @param unitId
	 * @param userRole
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitUserOutData> getUnitUserList(String unitId, String userRole) throws Exception;

	/**
	 * 消防站信息
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	FrontUnitXFZOutData getUnitXFZInfo(String unitId) throws Exception;

	/**
	 * 根据单位查询区域列表
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<BuildAreaOutData> getUnitAreaImgList(String unitId) throws Exception;

	/**
	 * 根据区域获取设备以及点位
	 * 
	 * @param buildAreaId
	 * @return
	 * @throws Exception
	 */
	List<AreaAssociatedEquipmentOutData> getAreaEqSiteList(String buildAreaId) throws Exception;

	/**
	 * 单位消防站中所有消防设备
	 * 
	 * @param station
	 * @return
	 */
	List<FireStationManageOutData> fireEquipment(String station) throws Exception;

	List<FrontUnitBuildPOutData> getUnitBuildAndArea(String unitID)  throws Exception ;

	List<FrontUnitBuildPOutData> getUserBuildAndArea(String userId)  throws Exception ;

}
