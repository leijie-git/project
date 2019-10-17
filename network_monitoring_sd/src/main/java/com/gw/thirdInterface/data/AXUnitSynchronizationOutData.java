package com.gw.thirdInterface.data;

import lombok.Data;

/**
 * 单位同步类（安迅）
 * @author zfg
 *
 */
@Data
public class AXUnitSynchronizationOutData {

	private Integer type;//操作类型（1新增，2修改）
	
	private String code;//机构唯一编码
	
	private String name;//机构名称
	
	private String address;//详细地址
	
	private String remark;//备注

	private String orgtype;//机构类别编码
	
	private String orgnature;//机构性质编码(dict1)
	
	private String orgclassify;//机构分级编码(dict2)
	
	private String industy;//所属行业编码
	
	private String lng;//经度（百度坐标系）
	
	private String lat;//纬度
	
	private String province;//省
	
	private String city;//市
	
	private String area;//区
	
	private String adminor;//负责人
	
	private String adminor_phone;//负责人电话
	
}
