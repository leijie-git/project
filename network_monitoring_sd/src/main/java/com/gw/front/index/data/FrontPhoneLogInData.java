package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontPhoneLogInData {
	private String id;
	private String unitId;       //单位ID
	private String sender;       //拨打人
	private String receiver;     //联系人
	private String phone;        //联系电话
	private String sendDate;     //拨打日期
	private String status;       //0未接通1接通未接听2接听
	private Integer longTime;    //通话时长 单位s
}
