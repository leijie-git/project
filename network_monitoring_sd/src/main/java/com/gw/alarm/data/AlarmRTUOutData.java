package com.gw.alarm.data;

import lombok.Data;
/**
 * 根据报警设备查询具体信息
 * @author zfg
 *
 */
@Data
public class AlarmRTUOutData {

	private String fireEquipmentDetialID;//设备明细id（端口id）
	
	private String normalValue;//正常值
	
	private String equipmentDetialName;//设备明细名称（端口名称）
	
	private String equipmentName;//设备名称
	
	private String unitID;//单位id
	
	private String reserve;//模拟量采集类型
	
	private String calibrationId; //单位标定
	
	private String eqSystemID;//系统id
	
	private String installPosition;//安装位置
	
	private String analogUnit;
	private String analogdown;
	private String analogup;
	private String netDeviceId;
	private String partCode;
	private String ownercode;
	private String videoId;
	private String buildAreaID;
}
