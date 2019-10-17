package com.gw.unit.data;

import lombok.Data;

@Data
public class UserReUnitInData {
	private Integer pageNumber;
	private Integer pageSize;
	private String userId;
	private Integer isRelation;
	private String unitName;
}
