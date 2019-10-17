package com.gw.front.lookup.data;

import java.util.Date;

import lombok.Data;

@Data
public class FrontHdSiterwellOutData {
	private Long id;

    private Integer isrelation;

    private String ownercode;

    private Integer deviceindex;

    private Integer deviceno;

    private Integer usingtype;

    private Integer gatewayid;

    private String gatewaycode;

    private String reserve;

    private Date lastupate;

    private String devicecode;

    private String installaddr;

    private String notifyphone;

    private Integer currentstatus;

    private String devicetype;

    private String protocoltype;

    private String model;

    private String manufacturename;

    private String imsi;

    private Float lon;

    private Float lat;

    private Integer battery;

    private Integer signal;

    private Integer isphone;

    private Date firsttime;

    private Long heartbeat;
    
    private String netdeviceid;

}
