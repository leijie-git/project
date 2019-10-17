package com.gw.systemManager.data;

import lombok.Data;

@Data
public class NotifyOutData {
	private String id;
	private String sender;
	private String title;
	private String content;
	private String sendDate;
	private String receiver;
	private String userId;
}
