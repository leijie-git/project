package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryAlarmInfoOutData {
	private String id;
	private String isdeal;// 是否已处理
	private String deviceName;// 设备名称
	private String time;//告警时间
	private String isTest;//是否测试
	private String alarmStatus;//报警类型
	private String jdid;
	private String alarmSourcedesc;//报警节点
	private String alarmCat;//报警分类
	private String alarmWheredesc;//报警地点
	private String unitName;//报警单位
	private String deviceCode;//设备编号
	private String deviceIndex;//报警名称
	private String deviceNo;//设备子号
	private String dealCode;//处理编号
	private String dealTime;//处理时间
	private String dealInfo;//处理内容
	private String dealResult;//处理结果
	private String dealUser;//处理人
	private String eqId;//设备ID
	private String eqName;//设备名称
	private String unitCode;//单位编号
	private String childstationnum;//子站号
	private String hostTime;//主机时间
	private String alarmDeviceDesc;//告警描述
	private String addressID;//CRTid
	private String unitId;
	private String netDeviceId;
	private String alarmWhereDesc;
	private String Lastupdate;
	private Integer count;
}
