package com.gw.apppush.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gw.apppush.PushAppMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.text.MessageFormat;

import static com.gw.apppush.AppPushConfig.HW_apiUrl;
import static com.gw.apppush.AppPushConfig.HW_appId;

/**
 *  华为平台推送app通知
 *
 * 参考文档 https://developer.huawei.com/consumer/cn/service/hms/catalog/huaweipush_agent.html?page=hmssdk_huaweipush_api_reference_agent_s2
 **/
public class HWPushAppMsg extends RegisterManager {

    private static final HWPushAppMsg single = new HWPushAppMsg();
    private static Logger log = LoggerFactory.getLogger(PushAppMessage.class);

    public static HWPushAppMsg getInstance(){
        return single;
    }

    /**
     * Push消息
     *
     * @param deviceTokens 设备唯一标识，单次最多100个
     * @param payload      发送消息封装
     * @return 是否发送成功
     */
    public boolean sendPushMessage(JSONArray deviceTokens, JSONObject payload) {

        //编号处理
        try {
            /*expire_time	可选	 格式ISO 8601[ 格式为：yyyy-MM-dd'T'HH:mm:ssXXX]：2013-06-03T17:30，采用本地时间精确到分钟。
                        此值为发出去消息的超期时间，即：如果用户没有在线，此消息会保存到PUSH服务器的时间。*/
            String postBody = MessageFormat.format("access_token={0}&nsp_svc={1}&nsp_ts={2}&device_token_list={3}&payload={4}",
                    URLEncoder.encode(getAccessToken(), "UTF-8"),
                    URLEncoder.encode("openpush.message.api.send", "UTF-8"),
                    URLEncoder.encode(String.valueOf(System.currentTimeMillis() / 1000), "UTF-8"),
                    URLEncoder.encode(deviceTokens.toString(), "UTF-8"),
                    URLEncoder.encode(payload.toString(), "UTF-8"));

            String postUrl = HW_apiUrl + "?nsp_ctx=" + URLEncoder.encode("{\"ver\":\"1\", \"appId\":\"" + HW_appId + "\"}", "UTF-8");
            String response = HuaWeiUtil.httpPost(postUrl, postBody, 5000, 5000);
            log.info("sendSuccess： " + response);
        }catch (Exception ex){
            log.error("sendFail: " + ex.toString());
            return false;
        }
        return true;
    }

    /**
     * 构建通知栏发送消息
     *
     * @param title      消息标题
     * @param content    消息内容体
     * @param appPkName  定义需要打开的appPkgName
     * @param actionType 动作类型    1 = 自定义行为，2 = 打开URL，3 = 打开APP
     * @param biTag      消息标签，如果带了这个标签，会在回执中推送给CP用于检测某种类型消息的到达率和状态
     * @param icon       自定义推送消息在通知栏的图标,value为一个公网可以访问的URL
     * @return 消息实例
     */
    public JSONObject buildMsg(String title, String content, String appPkName, int actionType, String biTag, String icon) {

        // 3.封装推送消息body，用于显示通知栏消息显示的标题和内容
        JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
        body.put("content", content);
        body.put("title", title);

        // 4.封装消息点击动作的参数，“com.huawei.hms.hmsdemo”为推送消息中需要打开的应用APK包名。请根据实际包名来修改。
        JSONObject param = new JSONObject();
        param.put("appPkgName", appPkName);

        // 5.封装消息点击动作，用于定义通知栏点击行为，param为步骤4封装的点击动作参数
        JSONObject action = new JSONObject();
        action.put("param", param);//消息点击动作参数
        action.put("type", actionType);

        // 6.封装消息类型，用于定义消息类型，区分是通知栏消息还是透传消息，其中action和body分别是步骤5和步骤3封装的消息。
        JSONObject msg = new JSONObject();
        msg.put("type", 3); //消息类型    1 = 透传异步消息，3 = 系统通知栏异步消息
        msg.put("body", body);//通知栏消息body内容
        msg.put("action", action);//消息点击动作

        // 7.封装扩展消息，扩展消息中可以设置biTag用于消息打点，也可以携带customize参数用于触发通知栏点击事件的onEvent回调。
        JSONObject ext = new JSONObject();//扩展信息，含BI消息统计，特定展示风格，消息折叠。
        ext.put("biTag", biTag);
        ext.put("icon", icon);

        // 8.封装整个消息体，可自定义实现单击消息后的跳转动作。封装完整的消息体请参考“payload消息体样例”。
        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
        hps.put("msg", msg);
        hps.put("ext", ext);

        JSONObject payload = new JSONObject();
        payload.put("hps", hps);

        return payload;
    }

    public JSONObject buildMsg(String title, String content, String appPkName) {

        // 3.封装推送消息body，用于显示通知栏消息显示的标题和内容
        JSONObject body = new JSONObject();//仅通知栏消息需要设置标题和内容，透传消息key和value为用户自定义
        body.put("title", title);
        body.put("content", content);

        // 4.封装消息点击动作的参数，“com.huawei.hms.hmsdemo”为推送消息中需要打开的应用APK包名。请根据实际包名来修改。
        JSONObject param = new JSONObject();
        param.put("appPkgName", appPkName);

        // 5.封装消息点击动作，用于定义通知栏点击行为，param为步骤4封装的点击动作参数
        JSONObject action = new JSONObject();
        action.put("param", param);//消息点击动作参数
        action.put("type", 3);
        // 6.封装消息类型，用于定义消息类型，区分是通知栏消息还是透传消息，其中action和body分别是步骤5和步骤3封装的消息。
        JSONObject msg = new JSONObject();
        msg.put("type", 3); //消息类型    1 = 透传异步消息，3 = 系统通知栏异步消息
        msg.put("action", action);//消息点击动作
        msg.put("body", body);//通知栏消息body内容

        // 8.封装整个消息体，可自定义实现单击消息后的跳转动作。封装完整的消息体请参考“payload消息体样例”。
        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
        hps.put("msg", msg);

        JSONObject payload = new JSONObject();
        payload.put("hps", hps);

        return payload;
    }


    /**
     * 构建透传异步消息
     *
     * @param body 自定义键值对
     * @return 消息实例
     */
    public JSONObject buildThroughMsg(JSONObject body) {

        JSONObject msg = new JSONObject();
        msg.put("body", body.toString());//body内容不一定是JSON，可以是String，若为JSON需要转化为String发送
        msg.put("type", 1);//1: 透传异步消息，通知栏消息请根据接口文档设置

        JSONObject hps = new JSONObject();//华为PUSH消息总结构体
        hps.put("msg", msg);

        JSONObject payload = new JSONObject();
        payload.put("hps", hps);

        return payload;
    }
}
