package com.gw.mapper;


import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.mapper.entity.UtLzRepairdetial;
import com.gw.wechat.data.FrontRepairDetailOutData;

public interface UtLzRepairdetialMapper extends BaseMapper<UtLzRepairdetial> {

	/**
	 * 维修流程
	 * 
	 * @param repairId
	 * @return
	 * @throws Exception
	 */
	FrontMaintenanceOutData getRepairDetail(@Param("repairId") String repairId) throws Exception;

	/**
	 * 查看维保详情
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	FrontRepairDetailOutData getMaintenanceDetail(@Param("id") String id) throws Exception;
}