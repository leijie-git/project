package com.gw.front.unit.data;

import lombok.Data;

/**
 * 灭火器
 * 
 * @author Administrator
 *
 */
@Data
public class FrontUnitExtinguisherOutData {
	private String id;
	private String extinguisherType;
	private String extinguisherPosition;
	private String productDate;
	private String jcDate;
	private String fillDate;
	private String validityDate;
	private String extinguisherNum;
	private String productUnitname;
	private String productUnitphone;
	private String remark;
	private String unitId;
	private String expireDate;
	private String extinguisherCode;
	private String validityCount;//灭火器即将报废
	private String expireCount;//灭火器即将到期
	private String tovalidityCount;//灭火器已经报废
	private String toexpireCount;//灭火器已经到期
}
