package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.front.index.data.FrontNotifyOutData;
import com.gw.mapper.entity.UtUnitNotify;
import com.gw.systemManager.data.NotifyInData;
import com.gw.systemManager.data.NotifyOutData;

public interface UtUnitNotifyMapper extends BaseMapper<UtUnitNotify> {

	/**
	 * 站内通知
	 * 
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	List<FrontNotifyOutData> getNotifyList(@Param("userId")String userId) throws Exception;
	
	/**
	 * 获取通知分页数据
	 * @param title
	 * @return
	 * @throws Exception
	 */
	List<NotifyOutData> getNotifyPageList(NotifyInData inData) throws Exception;
	
	/**
	 * 根据id查询通知
	 * @param id
	 * @return
	 * @throws Exception
	 */
	FrontNotifyOutData getNotifyInfoById(@Param("id") String id) throws Exception;
}