package com.gw.wechat.data;


import lombok.Data;

@Data
public class PhoneUploadProblem {

	private String id;//检查项id
	
	private String taskid;//任务id
	
	private String checkInfo;//检查内容
	
	private String promlemType;//问题类别（0-立即整改/1-生成隐患）
	
	private String badDesc;//问题描述
	
	private String picPath;//图片路径
	
	private String createUserId;//上传人id
	
	private String isNeedRepair;//是否需要维修
	
	
	
}
