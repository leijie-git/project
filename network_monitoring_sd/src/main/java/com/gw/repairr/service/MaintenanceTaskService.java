package com.gw.repairr.service;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.repairr.data.MaintenanceTaskData;
import com.gw.repairr.data.UserListData;

import java.util.List;

public interface MaintenanceTaskService {

    PageInfo<MaintenanceTaskData> getList(MaintenanceTaskData inData) throws Exception;

    List<MaintenanceTaskData> getPlanList(MaintenanceTaskData inData) throws Exception;

    void taskAdd(MaintenanceTaskData inData) throws Exception;

    void taskModify(MaintenanceTaskData inData) throws Exception;

    void taskDelete(String id) throws Exception;

    List<UserListData> getMaintenancePlanList(MaintenanceTaskData inData) throws Exception;

    List<UserListData> getMaintenanceUserList(MaintenanceTaskData inData) throws Exception;

}
