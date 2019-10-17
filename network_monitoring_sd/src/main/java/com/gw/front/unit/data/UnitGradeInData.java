package com.gw.front.unit.data;

import lombok.Data;

/**
 * 单位评分入参
 *
 * @author yja
 * @date 2019年8月6日13点47分
 */
@Data
public class UnitGradeInData {

	//单位Id
	private String unitId;

	private String userId;

	private String startDate;

	private String endDate;

	private String type;


}
