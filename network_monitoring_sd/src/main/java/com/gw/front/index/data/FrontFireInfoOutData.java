package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontFireInfoOutData {
	
	private String id;

	private String time;//告警时间

	private String alarmStatus;//告警类型

	private String alarmDevicedesc;//告警内容
	
	private String equipmentDetialName;//告警设备名称

	private String dealtime;//处理时间

	private String unitName;//单位名称
	
	private String unitId; //单位Id
	
	private String childStationNum; //子站号
	
	
	private String dealCode;//处理编号
	
	private String isDeal;//是否处理
	private String dealResult;//1误报，2真实报警
	private String unitCode;//单位编号
	private String calibrationId;//标定
	private String calibrationName;//标定内容
	private String addressID;//CRT点位id
	private String buildAreaID;//CRT点位id
	private String isTest;//是否测试
	private String isPhone;//是否已经打电话
	private String type;
	private String pointVideoId;
	private String detialId;
	private String classCode;
	private String alarmWhereDesc;
	private String alarmSourceDesc;
	private String Lastupdate;
}
