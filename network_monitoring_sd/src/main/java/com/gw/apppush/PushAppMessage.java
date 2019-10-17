package com.gw.apppush;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baidu.yun.core.log.YunLogEvent;
import com.baidu.yun.core.log.YunLogHandler;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.baidu.yun.push.exception.PushClientException;
import com.baidu.yun.push.exception.PushServerException;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToAllRequest;
import com.baidu.yun.push.model.PushMsgToAllResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import com.baidu.yun.push.model.QueryMsgStatusRequest;
import com.baidu.yun.push.model.QueryMsgStatusResponse;
import com.gw.util.Util;

import net.sf.json.JSONObject;

/**
 * 百度云平台推送app通知
 * 
 * @author lxy
 *
 */
public class PushAppMessage {

	private static Logger log = LoggerFactory.getLogger(PushAppMessage.class);

	/**
	 * 推送初始化
	 * 
	 * @return
	 */
	private static BaiduPushClient initPushClient(String apiKey, String secretKey) {
		/*
		 * 1. 创建PushKeyPair 用于app的合法身份认证 apikey和secretKey可在应用详情中获取
		 */
		PushKeyPair pair = null;
		// pair = new PushKeyPair("GxApW0TRv9aFqwGDB64oFkd2",
		// "jhVWS2LdaU70MnY9OkSwA58Qdzn2tQOE");
		pair = new PushKeyPair(apiKey, secretKey);

		// 2. 创建BaiduPushClient，访问SDK接口
		BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

		// 3. 注册YunLogHandler，获取本次请求的交互信息
		pushClient.setChannelLogHandler(new YunLogHandler() {
			@Override
			public void onHandle(YunLogEvent event) {
				System.out.println(event.getMessage());
			}
		});
		return pushClient;
	}

