package com.gw.inspect.data;

import lombok.Data;

/**
 * 巡查点检查项配置管理
 *
 * @author yja
 */
@Data
public class InspectBaseRelInData {

	private String inspectCycle;//巡查周期
	private String inspectCount;//次数
	private Integer pageNumber;//第几页
	private Integer pageSize;//每页条数
	private String unitId;//单位ID
	private String unitName;//单位名称
	private String buildName;//建筑名称
	private String buildAreaName;//区域名称
	private String lastUpdate ;//更新时间
	private String siteId;//巡查点ID
	private String siteClassDetialId;//检查项ID
	private String siteClassId;//设施ID
}
