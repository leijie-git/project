package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryDataFlowOutData {
	private Integer id;	
	private String rx; //接受流量
	private String tx; //发送流量
	private String unitName;//单位名称
	private String unitCode;//单位编号
	private String childstationnum;//子站号
	private String contact;//联系人
	private String contactPhone;//联系人电话
	

}
