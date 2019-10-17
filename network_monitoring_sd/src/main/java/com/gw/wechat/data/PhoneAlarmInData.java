package com.gw.wechat.data;

import lombok.Data;

@Data
public class PhoneAlarmInData {
	private String userId;
	private String isDeal;
	private Integer alarmStatus;
	private String alarmType;
	private String keyword;
	private String selectType;//sql查找标识
}
