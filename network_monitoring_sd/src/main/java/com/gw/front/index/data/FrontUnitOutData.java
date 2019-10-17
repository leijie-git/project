package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontUnitOutData {
	private String id;
	private String unitName;// 单位名称
	private String address;// 地址
	private String phone;// 电话
	private String email;// 邮箱
	private String contacts;// 联系人
	private String unitCode;// 单位编号
	private String unitPoint;//经纬度坐标
	private String unitType;//单位类型
}
