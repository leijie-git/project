package com.gw.openApi.common.data.out;

import lombok.Data;

@Data
public class UnitBuildingOutData {
    private String unitId;
    private String unitCode;
    private String unitAddress;
    private String phone;
    private String fireRating;
    private int inductionPoinCount;
    private String unitName;
    private String superviseLevel;
    private String unitDangerLevel;
    private String unitType;
    private String superviseType;
}
