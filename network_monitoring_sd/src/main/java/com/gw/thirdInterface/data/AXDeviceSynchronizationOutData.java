package com.gw.thirdInterface.data;

import lombok.Data;

/**
 * 安迅设备信息同步类
 * @author zfg
 *
 */
@Data
public class AXDeviceSynchronizationOutData {

	private Integer type;//类型（1新增；2修改）
	
	private String uuid;//设备序列号（唯一设备标识）
	
	private String orgcode;//所属机构编码
	
	private String name;//设备名称
	
	private String category;//大类编码
	
	private String sub_category;//小类编码
	
	private Integer isonline;//在线状态（0离线，1在线）
	
	private String lng;//经度（百度坐标系）
	
	private String lat;//纬度
	
	private String p_uuid;//父级序列号
	
	private String address;//地址
	
}
