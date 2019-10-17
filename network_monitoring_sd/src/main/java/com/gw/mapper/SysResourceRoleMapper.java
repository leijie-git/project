package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.SysResourceRole;

public interface SysResourceRoleMapper extends BaseMapper<SysResourceRole> {
	/**
	 * 查询角色的资源权限
	 * 
	 * @param id
	 * @return
	 */
	List<String> selectResourceListByRoleId(Long id);

	/**
	 * 批量插入资源权限
	 *
	 * @param roleId
	 * @param resource
	 * @return
	 */
	int insertBatch(@Param("roleId") Long roleId, @Param("resource") String[] resource);

	/**
	 * 删除角色资源关联关系
	 * 
	 * @param roleId
	 */
	void deleteByRoleId(Long roleId);
}