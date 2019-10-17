package com.gw.front.lookup.data;

import lombok.Data;

@Data
public class FrontLookupInData {
	private String keyword;
	private Integer pageNumber;
	private Integer pageSize;
	private String result;
	private String userId;
	private String startDate;
	private String endDate;
	private String unitId;
	private String database;
}
