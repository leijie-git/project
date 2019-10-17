package com.gw.front.unit.data;

import lombok.Data;

@Data
public class UnitGradeInterfaceOutListData {

	private String interfaceId;
	private String eqSystemName; //系统名称
	private String className;
	private String detialName;
	private Long time;
	private String ioType;//模拟量数字量
}
