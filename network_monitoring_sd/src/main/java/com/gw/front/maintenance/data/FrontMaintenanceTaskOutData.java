package com.gw.front.maintenance.data;

import java.util.List;

import lombok.Data;

@Data
public class FrontMaintenanceTaskOutData {
	private String taskID;//任务id
	private String taskDetailId;//任务检查项id
	private String unitCode;// 单位编号
	private String unitName;// 单位名称
	private String inspectUser;// 巡查人员
	private String checkInfo;// 
	private String planName;//计划名称
	private String siteName;// 点位名称
	private String siteDesc;//点位位置描述
	private String sitecode;//位置编号
	private String isInspect;// 巡查状态
	private String inspectTime;// 巡查时间
	private String planTime;//计划时间
	private String taskStart;//任务开始时间
	private String taskEnd;//任务结束时间
	private String lastDay;//剩余几天到期
	private String inspectCycleType;//周期类型
	private String isOK;//是否正常
	private String isNeedRepair;//是否需要维修
	private String picPath;
	private String checkName;//检查项名称
	private List<String> picPathList;//巡查问题照片
}
