package com.gw.front.analysis.data;


import lombok.Data;

@Data
public class FrontAnalysisInData {

	private Integer pageNumber;
	private Integer pageSize;
	private String isTest;
	private String startDate;//开始时间
	private String endDate;//结束时间
	private String keyword;//关键字
	private String userId;//用户id
	private String database;//数据库
	private String eventType;//事件类型
	private String unitId;//单位id
	private String sqlType;
	private String isDeal;//0未处理，1已处理
	private String dealResult;//1误报，2真实报警
	private String ioType;//1：模拟量 2：数字量
	private String type;//
}
