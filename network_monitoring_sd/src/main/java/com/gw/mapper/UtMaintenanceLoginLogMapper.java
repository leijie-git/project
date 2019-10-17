package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.front.index.data.FrontLoginLogOutData;
import com.gw.mapper.entity.UtMaintenanceLoginLog;

public interface UtMaintenanceLoginLogMapper extends BaseMapper<UtMaintenanceLoginLog> {

	/**
	 * 登录日志
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<FrontLoginLogOutData> getLoginLogs(@Param("userId") String userId, @Param("dataFrom") String dataFrom)
			throws Exception;

	/**
	 * 通过ip查询地址
	 * 
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	String selectAddressByIP(String ip) throws Exception;

	/**
	 * 插入数据
	 * 
	 * @param ip
	 * @param address
	 * @throws Exception
	 */
	void insetIPAddress(@Param("ip")String ip, @Param("address")String address) throws Exception;
}