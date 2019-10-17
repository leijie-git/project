package com.gw.openApi.common.service;

import com.gw.openApi.common.data.in.CheckedAlarmVo;
import com.gw.openApi.common.data.out.HistoryAlarmCount;

import java.util.List;

public interface IAlarmApiService {

    Object getAlarmList(CheckedAlarmVo alarmInData) throws Exception;

    List<HistoryAlarmCount> getAlarmCount(CheckedAlarmVo alarmInData) throws Exception;
}
