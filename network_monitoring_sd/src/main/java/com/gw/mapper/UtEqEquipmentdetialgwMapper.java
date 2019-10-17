package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.device.data.InterfaceOutData;
import com.gw.equipment.data.EquipmentDetailOutData;
import com.gw.equipment.data.EquipmentFacInData;
import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import com.gw.front.couplet.data.FrontCoupletInData;
import com.gw.front.couplet.data.FrontInterFaceStatusOutData;
import com.gw.front.couplet.data.FrontRTUHistoryInData;
import com.gw.mapper.entity.UtEqEquipmentdetialgw;
import com.gw.upload.data.UploadEquipmentOutData;

public interface UtEqEquipmentdetialgwMapper extends BaseMapper<UtEqEquipmentdetialgw> {

	/**
	 * 查询系统接口当前值
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontInterFaceStatusOutData> getInterfaceValueList(FrontCoupletInData inData) throws Exception;

	/**
	 * 查询气体系统告警主机
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontInterFaceStatusOutData> getGasAlarmList(FrontCoupletInData inData) throws Exception;

	// /**
	// * 查询设备
	// *
	// * @param list
	// * @param deviceType
	// * @param type
	// * @param buildId
	// * @return
	// * @throws Exception
	// */
	// List<FrontRTUHistoryInData>
	// getInterfaceInfoByBuildArea(@Param("BuildAreaIds") List<String> list,
	// @Param("deviceType") String deviceType, @Param("type") String type,
	// @Param("buildId") String buildId)
	// throws Exception;

	/**
	 * 查询模拟量历史记录
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getWaterSystemHistory(FrontRTUHistoryInData data) throws Exception;

	/**
	 * 查询数字量水系统历史记录
	 *
	 * @param data
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletCommonOutData> getWaterSystemHistory1(FrontRTUHistoryInData data) throws Exception;

	/**
	 * 根据设备id获取端口信息列表
	 *
	 * @param id
	 * @return
	 */
	List<EquipmentDetailOutData> getPortList(@Param("id") String id) throws Exception;

	/**
	 * 根据设备编号删除端口
	 *
	 * @param id
	 * @return
	 */
	Integer remove(@Param("id") String id);

	/**
	 * 根据设备编号查询所有端口信息
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	List<EquipmentFacInData> selectDetailList(@Param("id") String id) throws Exception;

	/**
	 * 通过设备查询所有接口信息
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontRTUHistoryInData> getInterfaceInfoByDevice(FrontCoupletInData inData) throws Exception;

	/**
	 * 根据联网设备id获取该设备下所有端口信息
	 *
	 * @param netdeviceid
	 * @return
	 */
	List<EquipmentDetailOutData> getListByNetDeviceid(@Param("netdeviceid") String netdeviceid) throws Exception;

	/**
	 * 历史数据数量
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	Integer selectWaterHistoryCount(FrontRTUHistoryInData data) throws Exception;

	/**
	 * 获取输出接口控制
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<InterfaceOutData> getInterfaceOutData(FrontCoupletInData inData) throws Exception;

	/**
	 * 获取接口信息
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<InterfaceOutData> getDetialgwList(FrontCoupletInData inData) throws Exception;

	/**
	 * 获取设备id
	 *
	 * @param id
	 * @param database
	 * @return
	 * @throws Exception
	 */
	String getDeviceId(@Param("id") String id, @Param("database") String database) throws Exception;

	/**
	 * 获取接口当前状态
	 *
	 * @param tableName
	 * @param deviceNo
	 * @param database
	 * @return
	 * @throws Exception
	 */
	String currentInterfaceStatus(@Param("tableName") String tableName, @Param("deviceNo") Integer deviceNo,
								  @Param("database") String database) throws Exception;

	/**
	 * 删除设备设施下面的端口
	 *
	 * @param id
	 */
	Integer removePortByEqId(@Param("eqId") String Id) throws Exception;

	/**
	 * 获取RTU和报警主机信息
	 *
	 * @param unitid
	 * @return
	 */
	List<UploadEquipmentOutData> getUploadEquipment(@Param("unitId") String unitid) throws Exception;

	/**
	 * 根据单位查找总接口数
	 */
	int getDetailCountByUnitId(@Param("unitId") String unitId, @Param("eqSystemId") String eqSystemId);
}