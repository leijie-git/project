package com.gw.front.history.data;

import lombok.Data;

import java.util.List;

/**
 * 设备状态数据
 * 
 * @author Administrator
 *
 */
@Data
public class FrontHisSDDeviceStatusOutData {
	private String id;
	private String deviceType;//设备类型
	private String deviceName;//设备名称
	private String deviceNo;//子号
	private String time;//使用时间
	private String deviceStatus;//当前状态
	private String originalStatus;//原始状态
	private Integer netDeviceStatus;//蓝版原始状态
	private Integer upCount;//上传数
	private String unitCode;//单位编号
	private String childstationnum;//子站号
	private String unitName;//单位名称
	private String exceptionCount;//异常次数
	private String version;//硬件版本
	private String softVersion;//软件版本
	private String ip;//设备ip
	private String deviceModel;//设备型号
	private String agreement;//协议
	private String calibrationId;//标定id
	private String calibrationStatus;//标定状态(0未标定时间或者标定时间已过期，1：标定时间且类型存在且在标定时间内，2：标定了还未到标定时间)
	private String eqSystemName;//系统名称
	private String ownerCode;//设备编号
	private String remark;//标定描述
	private String deviceCode;//设配器
	private String manufacturerCode;//制造商
	private String startDate;//标定开始时间
	private String endDate;//标定结束时间
	private Integer isuploada;//是否上传(1是0否) 新门海
	private Integer isuploadb;//是否上传(1是0否) 安讯
	private String deviceIndex;
	private String unitID;
	private List<String> eqIds;
	private String userId;//人员ID
}
