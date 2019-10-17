package com.gw.front.lookup.data;

import lombok.Data;

@Data
public class FrontNamingDataExport {
	private String unitcode;// 单位编码
	private String childstationnum;// 子站号
	private String unitname;// 单位名称
	private String userName;// 发送人
	private String sendDate;// 发送日期
	private String status;// 点名结果
	private String receiveDate;// 回应时间
}
