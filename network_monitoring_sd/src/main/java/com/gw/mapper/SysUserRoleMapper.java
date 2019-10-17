package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.SysUserRole;
import com.gw.systemManager.data.GetUserRolesData;
import com.gw.systemManager.data.SysRoleOutData;

public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {
	/**
	 * 获取用户角色
	 * 
	 * @param userId
	 * @return
	 */
	List<SysRoleOutData> getUserRoleByUserId(@Param("userId")Long userId);

	/**
	 * 查询人员拥有的角色
	 * 
	 * @param id
	 * @return
	 */
	List<GetUserRolesData> getUserHasRole(@Param("id")Long id,@Param("type")String type);

	/**
	 * 获取人员未拥有的角色
	 * 
	 * @param id
	 * @return
	 */
	List<GetUserRolesData> getUserNoRole(@Param("id")Long id,@Param("type")String type);

	/**
	 * 删除人员角色
	 * 
	 * @param userId
	 * @throws Exception
	 */
	void deleteByUserId(@Param("userId")Long userId) throws Exception;
	
	Integer addRoleToUser(@Param("roleId") Long roleId,@Param("userId") Long userId);
}