package com.gw.unit.data;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class TemporaryBaseUnitData {
	private String ownercode;
	private String unitname;
	private String unitaddress;
	private String unitpoint;
	private String phone;
	private String orgcode;
	private String saferesponsiblepersonname;
	private String saferesponsiblepersoncard;
	private String saferesponsiblepersonphone;
	private String safemanagername;
	private String safemanagercard;
	private String safemanagerphone;
	private String legalpersonname;
	private String legalppersoncard;
	private String legalpersonphone;
	private Integer staffcount;
	private BigDecimal buildingarea;
	private BigDecimal area;
	private String superviselevel;
}
