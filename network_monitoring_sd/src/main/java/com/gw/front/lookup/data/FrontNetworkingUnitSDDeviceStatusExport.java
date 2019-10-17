package com.gw.front.lookup.data;

import lombok.Data;

@Data
public class FrontNetworkingUnitSDDeviceStatusExport {
	private String childstationnum;//子站号
	private String ownerCode;//设备编号
	private String deviceType;//设备类型
	private String deviceName;//设备名称
	private String deviceStatus;//当前状态
}
