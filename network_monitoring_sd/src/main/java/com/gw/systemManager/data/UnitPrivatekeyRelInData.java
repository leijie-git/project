package com.gw.systemManager.data;

import lombok.Data;

@Data
public class UnitPrivatekeyRelInData {
	private String privateKey;
	private String unitId;
	private String unitName;
	
	private Integer pageNumber;
	private Integer pageSize;

}
