package com.gw.openApi.common.data.out;

import com.gw.openApi.common.data.CheckAcountBaseData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class EquipmentBaseData extends CheckAcountBaseData {
    @ApiModelProperty("eqId")
    private String eqId;
    @ApiModelProperty("eqIds")
    private List<String> eqIds;
    @ApiModelProperty("type")
    private int type;
    @ApiModelProperty("netDeviceId")
    private String netDeviceId;
    @ApiModelProperty("netDeviceName")
    private String netDeviceName;
    @ApiModelProperty("eqName")
    private String eqName;
    @ApiModelProperty("设备ID")
    private String eqSystemId;
    @ApiModelProperty("eqClassName")
    private String eqClassName;
    @ApiModelProperty("数据库来源")
    private String database;
}
