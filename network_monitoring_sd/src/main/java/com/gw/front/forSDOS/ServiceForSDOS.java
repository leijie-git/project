package com.gw.front.forSDOS;

import java.util.List;

public interface ServiceForSDOS {

	/**
	 * 报警主机
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<FireOutData> getFireAlarmDatas(FrontSdosInData inData) throws Exception;

	/**
	 * RTU当前值
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<RtuCurrentOutData> getRTUAlarmCurrentDatas(FrontSdosInData inData) throws Exception;

	/**
	 * RTU历史记录
	 * 
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	List<RtuHistoryOutData> getRTUAlarmHistoryDatas(FrontSdosInData inData) throws Exception;

}
