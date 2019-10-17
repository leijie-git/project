package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.fireStation.data.EquipmentOutData;
import com.gw.mapper.entity.UtUnitXfzequipment;

/**
 * 消防站设施Mapper层
 * @author SY
 *
 */
public interface UtUnitXfzequipmentMapper extends BaseMapper<UtUnitXfzequipment> {
	
	List<EquipmentOutData> selectAllEquipment(@Param("equipmentname") String equipmentname);
}