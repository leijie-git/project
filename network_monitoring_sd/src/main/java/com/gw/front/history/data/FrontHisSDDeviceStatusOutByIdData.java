package com.gw.front.history.data;

import lombok.Data;

/**
 * 设备状态数据
 *
 * @author Administrator
 */
@Data
public class FrontHisSDDeviceStatusOutByIdData {
	private String id;
	private String deviceType;//设备类型
	private String deviceName;//设备名称
	private String deviceStatus;//当前状态
	private String ownerCode;//设备编号
	private Integer netDeviceStatus;//蓝版原始状态
	private String unitName;//单位名称
	private String unitID;
	private String userId;//人员ID
	private Integer status;
}
