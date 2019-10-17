package com.gw.util;

import com.alibaba.fastjson.JSONObject;
import com.gw.common.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class UtilWechat {

	private static Logger logger = LoggerFactory.getLogger(UtilWechat.class);

//	private static final String BASE_URI = "https://api.weixin.qq.com";
//	private static final String MEDIA_URI = "http://file.api.weixin.qq.com";
//	private static final String MP_URI = "https://mp.weixin.qq.com";
//	private static final String MCH_URI = "https://api.mch.weixin.qq.com";
	private static final String OPEN_URI = "https://open.weixin.qq.com";
//	private static final String WIFI_URI = "https://wifi.weixin.qq.com";
//
//	private static Header jsonHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE,
//			ContentType.APPLICATION_JSON.toString());
//	private static Header xmlHeader = new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_XML.toString());
//
//	private static final String PARAM_ACCESS_TOKEN = "access_token";

	/**
	 * 生成网页授权 URL (第三方平台开发)
	 * 
	 * @param appid
	 *            appid
	 * @param redirect_uri
	 *            自动URLEncoder
	 * @param snsapi_userinfo
	 *            snsapi_userinfo
	 * @param state
	 *            可以为空
	 * @param component_appid
	 *            第三方平台开发，可以为空。 服务方的appid，在申请创建公众号服务成功后，可在公众号服务详情页找到
	 * @return url
	 */
	public static String connectOauth2Authorize(String appid, String redirect_uri, boolean snsapi_userinfo,
			String state, String component_appid) {
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(OPEN_URI + "/connect/oauth2/authorize?").append("appid=").append(appid).append("&redirect_uri=")
					.append(URLEncoder.encode(redirect_uri, "utf-8")).append("&response_type=code").append("&scope=")
					.append(snsapi_userinfo ? "snsapi_userinfo" : "snsapi_base").append("&state=")
					.append(state == null ? "" : state);
			if (component_appid != null) {
				sb.append("&component_appid=").append(component_appid);
			}
			sb.append("#wechat_redirect");
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			logger.error("", e);
		}
		return null;
	}

	/**
	 * 通过code换取网页授权access_token
	 * 
	 * @param appid
	 *            appid
	 * @param secret
	 *            secret
	 * @param code
	 *            code
	 * @return SnsToken
	 */
	public static SnsToken oauth2AccessToken(String appid, String secret, String code) {
		String url = String.format(ReqApiConst.GET_WECHAT_GETSNSTOKEN_URL,appid,secret,code);
		Json json = HttpClientUtil.doGet(url);
		String data = json.getObj().toString();
		logger.error("data"+data);
		SnsToken snsToken = JSONObject.parseObject(data, SnsToken.class);
		return snsToken;
	}
}
