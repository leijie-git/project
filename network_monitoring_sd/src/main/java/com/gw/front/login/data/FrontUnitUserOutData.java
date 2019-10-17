package com.gw.front.login.data;

import com.gw.front.index.data.FrontLoginLogOutData;

import lombok.Data;

@Data
public class FrontUnitUserOutData {

	private String id;

	private String username;

	private String account;

	private String password;

	private String usertype;

	private String userrole;

	private Integer sex;

	private String birthday;

	private String email;

	private String mobilephone;

	private String department;

	private String post;

	private String isdelete; 

	private String adddate;

	private String adduserid;

	private String userid;

	private String openId;

	private String certificatestype;

	private String certificatesno;

	private String photo;

	private String unitid;
	
	private String unitName;

	private String certificatespic;

	private String certificatesdate;

	private String licenseno;

	private String token;
	
	private String ispushfault;
	
	private String channelId;	//手机唯一标识，由客户端向相关云平台获取

	private String phoneName;	//手机型号，决定服务端使用何种方式推送app消息
	
	private String currAddress;
	
	private FrontLoginLogOutData log;
	
	private String loginTime;
	
	private String mapCenter;
	
	private String mapLevel;
	
	private String currentLogId;//当前
	
	private String ip;
	
	private String logourl;
}
