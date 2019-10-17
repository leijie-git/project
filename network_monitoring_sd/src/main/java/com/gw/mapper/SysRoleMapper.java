package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.SysRole;
import com.gw.systemManager.data.SysRoleInData;
import com.gw.systemManager.data.SysRoleOutData;

public interface SysRoleMapper extends BaseMapper<SysRole> {
	/**
	 * 查询所有角色(根据角色名称查询)
	 * 
	 * @return
	 */
	List<SysRoleOutData> selectRoles(@Param("roleName") String roleName,@Param("type")String type);
	
	/**
	 * 添加角色
	 * @param sysRoleData
	 * @return
	 */
	Integer addRoles(SysRoleInData sysRoleData);
	
	/**
	 * 编辑角色
	 * @param id
	 * @return
	 */
	SysRoleOutData editRoles(@Param("id") Long id);
	
	/**
	 * 更新角色
	 * @param sysRoleData
	 * @return
	 */
	Integer updateRoles(SysRoleInData sysRoleData);
	
	/**
	 * 批量删除角色
	 * @param id
	 * @return
	 */
	Integer deleteManyRole(@Param("id") String[] id);
	
	/**
	 * 查询该角色所对应的人员
	 * @param id
	 * @return
	 */
	String selectUserToRole(@Param("id") Long id);
	
	/**
	 * 查询该角色没有的人员
	 * @param id
	 * @return
	 */
	String selectUserNotRole(@Param("id") Long id);
	
	/**
	 * 将某个角色分配给用户
	 * @param id
	 * @return
	 */
	Integer setRoleToUser(@Param("userId") Long userId,@Param("roleId") Long roleId);
	
	/**
	 * 删除一个角色对应的所有用户
	 * @param roleId
	 * @return
	 */
	Integer deleteAllUser(@Param("roleId") Long roleId);

	/**
	 * 根据角色id查询角色名
	 * @param id
	 * @return
	 */
	String selectRoleName(@Param("id") Long id);

	/**
	 * 更新角色信息
	 * @param role
	 * @return
	 */
	Integer updateRole(SysRole role);

	Integer insertEmployee(SysRole role);


}