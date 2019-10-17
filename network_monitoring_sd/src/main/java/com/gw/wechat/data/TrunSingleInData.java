package com.gw.wechat.data;

import lombok.Data;

/**
 * 转单
 *
 * @author yja
 * @date 2019年7月15日
 */
@Data
public class TrunSingleInData {

	/**
	 * 任务Id
	 */
	private String taskId;
	/**
	 * 转单 1-发起转单 2-监督人已通过 3-监督人已驳回 4-接单人通过 5-接单人拒绝
	 */
	private Integer isChange;
	/**
	 * 转单人Id
	 */
	private String changeUserId;
	/**
	 * 是否接单(0否，1是）
	 */
	private Integer isReceive;
	/**
	 * 接单人Id
	 */
	private String receiveUserId;
	/**
	 * 转单时间
	 */
	private String changeTime;
	/**
	 * 接单时间
	 */
	private String receiveTime;
	/**
	 * 监督人Id
	 */
	private String supervisorId;
	/**
	 * 是否转单 0-未转单 1-发起转单 2-已通过 3-已驳回
	 */
	private Integer receiveStatus;

}
