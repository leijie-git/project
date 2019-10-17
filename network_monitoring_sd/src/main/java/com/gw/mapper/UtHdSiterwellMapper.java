package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.equipment.data.WirelessDeviceInData;
import com.gw.equipment.data.WirelessDeviceOutData;
import com.gw.mapper.entity.UtHdSiterwell;

public interface UtHdSiterwellMapper extends BaseMapper<UtHdSiterwell> {

	/**
	 * 查询所有无线设备信息
	 * @return
	 */
	List<WirelessDeviceOutData> getAllWirelessDevice(WirelessDeviceInData inData)throws Exception;

	/**
	 * 查询设备已关联无线设备信息
	 * @return
	 */
	List<WirelessDeviceOutData> getAssociatedList(WirelessDeviceInData inData);

	/**
	 * 查询设备未关联无线设备信息
	 * @return
	 */
	List<WirelessDeviceOutData> getUnrelatedList(WirelessDeviceInData inData);

	/**
	 * 查询hd_siterwell关联D表的数据
	 * 
	 * @param tableName
	 * @param deviceIndex
	 * @param deviceNo
	 * @param alarmReserve
	 * @param doubleHeartBeat
	 * @return
	 * @throws Exception
	 */
	Integer getSiteWellRelInfo(@Param("tableName") String tableName, @Param("deviceIndex") Integer deviceIndex,
			@Param("deviceNo") Integer deviceNo, @Param("alarmReserve") String alarmReserve,
			@Param("doubleHeartBeat") Long doubleHeartBeat) throws Exception;

	/**
	 * 更新hd_siterwell表状态
	 * 
	 * @param deviceIndex
	 * @param deviceNo
	 * @param alarmReserve
	 */
	void updateHdSiterwellStatus(@Param("database") String database, @Param("deviceIndex") Integer deviceIndex,
			@Param("deviceNo") Integer deviceNo, @Param("ownerCode") String ownerCode, @Param("usingtype") Integer usingtype, @Param("gateWayCode") String gateWayCode);

	/**
	 * 更新hd_siterwell表数据
	 * @param wells
	 * @param database
	 * @return
	 */
	Integer updateSiteWell(WirelessDeviceOutData wells);

	/**
	 * 新增hd_siterwell表数据
	 * @param wells
	 * @param database
	 * @return
	 */
	Integer insertSiteWell(UtHdSiterwell wells);

	/**
	 * 删除无线设备
	 * @param well
	 * @return
	 */
	Integer deleteSiteWell(@Param("database") String database,@Param("nsId") String nsId);
	
	/**
	 * 查询无线设备信息
	 * @return
	 */
	WirelessDeviceOutData getWirelessDevice(@Param("database") String database,@Param("id") String id)throws Exception;

	/**
	 * 查看单个无线设备
	 * @param well
	 * @return
	 */
	WirelessDeviceOutData getOneWireless(UtHdSiterwell well);
}