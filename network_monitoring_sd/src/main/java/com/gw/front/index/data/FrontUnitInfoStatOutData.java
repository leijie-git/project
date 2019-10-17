package com.gw.front.index.data;

import java.util.List;

import lombok.Data;

@Data
public class FrontUnitInfoStatOutData {
	private Integer unitUserCount;
	private Integer onlineDeviceCount;
	private Integer offlineDeviceCount;
	private List<FrontRecordOutData> onlineRecords;
	private List<FrontRecordOutData> offLineRecords;
	private Long unitId;
}
