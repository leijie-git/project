package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnalysisAlarmStatOutData {
	private Integer fireCount;// 报警系统
	private Integer fireRealCount;// 报警系统真火警
	private Integer fireDealCount;// 报警系统已处理
	private Double fireDealResultCount;// 报警系统误报
	private Integer waterCount;// 水系统
	private Integer eleCount; // 电气火灾
	private Integer videoCount; // 视频监控
	private Integer splitCount; // 防火分离
	private Integer smokeCount; // 防排烟系统
	private Integer gaseousCount;//气体系统
	private Integer gasCount;//燃气系统
	private Integer emergencyCount;//应急疏散
	private Integer smokeFeelingCount;//无线烟感
	private Integer smokeFeelingRealCount;//无线烟感真火警
	private Integer smokeFeelingDealCount;//无线烟感已处理
	private Double smokeFeelingDealResultCount;//无线烟感误报

}
