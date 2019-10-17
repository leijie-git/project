package com.gw.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenUtil {

	public static String getToken(String userId,String password) {
		String token = "";
		token = JWT.create().withAudience(userId).sign(Algorithm.HMAC256(password));
		return token;
	}

	public static String getUserIdByToken(String token) {
		// 获取 token 中的 user id
		String userId = JWT.decode(token).getAudience().get(0);
		return userId;
	}

}
