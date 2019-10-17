package com.gw.device.data;

import lombok.Data;

@Data
public class PointVideoInData {
	private Integer pageNumber;
	private Integer pageSize;
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
    private String unitId;
    private String unitName;
    private String serialnumber;
}
