package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontLoginLogOutData {

	private String id;

	private String loginName;// 登录名称

	private String loginDate;// 登录时间

	private String loginAddr;// 登录地址

	private String loginIp;// 登录ip

	private String loginOutDate;// 退出时间

	private String dataFrom;// 来源
	
	private String onlineTime;//在线时间
}
