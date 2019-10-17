package com.gw.device.data;

import lombok.Data;

@Data
public class InterfaceOutData {
	private String id;// 主键
	private String ownerCode;
	private Integer deviceIndex;
	private Integer deviceNo;
	private Integer ioPort;// 端口
	private String ioName;// 信号名称
	private Integer digitalNormal;// 正常值
	private String goodName;// 正常名称
	private String badName;// 异常名称
	private String fotActionUnit;// 单位
	private String fotActionTime;// 点动时间
	private boolean locked;// 锁定
	private Integer showOrder;// 排序
	private String reserve;// 备用字段
	private String lastupdate;// 更新时间
	private String database;// 数据库
	private String netDeviceId;
	private String locks;
	private Integer currentValue;//当前值
	private String buildAreaName;//
	
	private Integer orderType;//电瓶：1，点动：2
}
