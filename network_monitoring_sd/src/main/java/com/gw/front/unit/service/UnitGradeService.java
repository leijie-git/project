package com.gw.front.unit.service;

import com.gw.front.unit.data.UnitAlarmGradeOutData;
import com.gw.front.unit.data.UnitGradeInData;
import com.gw.front.unit.data.UnitGradeOutData;

/**
 * 单位评分
 *
 * @author yja
 * @date 2019年8月6日13点47分
 */
public interface UnitGradeService {

	/**
	 * 综合评分
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	UnitGradeOutData getUnitGrade(UnitGradeInData inData) throws Exception;

	/**
	 * 告警评分
	 *
	 * @param inData
	 * @return
	 * @throws Exception
	 */
	UnitAlarmGradeOutData getAlarmGrade(UnitGradeInData inData) throws Exception;
}
