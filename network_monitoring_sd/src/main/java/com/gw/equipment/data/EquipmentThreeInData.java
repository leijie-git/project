package com.gw.equipment.data;


import lombok.Data;

@Data
public class EquipmentThreeInData {

	 	private String detailid;

	    private String detialname;

	    private String iotype;

	    private Integer ioport;

	    private String analogup;
	    
	    private String analogdown;

	    private String analogunit;

	    private String analogk;

	    private String analogb;

	    private String digitalnormal;

	    private String reserve;
	    
	    private String maxValue;//最大值
	    
	    private String minValue;//最小值
}
