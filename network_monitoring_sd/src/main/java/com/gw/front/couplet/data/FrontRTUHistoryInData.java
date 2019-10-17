package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontRTUHistoryInData {
	private String tableName;// 表名
	private String deviceNo;// 子号
	private Integer iotype;// 端口类型
	private Integer ioport;// 端口号
	private String classCode;// 类型编号
	private String className;// 类型名称
	private String detialName;// 信号名称
	private String startDate;
	private String endDate;
	private String database;
	private String analogup;//最大值
	private String analogdown;//最小值
	private String analogWarningUp;//预警最大值（单纯最大值）
	private String analogWarningDown;//预警最小值（单纯最大值）
	private String analogUnit;//单位
	private String normalValue;//正常值
	private String goodName;//正常值
	private String badName;//异常值
	private Integer interval;
}
