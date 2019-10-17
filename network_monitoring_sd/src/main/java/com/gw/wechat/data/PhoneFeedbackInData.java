package com.gw.wechat.data;

import lombok.Data;

@Data
public class PhoneFeedbackInData {

	private String id;
	private String phone;//联系电话
	private String content;//反馈内容
	private String createDate;//创建时间
	private String createUser;//创建人id
	private String userName;//创建人姓名
	private String name;//联系人
}
