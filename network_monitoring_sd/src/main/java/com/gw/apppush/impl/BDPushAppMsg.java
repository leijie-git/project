package com.gw.apppush.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.push.model.PushBatchUniMsgRequest;
import com.baidu.yun.push.model.PushBatchUniMsgResponse;
import com.baidu.yun.push.model.PushMsgToSingleDeviceRequest;
import com.baidu.yun.push.model.PushMsgToSingleDeviceResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 百度云平台推送app通知
 * <p>
 * 参考文档 http://push.baidu.com/doc/java/api
 */
public class BDPushAppMsg extends RegisterManager {

    private static Logger log = LoggerFactory.getLogger(BDPushAppMsg.class);

    private static final BDPushAppMsg single = new BDPushAppMsg();

    public static BDPushAppMsg getInstance() {
        return single;
    }

    /**
     * 构建 Android 推送消息
     *
     * @param title                  通知标题，可以为空；如果为空则设为appid对应的应用名。
     * @param description            必选    通知文本内容，不能为空。
     * @param notificationBuilderId  可选   android客户端自定义通知样式，如果没有设置默认为0。
     * @param notificationBasicStyle 可选  只有notification_builder_id为0时有效，可以设置通知的基本样式包括(响铃：0x04;
     *                               振动：0x02;可清除：0x01;),这是一个flag整形，每一位代表一种样式,如果想选择任
     *                               意两种或三种通知样式，notification_basic_style的值即为对应样式数值相加后的值。
     * @param openType               可选  点击通知后的行为(1：打开Url; 2：自定义行为；)。 open_type =0，只回调onNotificationClicked方法，
     *                               不做其他操作； open_type = 1，url != null：打开网页； open_type = 2，pkg_content = null：直接
     *                               打开应用； open_type = 2，pkg_content != null：自定义动作打开应用。
     * @param url                    可选    需要打开的Url地址，open_type为1时才有效。
     * @param pkgContent             可选   open_type为2时才有效，Android端SDK会把pkg_content字符串转换成Android Intent,通过该Intent打开
     *                               对应app组件，所以pkg_content字符串格式必须遵循Intent uri格式，最简单的方法可以通过Intent方法toURI()获取。
     * @param jsonCustomCont        自定义内容，键值对，Json对象形式(可选)；在android客户端，这些键值对将以Intent中的extra进行传递。
     * @return 消息
     */
    public JSONObject buildAndroidMessage(String title, String description, String notificationBuilderId, String notificationBasicStyle,
                                          int openType, String url, String pkgContent, JSONObject jsonCustomCont) {

        JSONObject message = new JSONObject();

        message.put("title", title);
        message.put("description", description);
        message.put("notification_builder_id", notificationBuilderId);
        message.put("notification_basic_style", notificationBasicStyle);
        message.put("open_type", openType);
        message.put("url", url);
        message.put("pkg_content", pkgContent);
        message.put("custom_content", jsonCustomCont);

        return message;
    }

    public JSONObject buildAndroidMessage(String title, String description) {

        JSONObject message = new JSONObject();

        message.put("title", title);
        message.put("description", description);

        return message;
    }

    /**
     * 构建 ios 推送消息
     * @param alert 其内容可以为字符串或者字典，如果是字符串，那么将会在通知中显示这条内容。
     * @param sound 可选  指定通知展现时伴随的提醒音文件名。如果找不到指定的文件或者值为 default，那么默认的系统音将会被使用。如果为空，那么将没有声音。
     * @param badge 可选 其值为数字，表示当通知到达设备时，应用的角标变为多少。如果没有使用这个字段，那么应用的角标将不会改变。设置为 0 时，会清除应用的角标。
     * @return 消息实例
     */
    public JSONObject buildIOSMessage(String alert, String sound, Integer badge) {
        JSONObject message = new JSONObject();

        JSONObject jsonAPS = new JSONObject();
        jsonAPS.put("alert", alert);
        jsonAPS.put("sound", sound);
        jsonAPS.put("badge", badge);
        message.put("aps", jsonAPS);
        return message;
    }


    /**
     * 推送消息给单个设备
     *
     * @param message     消息内容
     * @param deviceType  设备类型，deviceType => 1 for web, 2 for pc,3 for android, 4 for ios, 5 for wp.
     * @param messageType 消息类型,0表示透传消息,1表示通知,默认为0.
     * @param channelId   内容为设备的channelId
     * @return 是否发送成功
     */
    public boolean sendMessage(JSONObject message, Integer deviceType, Integer messageType, String channelId) {

        // 4. 设置请求参数，创建请求实例
        PushMsgToSingleDeviceRequest request = new PushMsgToSingleDeviceRequest()
                .addChannelId(channelId)
                .addMessageType(messageType)
                .addMessage(message.toString())
                .addDeviceType(deviceType);
        //.addMsgExpires(new Integer(3600))   //设置消息的有效时间,单位秒,默认3600*5.
        //.addDeployStatus()  //仅IOS应用推送时使用，默认值为null，1=开发状态 2=生产状态
        //.addTopicId()   //类别主题

        // 5. http request
        try {
            PushMsgToSingleDeviceResponse response = getBaiduPushClient().pushMsgToSingleDevice(request);
            log.info("sendSuccess: msgId =" + response.getMsgId() + ",sendTime = " + response.getSendTime());
        } catch (Exception e) {
            log.error("sendFail: " + e.toString());
            return false;
        }
        return true;
    }

    /**
     * 推送消息给批量设备（批量单播）
     *
     * @param message     消息内容
     * @param deviceType  设备类型，deviceType => 1 for web, 2 for pc,3 for android, 4 for ios, 5 for wp.
     * @param messageType 消息类型,0表示透传消息,1表示通知,默认为0.
     * @param channelIds  内容为JSONArray格式，多个设备的channelId，格式为：[channelId1,channelId2,…]，最多一万个
     * @return 是否发送成功
     */
    public boolean sendMessage(JSONObject message, Integer deviceType, Integer messageType, List<String> channelIds) {

        // 4. 设置请求参数，创建请求实例
        PushBatchUniMsgRequest request = new PushBatchUniMsgRequest()
                .addChannelIds(channelIds.toArray(new String[0]))
                .addMessageType(messageType)
                .addMessage(message.toString())
                .addDeviceType(deviceType)
                //.addMsgExpires(new Integer(3600))   //设置消息的有效时间,单位秒,默认3600*5.
                .addTopicId("BaiduPush");// 设置类别主题

        try {
            // 5. http request
            PushBatchUniMsgResponse response = getBaiduPushClient().pushBatchUniMsg(request);
            // Http请求结果解析打印
            log.info("sendSuccess: msgId =" + response.getMsgId() + ",sendTime = " + response.getSendTime());
        } catch (Exception e) {
            log.error("sendFail: " + e.toString());
            return false;
        }
        return true;
    }

}
