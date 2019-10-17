package com.gw.generatereport;

import lombok.Data;

@Data
public class MaintenanceSiteOutData {

	private String siteName;
	
	private Integer maintenanceCount;//维保总次数
	
	private String maintenanceUserName;//巡查人
}
