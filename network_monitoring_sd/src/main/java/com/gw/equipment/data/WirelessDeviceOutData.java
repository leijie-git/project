package com.gw.equipment.data;

import lombok.Data;

@Data
public class WirelessDeviceOutData {

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

    private String code;//设备id（多种设备之和）

    private String netdeviceid;//关联联网设备id

    private String database;//数据表名

    private String lastupate;//更新时间

}
