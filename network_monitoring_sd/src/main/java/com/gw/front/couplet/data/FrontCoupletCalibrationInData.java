package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletCalibrationInData {

	private String eqId;
	
	private String calibrationType;
	
	private String calibrationId;
	
	private String remark;//标定备注
	
	private String startDate;//标定开始时间
	
	private String endDate;//标定结束时间

}
