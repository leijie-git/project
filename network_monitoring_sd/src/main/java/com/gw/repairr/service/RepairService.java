package com.gw.repairr.service;

import com.github.pagehelper.PageInfo;
import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.repairr.data.ParameterData;
import com.gw.repairr.data.RepairData;

import java.util.List;

public interface RepairService {

    PageInfo<RepairData> getRepairList(RepairData inData) throws Exception;

    List<GenerateMaintenanceUserData> getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData) throws Exception;

    void repairAdd(ParameterData inData) throws Exception;

    void repairModify(ParameterData inData) throws Exception;

    void repairDelete(String id) throws Exception;

    void repairReset(String id) throws Exception;

    void repairGenerate(ParameterData inData) throws Exception;

    Integer repairGenerateNumber(ParameterData inData) throws Exception;

    List<RepairData> getUnitList(RepairData inData) throws Exception;

}
