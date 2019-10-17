package com.gw.wechat.data;

import lombok.Data;

@Data
public class WeChatAlarmStatOutData {

	private Integer fireCount;// 报警主机未处理告警数量
	private Integer interfaceCount;// RTU告警 未处理数
	private Integer fireExtCount;//灭火器总数量
	private Integer hisRecordCount;//历史记录总数量(维保任务总数量+巡查任务总数量)
	private Integer mtcTaskCount;//维保任务未处理数量

}
