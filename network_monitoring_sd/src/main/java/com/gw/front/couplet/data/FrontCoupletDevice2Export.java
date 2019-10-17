package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletDevice2Export {
	private String deviceType;// 设备类型
	private String deviceName;// 设备名称
	private String deviceNo;// 子号
	private String time;// 使用时间
	private String deviceStatus;// 当前状态
	private Integer upCount;// 上传数
	private String unitCode;// 单位编号
	private String childstationnum;// 子站号
	private String unitName;// 单位名称
	private String exceptionCount;// 异常次数
	private String version;// 硬件版本
	private String softVersion;// 软件版本
	private String ip;// 设备ip
	private String deviceModel;//设备型号
	private String agreement;// 协议
}
