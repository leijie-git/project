package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnalysisCommunicationOutData {
	
	private String phoneCount;//电话拨打次数
	private String phoneAnswerCount;//电话接通次数
	private String phoneSucCount;//电话拨通次数
	private String messageCount;//短信数
	private String sendCount;//发送流量
	private String receiveCount;//接收流量
}
