package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontNBDeviceInfoExport {
	private String manufactureName;//设备厂家
	private String deviceType;//设备型号
	private String deviceCode;//IMEI
	private String firstTime;//入网时间
	private String currentStatus;//设备状态
	private Integer battery;//电量
	private String reserve;//压力值
	private String unitCode;//单位编号
	private String unitName;//单位名称
	private String installAddr;//安装位置
	private String notifyPhone;//通知电话
	private String lon;//经度
	private String lat;//纬度
}
