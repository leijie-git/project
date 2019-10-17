package com.gw.front.index.data;

import lombok.Data;

@Data
public class FrontVideoOutData {
	
	private String id;
	
	private String name;
	
	/**
	 * 单位ID
	 */
	private String unitId;
	
	private String unitName;
	
	/**
	 * 建筑物名称
	 */
	private String buildingName;

	/**
	 * 建筑物分区
	 */
	private String buildAreaName;
	/**
	 * 建筑物ID
	 */
	private String buildID;

	/**
	 * 建筑物分区
	 */
	private String buildAreaID;


	/**
	 * 视频分类(枚举EnumVideoClass)
	 */
	private String videoClass;

	/**
	 * 视频类型（1：单位 2：建筑物 3：建筑物分区）
	 */
	private String videoType;

	/**
	 * IP
	 */
	private String ip;

	/**
	 * 端口号 
	 */
	private Integer port;

	/**
	 * 用户名
	 */
	private String userName;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 安装位置
	 */
	private String position;

	/**
	 * 视频插件类型
	 */
	private String plugInType;//视频插件类型
	
	private String posCode;//编码
	
	private String manufactor;//生产厂家
	
	private String brand;//品牌
	
	private String eemark;//备注
	
	private String lastUpdate;//更新时间
	
	private String serialnumber;//序列号
	private String token;
	private String appKey;
}
