package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnalysisEventExport {
	private String eventTypeName;
	private String unitCode;
	private String childStationNum;
	private String unitName;
	private String startTime;
	private String endTime;
	private String endReason;
}
