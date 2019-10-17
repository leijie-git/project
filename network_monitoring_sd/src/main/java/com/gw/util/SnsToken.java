package com.gw.util;

import lombok.Data;

@Data
public class SnsToken {
	private String access_token;

	private Integer expires_in;

	private String refresh_token;

	private String openid;

	private String scope;
	
	private String unionid;

}
