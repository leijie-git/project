package com.gw.unit.data;

import lombok.Data;

@Data
public class UnitBaseInfoInData {
	private String id;
	private String unitcode;
	private String unitname;
	private String email ;
	private String unittype;
	private String unitaddress ;
	private String phone ;
	private String unitsummary ;
	private String technicalrepresentative ;
	private String networkstatus ;
	private String postcode;
	private String fireroomphone ;
	private String saferesponsiblepersonname ;
	private String saferesponsiblepersoncard ;
	private String saferesponsiblepersonphone;
	private String safemanagername ;
	private String safemanagercard ;
	private String safemanagerphone;
	private String assistmanagername ;
	private String assistmanagercard ;
	private String assistmanagerphone;
	private String legalpersonname ;
	private String legalppersoncard;
	private String legalpersonphone;
	private String staffcount;
	private String establishmenttime ;
	private String superiorunit;
	private String administunit;
	private String economictype;
	private String fixedassets ;
	private String area;
	private String buildingarea;
	private String onlinedate;
	private String superviselevel;
	private String supervisetype ;
	private String belongcentername;
	private String userrepresentative;
	private String unitpoint ;
	private String addressdetial ;
	private String datafrom;
	private String datafromid;
	private String important ;
	private String importantid ;
	private String socialcreditcode;
	private String propertyunitname;
	private String lzscore ;
	private String lzscorelevel;
	private String firerating;
	private String firecount ;
	private String watercount;
	private String videocount;
	private String electriccount ;
	private String unitdangerlevel ;
	private String proviceid ;
	private String cityid;
	private String regionid;
	private String townid;
	private String childstationnum ;
	private String inductionpointcount ;
	private String unitproximity ;
	private String xfzimg;
	private String unitbg;
	private String svgfile;
	private String xfcontractbook;
	private String xfcontractbookname;
	private String monitorunitname;//监测单位
	private String monitorunituser;//监测单位负责人
	private String monitorunitphone;//监测单位负责人电话
	private String messagephone;
	private Integer advancereminderday;
	private String receivealarmtype;
	private Integer messagenumber;
	private String orgCode;//单位组织机构代码
	private String logourl;
	private String maintenanceunitid;//维保单位id
	private String maintenanceunitName;//维保单位id

	private String isUploadA;//是否上传(1是0否) 新门海
	
	private String isUploadB;//是否上传(1是0否) 安讯
	
	private Integer isautocall;
	private Integer autocalldelay;

	private Integer pageNumber;
	private Integer pageSize;
	private String unitlink;
	private String systemNumber;
	private String autoCallNum;

	/** 萤石云密钥 */
	private String videoAppkey;
	private String videoAppsecret;

	/* 电话通知相关信息，可通话总数，电话告警类型 */
	private Integer callTotal;
	private String callAlarmType;
	//总节点
	private Integer totalNode;
	private String beforehand;//预案流程图
	private String beforehandName;//预案流程图名称
}
