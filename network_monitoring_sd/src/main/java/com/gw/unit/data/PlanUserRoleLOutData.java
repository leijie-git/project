package com.gw.unit.data;

import java.util.List;

import lombok.Data;

@Data
public class PlanUserRoleLOutData {

	private String id;
	
	private String name;
	
	private List<PlanUserRoleLOutData> hasRole;
	
	private List<PlanUserRoleLOutData> noRole;
}
