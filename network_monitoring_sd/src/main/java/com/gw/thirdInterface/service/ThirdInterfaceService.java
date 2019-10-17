package com.gw.thirdInterface.service;

import java.util.Date;
import java.util.List;

import com.gw.alarm.data.AlarmBJZJOutData;
import com.gw.alarm.data.AlarmRTUOutData;
import com.gw.front.unit.data.FrontUnitCRTOutData;
import com.gw.mapper.entity.UtLzBjzjalarm;
import com.gw.thirdInterface.data.DictOutData;
import com.gw.thirdInterface.data.TreeOutData;

public interface ThirdInterfaceService {

	/**
	 * 上传单位信息
	 * @param unitID
	 */
	void setOrg(String unitID, String companyName) throws Exception;

	/**
	 * 上传联网设备信息
	 * @param netDeviceID
	 * @throws Exception
	 */
	void setDevice(String netDeviceID,String unitID, String companyName) throws Exception;

	/**
	 * 设备上下线
	 * @param unitID
	 * @param netDeviceID
	 * @throws Exception 
	 */
	void setDeviceStatus(String unitId, String netDeviceId) throws Exception;

	/**
	 * 上传点位信息
	 * @param netDeviceID
	 * @param isFire 
	 * @param companyName 
	 * @param companyName 
	 */
	void setPoint(String pointID, String isFire, String unitID) throws Exception;

	/**
	 * 实时告警
	 * @param uuid
	 * @param event
	 * @param milliseconds
	 * @param address 
	 * @throws Exception 
	 */
	void realTimeAlarmAX(String uuid, String event, String milliseconds, String address) throws Exception;

	/**
	 * 消警
	 * @param netDeviceId
	 * @param unitId
	 * @throws Exception 
	 */
	void clearAlarm(String netDeviceId, String unitId) throws Exception;

	/**
	 * 获取字典数据
	 * @param t
	 * @throws Exception 
	 */
	List<DictOutData> getDict(String t) throws Exception;

	/**
	 * 获取树形数据
	 * @param t
	 * @return
	 * @throws Exception 
	 */
	List<TreeOutData> getTree(String t) throws Exception;
	
	/**
	 * 新门海平台RTU实时报警
	 * @param data
	 * @param alarmvalue
	 * @param alarmTime
	 * @throws Exception
	 */
	void xmhRtuAlarm(AlarmRTUOutData data, String alarmvalue, Date alarmTime) throws Exception;
	
	/**
	 * 新门海平台报警主机实时报警
	 * @param alarm
	 * @param data
	 * @param string
	 * @throws Exception
	 */
	void xmhBjzjAlarm(UtLzBjzjalarm alarm, AlarmBJZJOutData data, String ownercode,FrontUnitCRTOutData crtDatas) throws Exception;

}
