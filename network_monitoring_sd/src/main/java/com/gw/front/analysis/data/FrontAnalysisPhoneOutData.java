package com.gw.front.analysis.data;

import lombok.Data;

@Data
public class FrontAnalysisPhoneOutData {
	private String unitCode;
	private String childStationNum;
    private String unitName;
	private Integer phoneCount;//电话拨打次数
	private Integer phoneAnswerCount;//电话接通次数
	private Integer phoneSucCount;//电话拨通次数 
    private String longTime;//接通时长
	private String answerRate;// 通话率
	private String successRate;// 接通率
}
