package com.gw.inspect.data;

import lombok.Data;

import java.util.List;


@Data
public class InspectTaskInListData {

	private String siteid;
	private String siteClassid;
	private String siteClassDetailid;
	private String unitId;
	private List siteClassDetailids;

}
