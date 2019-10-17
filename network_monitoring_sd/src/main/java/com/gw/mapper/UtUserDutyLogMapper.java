package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryUserDutyLogOutData;
import com.gw.mapper.entity.UtUserDutyLog;

public interface UtUserDutyLogMapper extends BaseMapper<UtUserDutyLog> {
	
	/**
	 * 获取最后一条值班记录
	 * @return
	 */
	UtUserDutyLog getLastLog() throws Exception;
	
	/**
	 * 获取值班记录
	 * @param inData
	 * @return
	 */
	List<FrontHistoryUserDutyLogOutData> getUserDutyLogList(FrontHistoryInData inData) throws Exception;
}