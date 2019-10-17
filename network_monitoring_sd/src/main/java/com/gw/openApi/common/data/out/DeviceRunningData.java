package com.gw.openApi.common.data.out;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DeviceRunningData {
    private long netDeviceId;
    private int deviceStatus;
    private String originalStatus;

    private Map<String,List<InterfaceRunningData>> interfaceStatusList;//接口状态数据

}
