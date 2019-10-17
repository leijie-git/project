package com.gw.alarm.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class AlarmBJZJInData {

	@ApiModelProperty("设备描述")
	private String Alarm_DeviceDesc;//设备描述

    @ApiModelProperty("报警源")
	private String Alarm_SourceDesc;//报警源

    @ApiModelProperty("报警类型")
	private String Alarm_Status;//报警类型（EnumAlarmStatus）

    @ApiModelProperty("报警位置")
	private String Alarm_WhereDesc;//报警位置

    @ApiModelProperty("回路号")
	private String HLID;//回路号

    @ApiModelProperty("节点号")
	private String JDID;//节点号

    @ApiModelProperty("主机时间")
	private String Time;//主机时间（对应沈工表中lastUpdate）

    @ApiModelProperty("部位号")
	private String ZJID;//主机

    @ApiModelProperty("报警时间")
	private String alarmtime;//报警时间（对应沈工表中Time）

    @ApiModelProperty("设备索引")
	private String deviceindex;//设备索引

    @ApiModelProperty("设备编号")
	private String deviceno;//设备编号

    @ApiModelProperty("是否测试")
	private String isTest;//是否测试

    @ApiModelProperty("设备12位编码（单位编号）")
	private String onwercode;//设备12位编码（单位编号）
}
