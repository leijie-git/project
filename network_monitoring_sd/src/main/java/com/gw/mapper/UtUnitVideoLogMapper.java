package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.front.couplet.data.FrontCoupletInData;
import com.gw.front.couplet.data.FrontCoupletVideoLogOutData;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryVideoPicOutData;
import com.gw.mapper.entity.UtUnitVideoLog;

public interface UtUnitVideoLogMapper extends BaseMapper<UtUnitVideoLog> {

	/**
	 * 查询截图记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontCoupletVideoLogOutData> getUnitVideoLogs(FrontCoupletInData inData) throws Exception;

	/**
	 * 获取截图历史记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryVideoPicOutData> getVideoLogList(FrontHistoryInData inData) throws Exception;
}