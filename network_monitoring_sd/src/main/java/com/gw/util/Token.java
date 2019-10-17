package com.gw.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

/**
 * 萤石云获取token
 * 
 * @author Administrator 2019年2月26日
 */
public class Token {

	// 获取到的凭证
	private static String token;
	// token有效时间
	private static long expiresin = 0;

	@SuppressWarnings("unchecked")
	public static String getToken(String appkey, String appsecret) throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		if (Util.isEmpty(token) || expiresin < currentTimeMillis) {
			HttpJson json = HttpClientUtil.doPost(String.format(ReqApiConst.GET_YS_GETTOKEN_URL, appkey, appsecret));
			if (json.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getMsg());
				String errcode = (String) map.get("code");
				if ("200".equals(errcode)) {
					Map<String, Object> map1 = (Map<String, Object>) map.get("data");
					token = (String) map1.get("accessToken");
					expiresin = (long) map1.get("expireTime");
				} else {
					token = null;
					System.out.println("get token errcode:" + errcode);
				}
			} else {
				System.out.println("get token fail");
			}
		}
		return token;
	}

	public static void setExpiresin(long expiresin) {
		Token.expiresin = expiresin;
	}
	
	
}
