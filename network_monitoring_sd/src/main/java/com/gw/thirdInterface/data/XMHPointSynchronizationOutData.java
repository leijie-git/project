package com.gw.thirdInterface.data;

import lombok.Data;

@Data
public class XMHPointSynchronizationOutData {

	private Integer flag;//操作类型1 新增 2  修改
	
	private String fsocial_uuid;//社会单位标识
	
	private String ftransmission_id;//监控点标识(火灾自动报警系统时候：用传编号 水系统：传输模块编号)
	
	private String ftransmission_name;//监控点名称
	
	private String fcontrhost_code;//消控主机编号
	
	private String fmonitorpositon_type;//监控点类型
	
	private String ftotal_high;//总上限值(量程)
	
	private String fvalue_down;//告警下限值
	
	private String fvalue_up;//告警上限值
	
}
