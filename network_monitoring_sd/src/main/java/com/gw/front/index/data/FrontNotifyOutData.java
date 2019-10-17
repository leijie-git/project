package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontNotifyOutData {
	private String id;
	private String sender;//
	private String title;
	private String content;
	private String sendDate;
	private String isRead;
}
