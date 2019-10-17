package com.gw.alarm.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class ReturnData {
	@ApiModelProperty("请求处理状态：true，成功；false：异常")
	private boolean IsSuccess = true;
	@ApiModelProperty("请求处理描述")
	private String Message = "success!";
}
