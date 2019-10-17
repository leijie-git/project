package com.gw.build.data;

import lombok.Data;

@Data
public class BuildAreaOutData {

	private String id;//区域ID
	
	private String buildID;//建筑物ID
	
	private String unitID;//单位ID
	
	private String unitName;//单位名称
	
	private String buildAreaName;//区域名称
	
	private String buildAreaSite;//区域地址
	
	private String buildAreabg;//区域缩略图
	
	private String buildImgbg;//区域平面图
	
	private String status;//状态
	
	private String bgWidth;//缩略图宽度
	
	private String bgHeight;//缩略图高度
	
	private String remark;//备注
	
	private String createdate;

	private String lastupdate;
	
	private String buildName;//建筑名称
}
