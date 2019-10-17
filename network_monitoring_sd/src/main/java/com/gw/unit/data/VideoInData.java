package com.gw.unit.data;

import lombok.Data;

/**
 * 视频输入类
 * @author zfg
 *
 */
@Data
public class VideoInData {

	private String id;
	
	
	private String name;
	/**
	 * 单位ID
	 */
	private String unitId;
	
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
	private String buildId;

	/**
	 * 建筑物分区
	 */
	private String buildAreaId;


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
	
	private String poscode;//编码
	
	private String manufactor;//生产厂家
	
	private String brand;//品牌
	
	private String remark;//备注
	
	private String lastUpdate;//更新时间
	
	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数
    
    private String unitName;
    
    private String serialnumber;//序列号
    
}
