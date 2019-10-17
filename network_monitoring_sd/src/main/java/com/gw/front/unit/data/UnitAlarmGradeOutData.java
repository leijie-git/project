package com.gw.front.unit.data;

import lombok.Data;

/**
 * @author yja
 * @date 14点22分
 * 告警评分
 */
@Data
public class UnitAlarmGradeOutData {

	//火灾告警评分
	private Double alarmGrade;

	//水系统评分
	private Double waterGrade;

	//电气火灾
	private Double electricalGrade;

	//NB,loar
	private Double NBGrade;
}
