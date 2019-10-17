package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontStatisticalInData {
	private Integer pageNumber;
	private Integer pageSize;
	private Integer alarmStatus;
	private Integer calibrationType;
	private Integer deviceStatus;
	private String keyword;
	private String unitId;
	private String startDate;
	private String endDate;
	private String userId;
	private String database;
	private String isDeal;
	private String unitCode;
	private String unitname;
}
