package com.gw.front.maintenance.data;

import lombok.Data;

@Data
public class FrontMaintenanceRepairDetailOutData {
	private String operateUserName;//处理人
	private String dealInfo;//维修说明
	private String operateTime;//操作时间
	private String picPath;//维修照片
	private String dataFrom;//数据来源
}
