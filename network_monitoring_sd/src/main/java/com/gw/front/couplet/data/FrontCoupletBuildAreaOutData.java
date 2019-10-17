package com.gw.front.couplet.data;

import java.util.List;

import lombok.Data;

@Data
public class FrontCoupletBuildAreaOutData {
	private String buildAreaName;// 区域名称
	private List<FrontCoupletVideoOutData> videos;// 视频列表
	private String token;
	private String appKey;
}
