package com.gw.thirdInterface.data;

import lombok.Data;

/**
 * 单位同步类（新门海）
 * @author zfg
 *
 */
@Data
public class XMHUnitSynchronizationOutData {

	private String fsocial_uuid;//机构唯一编码
	
	private String fsocial_name;//机构名称
	
	private String faddress;//详细地址
	
	private String funit_type;//单位性质
	
	private String fis_active;//单位状态。正常运行 0  停用

	private String flongitude;//经度（百度坐标系）
	
	private String flatitude;//纬度
	
	private String fprovince_code;//省
	
	private String fcity_code;//市
	
	private String fcounty_code;//区
	
	private String ftown_code;//乡镇
	
	private String flink_man;//联系人
	
	private String ftel_no;//负责人电话
	
}
