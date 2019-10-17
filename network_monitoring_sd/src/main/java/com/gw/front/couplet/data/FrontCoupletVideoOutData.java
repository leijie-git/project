package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletVideoOutData {

	private Integer videoclass;

	private Integer videotype;

	private String ip;

	private Integer port;

	private String username;

	private String password;

	private String position;

	private String poscode;

	private String plugintype;

	private String manufactor;

	private String brand;
	private String name;//视频名称
	private String serialnumber;//序列号

}
