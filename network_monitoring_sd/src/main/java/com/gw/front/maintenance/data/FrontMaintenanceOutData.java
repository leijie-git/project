package com.gw.front.maintenance.data;

import java.util.Date;
import java.util.List;

import lombok.Data;

import javax.persistence.Column;

@Data
public class FrontMaintenanceOutData {
	//执行人
	private String executorID;
	//执行人名称
	private String executorName;
	//任务开始时间
	private Date taskStartTime;
	//任务结束时间
	private String UnitID;
	private Date taskEndTime;
	private String id;//维保编号
	private String repairCode;//维修单号
	private String alarmstatus;//报警状态
	private String baseid;//原始数据主键
	private String unitCode;// 单位编号
	private String unitName;// 单位名称
	private String repairSite;// 位置描述
	private String dealStatus;// 处理状态
	private String wBCLR;// 上报人员
	private String dateFrom;//数据来源
	private String checkInfo;// (报警点位)
	
	private String alarmWhere;//报警位置（报警主机：Alarm_DeviceDesc，RTU告警:EquipmentName+EquipmentDetialName）
	private String createtime;//上报时间
	private String repairPic;
	private List<String> repairPicList;//隐患照片
	
	private String repairDetailPic;
	private List<String> repairDetailPicList;//维修照片
	private String dealInfo;//维修说明
	private String dealDate;// 维修时间
	private String wBCLSJ;// 计划时间
	private String dealName;//维修人员
	private String expirationTime;//计划时间
	private String badDesc;//

}
