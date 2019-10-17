package com.gw.front.unit.data;

import java.util.List;

import com.gw.front.couplet.data.FrontCoupletCommonOutData;

import lombok.Data;

/**
 * 消防站数据
 * 
 * @author Administrator
 *
 */
@Data
public class FrontUnitXFZOutData {
	private Integer eqCount;
	private Integer userCount;
	private List<FrontCoupletCommonOutData> eqList;
	private List<FrontUnitXFUserOutData> userList;
}
