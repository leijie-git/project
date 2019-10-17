package com.gw.mapper;

import java.util.List;

import com.gw.common.BaseMapper;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.front.history.data.FrontHistoryOperationOutData;
import com.gw.mapper.entity.SysOperationLog;

public interface SysOperationLogMapper extends BaseMapper<SysOperationLog> {

	/**
	 * 操作记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryOperationOutData> getOperationList(FrontHistoryInData inData) throws Exception;

	/**
	 * 处理记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FrontHistoryOperationOutData> getDealOperationList(FrontHistoryInData inData) throws Exception;

}