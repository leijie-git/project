package com.gw.unit.data;

import lombok.Data;
/**
 * 维保单位输入实体
 * @author SY
 *
 */

@Data
public class MaintenanceUnitOutData {
	private String id;
	
	private String unitcode;
	
	private String unitname;
	
	private String address;
	
	private String contacts;
	
	private String telephone;
	
	private String remark;
	
	private String pointx;
	
	private String pointy;
	
	private String pid;
	
	private String proviceid;
	
	private String cityid;
	
	private String regionid;
	private String townid;
	private String logourl;
	private String email;
	private String systemNumber;

	private Integer mainType;//2监管 1维保
}
