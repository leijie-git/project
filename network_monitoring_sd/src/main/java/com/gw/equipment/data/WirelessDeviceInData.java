package com.gw.equipment.data;

import lombok.Data;

@Data
public class WirelessDeviceInData {

    private String id;

    private Integer isrelation;

    private String ownercode;

    private Integer deviceindex;

    private Integer deviceno;

    private Integer usingtype;

    private Integer gatewayid;

    private String gatewaycode;

    private String reserve;

    private String devicecode;

    private String installaddr;

    private String notifyphone;

    private Integer currentstatus;

    private String devicetype;

    private String protocoltype;

    private String model;

    private String manufacturename;

    private String imsi;

    private String lon;

    private String lat;

    private Integer battery;

    private Integer signal;

    private Integer isphone;

    private String heartbeat;

    private String netdeviceid;

    private Integer pageNumber;

    private Integer pageSize;

    private String partcode;//设备id

    private String keyword;//关键字

    private String nsId;//沈工表中id

    private String database;//数据库表名

}
