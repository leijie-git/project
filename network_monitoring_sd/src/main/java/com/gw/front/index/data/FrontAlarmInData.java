package com.gw.front.index.data;

import java.util.List;

import lombok.Data;

@Data
public class FrontAlarmInData {

	private String userId;
	
	private String loginTime;
	
	private List<String> types;
	
	private String isDeal;
	
	private String calibrationCode;

	private String selectType;//sql查找标识
}
