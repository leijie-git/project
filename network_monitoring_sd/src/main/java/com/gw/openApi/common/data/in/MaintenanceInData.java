package com.gw.openApi.common.data.in;

import com.gw.openApi.common.data.CheckAcountBaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("主机查询数据入参")
@Data
public class MaintenanceInData extends CheckAcountBaseData {
    @ApiModelProperty(hidden = true)
    private Integer pageNumber;
    @ApiModelProperty(hidden = true)
    private Integer pageSize;
    @ApiModelProperty("dateType")
    private String dateType;
    @ApiModelProperty("开始时间")
    private String startDate;
    @ApiModelProperty("结束时间")
    private String endDate;
    @ApiModelProperty("查询关键字")
    private String keyword;
    @ApiModelProperty("单位ID")
    private String unitId;
    @ApiModelProperty("状态")
    private String status;
    @ApiModelProperty("建筑ID")
    private String buildId;
    @ApiModelProperty("当前时间")
    private String nowDate;//当前时间
}
