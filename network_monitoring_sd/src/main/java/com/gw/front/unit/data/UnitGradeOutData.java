package com.gw.front.unit.data;


import lombok.Data;

/**
 * 单位评分出参
 *
 * @author yja
 * @date 2019年8月6日13点47分
 */
@Data
public class UnitGradeOutData {

	//资料完整性得分
	private Double materialGrade;
	//系统接入量得分
	private Double  systemAccessGrade;
	//巡检系统得分
	private Double  patrolGrade;
	//维修系统得分
	private Double  maintainGrade;
	//值班查岗系统得分
	private Double  sentriesGrade;

}
