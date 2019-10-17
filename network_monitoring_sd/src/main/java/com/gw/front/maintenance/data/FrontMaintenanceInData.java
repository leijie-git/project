package com.gw.front.maintenance.data;

import lombok.Data;

@Data
public class FrontMaintenanceInData {
	private Integer pageNumber;
	private Integer pageSize;
	private String dateType;
	private String startDate;
	private String endDate;
	private String keyword;
	private String unitId;
	private String status;
	private String userId;
	private String buildId;
	private String nowDate;//当前时间
	
}
