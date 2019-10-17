package com.gw.util;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.gw.alarm.data.TemplateMessage;
import com.gw.common.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.util.Map;

/**
 * 消息推送接口（短信、微信）
 * 
 * @author Administrator
 *
 */
@Component
public class PushMessage {

	private static Logger log = LoggerFactory.getLogger(PushMessage.class);

	/**
	 * 短信推送接口
	 * 
	 * @param phone 手机号
	 * @param message
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean sendShortMessage(String apiKey, String apiSecret, String phone, String message) {
		try {
			StringBuilder sb = new StringBuilder(2000);
			sb.append(ReqApiConst.GET_QIRUI_SENDSHORTMSG_URL);
			sb.append("?dc=15");
			sb.append("&sm=").append(URLEncoder.encode(message, "utf8"));
			sb.append("&da=").append(phone);
			sb.append("&un=").append(apiKey);
			sb.append("&pw=").append(apiSecret);
			sb.append("&tf=3&rd=1&rf=2"); // 短信内容编码为 urlencode+utf8

			Json json = HttpClientUtil.doGet(sb.toString());
			if (!json.isSuccess()) {
				log.error("error phone "+phone);
				return false;
			}

			Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
			String id = (String) map.get("id");
			if (Util.isEmpty(id)) {
				log.error("interface error phone： "+phone);
				log.info("short message interface error code: " + map.get("r"));
				return false;
			}
			log.info("short message interface success！");
			return true;
		}catch (Exception ex){
			ex.printStackTrace();
			return false;
		}
	}

	/**
	 * 发送微信消息(非模板消息)
	 * 
	 * @param openId
	 *            用户openID
	 * @param text
	 *            消息内容
	 * @param token
	 *            公众号token
	 * @return
	 * @throws Exception
	 */
	public static boolean sendWechatMsgToUser(String openId, String text, String token) throws Exception {

		JSONObject content = new JSONObject();
		content.put("content", text);
		JSONObject param = new JSONObject();
		param.put("touser", openId);
		param.put("msgType", "text");
		param.put("text", content);

		Json json = HttpClientUtil.doPost(String.format(ReqApiConst.POST_WECHAT_SENDCUSTMSG_URL, token), param.toString());
		if (json.isSuccess()) {
			log.info("sendWechatMsgToUser success！");
			return true;
		}
		return false;
	}

	/**
	 * 发送模板信息给用户
	 * 
	 * @param templateMessage
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static boolean sendTemplateMSGToUser(TemplateMessage templateMessage, String token) throws Exception {
		if(Util.isEmpty(token)) {
			log.error("token is null!");
			return false;
		}
		JSONObject param = new JSONObject();
		try {
			param.put("touser", templateMessage.getTouser());
			param.put("template_id", templateMessage.getTemplateId());
			param.put("url", templateMessage.getClickurl());
			param.put("topcolor", templateMessage.getTopcolor());
			param.put("data", templateMessage.getTemplateData());
		} catch (JSONException e) {
			log.error("json error!");
		}
		log.error("param:"+param.toString());
		Json json = HttpClientUtil.doPost(String.format(ReqApiConst.POST_WECHAT_SENDTEMMSG_URL, token), param.toString());
		if (json.isSuccess()) {
			Map<String, Object> map = (Map<String, Object>) JSONObject.parse((String) json.getObj());
			Integer errcode = (Integer) map.get("errcode");
			if (errcode == null || errcode == 0) {
				log.info("sendTemplateMSGToUser success！"+ templateMessage.getTouser());
				return true;
			}
			log.info("sendTemplateMSGToUser error code: " + errcode);
			return false;
		}
		return false;
	}
}
