package com.gw.front.lookup.data;

import lombok.Data;

@Data
public class FrontLookupUnitInfoData {

	private String deviceId;
	private String unitcode;// 单位编码
	private String childstationnum;// 子站号
	private String unitname;// 单位名称
	private String unitaddress;// 单位地址
	private String phone;// 联系电话
	private String ownerCode;//设备编号
	private String deviceIndex;//设备索引
	private String hasVideo;// 能否查看视频(1能 0不能)
	private String result;// 设备状态
	private String name;//设备名称
	private String unitId;
}
