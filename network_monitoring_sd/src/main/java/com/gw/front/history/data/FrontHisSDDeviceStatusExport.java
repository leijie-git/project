package com.gw.front.history.data;

import lombok.Data;

/**
 * 设备状态数据
 * 
 * @author Administrator
 *
 */
@Data
public class FrontHisSDDeviceStatusExport {
	private String deviceType;//设备类型
	private String deviceName;//设备名称
	private String deviceNo;//子号
	private String time;//使用时间
	private String deviceStatus;//当前状态
	private Integer upCount;//上传数
	private String unitName;//单位名称
	private String unitCode;//单位编号
	private String exceptionCount;//异常次数
	private String version;//硬件版本
	private String softVersion;//软件版本
}
