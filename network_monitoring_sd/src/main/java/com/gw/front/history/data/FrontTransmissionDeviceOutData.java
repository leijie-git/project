package com.gw.front.history.data;

import lombok.Data;

/**
 * 传输装置操作记录
 * 
 * @author Administrator
 *
 */
@Data
public class FrontTransmissionDeviceOutData {
	private String unitCode;// 单位编号
	private String childstationnum;// 子站号
	private String unitName;// 单位名称
	private String phone;// 电话
	private String type;// 操作类型
	private String opTime;// 操作时间
	private String reTime;// 接收时间
	private String description;//描述
	private String reserve;//备注
}
