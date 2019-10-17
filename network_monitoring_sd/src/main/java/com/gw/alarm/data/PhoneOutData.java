package com.gw.alarm.data;

import lombok.Data;

@Data
public class PhoneOutData {
	private String phone;// 电话
	private String msg;// 描述
	private String id;// 主键
	private String status;// 0未拨通1拨通未接听2接听
}
