package com.gw.repairr.data;

import lombok.Data;

@Data
public class EquipmentListData {

    private Long ID;
    private String EqName;//设备设施名称
    private String BuildingName;//建筑名称
    private String unitName;

    private String InstallPosition;//检测位置
    private String ClassName;//检测类型
    private String name;//联网设备名称


}
