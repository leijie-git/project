package com.gw.systemManager.service;

import javax.servlet.http.HttpServletRequest;

import com.github.pagehelper.PageInfo;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysRoleAuthorizeInData;
import com.gw.systemManager.data.SysRoleInData;
import com.gw.systemManager.data.SysRoleOutData;

/**
 * 角色业务接口
 * @author zfg
 *
 */
public interface SysRoleService {

	/**
	 * 编辑角色
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SysRoleOutData editSystemRole(Long id) throws Exception;

	/**
	 * 更新角色
	 *
	 * @param sysRole
	 * @param sessionInfo
	 * @throws Exception
	 */
	void updateSystemRole(SysRoleInData sysRole) throws Exception;

	/**
	 * 删除角色
	 *
	 * @param id
	 * @throws Exception
	 */
	void deleteSystemRole(Long id) throws Exception;

	/**
	 * 批量删除角色
	 *
	 * @param id
	 * @throws Exception
	 */
	void deleteManySystemRole(String id) throws Exception;

	/**
	 * 增加角色
	 *
	 * @param sysRole
	 * @param sessionInfo
	 * @throws Exception
	 */
	void addSystemRole(SysRoleInData sysRole) throws Exception;

	/**
	 * 把角色授权给用户
	 *
	 * @param inData
	 * @param sessionInfo
	 * @throws Exception
	 */
	void setSystemRoletoUser(SysRoleAuthorizeInData inData) throws Exception;

	/**
	 * 查询所有角色
	 *
	 * @param level
	 * @return
	 * @throws Exception
	 */
	PageInfo<SysRoleOutData> findAllRole(String roleName,String type,HttpServletRequest request) throws Exception;

	/**
	 * 查询该角色所对应的人员名
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	String selectUserToRole(Long id) throws Exception;
	/**
	 * 查询没有该角色的人员名
	 * @param id
	 * @return
	 * @throws Exception
	 */
	String selectUserNotRole(Long id) throws Exception;
	
	/**
	 * 查询人员拥有的和未拥有的角色
	 * 
	 * @param id
	 * @return
	 * @throws Exception 
	 */
	GetUserRolesData getUserRoles(Long id,String type) throws Exception;




}
