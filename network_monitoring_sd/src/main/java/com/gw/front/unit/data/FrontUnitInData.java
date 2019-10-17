package com.gw.front.unit.data;

import lombok.Data;

@Data
public class FrontUnitInData {
	private Integer pageNumber;
	private Integer pageSize;
	private String startDate;
	private String endDate;
	private String buildingName;
	private String unitId;
}
