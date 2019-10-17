package com.gw.front.maintenance.data;

import lombok.Data;

@Data
public class InspectEpirationTaskExport {
	private String inspectUser;// 巡查人员
	private String siteName;// 位置
	private String isInspect;// 巡查状态
	private String unitName;// 单位名称
	private String unitCode;// 单位编号
	private String taskEnd;//任务结束时间
	private String lastDay;//剩余几天到期
	private String inspectCycleType;//周期类型
	
}
