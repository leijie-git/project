package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.device.data.AddressRelOutData;
import com.gw.mapper.entity.UtUnitNetdeviceRel;

public interface UtUnitNetdeviceRelMapper extends BaseMapper<UtUnitNetdeviceRel> {
	
	List<AddressRelOutData> getAllNetDeviceRel(@Param("netdeviceid")Long netdeviceid);
}