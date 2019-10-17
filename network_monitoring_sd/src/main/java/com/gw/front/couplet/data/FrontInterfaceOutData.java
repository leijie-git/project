package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontInterfaceOutData {
	private String id;
	private String eqName;
	private String buildareaname;//区域
	private String status;//当前状态
	private String goodName;
	private String badName;
	private String statusValue;
	private String digitalNormal;
	private Integer orderType;//控制类型
}
