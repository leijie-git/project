package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryUserDutyLogOutData {
	private String id;
	private String userName;//值班人
	private String unitName;//所属单位
	private String controlRoom;//消控室
	private String startDate;//开始时间
	private String endDate;//结束时间
	private String address;//住址
	private String phone;//电话
	private String certificatesNO;//身份证
}
