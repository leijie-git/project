package com.gw.alarm.service;

import com.github.pagehelper.PageInfo;
import com.gw.alarm.data.AlarmInData;
import com.gw.alarm.data.PhoneOutData;
import com.gw.mapper.entity.AddresselHostpointOut;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AlarmInfoService {

	/**
	 * 接收RTU告警
	 *
	 * @param request
	 * @throws Exception
	 */
	void sendRTUAlarm(HttpServletRequest request) throws Exception;

	/**
	 * 接收报警主机告警
	 *
	 * @param request
	 * @throws Exception
	 */
	void sendBJZJAlarm(HttpServletRequest request) throws Exception;

	/**
	 * 获取需要拨打的电话
	 * 
	 * @return
	 * @throws Exception
	 */
	List<PhoneOutData> getPhones() throws Exception;

	/**
	 * 更新状态
	 * 
	 * @param ids
	 * @throws Exception
	 */
	void updatePhoneStatus(String ids) throws Exception;

	/**
	 *@描述  根据主机ID查询当前主机的所有点位
	 *@创建人 Jie.Lei
	 *@参数
	 *@返回值
	 *@创建时间 2019/7/24
	 */
	PageInfo<AddresselHostpointOut> getPointByEqid(AlarmInData alarmInData);

}
