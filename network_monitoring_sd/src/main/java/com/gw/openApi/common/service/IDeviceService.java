package com.gw.openApi.common.service;

import com.github.pagehelper.PageInfo;
import com.gw.front.history.data.FrontHistoryInData;
import com.gw.openApi.common.data.in.UnitDeviceInData;
import com.gw.openApi.common.data.out.DeviceRunningData;
import com.gw.openApi.common.data.out.EquipmentBaseData;
import com.gw.openApi.common.data.out.UnitDeviceOutData;

import java.util.Map;

public interface IDeviceService {

    DeviceRunningData getEquipmentRunningInfo(EquipmentBaseData inData) throws Exception;

    PageInfo<UnitDeviceOutData> getUnitDeviceList(UnitDeviceInData deviceInData) throws Exception;

    Map<String, String> getDeviceTypeList();

    Object getDeviceList(FrontHistoryInData inData) throws Exception;
}
