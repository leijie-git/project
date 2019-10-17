package com.gw.front.couplet.data;

import lombok.Data;

@Data
public class FrontCoupletUnitInfo {
	private String unitId;// 单位id
	private String unitName;// 单位名称
	private String hasVideo;// 单位是否有视频
	
	
	private String toDateCount;//灭火器即将报废数量
	private String scrapCount;//灭火器即将到期数量
	
	
}
