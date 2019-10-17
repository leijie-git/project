package com.gw.front.history.data;

import lombok.Data;

@Data
public class FrontHistoryDataFlowExport {
	private String unitCode;//单位编号
	private String unitName;//单位名称
	private String contact;//联系人
	private String contactPhone;//联系人电话
	private Integer all; //总流量
	private Integer tx; //发送流量
	private Integer rx; //接受流量
}
