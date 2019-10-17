package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletRTUAlarmExport {

	private String dealResult;//处理结果
	private String unitCode;//
	private String unitName;//
	private String alarmTime;//
	private String interfaceInfo;
	private String alarmDevicedesc;
	private String isDeal;//
	private String alarmCode;// 处理编号
	
}
