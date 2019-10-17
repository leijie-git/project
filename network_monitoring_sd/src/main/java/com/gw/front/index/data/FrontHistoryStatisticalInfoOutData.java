package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontHistoryStatisticalInfoOutData {
	private Integer onLine = 0; // 在线
	private Integer total = 0; // 总数
	private Integer concern = 0;// 关注
	private Integer report = 0;// 报备
	private Integer test = 0;// 测试
	private Integer fireAlarm = 0;// 火警
	private Integer fault = 0;// 故障
	private Integer startUp = 0;// 启动
	private Integer feedBack = 0;// 反馈
	private Integer supervise = 0;// 监管
	private Integer shield = 0;// 屏蔽
	private Integer reset = 0;// 复位
}
