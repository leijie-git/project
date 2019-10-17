package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.device.data.DeviceRelData;
import com.gw.device.data.InterfaceOutData;
import com.gw.device.data.NetDeviceInData;
import com.gw.device.data.NetDeviceOutData;
import com.gw.equipment.data.EquipmentNetDeviceOutData;
import com.gw.equipment.data.SdInterfaceOutData;
import com.gw.equipment.data.WirelessDeviceOutData;
import com.gw.front.couplet.data.FrontCoupletCalibrationInData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.couplet.data.FrontNBDeviceInfoInData;
import com.gw.front.couplet.data.FrontNBDeviceInfoOutData;
import com.gw.front.history.data.*;
import com.gw.front.index.data.FrontRecordOutData;
import com.gw.front.index.data.FrontUnitInfoStatOutData;
import com.gw.front.lookup.data.FrontLookupUnitInfoData;
import com.gw.mapper.entity.UtUnitNetdevice;
import com.gw.thirdInterface.data.DeviceStatusOutData;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 联网设备Mapper层
 *
 * @author SY
 *
 */
public interface UtUnitNetdeviceMapper extends BaseMapper<UtUnitNetdevice> {

	/**
	 * 分页查询单位设备
	 *
	 * @param deviceno
	 * @return
	 * @throws Exception
	 */
	List<NetDeviceOutData> list(NetDeviceInData inData) throws Exception;

	/**
	 * 查询需要导入的数据
	 *
	 * @param OwnerCode
	 * @return
	 * @throws Exception
	 */
	List<NetDeviceOutData> getImportDeviceList(@Param("databaseName") String databaseName,
											   @Param("soureaddress") String soureaddress) throws Exception;

	/**
	 * 根据ID查询需要导入的数据
	 *
	 * @param databaseName
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<NetDeviceOutData> getDeviceListById(@Param("databaseName") String databaseName, @Param("id") String id)
			throws Exception;

	/**
	 * 根据系统id获取联网设备列表
	 *
	 * @param inData
	 * @return
	 */
	List<NetDeviceOutData> getArrayList(NetDeviceInData inData) throws Exception;

	/**
	 * 根据unitid查询netDevice的name和id
	 *
	 * @param unitid
	 * @return
	 */
	List<NetDeviceOutData> getNetDeviceNameSelect(@Param("unitid") Long unitid) throws Exception;

	/**
	 * 设备当前状态
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHisSDDeviceStatusOutData> getSDDeviceStatusList(FrontHistoryInData inData) throws Exception;

	List<FrontHisSDDeviceStatusOutByIdData> getSDDeviceStatusById(@Param("database") String database) throws Exception;

	/**
	 * 根据id获取详情
	 *
	 * @param id
	 * @param database
	 * @param buildId
	 * @return
	 * @throws Exception
	 */
	FrontHisDeviceStatusOutData getDeviceInfo(FrontHistoryInData inData) throws Exception;

	/**
	 * 查询设备状态列表
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getDeviceStatusList(FrontHistoryInData inData) throws Exception;

	/**
	 * 统计在线设备和离线先设备
	 *
	 * @param userId
	 * @param database
	 * @return
	 * @throws Exception
	 */
	FrontUnitInfoStatOutData getUnitNetdeviceList(@Param("userId") String userId, @Param("database") String database)
			throws Exception;

	/**
	 * 根据联网设备id获取详情
	 *
	 * @param netdeviceid
	 * @return
	 */
	SdInterfaceOutData getDeviceMsg(@Param("id") String netdeviceid) throws Exception;

	/**
	 * 根据联网单位id和设备类型获取设备列表
	 *
	 * @param unitId
	 * @param deviceIndex
	 * @return
	 */
	List<NetDeviceOutData> getNetDeviceListByUnitId(@Param("unitId") String unitId,
													@Param("deviceIndex") Integer deviceIndex) throws Exception;

	/**
	 * 用户传输装置操作记录
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontTransmissionDeviceOutData> getOPDeviceHistory(FrontHistoryInData inData) throws Exception;

	/**
	 * 从外部数据库获取设备信息
	 *
	 * @param databaseName
	 * @return
	 */
	List<NetDeviceOutData> getNetDeviceFromOthers(@Param("database") String database) throws Exception;

	/**
	 * 从外部数据库获取RTU设备信息
	 *
	 * @param databaseName
	 * @return
	 */
	List<DeviceRelData> getRtuDeviceFromOthers(@Param("database") String databaseName) throws Exception;

