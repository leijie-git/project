package com.gw.front.login.data;

import lombok.Data;

@Data
public class CheckAccountMsg {

	
	private String isAccount;//1：为账号不存在
	
	private String isPassword;//1：密码不正确
}
