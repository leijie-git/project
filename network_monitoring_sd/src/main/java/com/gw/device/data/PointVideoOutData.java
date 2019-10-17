package com.gw.device.data;

import lombok.Data;

@Data
public class PointVideoOutData {
	private String id;
	private String name;
	private String ip;
	private String brand;
	private Integer port;
	private String username;
    private String password;
    private String manufactor;
    private String position;
    private String plugintype;
    private String poscode;
    private Long unitId;
    private String unitName;
    private String serialnumber;//序列号
}
