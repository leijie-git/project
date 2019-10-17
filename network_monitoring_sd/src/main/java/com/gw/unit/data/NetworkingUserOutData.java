package com.gw.unit.data;

import lombok.Data;
/**
 * 维保单位人员
 * @author SY
 *
 */

@Data
public class NetworkingUserOutData {
	private String id;
	private String account;
	private String password;
	private String userrole;
	private String usertype;
	private String sex;
	private String username;
	private String birthday;
	private String email;
	private String mobilephone;
	private String department;
	private String post;
	private String certificatestype;
	private String certificatesno;
	private String certificatesdate;
	private String licenseno;
	private String photo;
	private String certificatespic;
	private String unitname;
	private String unitid;
	private String expirytime;
	private String receivealarmtype;
}
