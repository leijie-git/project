package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontLookupUserInfoOutData {
	private byte[] cardInfoPath;//证件信息
	private String lookupInfoPath;//查岗人员影像
	private String userName;//姓名
	private String phone;//联系电话
	private String cardType;//证件类型
	private String cardNo;//身份证号
	private String cardDate;//发证日期
	private String cardNumber;//证书编号
	private String address;//地址
	private String sex;//性别
	private String nation;//民族
	private String cardFrom;
	private String birth;
	private String startDate;
	private String endDate;
	private String certificate;//上岗证号
	private String infoId;//行业人员id
}
