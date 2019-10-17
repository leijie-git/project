package com.gw.inspect.data;

import lombok.Data;

/**
 * 巡查点位分类检查项输入类
 * @author zfg
 *
 */
@Data
public class SiteClassDetialInData {
	
	private String ID;//分类检查项id
	
	private String UnitID;//所属单位id

	private String CheckInfo;//检查内容
	
	private String CheckMethod;//检查方式

	private Integer pageNumber;//第几页
	
    private Integer pageSize;//每页条数


}
