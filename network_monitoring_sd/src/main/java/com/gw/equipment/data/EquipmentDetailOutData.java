package com.gw.equipment.data;

import lombok.Data;

@Data

public class EquipmentDetailOutData {

	 private String id;//端口id

	    private String detialname;//端口名称
	    
	    private Integer iotype;

	    private Integer ioport;

	    private String analogup;

	    private String analogdown;

	    private String analogunit;

	    private String analogk;

	    private String analogb;

	    private Integer digitalnormal;

	    private String goodname;

	    private String badname;

	    private String remark;

	    private String eqid;
	    
	    private String reserve;

	    private String classCode;

	    private String maxValue;//最大值
	    
	    private String minValue;//最小值
	    
	    private String eqSystemID;//系统id
}
