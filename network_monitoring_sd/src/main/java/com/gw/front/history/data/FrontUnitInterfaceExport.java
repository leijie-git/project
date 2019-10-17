package com.gw.front.history.data;

import lombok.Data;

/**
 * 电气火灾
 * 
 * @author Administrator
 *
 */
@Data
public class FrontUnitInterfaceExport {
	private String eqName;//报警设备
	private String alarmTime;// 报警时间
	private String alarmCat;// 描述
	private Integer count;// 次数
}