	/**
	 * 统计设备上传数量
	 *
	 * @param db
	 * @param deviceIndex
	 * @param deviceno
	 * @return
	 * @throws Exception
	 */
	Integer getDeviceUploadCount(@Param("db") String db, @Param("deviceIndex") Integer deviceIndex,
								 @Param("deviceno") Integer deviceno, @Param("startDate") String startDate, @Param("endDate") String endDate) throws Exception;

	/**
	 * 设备在线离线详情
	 *
	 * @param userId
	 * @param database
	 * @param deviceStatus
	 * @return
	 * @throws Exception
	 */
	List<FrontRecordOutData> getUnitNetdeviceStatList(@Param("userId") String userId,
													  @Param("database") String database, @Param("deviceStatus") String deviceStatus) throws Exception;

	/**
	 * 统计当前时间所有单位在离线设备数量
	 *
	 * @param database
	 *
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitInfoStatOutData> getDeviceStatusByUnit(@Param("database") String database) throws Exception;

	/**
	 * 根据单位ID获取单位设备信息
	 *
	 * @param inData
	 * @return
	 */
	List<FrontHisSDDeviceStatusOutData> getNetDevicesByUnitId(FrontHistoryInData inData) throws Exception;

	/**
	 * 根据设备id获取标定信息
	 *
	 * @param eqId
	 * @return
	 */
	FrontCoupletCalibrationInData getDeviceCalibration(@Param("eqId") String eqId) throws Exception;

	/**
	 * 获取设备关联输出口
	 *
	 * @param databaseName
	 * @param ownercode
	 * @param deviceno
	 * @return
	 * @throws Exception
	 */
	List<InterfaceOutData> getInterfaceOutList(@Param("databaseName") String databaseName,
											   @Param("ownercode") String ownercode, @Param("deviceno") Integer deviceno) throws Exception;

	/**
	 * 新增接口控制数据
	 *
	 * @param indata
	 * @throws Exception
	 */
	void addInterfaceOut(InterfaceOutData indata) throws Exception;

	/**
	 * 更新接口控制数据
	 *
	 * @param indata
	 * @throws Exception
	 */
	void updateInterfaceOut(InterfaceOutData indata) throws Exception;

	/**
	 * 删除控制接口
	 *
	 * @param id
	 * @param databaseName
	 * @throws Exception
	 */
	void deleteInterfaceOut(@Param("id") String id, @Param("database") String databaseName) throws Exception;

	/**
	 * 联网单位 查岗列表 获取用户传输装置
	 *
	 * @param unitid
	 * @param keyWord
	 * @param database
	 * @return
	 */
	List<FrontLookupUnitInfoData> getNetworkingUnitTransferDevice(@Param("unitId") String unitId,
																  @Param("keyWord") String keyWord, @Param("database") String database) throws Exception;

	/**
	 * 联网单位用户 点名列表
	 *
	 * @param inData
	 * @return
	 */
	List<FrontHisSDDeviceStatusOutData> getNetworkingUnitSDDeviceStatusList(FrontHistoryInData inData);

	/**
	 * 获取当前值
	 *
	 * @param id
	 * @param database
	 * @return
	 * @throws Exception
	 */
	InterfaceOutData getCurrentStatus(@Param("id") String id, @Param("database") String database) throws Exception;

	/**
	 * 获取NB设备
	 * @param inData
	 * @return
	 */
	List<FrontNBDeviceInfoOutData> getNBDevice(FrontNBDeviceInfoInData inData);

	/**
	 * 获取NB在离线总数
	 *
	 * @return
	 */
	Map<String, Integer> getNBStatusCount(@Param("userId") String userId, @Param("database") String database);

	/**
	 * 无线设备关联联网设备
	 * @param data
	 * @return
	 */
	UtUnitNetdevice getNetDevice(WirelessDeviceOutData data);

	/**
	 * 获取设备离在线状态
	 * @param database
	 * @param unitID
	 * @param netDeviceID
	 * @return
	 */
	List<DeviceStatusOutData> getNetDeviceStatus(@Param("unitId")String unitId, @Param("netDeviceId")String netDeviceId, @Param("database")String database);

	/**
	 * 根据onwercode 、deviceindex、deviceno确定一个设备
	 * @param onwercode
	 * @param deviceindex
	 * @param deviceno
	 * @return
	 */
	String getDeviceByAlarm(@Param("onwercode") String onwercode, @Param("deviceindex") Integer deviceindex, @Param("deviceno")Integer deviceno);

	/**
	 * 联网设备添加接口查看可编辑
	 */

	List<EquipmentNetDeviceOutData> getNetEq(@Param("netDeviceId")String netDeviceId);
}