package com.gw.unit.service;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.gw.unit.data.BaseInfoSelectOutData;
import com.gw.unit.data.NetworkingUserInData;
import com.gw.unit.data.NetworkingUserOutData;

/**
 * 维保单位人员管理
 * @author SY
 *
 */
public interface NetworkingUserService {
	
	/**
	 * 添加维保单位人员
	 * @param userId 
	 * @param inData
	 */
	void addNetworkingUser(Long userId, NetworkingUserInData inData) throws Exception;
	
	/**
	 * 分页查寻人员
	 * @param userId 
	 * @param userId 
	 * @param request
	 * @param username
	 * @return
	 */
	PageInfo<NetworkingUserOutData> list(Long userId, NetworkingUserInData inData) throws Exception;
	
	/**
	 * 更新人员
	 * @param inData
	 * @throws Exception
	 */
	void updateNetworkingUser(Long id, NetworkingUserInData inData) throws Exception;
	
	/**
	 * 删除人员
	 * @param id
	 * @throws Exception
	 */
	void deleteNetworkingUser(String id) throws Exception;
	
	
	/**
	 * 为下拉框选择数据
	 * @return
	 * @throws Exception
	 */
	List<BaseInfoSelectOutData> selectUnitnameAndId() throws Exception;

	/**
	 * 重置用户密码
	 * @param id
	 * @throws Exception 
	 */
	void resetPas(String id) throws Exception;
}
