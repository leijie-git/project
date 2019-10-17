package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletAlarmInfoOutData {
	private String id;
	private String isdeal;// 是否已处理
	private String deviceName;// 设备名称
	private String time;//告警时间
	private String isTest;//是否测试
	private String alarmStatus;//报警类型
	private Integer jdid;
	private String alarmSourcedesc;//报警节点
	private String alarmCat;//报警分类
	private String alarmWheredesc;//报警地点
	private String unitName;//报警单位
	private String deviceCode;//设备编号
	private String deviceIndex;//报警名称
	private String deviceNo;//设备子号
	private String dealCode;//处理编号
	private String dealTime;//处理时间
	private String dealInfo;//处理内容(app端是否误报下的描述，RTU)
	
	private String equipmentDetialName;//(RTU端口名)
	
	private String dealResult;//处理结果(是否误报)
	private String dealUser;//处理人
	private String eqName;//设备名称(RTU设备设施名)
	private String eqId;//设备id(RTU设备设施id)
	private String alarmDevicedesc;//报警描述
	private String recordType;//0操作记录，1处理记录
	private String repairSite;//维修位置
	private String picUrl;//图片
	private String badDesc	;//问题描述(是否需要维修下的描述)
	private String isNeedMaintain;//是否是需要维修
	private String addressID;//CRT点位id
	private String userId;
	private String currAddress;
	private String alarmTime;//告警时间
	private String alarmValue;//告警值
	private String normalValue;//正常值
}
