package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryAlarmInfoExport {
	private String dealResult;//处理结果
	private String isTest;//是否测试
	private String unitCode;//单位编号
	private String childstationnum;//子站号
	private String unitName;//报警单位
	private String eqName;//设备名称
	private String time;//告警时间
	private String hostTime;//主机时间
	private String alarmStatus;//报警类型
	private String alarmSourcedesc;//报警节点
	private String alarmDeviceDesc;//告警描述
	private String alarmWheredesc;//报警地点
	private String isdeal;// 是否已处理
	private String dealCode;//处理编号
	private String dealInfo;//处理内容
}
