package com.gw.inspect.data;

import lombok.Data;
/**
 * 巡查点位分类输入类
 * @author zfg
 *
 */
@Data
public class InspectSiteClassInData {

	private String ID;//分类id
	
	private String UnitID;//单位id
	
	private String SiteClassName;//对象名称
	
	private String SiteClassDesc;//位置描述
	
	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数
	
}
