package com.gw.util;

public class ReqApiConst {

    /**
     * 微信发送模板消息url(参数已格式化)
     */
    public static final String POST_WECHAT_SENDTEMMSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=%s";
    /**
     * 微信发送客户消息url(参数已格式化)
     */
    public static final String POST_WECHAT_SENDCUSTMSG_URL = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=%s";
    /**
     * 微信获取snstoken
     */
    public static final String GET_WECHAT_GETSNSTOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%1$s&secret=%2$s&code=%3$s&grant_type=authorization_code";
    /**
     * 微信获取token的url
     */
    public static final String GET_WECHAT_GETTOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

    /**
     * 微信获取jsapi_ticket
     */
    public static final String GET_WECHAT_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=%s&type=jsapi";



    /**
     * 奇瑞发送短信
     */
    public static final String GET_QIRUI_SENDSHORTMSG_URL = "http://api.qirui.com:7891/mt";
    /**
     * 同步设备设施数据到红版api
     */
    public static final String POST_REDSERVER_SYNCHEQ_API = "NSyncCommand?access_token=%1$s&userid=%2$s&ownercode=%3$s&cmd=%4$s";
    /**
     * 查岗/点名红版api
     */
    public static final String GET_REDSERVER_CHECKDEVICE_API = "NCommandToInspectOrEnroll?access_token=%1$s&userid=%2$s&flag=%3$s&ownercode=%4$s&deviceindex=%5$s&deviceno=%6$s";
    /**
     * 红版设备控制api
     */
    public static final String GET_REDSERVER_SENDCMD_API = "NCommandToRTU?access_token=%1$s&userid=%2$s&deviceid=%3$s&interfaceid=%4$s&actiontype=%5$s&level=%6$s";
    /**
     * 淘宝获取ip位置url
     */
    public static final String GET_TAOBAO_GETIPLOCAL_URL = "http://ip.taobao.com/service/getIpInfo.php?ip=%1$s";
    /**
     * 百度获取ip位置url
     */
    public static final String GET_BAIDU_GETIPLOCAL_URL = "http://api.map.baidu.com/location/ip?ip=%1$s&ak=%2$s&coor=bd09ll";
    /**
     * 解析token
     */
    public static final String GET_101_ANALYSETOKEN_URL = "http://47.96.127.101:8083/token/analyseToken?token=%1$s";
    /**
     * 获取新门海平台token
     */
    public static final String GET_XINMENHAI_GETTOKEN_URL = "http://120.26.41.225:9058/shinterface/api";
    /**
     * 新门海token获取参数拼接(第一次获取)
     */
    public static final String GET_XINMENHAI_GETTOKEN_PARAM ="?action=token&method=getToken&grant_type=client&appid=iczjax&secret=229E7377B2CC45668CF91AEA23330001";
    /**
     * 新门海token获取参数拼接(第二次获取)
     */
    public static final String GET_XINMENHAI_UPDATETOKEN_PARAM ="?action=token&method=getToken&token=%1$s";
    /**
     * 安讯token获取url
     */
    public final static String GET_ANXUN_GETTOKEN_URL = "http://chianzz.msu7.net/apis";
    /**
     * 获取token
     */
    public final static String GET_YS_GETTOKEN_URL = "https://open.ys7.com/api/lapp/token/get?appKey=%s&appSecret=%s";
}
