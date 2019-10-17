package com.gw.alarm.data;

import lombok.Data;

@Data
public class EmployeeForSendMsgData {

	private String id;//用户id

	private String username;//用户名称

	private String useraccount;//用户账号

	private String mobileno;//手机号

	private String openid;//微信端推送关键字

	private String unitid;//单位id

	private String unitName;//单位名称

	private String eqName;//设备名称

	private String onwerCode;//设备编号

	private String alarmTime;//报警时间

	private String alarmArea;//报警地点

	private String alarmSourceDesc;//报警描述

	private String alarmType;//报警类型

	private String alarmDetail;//节点详情

	private String channelid;//app推送关键字段

	private String phoneName;//app推送关键字段

	private String calibrationId;//单位标定描述

	private String alarmId;//告警Id

	private String receiveAlarmType;//接收报警类型

	private String alarmvalue;//报警值
	private String deviceno;//设备编号
	private String ioport;//端口号
	private String scope;//参考范围
	private String detialname;//信号名称
	private String expireDate;//药剂到期时间
	private String extinguisherCode;//灭火器编号
	private String extinguisherType;//灭火器类型
	private String extinguisherPosition;//灭火器位置
	private String validityDate;//灭火器报销时间
	private String sitename;//巡查计划名称
	private String taskUserName;//巡查执行人员
	private String sitedesc;//巡查任务描述
	private String taskstart;
	private String taskend;
	private String buildAreaId;

}
