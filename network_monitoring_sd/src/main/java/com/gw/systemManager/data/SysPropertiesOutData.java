package com.gw.systemManager.data;

import java.io.Serializable;

import lombok.Data;

@Data
public class SysPropertiesOutData implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String id;

    private String wxAppid; //微信appid
    private String wxSecret;    //微信秘钥
    private String wxRedirectUri;   //微信跳转路径
    private String wxTempId;    //微信模板id
    private String wxTempRtuId; //微信模板RTUid
    private String wxClickUrl;  //微信跳转clickurl
    private String wxTempInspectTaskId;
    private String wxTempExtinguisherId;
    private String shortMessageApiKey;  //启瑞短信信息 接口账号
    private String shortMessageApiSecret;   //启瑞短信信息 认证秘钥
    private String signName;    //启瑞短信信息 签名

    private String redServerPort;   //原始数据同步服务器路径
    private String accessToken; //原始数据同步令牌

    private String androidApiKey;   //apiKey
    private String androidApiSecret;    //apiSecret

    private String projectDomainName;   //项目域名
    private String mapCenter;   //首页地图中心点
    private String mapLevel;    //地图级别
    private String serverTimeOutPhone;  //服务到期提醒人电话
    private String wzTitle; //项目标题
    private String jsProviders; //公司名称
    private String gwUrl;   //公司官网链接
    private String wzRecordaddress; //备案号
    private String wzRecord;    //备案号链接

    private String yxAccount;   //邮箱账号
    private String yxPwd;   //邮箱密码
    private String yxSmtpserver;    //邮箱代理服务器
    private String yxSmtpport;  //邮箱代理服务器端口

    private String videoAppkey; //萤石云密钥
    private String videoAppsecret;  //萤石云密钥
    //稠江token
    private String chouJiangToken;
    //苏溪token
    private String suXiToken;
    //过期时间
    private long expiresin;

}
