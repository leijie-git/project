package com.gw.wechat.data;

import lombok.Data;

@Data
public class WechatAlarmInfoOutData {

	private String id;
	private String unitName;
	private String alarmStatus;
	private String isTest;
	private String alarmTime;
	private String alarmCat;
	private String alarmType;

}
