package com.gw.wechat.data;

import java.util.List;

import lombok.Data;

@Data
public class UpLoadAllTaskInData {
	
	private String taskid;//任务id
	
	private String inspectTime;//巡查时间
	
	private List<PhoneUploadProblem> siteClassDetailList;//当前任务下的所有检查项
}
