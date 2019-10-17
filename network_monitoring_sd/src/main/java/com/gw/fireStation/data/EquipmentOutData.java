package com.gw.fireStation.data;

import lombok.Data;

/**
 * 消防设施输出实体
 * @author zfg
 *
 */
@Data
public class EquipmentOutData {
	
	private String id;//设施ID
	
	private String unitid;//单位ID
	
	private String equipmentname;//设施名称
	
	private String equipmentcount;//数量
	
	private String unit;//单位
	
	private String unitname;
	
}
