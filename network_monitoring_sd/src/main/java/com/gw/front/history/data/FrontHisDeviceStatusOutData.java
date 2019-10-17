package com.gw.front.history.data;

import lombok.Data;

/**
 * 设备在线离线情况
 * 
 * @author Administrator
 *
 */
@Data
public class FrontHisDeviceStatusOutData {
	private String unitName;// 单位名称
	private String exceptionCount;// 异常数
	private String exceptionTime;// 异常时长
	private String exceptionRate;// 异常率
	private String total;//总数
}
