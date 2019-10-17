package com.gw.systemManager.data;


import lombok.Data;

/**
 * 输出用户实体
 *
 */
@Data
public class SysUserOutData {

	private String id;

	private String userName;

	private String account;
	
	private String phone;

	private String password;
	
	private String userHeader;
	
	private String createDate;
	
	private String createUser;
	
	private String updateDate;
	
	private String updateUser;
	private String birthday;
	private String sex;
	private String address;
	private String ethnicGroup;
	private String sign;
	private String remark;

}
