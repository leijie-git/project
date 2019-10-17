package com.gw.front.maintenance.data;

import lombok.Data;

@Data
public class FrontMaintenanceExport {
	private String unitCode;// 单位编号
	private String unitName;// 单位名称
	private String wBCLR;// 维保人员
	private String badDesc;// 维保内容
	private String repairSite;// 位置
	private String dealStatus;// 处理状态
	private String dealDate;// 维修时间
	private String wBCLSJ;// 计划时间
}
