package com.gw.equipment.data;

import lombok.Data;

@Data
public class WirelessDeviceImportData {
	//导入表字段
    private String deviceId;
    
    private String deviceName;
    
    private String nodeId;
    
    private String status;
    
    private String tags;
    
    private String deviceType;
    
    private String protocolType;

    private String model;
    
    private String manufacturerName;
    
    private String manufacturerId;
    
    private String creationTime;
    private String lastOnlineTime;
    
    private String isSecure;
    private String description;
    
    private String location;
    private String fwVersion;
    
    private String hwVersion;
    private String swVersion;
    private String imsi;
    
    private String radiusIp;
    
    private String cellId;
    private String batteryLevel;
    
    private String batteryVoltage;
    private String signalStrength;
    
}
