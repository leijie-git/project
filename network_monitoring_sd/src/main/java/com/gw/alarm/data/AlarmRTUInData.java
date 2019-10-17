package com.gw.alarm.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class AlarmRTUInData {
	@ApiModelProperty("报警类型")
	private String Status;//报警类型
	@ApiModelProperty("报警时间")
	private String alarmtime;//报警时间
	@ApiModelProperty("报警值")
	private String alarmvalue;//报警值
	@ApiModelProperty("设备索引")
	private String deviceindex;//设备索引
	@ApiModelProperty("设备编号")
	private String deviceno;//设备编号
	@ApiModelProperty("端口号")
	private String ioport;//端口号
	@ApiModelProperty("端口类型")
	private String iotype; //端口类型
	@ApiModelProperty("单位编号")
	private String onwercode; //单位编号
	@ApiModelProperty("报警状态")
	private String alarmStatus;//报警状态
}
