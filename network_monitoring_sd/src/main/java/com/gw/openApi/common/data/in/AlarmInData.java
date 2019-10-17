package com.gw.openApi.common.data.in;

import com.gw.openApi.common.data.CheckAcountBaseData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("主机报警数据入参")
@Data
public class AlarmInData extends CheckAcountBaseData {
    @ApiModelProperty("报警类型")
    private int alarmType;//报警类型
    @ApiModelProperty("系统类型")
    private int sysType;//系统类型
    @ApiModelProperty("告警ID")
    private String alarmId;//告警ID
    @ApiModelProperty("是否已处理")
    private String isDeal;// 是否已处理
    @ApiModelProperty("报警单位")
    private String keyword;//报警单位
    @ApiModelProperty("报警开始时间")
    private String startDate;//报警开始时间
    @ApiModelProperty("报警结束时间")
    private String endDate;//报警结束时间
    private int pageSize;
    private int pageNumber;
}
