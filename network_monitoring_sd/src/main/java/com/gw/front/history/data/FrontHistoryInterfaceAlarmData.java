package com.gw.front.history.data;

import lombok.Data;

/**
 * 电气火灾
 *
 * @author Administrator
 *
 */
@Data
public class FrontHistoryInterfaceAlarmData {
	private String id;//主键
	private String equipmentDetialName;// 检测设备详情名称
	private String equipmentName;
	private String eqName;
	private String normalValue;// 正常值
	private String alarmValue;// 报警值
	private String alarmTime;// 报警时间
	private String netDeviceID;//报警设备
	private String eqId;//设备ID
	private String alarmStatus;//报警类型
	private String dealUser;// 处理人
	private String dealInfo;//处理内容
	private String dealTime;// 处理时间
	private String dealResult;// 处理结果
	private String alarmCode;// 处理编号
	private String isDeal;// 是否处理
	private String unitName;//单位名称
	private String unitCode;//单位编号
	private String unitId;//单位编号
	private String alarmCat;//描述
	private int count;//
}
