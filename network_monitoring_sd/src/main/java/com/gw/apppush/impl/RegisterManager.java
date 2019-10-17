package com.gw.apppush.impl;

import com.alibaba.fastjson.JSONObject;
import com.baidu.yun.push.auth.PushKeyPair;
import com.baidu.yun.push.client.BaiduPushClient;
import com.baidu.yun.push.constants.BaiduPushConstants;
import com.gw.apppush.AppPushConfig;
import com.gw.util.Util;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.text.MessageFormat;

import static com.gw.apppush.AppPushConfig.*;

/**
 * app推送服务初始化
 **/
class RegisterManager {

    private static Logger log = LoggerFactory.getLogger(RegisterManager.class);

    private static Sender sender;

    private static String accessToken;//下发通知消息的认证Token
    private static long tokenExpiredTime;  //accessToken的过期时间

    private static BaiduPushClient baiduPushClient;

    static {

        //注册小米云推送
        //Constants.useSandbox();   测试环境下使用Push服务   测试环境暂只支持iOS，不支持Android
        Constants.useOfficial();
        sender = new Sender(AppPushConfig.MI_APP_SECRET);
        log.info("小米云注册成功");

        //注册华为云推送
        //refreshToken();

        //注册百度云推送
        initPushClient();
    }

    /**
     * 获取token，即注册华为云推送
     * Access Token存在有效期，当前有效期为1个小时，未来可能有变动，有效期通过响应参数消息的expires_in参数传达。Access Token在有效期内请尽量复用，
     * 如果开发者应用服务器频繁的申请Access Token，可能会被流控。Access Token过期前，业务要根据这个有效时间提前去申请新Access Token。业务在API调
     * 用获知Access Token已经超过的情况下（NSP_STATUS=6），需要触发access_token的申请流程。
     */
    private static void refreshToken() {
        try {
            String msgBody = MessageFormat.format(
                    "grant_type=client_credentials&client_secret={0}&client_id={1}",
                    URLEncoder.encode(HW_appSecret, "UTF-8"), HW_appId);
            String response = HuaWeiUtil.httpPost(HW_tokenUrl, msgBody, 5000, 5000);
            JSONObject obj = JSONObject.parseObject(response);

            if (Util.isEmpty(obj.getString("error"))) {
                accessToken = obj.getString("access_token");
                tokenExpiredTime = System.currentTimeMillis() + obj.getLong("expires_in") - 5 * 60 * 1000;
                log.info("华为云获取token成功：accessToken - {},tokenExpiredTime - {}", accessToken, tokenExpiredTime);
            } else {
                log.error("华为云获取token失败：errorCode - {},errorDesc - {}", obj.getString("error"), obj.getString("error_description"));
            }
        } catch (Exception e) {
            log.error("华为云获取token失败：", e.getMessage());
        }

    }

    /**
     * 注册百度云推送
     */
    private static void initPushClient() {

        //1. 创建PushKeyPair 用于app的合法身份认证 apikey和secretKey可在应用详情中获取
        PushKeyPair pair = new PushKeyPair(BD_apiKey, BD_Secret);

        // 2. 创建BaiduPushClient，访问SDK接口
        BaiduPushClient pushClient = new BaiduPushClient(pair, BaiduPushConstants.CHANNEL_REST_URL);

        // 3. 注册YunLogHandler，获取本次请求的交互信息
        pushClient.setChannelLogHandler(event -> log.info(event.getMessage()));
        baiduPushClient = pushClient;

        log.info("百度云注册成功");
    }

    Sender getSender() {
        return sender;
    }

    String getAccessToken() {
        if (tokenExpiredTime <= System.currentTimeMillis()) {
            refreshToken();
        }
        return accessToken;
    }

    BaiduPushClient getBaiduPushClient() {
        return baiduPushClient;
    }
}
