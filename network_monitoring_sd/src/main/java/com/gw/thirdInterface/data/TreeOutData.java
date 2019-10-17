package com.gw.thirdInterface.data;

import java.util.List;

import lombok.Data;

@Data
public class TreeOutData {
	private String code;
	private String name;
	private String children;
	
	private List<TreeChildrenData> childList;
}
