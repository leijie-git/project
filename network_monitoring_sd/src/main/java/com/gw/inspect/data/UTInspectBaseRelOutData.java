package com.gw.inspect.data;

import lombok.Data;

/**
 * 巡查点检查项配置管理
 */
@Data
public class UTInspectBaseRelOutData {
	private String id;//检查项配置ID
	private String siteId;//巡查点ID
	private String siteName;//巡查点
	private String siteClassDetialId;//检查项ID
	private String checkInfo;//检查项
	private String checkMethod;//检查方式
	private String siteClassId;//设施ID
	private String siteClassName;//设施
	private String unitId;//单位ID
	private String unitName;//单位名称
	private String buildId;//建筑ID
	private String buildName;//建筑名称
	private String buildAreaId;//区域ID
	private String buildAreaName;//区域名称
	private String inspectCycle;//巡查周期
	private String inspectCycleType;//周期类型
	private String inspectCount;//次数
	private String taskUserName;//巡查人姓名
	private String defaultUserName;//默认人
	private String executorID;//执行人
	private String taskStart;//开始时间
	private String taskEnd;//结束时间
	private String NFCCode;//NFC
	private String qrCode;//QR
}
