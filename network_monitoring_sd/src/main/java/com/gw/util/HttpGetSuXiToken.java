package com.gw.util;

import com.alibaba.fastjson.JSONObject;
import com.gw.common.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * 苏溪获取token
 * 
 * @author zfg
 *
 */
public class HttpGetSuXiToken {

	private static Logger log = LoggerFactory.getLogger(HttpGetSuXiToken.class);

	// 获取到的凭证
	private static String token;
	// token有效时间
	private static long expiresin=Long.MAX_VALUE;
	// token获取路径(第一次获取)
	private static String tokenUrl ="dock/suxi/getToken?secret=";
	// 获取token的密钥
	private static String secret = "CYCQzE1NDgxNDMxMDk2ODU0XzFjeGw=";
	// token获取路径(第一次获取)
	private static String updateUrl ="/dock/anxun/refreshToken?token=";
	
	public static String getToken(String url) throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		if (Util.isEmpty(token)) {//token为空时根据密钥获取token
			token = deal(url+tokenUrl,secret);

		}else if(expiresin < currentTimeMillis){//token存在但是已经过期需要根据过期的token进行更新
			token = deal(url+updateUrl,token);
		}
		return token;
	}
	/**
	 *
	 * @param url 请求路径
	 * @param content 请求参数
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String deal(String urls,String content) {
		long currentTimeMillis = System.currentTimeMillis();
		log.error("token:"+token);
		log.error("expiresin:"+expiresin);
		log.error("currentTimeMillis:"+currentTimeMillis);
		String url = String.format(urls+content);
		log.error("url:"+url);
		Json json = HttpClientUtil.doGet(url);
		log.error("json:"+json.toString());
		if (json.isSuccess() ) {
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
			log.error("map:"+map);
			Integer code = (Integer) map.get("code");
			log.error("code:"+code);
			if(code==0) {
				JSONObject data = (JSONObject) map.get("data");
				log.error("data:"+data.toJSONString());
				Map<String, Object> dataMap = (Map<String, Object>) JSONObject.parse(data.toJSONString());
				log.error("dataMap:"+dataMap);
				token = (String)dataMap.get("token");
				log.error("token:"+token);
				expiresin = (Integer) dataMap.get("expire")*1000+currentTimeMillis;
				log.error("expiresin:"+expiresin);
			}else {
				token = null;
			}
		} else {
			log.error("get token fail");
		}
		return token;
	}
	public static void setExpiresin(long expiresin) {
		HttpGetSuXiToken.expiresin = expiresin;
	}


}
