package com.gw.util;

import com.alibaba.fastjson.JSONObject;
import com.gw.common.Json;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * 获取token
 * 
 * @author zfg
 *
 */
@Slf4j
public class HttpGetXMHToken {

	// 获取到的凭证
	private static String token;
	// token有效时间
	private static long expiresin=Long.MAX_VALUE;

	
	public static String getToken(String url) throws Exception {
		long currentTimeMillis = System.currentTimeMillis();
		if (Util.isEmpty(token)) {//token为空时根据密钥获取token
			token = deal(url+ReqApiConst.GET_XINMENHAI_GETTOKEN_PARAM);
			
		}else if(expiresin < currentTimeMillis){//token存在但是已经过期需要根据过期的token进行更新
			token = deal(url+String.format(ReqApiConst.GET_XINMENHAI_UPDATETOKEN_PARAM,token));
		}
		return token;
	}
	/**
	 * 
	 * @param url 请求路径
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String deal(String url) {
		long currentTimeMillis = System.currentTimeMillis();
		log.error("token:"+token);
		log.error("expiresin:"+expiresin);
		log.error("currentTimeMillis:"+currentTimeMillis);
		log.error("url:"+url);
		Json json = HttpClientUtil.doGet(url);
		log.error("json:"+json.toString());
		if (json.isSuccess() ) {
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
			log.error("map:"+map);
			String code = (String) map.get("code");
			log.error("code:"+code);
			if("1".equals(code)) {
				JSONObject data = (JSONObject) map.get("data");
				log.error("data:"+data.toJSONString());
				Map<String, Object> dataMap = (Map<String, Object>) JSONObject.parse(data.toJSONString());
				log.error("dataMap:"+dataMap);
				token = (String)dataMap.get("token");
				log.error("token:"+token);
				expiresin = (Integer) dataMap.get("expires")*1000+currentTimeMillis;
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
		HttpGetXMHToken.expiresin = expiresin;
	}
	
	
}
