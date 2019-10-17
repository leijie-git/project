package com.gw.fireStation.data;

import lombok.Data;

/**
 * 消防人员输出实体
 * @author zfg
 *
 */

@Data
public class FireFighterOutData {

	private String ID;//消防人员ID
	
	private String UnitID;//单位ID
	
	private String UserPost;//职务
	
	private String UserName;//姓名
	
	private String Tel;//电话
	
}
