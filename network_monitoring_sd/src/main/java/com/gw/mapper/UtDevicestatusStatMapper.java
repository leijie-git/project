package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.front.index.data.FrontUtDevicestatusStatOutData;
import com.gw.mapper.entity.UtDevicestatusStat;

public interface UtDevicestatusStatMapper extends BaseMapper<UtDevicestatusStat> {

	/**
	 * 按日统计设备在离线情况
	 * 
	 * @param userId
	 * @param database
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws Exception
	 */
	List<FrontUtDevicestatusStatOutData> getdeviceStatList(@Param("userId") String userId, @Param("startDate") String startDate,
			@Param("endDate") String endDate) throws Exception;
}