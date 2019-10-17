package com.gw.openApi.common.data.in;

import com.gw.openApi.common.data.CheckAcountBaseData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnitBaseInData extends CheckAcountBaseData {
    @ApiModelProperty("单位id")
    private String unitId;
    @ApiModelProperty("单位编号")
    private String unitCode;
    @ApiModelProperty("单位地址")
    private String unitAddress;
    @ApiModelProperty("单位名称")
    private String unitName;
    @ApiModelProperty("联网设备ID")
    private long netDeviceId;
    @ApiModelProperty("superviseLevel")
    private String superviseLevel;
    @ApiModelProperty("unitDangerLevel")
    private String unitDangerLevel;
    @ApiModelProperty("unitType")
    private int unitType;
    @ApiModelProperty("superviseType")
    private int superviseType;
    private int pageSize;
    private int pageNumber;
}
