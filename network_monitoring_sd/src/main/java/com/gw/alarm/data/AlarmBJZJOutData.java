package com.gw.alarm.data;

import lombok.Data;

@Data
public class AlarmBJZJOutData {

	private String unitID;//单位id
	private String calibrationId;//单位标定描述
	private String eqname;//设备名称
	private String eqDetailName;//部位名称
	private String videoId;//视频id
	private String netDeviceId;//联网设备id
	private String isuploada;
	private String isuploadb;
	private String addressRelId;//点位id
	private String partcode;//部位号
	private String eqAddress;//设备位置
	private String eqId;//设备ID
	private String buildAreaID;//区域ID

}
