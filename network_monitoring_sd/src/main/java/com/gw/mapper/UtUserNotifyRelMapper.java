package com.gw.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.gw.common.BaseMapper;
import com.gw.mapper.entity.UtUserNotifyRel;
import com.gw.systemManager.data.NotifyRelUserOutData;

public interface UtUserNotifyRelMapper extends BaseMapper<UtUserNotifyRel> {
	
	List<NotifyRelUserOutData> getNotifyRelUser(@Param("notifyId")String notifyId);
}