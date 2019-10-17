package com.gw.repairr.data;

import lombok.Data;

@Data
public class MaintenanceTaskData {
    private String ID;
    private String RepairSite;//地址
    private String eqName;//设备设施名称
    private String eqid;//设备设施id
    private Long executorID;//执行人id
    private String UserName;//执行人
    private String UnitName;//单位名称
    private String UnitID;//单位id
    private String BuildingName;//建筑名称
    private String BuildAreaName;//区域名称
    private String taskStartTime;//开始时间
    private String taskEndTime;//结束时间
    private String planName;//计划名称
    private String planID;//计划表id
    private String CreateUserID;//创建人id
    private Integer pageNumber;
    private Integer pageSize;

}
