package com.gw.device.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.data.NetDeviceInData;
import com.gw.device.data.NetDeviceOutData;
import com.gw.equipment.data.EquipmentNetDeviceOutData;

/**
 * 联网设备服务层接口
 *
 * @author SY
 *
 */
public interface NetDeviceService {

	/**
	 * 新增联网设备
	 *
	 * @param inData
	 */
	void add(NetDeviceInData inData) throws Exception;

	/**
	 * 更新联网设备
	 *
	 * @param inData
	 * @throws Exception
	 */
	void update(NetDeviceInData inData) throws Exception;

	/**
	 * 分页查询
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<NetDeviceOutData> list(NetDeviceInData inData, Long id) throws Exception;

	/**
	 * 删除联网设备
	 *
	 * @param id
	 * @throws Exception
	 */
	void delete(String id) throws Exception;

	/**
	 * 获得需要导入的数据
	 *
	 * @param unitid
	 * @return
	 * @throws Exception
	 */
	PageInfo<NetDeviceOutData> getImportList(String unitId) throws Exception;

	/**
	 * 导入方法
	 *
	 * @param userId
	 * @param id
	 * @param unitid
	 */
	void addAll(Long userId, String ids, String unitid) throws Exception;

	/**
	 * 获得系统ID下拉框
	 *
	 * @return
	 */
	List<EqSystemOutData> getEqSystemSelect() throws Exception;

	/**
	 * 获得联网设备名称下拉框
	 *
	 * @return
	 * @throws Exception
	 */
	List<NetDeviceOutData> getNetDeviceNameSelect(Long unitid) throws Exception;

	/**
	 * 根据系统id获取联网设备列表
	 *
	 * @param inData
	 * @return
	 */
	List<NetDeviceOutData> getArrayList(NetDeviceInData inData) throws Exception;

	/**
	 * 从外部数据库导入设备信息
	 *
	 * @param userId
	 * @throws Exception
	 */
	void importDevicesFromOthers(Long userId) throws Exception;

	/**
	 * 判断当前联网设备下是否存在设备设施
	 *
	 * @param id
	 * @return
	 */
	Boolean hasEquipments(String id) throws Exception;

	/**
	 * 添加关联网关
	 *
	 * @param netDeviceID
	 * @param gateWayID
	 */
	void addGateWay(String netDeviceID, String gateWayID) throws Exception;

	/**
	 * 取消关联网关
	 *
	 * @param netDeviceID
	 * @param gateWayID
	 */
	void removeGateWay(String gateWayID) throws Exception;

	List<EquipmentNetDeviceOutData> getNetEq(String netDeviceId);
}
