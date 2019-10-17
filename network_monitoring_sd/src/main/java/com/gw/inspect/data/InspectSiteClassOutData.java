package com.gw.inspect.data;
import lombok.Data;
/**
 * 巡查点位分类输出类
 * @author zfg
 *
 */
@Data
public class InspectSiteClassOutData {

	private String ID;//分类id
	
	private String UnitId;//单位id

	private String SiteClassName;//对象名称
	
	private String SiteClassDesc;//位置描述
	
}
