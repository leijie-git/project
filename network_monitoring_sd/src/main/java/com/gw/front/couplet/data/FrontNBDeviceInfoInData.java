package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontNBDeviceInfoInData {
	private String keyword;
	private String userId;
	private String database;
	private Integer currentStatus;
	
	private Integer pageNumber;
	private Integer pageSize;
}
