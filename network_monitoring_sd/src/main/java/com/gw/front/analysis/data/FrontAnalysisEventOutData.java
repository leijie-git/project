package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnalysisEventOutData {
	private String id;
	private String ownercode;
	private String eventType;
	private String eventTypeName;
	private String startTime;
	private String endTime;
	private String startreason;
	private String endReason;
	private String reserve;
	private String lastupdate;
	private String deviceindex;
	private String deviceno;
	private String unitid;
	private String eventUrl;
	private String childStationNum;
	private String unitName;
	private String unitCode;
}
