package com.gw.wechat.data;

import lombok.Data;

@Data
public class PhoneAlarmInfoIndata {
	private String id;
	private String picUrl;// 微信上传图片路径
	private String isPushWb;// 是否推送维保
	private String pushDesc;// 推送描述
	private String dealResult;// 处理结果（是否误报）
	private String dealInfo;// 处理内容
	private String token;
	private String userId;
	private String openId;
}
