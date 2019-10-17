package com.gw.generatereport;

import lombok.Data;

@Data
public class RepairSiteOutData {

	private String siteName;//点位名称
	
	private Integer count;//每个点位生成维修次数
	
	private String siteID;//点位id
	
	private String unitID;//单位id
}
