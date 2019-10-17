package com.gw.front.index.data;

import lombok.Data;

@Data
public class RealTimeAlarmCount {

	private int alarmCount;//正常火警

	private int testAlarmCount;//测试火警数量

	private int faultAlarmCount;//故障报警

	private int shieldAlarmCount;//屏蔽报警

	private int otherAlarmCount;//其他告警

	private int waterAlarmCount;//水系统告警

	private int electricAlarmCount;//电气火灾告警

	private int inforAlarmCount;//用户信息传输装置

	private int extingAlarmCount;//气体灭火告警

	private int onlineAlarmCount;//设备离在线告警
}
