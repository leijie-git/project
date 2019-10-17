package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnalysisUnitExport {
	private String unitCode;//单位编号
	private String childStationNum;//子站号
	private String unitName;//单位名称
	private Integer deviceCount;// 设备总数
	private Integer lookupCount;// 查岗次数
	private Integer lookupAnswerCount;// 查岗应答次数
	private Integer longTime;// 接通时长
	private Integer messageCount;// 短信数量
	private String totalFlow;// 总流量
	private String sendFlow;// 发出流量
	private String receiveFlow;// 接收流量
}
