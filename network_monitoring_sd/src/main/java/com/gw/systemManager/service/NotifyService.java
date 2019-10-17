package com.gw.systemManager.service;

import com.github.pagehelper.PageInfo;
import com.gw.systemManager.data.NotifyInData;
import com.gw.systemManager.data.NotifyOutData;
import com.gw.systemManager.data.NotifyRelUserOutData;

/**
 * 通知服务层
 * @author SY
 * @data 2018年9月21日
 */

public interface NotifyService {
	
	/**
	 * 获取通知分页数据
	 * @param pageNumber
	 * @param pageSize
	 * @param title
	 * @return
	 */
	PageInfo<NotifyOutData> pageList(NotifyInData inData) throws Exception;
	
	/**
	 * 新增通知
	 * @param inData
	 */
	void addNotify(Long sender, NotifyInData inData) throws Exception;
	
	/**
	 * 更新通知
	 * @param inData
	 */
	void updateNotify(NotifyInData inData) throws Exception;
	
	/**
	 * 删除通知
	 * @param id
	 * @param userId 
	 */
	void deleteNotify(String notifyId) throws Exception;

	/**
	 * 获得所有人员
	 * @param userIds 
	 */
	PageInfo<NotifyRelUserOutData> getAllUserSelect(Integer pageNumber, Integer pageSize, String userIds) throws Exception;
	
	/**
	 * 获得通知关联的用户
	 * @param id
	 * @param userIds 
	 * @return
	 */
	PageInfo<NotifyRelUserOutData> getNotifyRelUser(Integer pageNumber, Integer pageSize, String id, String userIds) throws Exception;
	
	/**
	 * 获得未关联通知的用户
	 * @param pageNumber
	 * @param pageSize
	 * @param userIds 
	 * @param alReceiverName
	 * @return
	 */
	PageInfo<NotifyRelUserOutData> getAllUserDisSelect(Integer pageNumber, Integer pageSize, String id, String userIds) throws Exception;
	
	/**
	 * 添加新绑定通知的用户
	 * @param id
	 * @param receiver
	 * @throws Exception
	 */
	void addNotifyRelUser(String id, String receiver) throws Exception;
	
	/**
	 * 删除通知关联的用户
	 * @throws Exception
	 */
	void deleteNotifyRelUser(String notifyId,String userId) throws Exception;
}
