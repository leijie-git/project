package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.analysis.data.FrontAnalysisUnitOutData;
import com.gw.front.couplet.data.FrontCoupletUnitInfo;
import com.gw.front.history.data.FrontHistoryDataFlowOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.index.data.FrontOnlineStatisticalOutData;
import com.gw.front.index.data.FrontRecordOutData;
import com.gw.front.index.data.FrontUnitOutData;
import com.gw.front.index.data.FrontUnitPointOutData;
import com.gw.front.lookup.data.FrontLookupUnitInfoData;
import com.gw.front.unit.data.FrontUnitInfoOutData;
import com.gw.mapper.entity.UtUnitBaseinfo;
import com.gw.myAnnotation.PassToken;
import com.gw.openApi.common.data.in.UnitBaseInData;
import com.gw.openApi.common.data.out.UnitBuildingOutData;
import com.gw.unit.data.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtUnitBaseinfoMapper extends BaseMapper<UtUnitBaseinfo> {

	/**
	 * 查询所有UnitBaseInfo
	 * 
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
	List<UnitBaseInfoOutData> selectAllUnitBaseInfo(UnitBaseInfoInData inData) throws Exception;

	/**
	 * 根据ID查看所有UnitBaseInfo详细信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<UnitBaseInfoOutData> selectAllUnitBaseInfoById(@Param("id") String id) throws Exception;

	/**
	 * 根据ID查看所有关联单位信息
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<UnitBaseInfoOutUnitData> selectAllUnitById(@Param("id") String id) throws Exception;

	/**
	 * 查询联网单位数量
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	Integer getNetUnitCount(@Param("userId")String userId) throws Exception;

	/**
	 * 获取包含用户传输装置的单位
	 * 
	 * @param userId
	 * @param keyWord
	 * @param database
	 * @return
	 * @throws Exception
	 */
	List<FrontLookupUnitInfoData> getTransferDeviceUnit(@Param("userId") String userId,
			@Param("keyWord") String keyWord, @Param("database") String database) throws Exception;

	/**
	 * 查询已联网单位的unitname和id
	 * 
	 * @return
	 * @throws Exception
	 */
	List<BaseInfoSelectOutData> selectUnitNameAndId() throws Exception;

	/**
	 * 查询单位列表
	 * 
	 * @param userId
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletUnitInfo> getUnits(@Param("userId") String userId, @Param("unitName") String unitName)
			throws Exception;

	/**
	 * 查询所有联网单位部分信息
	 * 
	 * @return
	 * @throws Exception
	 */
	List<BaseInfoSelectOutData> getArrayList() throws Exception;

	/**
	 * 单位分析统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontAnalysisUnitOutData> getUnitStatList(FrontAnalysisInData inData) throws Exception;

	/**
	 * 获取单位信息
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitInfoOutData> getUnitInfoById(@Param("unitId") String unitId) throws Exception;

	/**
	 * 根据上级单位查询单位列表信息
	 *
	 * @param unitId
	 * @param manageId
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitInfoOutData> getUnitInfoById(@Param("unitId") String unitId, @Param("manageId") String manageId) throws Exception;


	/**
	 * 获取地图点位
	 *
	 * @param userId
	 * @param sysType
	 * @param alarmType
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitPointOutData> getUnitMapPoint(@Param("userId") String userId, @Param("sysType") String sysType,
												@Param("alarmType") String alarmType, @Param("unitName") String unitName,
												@Param("selectType")String selectType) throws Exception;

	/**
	 * 获取地图点位
	 * 
	 * @param userId
	 * @param sysType
	 * @param alarmType
	 * @param unitName
	 * @return
	 * @throws Exception
	 */
/*	List<FrontUnitPointOutData> getUnitMapPointByBJZJ(@Param("userId") String userId, @Param("sysType") String sysType,
												@Param("alarmType") String alarmType, @Param("unitName") String unitName) throws Exception;*/

	List<FrontUnitPointOutData> getUnitMapPointByRTU(@Param("userId") String userId, @Param("sysType") String sysType,
												@Param("alarmType") String alarmType, @Param("unitName") String unitName) throws Exception;
	/**
	 * 获取联网单位关联的维保人员
	 * 
	 * @param unitId
	 * @return
	 */
	List<GetUnitUsersData> getHasUsers(@Param("unitId") String unitId) throws Exception;

	/**
	 * 获取联网单位未关联的维保人员
	 * 
	 * @param unitId
	 * @return
	 */
	List<GetUnitUsersData> getNoUsers(@Param("unitId") String unitId) throws Exception;

	/**
	 * 根据unitId删除联网单位关联维保人员
	 * 
	 * @param unitId
	 */
	void deleteByUserId(@Param("unitId") String unitId) throws Exception;

	/**
	 * 获取流量统计
	 * 
	 * @param inData
	 * @param year
	 * @param month
	 * @return
	 */
	List<FrontHistoryDataFlowOutData> getDataFlowList(FrontHistoryInData inData) throws Exception;

	/**
	 * 根据单位类型统计单位
	 * 
	 * @param id
	 * @return
	 */
	List<FrontRecordOutData> getUnitStatByUnitType(@Param("userId") String userId) throws Exception;

	/**
	 * 根据监管类型统计单位
	 * 
	 * @param userId
	 * @return
	 */
	List<FrontRecordOutData> getUnitStatBySuperviseType(@Param("userId") String userId) throws Exception;

	/**
	 * 查询外库的所有单位信息
	 * 
	 * @param database
	 */
	List<TemporaryBaseUnitData> selectAllUnitsFromOthers(@Param("database") String database) throws Exception;

	/**
	 * 根据用户ID查询在线的单位
	 * 
	 * @param userId
	 * @param database
	 * @param unitname
	 * @return
	 */
	List<FrontOnlineStatisticalOutData> getOnline(@Param("userId") String userId, @Param("database") String database,
			@Param("unitname") String unitname) throws Exception;

	/**
	 * 根据用户ID查询所有的单位
	 * 
	 * @param userId
	 * @param database
	 * @param string
	 * @return
	 */
	List<FrontOnlineStatisticalOutData> getTotal(@Param("userId") String userId, @Param("database") String database,
			@Param("unitname") String unitname) throws Exception;

	/**
	 * 根据单位id查询单位名称，单位编号
	 * 
	 * @return
	 */
	FrontUnitInfoOutData getUnitNameById(@Param("unitId") Long unitId) throws Exception;

	/**
	 * 获取用户关联单位的单位ID
	 ** @param unitId

	 * @param unitName
	 * @return
	 */
	List<UnitBaseInfoOutData> getUnitByName(@Param("unitName") String unitName, @Param("unitId") String unitId) throws Exception;

	/**
	 * 获取单位的传输装置
	 * 
	 * @param unitId
	 * @param database
	 * @return
	 */
	List<FrontLookupUnitInfoData> getUnitTransferDevice(@Param("unitId") String unitId,
			@Param("database") String database) throws Exception;

	/**
	 * 根据单位获取联网单位
	 *
	 * @param inData
	 * @return
	 */
	UnitBaseInfoOutData getUnitById(UnitBaseInfoInData inData) throws Exception;

	/**
	 * 获得所有单位(按是否上传来排序)
	 * @param inData
	 * @return
	 */
	List<UnitBaseInfoOutData> getUploadUnits(UnitBaseInfoInData inData) throws Exception;

	/**
	 * 获取联网单位基本信息
	 * @param baseInData
	 * @return
	 */
    List<UnitBuildingOutData> getUnitBaseList(UnitBaseInData baseInData);

	/**
	 * 查询系统编号
	 * @return
	 */
	int selectSystemNumber();

	List<FrontUnitOutData> getByUnitpoint(@Param("unitPoint") String unitPoint);

	float getCountEqSystem(@Param("unitId")String unitId);

	List<FrontUnitOutData> getByMaintenanceUnit(@Param("id") String id);

	/**
	 * 查询单位联系电话
	 */
	String selectPhone(@Param("id") String id);
}