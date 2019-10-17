package com.gw.mapper;

import java.util.List;

import com.gw.unit.data.NetworkingUserOutData;
import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.device.data.NetDeviceOutData;
import com.gw.mapper.entity.UtUnitBaseinfoRelation;
import com.gw.unit.data.UnitBaseInfoRelationInData;
import com.gw.unit.data.UnitBaseInfoRelationOutData;

/**
 * 联网单位编号mapper层
 * @author SY
 *
 */
public interface UtUnitBaseinfoRelationMapper extends BaseMapper<UtUnitBaseinfoRelation> {
	
	/**
	 * 根据单位获取单位绑定编号信息
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	List<UnitBaseInfoRelationOutData> selectBaseinfoRelationByUnitId(@Param("unitId") String unitId) throws Exception;
	
	/**
	 * 获取可以导入的设备信息
	 * @param unitId
	 * @param databaseName
	 * @return
	 * @throws Exception
	 */
	List<NetDeviceOutData> getImportList(@Param("unitId") Long unitId, @Param("databaseName")String databaseName) throws Exception;

	/**
	 * 获取单位绑定编号下拉框数据
	 * @param unitid
	 * @return
	 */
	List<UnitBaseInfoRelationOutData> soureaddressSelect(@Param("unitid")String unitid);

	/**
	 * 获取所有单位绑定编号信息
	 * @param inData
	 * @return
	 */
	List<UnitBaseInfoRelationOutData> getAllUnitRel(UnitBaseInfoRelationInData inData);

	/**
	 * 提供根据单位查询单位用户接口
	 * */
	List<NetworkingUserOutData> selectAccountByUnitID(@Param("UnitID") String UnitID,@Param("role")String role);
	
}