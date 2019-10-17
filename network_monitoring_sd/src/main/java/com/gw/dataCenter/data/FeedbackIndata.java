package com.gw.dataCenter.data;

import lombok.Data;

@Data
public class FeedbackIndata {
	private Integer pageNumber;
	private Integer pageSize;
	private String content;
	private String createUser;
	private String phone;
}
