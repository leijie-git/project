package com.gw.alarm.data;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *@描述
 *@创建人 Jie.Lei
 *@创建时间 2019/7/24
 */
@Data
public class AlarmInData {
	//主机id
 private String id;
	//第几页
private Integer pageNumber;
	//每页条数
private Integer pageSize;

}
