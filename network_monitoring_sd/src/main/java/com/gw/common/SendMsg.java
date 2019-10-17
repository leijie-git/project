package com.gw.common;

import com.alibaba.fastjson.JSONObject;
import com.gw.apppush.PushAppMessage;
import com.gw.systemManager.data.SysPropertiesOutData;
import com.gw.systemManager.service.PropertiesManageService;
import com.gw.util.HttpGetWXToken;
import com.gw.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 微信app推送
 */
@Component
public class SendMsg {

	private static PropertiesManageService propertiesManageService;

	@Autowired
	public  void setPropertiesManageService(PropertiesManageService propertiesManageService) {
		SendMsg.propertiesManageService = propertiesManageService;
	}

	/**
	 * 获取token
	 *
	 * @return
	 * @throws Exception
	 */
	public static String getToken() throws Exception {
		SysPropertiesOutData properties = propertiesManageService.getProperties();
		return HttpGetWXToken.getToken(properties.getWxAppid(), properties.getWxSecret());
	}

	public static Object getJsonData(String string) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("value", Util.isEmpty(string) ? "" : string);
		jsonObject.put("color", "#173177");
		return jsonObject;
	}

	/**
	 * 推送安卓app消息
	 *
	 * @param channelIdList
	 * @param msg
	 * @throws Exception
	 */
	public static void sendAppMsg(List<String> channelIdList, String msg) throws Exception {
		if(Util.isNotEmpty(channelIdList)){
			String[] channelIds = channelIdList.toArray(new String[channelIdList.size()]);
			SysPropertiesOutData properties = propertiesManageService.getProperties();
			String title = "通知";
			Integer deviceType = 3;
			PushAppMessage.androidPushBatchUniMsg(title, msg, channelIds, deviceType, properties.getAndroidApiKey(),
					properties.getAndroidApiSecret());
		}
	}
}
