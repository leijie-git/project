package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "SYS_PROPERTIES")
@Data
public class SysProperties implements Serializable {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "wx_appid")
    private String wxAppid; //微信appid

    @Column(name = "wx_secret")
    private String wxSecret;    //微信秘钥

    @Column(name = "wx_redirect_uri")
    private String wxRedirectUri;   //微信跳转路径

    @Column(name = "wx_temp_id")
    private String wxTempId;    //微信模板id

    @Column(name = "wx_temp_rtu_id")
    private String wxTempRtuId; //微信模板RTUid

    @Column(name = "wx_temp_inspecttask_id")
    private String wxTempInspectTaskId;//微信模板巡查Id

    @Column(name = "wx_temp_extinguisher_id")
    private String wxTempExtinguisherId;//微信模板灭火器Id

    @Column(name = "wx_click_url")
    private String wxClickUrl;  //微信跳转clickurl

    @Column(name = "short_message_api_key")
    private String shortMessageApiKey;  //启瑞短信信息 接口账号

    @Column(name = "short_message_api_secret")
    private String shortMessageApiSecret;   //启瑞短信信息 认证秘钥

    @Column(name = "sign_name")
    private String signName;    //启瑞短信信息 签名

    @Column(name = "red_server_port")
    private String redServerPort;   //原始数据同步服务器路径

    @Column(name = "access_token")
    private String accessToken;     //原始数据同步令牌

    @Column(name = "android_api_key")
    private String androidApiKey;   //apiKey

    @Column(name = "android_api_secret")
    private String androidApiSecret;    //apiSecret

    @Column(name = "project_domain_name")
    private String projectDomainName;   //项目域名

    @Column(name = "map_center")
    private String mapCenter;   //首页地图中心点

    @Column(name = "map_level")
    private String mapLevel;    //地图级别

    @Column(name = "server_time_out_phone")
    private String serverTimeOutPhone;  //服务到期提醒人电话

    @Column(name = "wz_title")
    private String wzTitle; //项目标题

    @Column(name = "js_providers")
    private String jsProviders; //公司名称

    @Column(name = "gw_url")
    private String gwUrl;   //公司官网链接

    @Column(name = "wz_recordAddress")
    private String wzRecordaddress; //备案号

    @Column(name = "wz_record")
    private String wzRecord;    //备案号链接

    @Column(name = "yx_account")
    private String yxAccount;   //邮箱账号

    @Column(name = "yx_pwd")
    private String yxPwd;   //邮箱密码

    @Column(name = "yx_smtpserver")
    private String yxSmtpserver;    //邮箱代理服务器

    @Column(name = "yx_smtpport")
    private String yxSmtpport;  //邮箱代理服务器端口

    @Column(name = "video_appkey")
    private String videoAppkey; //萤石云密钥

    @Column(name = "video_appsecret")
    private String videoAppsecret;  //萤石云密钥
    //稠江Token
    @Column(name = "chouJiangToken")
    private String chouJiangToken;
    //苏溪Token
    @Column(name = "suXiToken")
    private String suXiToken;

    //token过期时间
    @Column(name = "expiresin")
    private Long expiresin;
    private static final long serialVersionUID = 1L;


}