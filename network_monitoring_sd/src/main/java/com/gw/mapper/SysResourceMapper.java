package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.SysResource;
import com.gw.systemManager.data.SysResourceOutData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysResourceMapper extends BaseMapper<SysResource> {

	/**
	 * 查询用户拥有的资源权限
	 *
	 * @param userId
	 * @param account
	 * @return
	 */
	List<SysResourceOutData> listResourcesByUserId(@Param("userId") Long userId, @Param("account") String account,@Param("type")String type);

	/**
	 * 查询前台用户拥有的资源权限
	 * @param userId
	 * @param account
	 * @param type
	 * @return
	 */
	List<SysResourceOutData> listStageResourcesByUserId(@Param("userId") Long userId, @Param("account") String account,@Param("type")String type);


	/**
	 * 列出所有的资源
	 *
	 * @param name
	 * @param status
	 * @return
	 * @throws Exception
	 */
	List<SysResourceOutData> getAllResource(@Param("name") String name, @Param("status") String status, @Param("type") String type)
			throws Exception;

	/**
	 * 筛选资源名称
	 *
	 * @param pid
	 * @param id
	 * @return
	 */
	List<String> selectResourceNameByPidExcludeSelf(@Param("pid") Long pid, @Param("id") Long id,@Param("type")String type);

	/**
	 * 编辑资源
	 *
	 * @param id
	 * @return
	 * @throws Exception
	 */
	SysResourceOutData editResource(@Param("id") String id) throws Exception;

	/**
	 * 更新资源
	 *
	 * @param sysResource
	 * @param sessionInfo
	 * @throws Exception
	 */
	Integer updateResource(SysResource sysResource) throws Exception;

	/**
	 * 删除资源
	 *
	 * @param id
	 * @throws Exception
	 */
	Integer deleteResource(@Param("id") Long id) throws Exception;

	/**
	 * 条件查询资源
	 *
	 * @param indata
	 * @return
	 * @throws Exception
	 */
	List<SysResourceOutData> getResourceList(SysResource indata) throws Exception;

	/**
	 * 根据资源id查询资源名。用于比较名称是否重复
	 *
	 * @param id
	 * @return
	 */
	String selectResourceName(@Param("id") Long id);

}