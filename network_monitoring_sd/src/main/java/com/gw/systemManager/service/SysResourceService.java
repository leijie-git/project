package com.gw.systemManager.service;

import com.gw.systemManager.data.SysResourceIndata;
import com.gw.systemManager.data.SysResourceOutData;

import java.util.List;

public interface SysResourceService {

	/**
	 * 列出所有的资源
	 *
	 * @param name
	 * @param status
	 * @return
	 * @throws Exception
	 */
	List<SysResourceOutData> getAllResource(String name, String status, String type) throws Exception;

	/**
	 * 增加资源
	 *
	 * @param sysResource
	 * @param userId
	 * @throws Exception
	 */
	void addSystemResource(SysResourceIndata sysResource) throws Exception;

	/**
	 * 编辑资源
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SysResourceOutData editSystemResource(String id) throws Exception;

	/**
	 * 更新资源
	 *
	 * @param inData
	 * @param sessionInfo
	 * @throws Exception
	 */
	void updateSystemResource(SysResourceIndata inData, Long userId) throws Exception;

	/**
	 * 删除资源
	 *
	 * @param id
	 * @throws Exception
	 */
	void deleteSystemResource(Long id) throws Exception;

	/**
	 * 初始化后台用户拥有的权限列表
	 *
	 * @param userId
	 * @param type
	 * @param account
	 * @return
	 * @throws Exception
	 */
	List<SysResourceOutData> listResourcesByUserId(Long userId, String account,String type) throws Exception;

	/**
	 * 初始化前台用户拥有的权限列表
	 *
	 * @param userId
	 * @param type
	 * @param account
	 * @return
	 * @throws Exception
	 */
	List<SysResourceOutData> listStageResourcesByUserId(Long userId, String account,String type) throws Exception;

	/**
	 * 分页查询资源
	 *
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	List<SysResourceOutData> resourceList(SysResourceIndata indata) throws Exception;

}
