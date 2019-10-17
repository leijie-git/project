package com.gw.openApi.common.service;

import com.github.pagehelper.PageInfo;
import com.gw.front.maintenance.data.FrontMaintenanceOutData;
import com.gw.openApi.common.data.in.MaintenanceInData;

public interface IMaintenanceService {
    PageInfo<FrontMaintenanceOutData> getMaintenanceList(MaintenanceInData inData) throws Exception;

    FrontMaintenanceOutData getMaintenanceDetail(String repairId) throws Exception;
}
