package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontUnitFireListExport {
	private String eqName;//报警设备
	private String alarmStatus;//报警类型
	private String time;//告警时间
	private String alarmWheredesc;//报警地点
	private String alarmSourcedesc;//报警节点
	private String alarmCat;//报警描述
	private int count;//次数
}
