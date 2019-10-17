package com.gw.thirdInterface.data;

import lombok.Data;

@Data
public class RealTimeAlarmXMHInData {
	private String f_type;//报警类型
	private String fsocial_uuid;//单位ID
	private String fdevice_uuid ;//设备ID
	private String ftransmission_id;//监控点ID
	private String fcontrhost_code;//消控主机编号
	private String floop_num;//部位号
	private String fdevice_address;//地址
	private String fvalue;//报警值
	private String fcome_time;//报警时间
}
