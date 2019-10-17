package com.gw.front.maintenance.service;


import java.text.ParseException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.github.pagehelper.PageInfo;
import com.gw.front.index.data.UserUnitIDSiteStatus;
import com.gw.front.maintenance.data.FrontMaintenanceFireOutData;
import com.gw.front.maintenance.data.FrontMaintenanceInData;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.front.maintenance.data.FrontMaintenanceStatOutData;
import com.gw.front.maintenance.data.FrontMaintenanceTaskOutData;
import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.mapper.entity.UtLzRepair;
import com.gw.mapper.entity.UtLzRepairInData;

public interface FrontMaintenanceService {

	/**
	 * 维保巡查信息统计
	 * 
	 * @param unitId
	 * @return
	 * @throws Exception
	 */
	FrontMaintenanceStatOutData getMaintenanceStat(String unitId,String userId) throws Exception;

	/**
	 * 获取灭火器记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontMaintenanceFireOutData> getFireExtinguisherList(FrontMaintenanceInData inData,String userId) throws Exception;

	/**
	 * 导出灭火器列表
	 * 
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportFireExtinguisherList(FrontMaintenanceInData inData, HttpServletResponse response,String userId) throws Exception;

	/**
	 * 分页查询维保列表
	 * 
	 * @param inData
	 * @return
	 */
	PageInfo<FrontMaintenanceOutData> getRepairList(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 导出维修列表
	 * 
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportRepairList(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 维修流程
	 * 
	 * @param repairId
	 * @return
	 * @throws Exception
	 */	
	FrontMaintenanceOutData getRepairDetail(String repairId) throws Exception;

	/**
	 * 巡查任务列表
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<FrontMaintenanceTaskOutData> getInspectTaskList(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 导出巡查任务列表
	 * 
	 * @param inData
	 * @param response
	 * @throws Exception
	 */
	void exportInspectTaskList(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 根据任务id获取任务详情
	 * @param taskID
	 * @return
	 */
	List<FrontMaintenanceTaskOutData> getInspectTaskDetail(String taskID) throws Exception;

	/**
	 * 获取该单位快过期的任务列表
	 * @param inData
	 * @return
	 */
	PageInfo<FrontMaintenanceTaskOutData> getInspectEpirationTaskList(FrontMaintenanceInData inData) throws Exception;

	/**
	 * 导出巡查快过期任务列表
	 * @param response
	 * @param inData
	 * @throws ParseException 
	 * @throws Exception 
	 */
	void exportInspectEpirationTaskList(HttpServletResponse response, FrontMaintenanceInData inData) throws ParseException, Exception;

	/**
	 * 导出巡查月度报告
	 * @param inData
	 * @param response
	 */
	void exportInspectMouthReport(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception;

	/**
	 * 导出維保月度报告
	 * @param inData
	 * @param response
	 */
	void exportMaintenanceReport(FrontMaintenanceInData inData, HttpServletResponse response) throws Exception;
	/**
	 *@描述 统计当前用户的单位下的检查点的状态统计
	 *@创建人 Jie.Lei
	 *@参数
	 *@返回值
	 *@创建时间 2019/7/12
	 */

	UserUnitIDSiteStatus getUserUnitSiteStatus(String UnitID);

	/**
	 *@描述 巡查进度统计 总的巡查个数 已完成个数
	 *@创建人 Jie.Lei
	 *@参数
	 *@返回值
	 *@创建时间 2019/7/12
	 */

	UserUnitIDSiteStatus getSiteCountAndOkCheckCount(String UnitID);
	/**
	 * 通过联网单位Id查出维保人员
	 * lei.jie
	 */

	List<GenerateMaintenanceUserData> getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData);


	/**
	 * 分配任务
	 */
	public int updateUtLzRepair(UtLzRepairInData  utLzRepair) throws Exception ;

}
