package com.gw.inspect.data;

import lombok.Data;

@Data
public class DownLoadTaskInData {

	private String userID;
	private String taskId;
	private String inspectuserid;
	private String receiveuserid;
	private String supervisorID;

	private String inspectTime;

	private String downLoadTime;

	private String startTime;

	private String endTime;

	private String inspectcycleType;//周期类型
	//用户角色id
	private int userRole;
}
