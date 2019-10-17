package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.SysUser;
import com.gw.systemManager.data.SysUserOutData;

public interface SysUserMapper extends BaseMapper<SysUser> {

	/**
	 * 用户列表
	 * 
	 * @param userName
	 * @return
	 * @throws Exception
	 */
	List<SysUserOutData> selectAllUser(@Param("userName")String userName) throws Exception;
}