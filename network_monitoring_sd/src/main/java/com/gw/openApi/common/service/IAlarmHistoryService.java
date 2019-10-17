package com.gw.openApi.common.service;

import com.github.pagehelper.PageInfo;
import com.gw.front.couplet.data.FrontCoupletAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryAlarmInfoOutData;
import com.gw.front.history.data.FrontHistoryInterfaceAlarmData;
import com.gw.openApi.common.data.in.AlarmInData;

import java.util.Map;

public interface IAlarmHistoryService {
    PageInfo<FrontHistoryAlarmInfoOutData> getMainframeAlarmList(AlarmInData inData) throws Exception;

    PageInfo<FrontHistoryInterfaceAlarmData> getRTUAlarmList(AlarmInData inData) throws Exception;

    Map<Integer, String> getAlarmTypeList();

    FrontCoupletAlarmInfoOutData getMainframeDealDetail(String alarmId) throws Exception;

    FrontCoupletAlarmInfoOutData getRtuDealDetail(String alarmId) throws Exception;
}