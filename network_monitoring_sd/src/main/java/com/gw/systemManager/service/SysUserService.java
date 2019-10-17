package com.gw.systemManager.service;

import com.github.pagehelper.PageInfo;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysRoleAuthorizeInData;
import com.gw.systemManager.data.SysUserInData;
import com.gw.systemManager.data.SysUserOutData;

import javax.servlet.http.HttpServletRequest;

public interface SysUserService {

	/**
	 * 查询所有用户信息
	 * @param userName
	 * @param request
	 * @return
	 * @throws Exception
	 */
	PageInfo<SysUserOutData> getUserList(String userName,HttpServletRequest request) throws Exception;

	/**
	 * 添加用户
	 * @param sysUserInData
	 * @param request
	 * @throws Exception
	 */
	void addUser(SysUserInData sysUserInData, HttpServletRequest request) throws Exception;

	/**
	 * 删除用户
	 * @param id
	 * @throws Exception
	 */
	void deleteUser(Long id) throws Exception;

	/**
	 * 批量删除用户
	 * @param id
	 * @throws Exception
	 */
	void deleteManyUser(String id) throws Exception;

	
	/**
	 * 更新用户信息
	 * @param sysUserInData
	 * @throws Exception
	 */
	void updateUser(SysUserInData sysUserInData) throws Exception;

	/**
	 * 为用户设置角色
	 * @param sysRoleAuthorizeInData
	 * @throws Exception
	 */
	void setUserRole(SysRoleAuthorizeInData sysRoleAuthorizeInData) throws Exception;

	/**
	 * 获取用户角色
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	GetUserRolesData getUserRole(Long userId,String type) throws Exception;

	/**
	 * 后台人员管理重置密码
	 * @param id
	 * @throws Exception
	 */
	void resetPas(String id) throws Exception;
	
}
