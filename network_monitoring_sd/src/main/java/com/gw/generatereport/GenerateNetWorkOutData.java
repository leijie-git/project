package com.gw.generatereport;

import java.util.List;

import lombok.Data;

@Data
public class GenerateNetWorkOutData {

	private String unitName;//单位名称
	
	private Long unitId;//单位id
	
	private String unitAddress;//单位地址
	
	private String unitChargePerson;//单位负责人
	
	private String personPhone;//负责人联系电话
	
	private String receviceAccount;//邮箱账号
	
	private Integer inspectPersonCount;//巡查人数
	
	private Integer taskCount;//巡查总次数
	
	private Integer inspectCount;//已巡查次数;
	
	private String completionRate;//完成率
	
	private String reportDate;//报告月份
	
	private String opinion;//意见总结
	
	private List<InspectUserOutData> userData;//人员详情
	
	private List<InspectSiteOutData> siteData;//点位详情
	
}
