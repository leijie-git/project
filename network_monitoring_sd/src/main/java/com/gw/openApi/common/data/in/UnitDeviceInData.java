package com.gw.openApi.common.data.in;

import com.gw.openApi.common.data.CheckAcountBaseData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UnitDeviceInData extends CheckAcountBaseData {
    @ApiModelProperty("单位ID")
    private String unitId;
    @ApiModelProperty("设备名称")
    private String deviceName;
    @ApiModelProperty("设备类型")
    private int deviceType;
    private int pageSize;
    private int pageNumber;
}
