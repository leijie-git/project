package com.gw.device.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class EqClassOutData {

	private String id;

	private Integer classcode;

	private String classname;

	private Integer classtype;

	private String areacode;

	private String classimg;

	private BigDecimal imgwidth;

	private BigDecimal imgheight;

	private Integer monitornum;

	private Integer status;

	private String remark;

	private Integer isdelete;

	private Integer sortorder;

	private Integer cycle;

	private Integer cycleunit;

	private Integer frequency;

	private String cycleinfo;

	private String mincycleinfo;

	private Integer inspectcycle;

	private Integer inspectcycleunit;

	private Integer inspectfrequency;

	private String eqsystemid;
	
	private String eqsystemname;
}
