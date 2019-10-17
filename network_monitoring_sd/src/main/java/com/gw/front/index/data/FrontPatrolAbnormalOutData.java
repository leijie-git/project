package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontPatrolAbnormalOutData {
	private Integer patrolFinishCount;// 巡查完成数
	private Integer patrolCount;// 巡查数
	private Integer patrolAbnormalCount;// 异常数
}
