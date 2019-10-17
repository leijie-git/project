package com.gw.generatereport;

import lombok.Data;

@Data
public class InspectUserOutData {

	private String userName;//巡查人员
	
	private Integer taskCount;//总任务数
	
	private Integer inspectCount;//巡查任务数
	
	private String completionRate;//巡查完成率
}
