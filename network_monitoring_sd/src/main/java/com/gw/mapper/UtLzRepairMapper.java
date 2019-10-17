package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.data.FrontMaintenanceStatOutData;
import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.generatereport.RepairSiteOutData;
import com.gw.generatereport.RemindExecutorIDAndLiableUserData;
import com.gw.mapper.entity.UtLzRepair;
import com.gw.repairr.data.MaintenanceTaskData;
import com.gw.repairr.data.UserListData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UtLzRepairMapper extends BaseMapper<UtLzRepair> {

	/**
	 * 维保进度统计
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	FrontMaintenanceStatOutData getMaintenanceStatusStat(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 分页查询单位维修列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontMaintenanceOutData> getRepairList(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 获取某单位当月维修列表
	 * @param lastTime 
	 * @param fristTime 
	 * @return
	 */
	List<UtLzRepair> selectNowMouthList(@Param("fristTime") String fristTime, @Param("lastTime")String lastTime,@Param("unitID") String unitID) throws Exception;
	
	/**
	 * 获取报警主机点位维保状况
	 * @return
	 * @throws Exception
	 */
	List<RepairSiteOutData> getBJZJRepairList(@Param("fristTime") String fristTime, @Param("lastTime")String lastTime) throws Exception;
	
	/**
	 * 获取报警主机同一点位维保人
	 * @return
	 * @throws Exception
	 */
	List<String> getBJZJSiteUserList(@Param("siteID") String siteID) throws Exception;
	/**
	 * 获取RTU点位维保状况
	 * @return
	 * @throws Exception
	 */
	List<RepairSiteOutData> getRTURepairList(@Param("fristTime") String fristTime, @Param("lastTime")String lastTime) throws Exception;
	
	/**
	 * 获取RTU同一点位维保人
	 * @return
	 * @throws Exception
	 */
	List<String> getRTUSiteUserList(@Param("siteID") String siteID) throws Exception;

	/**
	 * 获取巡查点位维保状况
	 * @return
	 * @throws Exception
	 */
	List<RepairSiteOutData> getInspectRepairList(@Param("fristTime") String fristTime, @Param("lastTime")String lastTime) throws Exception;
	
	/**
	 * 获取巡查同一点位维保人
	 * @return
	 * @throws Exception
	 */
	List<String> getInspectSiteUserList(@Param("siteID") String siteID) throws Exception;


	/**
	 * 通过联网单位Id查出维保人员
	 * lei.jie
	 */
	List<GenerateMaintenanceUserData> getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData);

	/**
	 *获取维保任务的时间 以及提醒执行人以及责任人
	 */

	 List<RemindExecutorIDAndLiableUserData>  remindExecutorIDAndLiableUser();

	/**
	 * 获取维保任务列表
	 * @param inData
	 * @return
	 */
	 List<MaintenanceTaskData> getList(MaintenanceTaskData inData);

	 List<UserListData> getMaintenancePlanList(MaintenanceTaskData inData);

	 List<UserListData> getMaintenanceUserList(MaintenanceTaskData inData);
}