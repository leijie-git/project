package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontInterFaceStatusExport {
	private String eqSystemName; //系统名称
	private String detialName;//信号名称
	private String ioType; //信号类型
	private String deviceNo; //子号
	private String ioPort; //端口索引
	private String scope;//参考范围
	private String unitName;//单位名称
	private String currentValue;//当前值
}
