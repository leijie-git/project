package com.gw.alarm.data;

import com.alibaba.fastjson.JSONObject;

import lombok.Data;

@Data
public class TemplateMessage {
	private String touser; // 用户OpenID
	private String templateId; // 模板消息ID
	private String clickurl; // URL置空，在发送后，点模板消息进入一个空白页面（ios），或无法点击（android）。
	private String topcolor; // 模板字体的颜色
	private JSONObject templateData; // 模板详情变量 Json格式
}
