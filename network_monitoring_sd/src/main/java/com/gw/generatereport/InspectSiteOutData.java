package com.gw.generatereport;

import lombok.Data;

@Data
public class InspectSiteOutData {

	
	private String siteName;
	
	private Integer taskCount;//巡查总次数
	
	private Integer inspectCount;//已巡查次数;
	
	private Integer expectionCount;//异常次数
	
	private String expectionRate;//异常率
	
	private String inspectUserName;//巡查人
}
