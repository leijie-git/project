package com.gw.front.maintenance.data;

import lombok.Data;

@Data
public class FrontMaintenanceStatOutData {
	private String toDateCount;//灭火器即将报废数量
	private String scrapCount;//灭火器即将到期数量
	
	private String totalCount;//灭火器总数量
	private String wbNodeal;//维保未处理
	private String wbdealing;//维保处理中
	private String wbdealed;//维保已处理
	private String xcNodeal;//巡查未处理
	private String xcdealing;//巡查处理中
	private String xcdealed;//巡查已处理
	private Integer xcExpire;//维保到期数量
}
