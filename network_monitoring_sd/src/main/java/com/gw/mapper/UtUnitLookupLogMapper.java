package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.front.analysis.data.FrontAnaLysisLookupOutData;
import com.gw.front.analysis.data.FrontAnalysisInData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.couplet.data.FrontLookupUserInfoOutData;
import com.gw.front.lookup.data.FrontLookupInData;
import com.gw.front.lookup.data.FrontLookupLogOutData;
import com.gw.mapper.entity.UtUnitLookupLog;

public interface UtUnitLookupLogMapper extends BaseMapper<UtUnitLookupLog> {

	/**
	 * 查岗记录
	 * 
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	List<FrontLookupLogOutData> getLookupLogs(FrontLookupInData indata) throws Exception;

	/**
	 * 统计查岗次数
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontCoupletCommonOutData getUplookCout(FrontAnalysisInData inData) throws Exception;

	/**
	 * 统计查岗记录列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontAnaLysisLookupOutData> getLookupStatList(FrontAnalysisInData inData) throws Exception;

	/**
	 * 查岗人员信息
	 * 
	 * @param id
	 * @param database
	 * @return
	 * @throws Exception
	 */
	FrontLookupUserInfoOutData getLookupUserInfo(@Param("id") String id, @Param("database") String database)
			throws Exception;

	/**
	 * 点名记录
	 * 
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	List<FrontLookupLogOutData> getNamingLogs(FrontLookupInData indata) throws Exception;

	/**
	 * 查岗设备当前状态
	 * 
	 * @param deviceIds
	 * @param database
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getLookupCurrentstatus(@Param("deviceIds") List<String> deviceIds,
			@Param("database") String database) throws Exception;

	/**
	 * 点名设备当前状态
	 * 
	 * @param deviceIds
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getNamingCurrentstatus(@Param("deviceIds") List<String> deviceIds,
			@Param("database") String database) throws Exception;

	/**
	 * 获取联网单位用户 查岗记录
	 * @param indata
	 * @return
	 */
	List<FrontLookupLogOutData> getNetworkingUnitLookupLogs(FrontLookupInData indata) throws Exception;

	/**
	 * 获取联网单位用户 点名记录
	 * @param indata
	 * @return
	 */
	List<FrontLookupLogOutData> getNetworkingUnitNamingLogs(FrontLookupInData indata) throws Exception;

}