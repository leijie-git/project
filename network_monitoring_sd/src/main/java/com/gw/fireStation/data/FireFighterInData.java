package com.gw.fireStation.data;

import lombok.Data;

/**
 * 消防人员输入实体
 * @author zfg
 *
 */
@Data
public class FireFighterInData {
	
	private String ID;//消防人员ID
	
	private String UnitID;//单位ID
	
	private String UserPost;//职务
	
	private String UserName;//姓名
	
	private String Tel;//电话
	
	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数
}
