package com.gw.front.lookup.data;

import lombok.Data;

@Data
public class FrontLookupUnitInfoDataExport {
	private String unitcode;// 单位编码
	private String childstationnum;// 子站号
	private String unitname;// 单位名称
	private String ownerCode;
	private String unitaddress;// 单位地址
	private String phone;// 联系电话
	private String result;// 结果
}
