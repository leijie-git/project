package com.gw.wechat.data;

import lombok.Data;

@Data
public class FrontRepairDetailOutData {

	private String id;//详情id
	private String dealUser;// 处理人
	private String dealDate;// 执行时间
	private String siteName;// 放置位置
	
	private String questionInfo;// 问题描述
	private String quetionUrl;//问题照片
	
	private String checkInfo;//维保内容
	private String checkUrl;//维保照片

}
