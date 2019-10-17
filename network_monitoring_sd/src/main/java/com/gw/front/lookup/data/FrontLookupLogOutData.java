package com.gw.front.lookup.data;

import lombok.Data;

@Data
public class FrontLookupLogOutData {
	private String id;
	private String unitcode;//单位编码
	private String childstationnum;//子站号
	private String unitname;//单位名称
	private String unitaddress;//单位地址
	private String sendDate;//发送日期
	private String status;//发送状态
	private String lookStatus;//查岗状态
	private String receiveDate;//回应时间
	private String cardName;//身份证名字
	private String recerveUser;//回应人
	private String userName;//发送人
	private String remark;
	private String cardID;
	private String isValidate;//是否有效

}
