package com.gw.mapper;

import com.gw.common.BaseMapper;
import com.gw.generatereport.GenerateMaintenanceUserData;
import com.gw.mapper.entity.UtLzRepairPlan;
import com.gw.repairr.data.EquipmentListData;
import com.gw.repairr.data.MaintenanceTaskData;
import com.gw.repairr.data.RepairData;

import java.util.List;

public interface UtLzRepairPlanMapper extends BaseMapper<UtLzRepairPlan> {

    List<RepairData> getRepairList(RepairData inData) throws Exception;

    List<GenerateMaintenanceUserData> getMaintenanceUserBynetworkingID(GenerateMaintenanceUserData generateMaintenanceUserData) throws Exception;

    List<EquipmentListData> getEquipmentList(Long UnitID) throws Exception;

    List<MaintenanceTaskData> getPlanList(MaintenanceTaskData inData) throws Exception;

    List<RepairData> getUnitList(RepairData inData) throws Exception;

}
