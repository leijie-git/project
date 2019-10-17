package com.gw.front.analysis.data;

import com.gw.front.couplet.data.FrontCoupletCommonOutData;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class FrontAnalysisAlarmCountOutData {
	private List<FrontCoupletCommonOutData> fireCount = new ArrayList<>();// 报警系统
	private List<FrontCoupletCommonOutData> fireRealCount = new ArrayList<>();// 报警系统
	private List<FrontCoupletCommonOutData> fireDealCount = new ArrayList<>();// 报警系统
	private List<FrontCoupletCommonOutData> waterCount = new ArrayList<>();// 水系统
	private List<FrontCoupletCommonOutData> eleCount = new ArrayList<>(); // 电气火灾
	private List<FrontCoupletCommonOutData> videoCount = new ArrayList<>(); // 视频监控
	private List<FrontCoupletCommonOutData> splitCount = new ArrayList<>(); // 防火分离
	private List<FrontCoupletCommonOutData> smokeCount = new ArrayList<>(); // 防排烟系统
	private List<FrontCoupletCommonOutData> gaseousCount = new ArrayList<>();// 气体系统
	private List<FrontCoupletCommonOutData> gasCount = new ArrayList<>();// 燃气系统
	private List<FrontCoupletCommonOutData> emergencyCount = new ArrayList<>();// 应急疏散
	private List<FrontCoupletCommonOutData> smokeFeelingCount = new ArrayList<>();// 无线烟感
	private List<FrontCoupletCommonOutData> smokeFeelingRealCount = new ArrayList<>();// 无线烟感
	private List<FrontCoupletCommonOutData> smokeFeelingDealCount = new ArrayList<>();// 无线烟感
}
