package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletFireAlarmExport {
	private String eqName;//设备名称
	private String time;//告警时间
	private String isTest;//是否测试
	private String alarmStatus;//报警类型
	private String alarmSourcedesc;//报警节点
	private String alarmCat;//报警分类
	private String alarmWheredesc;//报警地点
	
}
