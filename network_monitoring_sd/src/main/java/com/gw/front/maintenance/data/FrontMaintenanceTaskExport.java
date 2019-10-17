package com.gw.front.maintenance.data;

import lombok.Data;

@Data
public class FrontMaintenanceTaskExport {
	private String unitCode;// 单位编号
	private String unitName;// 单位名称
	private String inspectUser;// 巡查人员
	private String planName;// 计划名称
	private String planTime;//计划时间
	private String siteName;// 位置
	private String isInspect;// 处理状态
	private String inspectTime;// 时间
}
