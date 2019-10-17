package com.gw.front.couplet.data;

import java.util.List;

import lombok.Data;

@Data
public class FrontElectricalStatOutData {
	private String detialName;//端口名称
	private String className;//端口类型
	private String classCode;//端口类型
	private List<FrontCoupletCommonOutData> statData;//统计数据
	private String iOType;//端口类型
	private String iOPort;//端口
	private String analogup;//最大值
	private String analogdown;//最小值
	private String analogWarningUp;//预警最大值（单纯最大值）
	private String analogWarningDown;//预警最小值（单纯最大值）
	private String analogUnit;//单位
	private String normalValue;//正常值
	private String goodName;//正常值
	private String badName;//异常值
}
