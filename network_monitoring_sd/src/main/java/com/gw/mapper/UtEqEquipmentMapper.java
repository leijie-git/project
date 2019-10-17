package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.equipment.data.EquipmentFacInData;
import com.gw.equipment.data.EquipmentFacOutData;
import com.gw.mapper.entity.UtEqEquipment;
import com.gw.openApi.common.data.in.UnitDeviceInData;
import com.gw.openApi.common.data.out.DeviceRunningData;
import com.gw.openApi.common.data.out.EquipmentBaseData;
import com.gw.openApi.common.data.out.UnitDeviceOutData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtEqEquipmentMapper extends BaseMapper<UtEqEquipment> {

	/**
	 * 获取设备设施列表
	 * @param inData
	 * @return
	 */
	List<EquipmentFacOutData> getList(EquipmentFacInData inData) throws Exception;

	/**
	 * 获取区域关联的设备信息
	 * @param buildAreaId
	 * @return
	 */
	List<EquipmentFacOutData> getAreaAssociatedEquipment(@Param("buildAreaId") String buildAreaId);

	/**
	 * 根据设备分类id 和联网设备id查询设备设施
	 * @param netdeviceid
	 * @param netdeviceid
	 * @return
	 */
	List<EquipmentFacOutData> getListByNetDeviceid(@Param("netdeviceid") String netdeviceid);

	DeviceRunningData getEquipmentRunningData(EquipmentBaseData inData);

	List<EquipmentBaseData> getBaseEquipmentList(String netDeviceId);

	List<UnitDeviceOutData> getUnitEquipmentList(UnitDeviceInData deviceInData);

	/**
	 * 查询设备设施平面图
	 * @param id
	 * @return
	 */
	EquipmentFacOutData getBuildImgbg(@Param("id")Long id);

}