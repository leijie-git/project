package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontLoginLogExport {
	private String loginName;// 登录名称
	private String loginDate;// 登录时间
	private String loginAddr;// 登录地点
	private String loginIp;// 登录IP
	private String loginOutDate;// 退出时间
	private String onlineTime;//在线时长
	private String dataFrom;// 来源
}
