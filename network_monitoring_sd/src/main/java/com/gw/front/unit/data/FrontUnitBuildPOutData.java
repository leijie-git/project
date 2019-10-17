package com.gw.front.unit.data;

import lombok.Data;

@Data
public class FrontUnitBuildPOutData {
	private String name;//建筑或区域名称
	private String id;//建筑或区域id
	private String pid;//区域时为建筑id 建筑时为0
}
