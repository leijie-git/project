package com.gw.equipment.data;

import lombok.Data;

@Data
public class EquipmentRTUInData {
	  
		private String detailid;

	    private String detailname;
	    
	    private Integer iotype;

	    private Integer ioport;

	    private Integer digitalnormal;

	    private String goodname;

	    private String badname;

	    private String remark;

	    private String eqid;
	    
	    private String reserve;//信号名称
	    
}
