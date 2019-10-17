package com.gw.util;

import com.alibaba.fastjson.JSONObject;
import com.gw.common.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 获取token
 *
 * @author Administrator
 */
@Component
public class HttpGetWXToken {

	private static Logger log = LoggerFactory.getLogger(HttpGetWXToken.class);

	private static String appId;
	private static String secret;

	@Value("${wx.appid}")
	public void setAppId(String appId) {
		HttpGetWXToken.appId = appId;
	}

	@Value("${wx.secret}")
	public void setSecret(String secret) {
		HttpGetWXToken.secret = secret;
	}

	// 获取到的凭证
	private static String token;

	// 获取到的凭证ticket
	private static String ticket;
	// token有效时间
	private static long expiresin = Long.MAX_VALUE;

	@SuppressWarnings("unchecked")
	public static String getToken(String appId, String wxSecret) throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		log.error("token:" + token);
		log.error("expiresin:" + expiresin);
		log.error("currentTimeMillis:" + currentTimeMillis);
		if (Util.isEmpty(token) || expiresin < currentTimeMillis) {
			String url = String.format(ReqApiConst.GET_WECHAT_GETTOKEN_URL, appId, wxSecret);
			log.error("url:" + url);
			Json json = HttpClientUtil.doGet(url);
			log.error("json:" + json.toString());
			if (json.isSuccess()) {
				Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
				log.error("map:" + map);
				Integer errcode = (Integer) map.get("errcode");
				if (errcode == null || errcode == 0) {
					token = (String) map.get("access_token");
					expiresin = (Integer) map.get("expires_in") * 1000 + currentTimeMillis;
				} else {
					token = null;
					log.error("get token errcode:" + errcode);
				}
			} else {
				log.error("get token fail");
			}
		}
		return token;
	}

	public static void setExpiresin(long expiresin) {
		HttpGetWXToken.expiresin = expiresin;
	}

	public static String getTicket() throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		log.error("ticket:" + ticket);
		log.error("expiresin:" + expiresin);
		log.error("currentTimeMillis:" + currentTimeMillis);
		if (Util.isEmpty(ticket) || expiresin < currentTimeMillis) {
			String token = HttpGetWXToken.getToken(appId, secret);
			String url = String.format(ReqApiConst.GET_WECHAT_TICKET_URL, token);
			String backTicket = sendGet(url, "utf-8", 60000);
			ticket = (String) net.sf.json.JSONObject.fromObject(backTicket).get("ticket");

		}
		return ticket;
	}

	/**
	 * @param url
	 * @param charset
	 * @param timeout
	 * @return
	 * @title sendGet
	 * @Description
	 * @author sss
	 * @Date 2018-5-18上午11:15:33
	 */
	public static String sendGet(String url, String charset, int timeout) {
		String result = "";
		try {
			URL u = new URL(url);
			try {
				URLConnection conn = u.openConnection();
				conn.connect();
				conn.setConnectTimeout(timeout);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), charset));
				String line = "";
				while ((line = in.readLine()) != null) {
					result = result + line;
				}
				in.close();
			} catch (IOException e) {
				return result;
			}
		} catch (MalformedURLException e) {
			return result;
		}
		return result;
	}
}
