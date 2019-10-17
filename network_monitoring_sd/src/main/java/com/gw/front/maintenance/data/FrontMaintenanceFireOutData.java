package com.gw.front.maintenance.data;

import lombok.Data;

@Data
public class FrontMaintenanceFireOutData {
	private String extinguisherType;//灭火剂类型
	private String extinguisherPosition;//灭火器位置
	private String productDate;//出厂日期
	private String jcDate;//检测日期
	private String eqChangeDate;//药剂到期时间
	private String fullDate;//药剂充填时间
	private String validityDate;//报废时间
	private String id;
}
