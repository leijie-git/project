package com.gw.device.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class DeviceRelData {
	private String OwnerCode;
	private Integer DeviceIndex;
	private Integer DeviceNo;
	private Long id;
    private String detialname;
    private Integer iotype;
    private Integer ioport;
    private BigDecimal analogup;
    private BigDecimal analogdown;
    private String analogunit;
    private BigDecimal analogk;
    private BigDecimal analogb;
    private Integer digitalnormal;
    private String goodname;
    private String badname;
    private String remark;
    private Long eqid;
    private Long netdeviceid;
    private BigDecimal analogwarningup;
    private BigDecimal analogwarningdown;
    private String reserve;
    private Integer systype;
    private String analogpara;
    
}
