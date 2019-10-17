package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.device.data.AddressRelOutData;
import com.gw.device.data.CRTInData;
import com.gw.device.data.EquipmentSelectData;
import com.gw.front.unit.data.FrontUnitCRTOutData;
import com.gw.mapper.entity.AddresselHostpointOut;
import com.gw.mapper.entity.UtEqAddressRel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtEqAddressRelMapper extends BaseMapper<UtEqAddressRel> {

	/**
	 * 根据设备设施id所有关联地址
	 *
	 * @param eqid
	 * @param unitName
	 * @return
	 */
	List<AddressRelOutData> getAllAddressRel(@Param("eqid") String eqid, @Param("unitName") String unitName);

	/**
	 * 根据单位查询设备
	 *
	 * @param unitid
	 * @return
	 */
	List<EquipmentSelectData> getEquipmentSelect(@Param("unitid") Long unitid);

	/**
	 * 查询单位CRT列表
	 *
	 * @param unitID
	 * @return
	 */
	List<FrontUnitCRTOutData> getCTRListByUnitID(@Param("unitID") String unitID);

	/**
	 * 根据建筑区域id获取点位
	 *
	 * @param inData
	 * @return
	 */
	List<AddressRelOutData> getAddressRelByBuildAreaId(CRTInData inData);

	/**
	 * 更新关联地址信息
	 *
	 * @param utEqAddressRel
	 */
	void updateEqAddressRel(UtEqAddressRel utEqAddressRel);

	/**
	 * 根据点位id获取点位
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontUnitCRTOutData getOneCRT(@Param("addressID") String addressID) throws Exception;

	/**
	 * 根据点位id获取点位
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitCRTOutData> getUnitOneCRT(@Param("buildAreaID") String buildAreaID) throws Exception;


	/**
	 * 根据点位id和单位id获取点位
	 *
	 * @param addressID
	 * @return
	 * @throws Exception
	 */
	List<FrontUnitCRTOutData> getCRTList(@Param("addressID") String addressID, @Param("unitID") String unitID) throws Exception;

	/**
	 * 根据联网设备id和partcode查询真实地址信息
	 *
	 * @param jdid
	 * @param netDeviceId
	 * @return
	 * @throws Exception
	 */
	FrontUnitCRTOutData getaddressRelByPartCode(@Param("partCode") String jdid, @Param("eqId") String eqId) throws Exception;


	/**
	 * @描述 根据主机ID查询当前主机的所有点位
	 * @创建人 Jie.Lei
	 * @参数
	 * @返回值
	 * @创建时间 2019/7/24
	 */
	List<AddresselHostpointOut> getPointByEqid(String id);
}