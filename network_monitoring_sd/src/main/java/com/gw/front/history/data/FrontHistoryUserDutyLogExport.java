package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryUserDutyLogExport {
	private String userName;//值班人
	private String controlRoom;//消控室
	private String unitName;//所属单位
	private String startDate;//值班开始时间
	private String endDate;//值班结束时间
	private String address;//住址
	private String phone;//电话
	private String certificatesNO;//身份证
}