	/**
	 * Android 推送消息给批量设备（批量单播）IOS 不支持
	 * 
	 * @param title	通知标题
	 * @param description	通知文本内容
	 * @param channelIds	内容为JSONArray格式，多个设备的channelId，格式为：[channelId1,channelId2,…]，最多一万个
	 * @param deviceType	设备类型,3：Android
	 * @return	成功返回值
	 * @throws IOException
	 */
	public static Map<String, Object> androidPushBatchUniMsg(String title, String description, String[] channelIds,
			int deviceType, String apiKey, String secretKey) throws IOException {

		BaiduPushClient pushClient = initPushClient(apiKey, secretKey);
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		try {
			// 4.specify request arguments
			// 创建 Android 通知
			JSONObject notification = new JSONObject();
			notification.put("title", title);
			notification.put("description", description);
			notification.put("notification_builder_id", 0);
			notification.put("notification_basic_style", 4);
			notification.put("open_type", 1);
			notification.put("url", "http://push.baidu.com");

			JSONObject jsonCustormCont = new JSONObject();
			jsonCustormCont.put("key", "value"); // 自定义内容，key-value
			notification.put("custom_content", jsonCustormCont);

			PushBatchUniMsgRequest request = new PushBatchUniMsgRequest().addChannelIds(channelIds)
					.addMsgExpires(new Integer(3600)).addMessageType(1).addMessage(notification.toString())
					.addDeviceType(deviceType).addTopicId("BaiduPush");// 设置类别主题
			// 5. http request
			PushBatchUniMsgResponse response = pushClient.pushBatchUniMsg(request);
			// Http请求结果解析打印
			System.out.println(String.format("msgId: %s, sendTime: %d", response.getMsgId(), response.getSendTime()));

			log.debug(String.format("msgId: %s, sendTime: %d", response.getMsgId(), response.getSendTime()));

			jsonMap.put("msgId", response.getMsgId());
			jsonMap.put("sendTime", response.getSendTime());

		} catch (PushClientException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				try {
					throw e;
				} catch (PushClientException e1) {
					e1.printStackTrace();
				}
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				try {
					throw e;
				} catch (PushServerException e1) {
					e1.printStackTrace();
				}
			} else {
				log.error(String.format("requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
						e.getErrorCode(), e.getErrorMsg()));
			}
		}
		return jsonMap;
	}

	/**
	 * 推送消息给所有设备，广播推送。
	 * 
	 * @param title
	 * @param description
	 * @param sendTime
	 * @param expireTime
	 * @param messageType
	 * @param openType
	 * @param url
	 * @param deviceType
	 * @return
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	public static Map<String, Object> pushMsgToAll(String title, String description, long sendTime, int expireTime,
			int messageType, int openType, String url, int deviceType, String alert, String apiKey, String secretKey,
			Object... obj) throws PushClientException, PushServerException {
		BaiduPushClient pushClient = initPushClient(apiKey, secretKey);
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		JSONObject message = new JSONObject();

		if (deviceType == 3) { // Android 设备
			message.put("title", title);
			message.put("description", description);
			message.put("notification_builder_id", 0);
			message.put("notification_basic_style", 4);
			message.put("open_type", openType); // 默认 1
			message.put("url", url);

			JSONObject jsonCustormCont = new JSONObject();

			if (Util.isNotEmpty(obj.toString())) {
				for (int i = 0; i < obj.length; i++) {
					jsonCustormCont.put("key" + (i + 1), obj[i]); // 自定义内容，key-value
				}
			}

			message.put("custom_content", jsonCustormCont);

		} else if (deviceType == 4) { // iOS 设备

			JSONObject jsonAPS = new JSONObject();
			jsonAPS.put("alert", alert);
			jsonAPS.put("sound", "ttt"); // 设置通知铃声样式，例如"ttt"，用户自定义。
			message.put("aps", jsonAPS);

			if (Util.isNotEmpty(obj.toString())) {
				for (int j = 0; j < obj.length; j++) {
					message.put("key" + (j + 1), obj[j]);
				}
			}
		}

		try {
			PushMsgToAllRequest request = new PushMsgToAllRequest().addMsgExpires(new Integer(expireTime)) // 默认 new
																											// Integer(3600)
					.addMessageType(messageType) // 0:透传消息 1：通知 默认是 0
					.addMessage(message.toString())// 添加透传消息
					.addSendTime(sendTime) // 设置定时推送时间，必需超过当前时间一分钟，单位秒.实例2分钟后推送 System.currentTimeMillis() / 1000 + 120
					.addDeviceType(deviceType);

			// 5. http request
			PushMsgToAllResponse response = pushClient.pushMsgToAll(request);

			// Http请求返回值解析
			log.debug(String.format("msgId: %s, sendTime: %d", response.getMsgId(), response.getSendTime()));

			jsonMap.put("msgId", response.getMsgId());
			jsonMap.put("sendTime", response.getSendTime());
			jsonMap.put("timerId", response.getTimerId()); // 推送定时消息时，返回该字段，标识定时任务。

		} catch (PushClientException e) {
			// ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
			// 'true' 表示抛出, 'false' 表示捕获。
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				log.error("推送消息给所有设备出现异常 " + e.getMessage());
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				log.error(String.format("requestId: %d, errorCode: %d, errorMsg: %s", e.getRequestId(),
						e.getErrorCode(), e.getErrorMsg()));
			}
		}
		return jsonMap;
	}

	/**
	 * 向单个设备推送消息。
	 * 
	 * @param channelId
	 * @param expireTime
	 * @param messageType
	 * @param title
	 * @param description
	 * @param openType
	 * @param url
	 * @param deviceType
	 * @param obj
	 * @return
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	public static Map<String, Object> pushMsgToSingleDevice(String channelId, int expireTime, int messageType,
			String title, String description, int openType, String url, int deviceType, String alert, String apiKey,
			String secretKey, Object... obj) throws PushClientException, PushServerException {

		BaiduPushClient pushClient = initPushClient(apiKey, secretKey);
		Map<String, Object> jsonMap = new HashMap<String, Object>();

		JSONObject message = new JSONObject();

		if (deviceType == 3) { // Android 设备
			message.put("title", title);
			message.put("description", description);
			message.put("notification_builder_id", 0);
			message.put("notification_basic_style", 4);
			message.put("open_type", openType); // 默认 1
			message.put("url", url);

			JSONObject jsonCustormCont = new JSONObject();

			if (Util.isNotEmpty(obj.toString())) {
				for (int i = 0; i < obj.length; i++) {
					jsonCustormCont.put("key" + (i + 1), obj[i]); // 自定义内容，key-value
				}
			}

			message.put("custom_content", jsonCustormCont);

		} else if (deviceType == 4) { // iOS 设备

			JSONObject jsonAPS = new JSONObject();
			jsonAPS.put("alert", alert);
			jsonAPS.put("sound", "ttt"); // 设置通知铃声样式，例如"ttt"，用户自定义。
			message.put("aps", jsonAPS);

			if (Util.isNotEmpty(obj.toString())) {
				for (int j = 0; j < obj.length; j++) {
					message.put("key" + (j + 1), obj[j]);
				}
			}
		}

		try {
			PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest().addChannelId(channelId)
					.addMsgExpires(expireTime) // message有效时间
					.addMessageType(messageType)// 1：通知,0:透传消息. 默认为0 注：IOS只有通知.
					.addMessage(message.toString()).addDeviceType(deviceType);// deviceType => 3:android, 4:ios

			// 5. http request
			PushMsgToSingleDeviceResponse response = pushClient.pushMsgToSingleDevice(request);

			log.debug("msgId: " + response.getMsgId() + ",sendTime: " + response.getSendTime());

			jsonMap.put("msgId", response.getMsgId());
			jsonMap.put("sendTime", response.getSendTime());

		} catch (PushClientException e) {
			// ERROROPTTYPE 用于设置异常的处理方式 -- 抛出异常和捕获异常,
			// 'true' 表示抛出, 'false' 表示捕获。
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				log.error("推送消息给所有设备出现异常 " + e.getMessage());
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				log.error(String.format("requestId: %d, errorCode: %d, errorMsg: %s", e.getRequestId(),
						e.getErrorCode(), e.getErrorMsg()));
			}
		}
		return jsonMap;
	}

	/**
	 * 查询消息推送状态，包括成功、失败、待发送、发送中4种状态。
	 * 
	 * @return
	 * @throws PushClientException
	 * @throws PushServerException
	 */
	public static List<?> QueryMsgStatus(String[] msgIds, Integer deviceType, String apiKey, String secretKey)
			throws PushClientException, PushServerException {

		BaiduPushClient pushClient = initPushClient(apiKey, secretKey);
		// List<MsgSendInfo> sendInfo = null;

		QueryMsgStatusResponse response = null;
		try {
			// 4. specify request arguments
			QueryMsgStatusRequest request = new QueryMsgStatusRequest().addMsgIds(msgIds).addDeviceType(deviceType);

			// 5. http request
			response = pushClient.queryMsgStatus(request);

			// Http请求结果解析打印
			log.debug("totalNum: " + response.getTotalNum() + "\n" + "result:");
			/*
			 * 将通知消息保存到日志中 if( null != response){ List<?> list = response.getMsgSendInfos();
			 * for (int i = 0; i < list.size(); i++) { Object object = list.get(i);
			 * if(object instanceof MsgSendInfo){ MsgSendInfo msgSendInfo = (MsgSendInfo)
			 * object; StringBuilder strBuilder = new StringBuilder();
			 * strBuilder.append("List[" + i + "]: {" + "msgId = " + msgSendInfo.getMsgId()
			 * + ",status = " + msgSendInfo.getMsgStatus() + ",sendTime = " +
			 * msgSendInfo.getSendTime() + ",success = " + msgSendInfo.getSuccessCount());
			 * strBuilder.append("}\n"); log.debug(strBuilder.toString()); } } }
			 * 
			 */
		} catch (PushClientException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				e.printStackTrace();
			}
		} catch (PushServerException e) {
			if (BaiduPushConstants.ERROROPTTYPE) {
				throw e;
			} else {
				System.out.println(String.format("requestId: %d, errorCode: %d, errorMessage: %s", e.getRequestId(),
						e.getErrorCode(), e.getErrorMsg()));
			}
		}
		return response.getMsgSendInfos();
	}

