package com.gw.inspect.data;
import lombok.Data;
/**
 * 巡查点位分类检查项输出类
 * @author zfg
 *
 */
@Data
public class SiteClassDetialOutData {

	private String ID;//分类检查项id

	private String CheckInfo;//检查内容
	
	private String CheckMethod;//检查方式

	private String UnitID;//单位id

}
