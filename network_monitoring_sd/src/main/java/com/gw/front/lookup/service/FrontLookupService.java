package com.gw.front.lookup.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.couplet.data.FrontLookupUserInfoOutData;
import com.gw.front.history.data.FrontHisSDDeviceStatusOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.lookup.data.FrontLookupInData;
import com.gw.front.lookup.data.FrontLookupLogOutData;
import com.gw.front.lookup.data.FrontLookupUnitInfoData;
import com.gw.front.lookup.data.FrontUserExperienceOutData;

public interface FrontLookupService {

	/**
	 * 获取包含用户传输装置的单位
	 * 
	 * @param userId
	 * @param pageNumber
	 * @param pageSize
	 * @param keyWord
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontLookupUnitInfoData> getTransferDeviceUnit(String userId, String keyWord, Integer pageSize,
			Integer pageNumber) throws Exception;

	/**
	 * 查岗
	 * 
	 * @param userId
	 * @param deviceIds
	 * @param type
	 * @return
	 * @throws Exception
	 */
	boolean lookup(String userId, String deviceIds, String type) throws Exception;

	/**
	 * 查岗记录
	 * 
	 * @param userId
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontLookupLogOutData> getLookupLogs(String userId, FrontLookupInData indata) throws Exception;

	/**
	 * 导出查岗日志
	 * 
	 * @param id
	 * @param indata
	 * @param response
	 * @throws Exception
	 */
	void exportLookupLogs(String id, FrontLookupInData indata, HttpServletResponse response) throws Exception;

	/**
	 * 导出查岗列表
	 * 
	 * @param id
	 * @param keyWord
	 * @param response
	 * @throws Exception
	 */
	void exportTransferDeviceUnit(String id, String keyWord, HttpServletResponse response) throws Exception;

	/**
	 * 查岗人员信息
	 * 
	 * @param id
	 * @return
	 */
	FrontLookupUserInfoOutData getLookupUserInfo(String id) throws Exception;

	/**
	 * 人员工作经历
	 * 
	 * @param infoId
	 * @return
	 * @throws Exception
	 */
	List<FrontUserExperienceOutData> getUserExperience(String infoId) throws Exception;

	/**
	 * 导出工作记录
	 * 
	 * @param infoId
	 * @param response
	 * @throws Exception
	 */
	void exportUserExperience(String infoId, HttpServletResponse response) throws Exception;

	/**
	 * 定名记录
	 * 
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontLookupLogOutData> getNamingLogs(FrontLookupInData indata) throws Exception;

	/**
	 * 导出点名记录
	 * 
	 * @param indata
	 * @param response
	 * @throws Exception
	 */
	void exportNamingLogs(FrontLookupInData indata, HttpServletResponse response) throws Exception;

	/**
	 * 查岗设备当前状态
	 * 
	 * @param deviceIds
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getLookupCurrentstatus(String deviceIds) throws Exception;

	/**
	 * 点名设备当前状态
	 * 
	 * @param deviceIds
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getNamingCurrentstatus(String deviceIds) throws Exception;

	/**
	 * 联网单位用户 查岗列表 获取用户传输装置
	 * @param unitid
	 * @param keyWord
	 * @param pageSize
	 * @param pageNumber
	 * @return
	 */
	PageInfo<FrontLookupUnitInfoData> getNetworkingUnitTransferDevice(String unitId, String keyWord, Integer pageSize,
			Integer pageNumber) throws Exception;

	/**
	 * 联网单位用户 查岗
	 * @param id
	 * @param deviceId
	 * @param type
	 * @return
	 */
	boolean networkingUnitLookUp(String userId, String deviceIds, String type) throws Exception;

	/**
	 * 获取联网单位用户  查岗记录
	 * @param unitid
	 * @param indata
	 * @return
	 */
	PageInfo<FrontLookupLogOutData> getNetworkingUnitLookupLogs(String unitId, FrontLookupInData indata) throws Exception;

	/**
	 * 导出联网单位用户  查岗记录
	 * @param unitId
	 * @param indata
	 * @param response
	 * @throws Exception 
	 */
	void exportNetworkingUnitLookupLogs(String unitId, FrontLookupInData indata, HttpServletResponse response) throws Exception;

	/**
	 * 联网单位用户  点名列表
	 * @param inData
	 * @return
	 * @throws Exception 
	 */
	PageInfo<FrontHisSDDeviceStatusOutData> getNetworkingUnitSDDeviceStatusList(FrontHistoryInData inData) throws Exception;

	/**
	 * 获取联网单位用户  点名记录
	 * @return
	 * @throws Exception 
	 */
	PageInfo<FrontLookupLogOutData> getNetworkingUnitNamingLogs(FrontLookupInData indata) throws Exception;

	/**
	 * 导出联网单位用户  点名记录
	 * @param indata
	 * @param response
	 * @throws Exception 
	 */
	void exportNetworkingUnitNamingLogs(FrontLookupInData indata, HttpServletResponse response) throws Exception;

	/**
	 * 联网单位用户 查岗列表 导出用户传输装置
	 * @param unitid
	 * @param keyWord
	 * @param response
	 * @throws Exception 
	 */
	void exportNetworkingUnitTransferDevice(String unitid, String keyWord, HttpServletResponse response) throws Exception;

	/**
	 * 联网单位用户  导出点名列表
	 * @param response 
	 * @param inData
	 */
	void exportNetworkingUnitSDDeviceStatusList(HttpServletResponse response, FrontHistoryInData inData) throws Exception;

}
