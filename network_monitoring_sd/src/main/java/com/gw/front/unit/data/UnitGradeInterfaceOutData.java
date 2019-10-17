package com.gw.front.unit.data;

import lombok.Data;

import java.util.List;

@Data
public class UnitGradeInterfaceOutData {

	private Long simulationTime;
	private Long digitalTime;
	private List<UnitGradeInterfaceOutListData> list;
	
}
