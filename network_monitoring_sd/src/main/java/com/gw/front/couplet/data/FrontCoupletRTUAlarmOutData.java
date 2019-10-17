package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletRTUAlarmOutData {
	private String id;//主键
	private String equipmentDetialName;// 检测设备详情名称
	private String normalValue;// 正常值
	private String alarmValue;// 报警值
	private String alarmTime;// 报警时间
	private String dealUser;// 处理人
	private String dealTime;// 处理时间
	private String dealResult;// 处理结果
	private String alarmCode;// 处理编号
	private String isDeal;// 是否处理
	private String unitName;//单位名称
	private String unitCode;//单位编号
	private String interfaceInfo;//接口信息
	private String alarmDevicedesc;//报警描述
}
