package com.gw.unit.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.GetUserUnitsData;
import com.gw.unit.data.MaintenanceUserInData;
import com.gw.unit.data.MaintenanceUserOutData;
import com.gw.unit.data.PlanUserRoleLOutData;
import com.gw.unit.data.UserReUnitInData;

/**
 * 维保单位人员管理
 * @author SY
 *
 */
public interface MaintenanceUserService {
	
	/**
	 * 添加维保单位人员
	 * @param userId 
	 * @param inData
	 */
	void addMaintenanceUser(Long userId, MaintenanceUserInData inData) throws Exception;
	
	/**
	 * 分页查寻人员
	 * @param request
	 * @param username
	 * @param account 
	 * @param unitname 
	 * @return
	 */
	PageInfo<MaintenanceUserOutData> list(HttpServletRequest request,String username, String unitname, String account,String UnitID) throws Exception;
	
	/**
	 * 更新人员
	 * @param inData
	 * @throws Exception
	 */
	void updateMaintenanceUser(MaintenanceUserInData inData) throws Exception;
	
	/**
	 * 删除人员
	 * @param id
	 * @throws Exception
	 */
	void deleteMaintenanceUser(Long id) throws Exception;

	/**
	 * 根据单位查询人员
	 * @param unitID
	 * @return 
	 */
	List<MaintenanceUserOutData> getUserList(String unitID,String userrole) throws Exception;
	
	/**
	 * 查询维保单位人员关联联网单位
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	PageInfo<GetUserUnitsData> getUserReUnit(UserReUnitInData inData) throws Exception;
	
	/**
	 * 设置维保单位人员关联联网单位
	 * @param userId
	 * @param manyUnitId
	 * @throws Exception
	 */
	void setUserReUnit(String userId, String manyUnitId) throws Exception;

	/**
	 * 根据计划id查询计划巡查人（已分配和未分配）
	 * @param unitID
	 * @return
	 * @throws Exception
	 */
	PlanUserRoleLOutData getUserRoleList(String siteID,String UnitID,String userrole)throws Exception;

	/**
	 * 删除人员关联单位
	 * @param userId
	 * @param unitIds
	 * @throws Exception 
	 */
	void delUserReUnit(String userId, String unitIds) throws Exception;

	/**
	 * 维保用户重置密码
	 * @param id
	 * @throws Exception
	 */
	void resetPas(String id) throws Exception;
}
