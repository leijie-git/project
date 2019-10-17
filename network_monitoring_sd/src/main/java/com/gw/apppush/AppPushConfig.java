package com.gw.apppush;

/**
 * @description: app推送平台相关配置
 **/
public class AppPushConfig {

    //小米云
    public static String MI_APP_Id = "2882303761518010264";
    public static String MI_APP_KEY = "5561801098264";
    public static String MI_APP_SECRET = "9KPM8W0ESqXTwiOyAVbwxQ==";
    public static String MI_PACKAGE_NAME = "com.sd.gzsystem.firenetmonitorsys";

    public static int MI_default_retries = 3;   //默认重试次数

    //华为云
    public static String HW_appId = "100875377";//用户在华为开发者联盟申请的appId和appSecret（会员中心->应用管理，点击应用名称的链接）
    public static String HW_appSecret = "53dc70760ce4fbacf9802789643def24c2b26cc1de0eb08e84c2b7ed50cd40bd";
    public static String HW_PACKAGE_NAME = "com.sd.gzsystem.firenetmonitorsys"; //应用级消息下发API
    //public static String HW_tokenUrl = "https://login.vmall.com/oauth2/token"; //获取认证Token的URL
    public static String HW_tokenUrl = "https://login.cloud.huawei.com/oauth2/v2/token"; //获取认证Token的URL
    public static String HW_apiUrl = "https://api.push.hicloud.com/pushsend.do"; //应用级消息下发API

    //百度云
    public static String BD_apiKey = "GTRPjSjjXdxNeFg9IYghzyfv"; //应用级消息下发API
    public static String BD_Secret = "EpPB7iEAQfy4Otii31l1NnVohxy9dnbb"; //应用级消息下发API

    public static int BD_DEVICETYPE_Android = 3; //设备类型-安卓
    public static int BD_MSGTYPE_notice= 1; //消息类型-通知

}
