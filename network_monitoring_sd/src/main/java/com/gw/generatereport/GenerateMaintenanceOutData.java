package com.gw.generatereport;

import java.util.List;

import lombok.Data;

@Data
public class GenerateMaintenanceOutData {

private String unitName;//单位名称
	
	private Long unitId;//单位id
	
	private String unitAddress;//单位地址
	
	private String unitChargePerson;//单位负责人
	
	private String personPhone;//负责人联系电话
	
	private String receviceAccount;//邮箱账号
	
	private Integer maintenancePersonCount;//维保人数
	
	private Integer maintenanceCount;//维保总次数
	
	private String reportDate;//报告月份
	
	private String opinion;//意见总结
	
	private List<MaintenanceUserOutData> userData;//人员详情
	
	private List<MaintenanceSiteOutData> siteData;//点位详情
}