//	public static void main(String[] args) throws PushClientException, PushServerException, IOException {
//
//		/**
//		 * 单条推送 Map<String, Object> resultMap =
//		 * PushMessageNew.pushMsgToSingleDevice("3814863451180523498", 3600, 1, "代码测试",
//		 * "测试ing", 2, "www.baidu.com", 4, "","");
//		 */
//
//		/**
//		 * 批量推送 String[] channelIds = {"4182697971366550372","3814863451180523498"} ;
//		 * Map<String, Object> resultMap =
//		 * PushMessageNew.androidPushBatchUniMsg("测试*****", "测试1205", channelIds , 3);
//		 */
//
//		/**
//		 * 广播推送 Map<String, Object> resultMap = PushMessageNew.pushMsgToAll("涌泉金服",
//		 * "注册有礼", System.currentTimeMillis() / 1000 + 120, 3600, 1, 1,
//		 * "https://www.baidu.com/", 3, ""); System.out.println(resultMap);
//		 */
//
//		/**
//		 * 消息状态的查看
//		 */
//		String[] strs = { "8361003517954776467" };
//		List<?> list = PushAppMessage.QueryMsgStatus(strs, 3, "GxApW0TRv9aFqwGDB64oFkd2",
//				"jhVWS2LdaU70MnY9OkSwA58Qdzn2tQOE");
//		System.out.println(list);
//	}
}
