package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontUserDutyLogInData {
	private String shiftUser;  //交班人
	private String shiftUserAccount;  //交班人账号
	private String shiftUserPassword;//交班人密码
	private String dutyUser;  //值班人
	private String dutyUserAccount;  //值班人账号
	private String dutyUserPassword;  //值班人密码
	private String startDate;//开始时间
	private String endDate;//结束时间
	private String buildArea;//监控区域
	private String remark;//备注

}
