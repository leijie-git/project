package com.gw.device.data;

import lombok.Data;

@Data
public class InterfaceOutImportData {
	private String ioPort;// 端口
	private String ioName;// 信号名称
	private String digitalNormal;// 正常值
	private String goodName;// 正常名称
	private String badName;// 异常名称
	private String fotActionUnit;// 单位
	private String fotActionTime;// 点动时间
	private String locks;
}
