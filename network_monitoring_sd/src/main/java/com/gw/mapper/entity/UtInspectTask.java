package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "UT_Inspect_Task")
@Data
public class UtInspectTask implements Serializable {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "NFCCode")
	private String nfccode;

	@Column(name = "SiteCode")
	private String sitecode;

	@Column(name = "SiteName")
	private String sitename;

	@Column(name = "SiteDesc")
	private String sitedesc;

	@Column(name = "OrderIndex")
	private Integer orderindex;

	@Column(name = "Remark")
	private String remark;

	@Column(name = "TaskStart")
	private Date taskstart;

	@Column(name = "TaskEnd")
	private Date taskend;

	@Column(name = "IsInspect")
	private Integer isinspect;

	@Column(name = "InspectTime")
	private Date inspecttime;

	@Column(name = "ExpirationTime")
	private Date expirationTime;

	@Column(name = "InspectUserID")
	private Long inspectuserid;

	@Column(name = "IsOK")
	private Integer isok;

	@Column(name = "UnitID")
	private Long unitid;

	@Column(name = "PlanDetialID")
	private Long plandetialid;

	@Column(name = "SiteID")
	private Long siteid;

	@Column(name = "SiteClassID")
	private Long siteclassid;

	@Column(name = "IsChange")
	private Integer ischange;

	@Column(name = "IsReceive")
	private Integer isreceive;

	@Column(name = "ChangeUserID")
	private Long changeuserid;

	@Column(name = "ReceiveUserID")
	private Long receiveuserid;

	@Column(name = "ChangeTime")
	private Date changetime;

	@Column(name = "ReceiveTime")
	private Date receivetime;

	//监督人ID
	@Column(name = "supervisorID")
	private Long supervisorid;
	//检查点个数
	@Column(name = "siteCount")
	private Integer sitecount;
	//已完成检查个数
	@Column(name = "okCheckCount")
	private Integer okcheckcount;
	//转单状态
	@Column(name = "ReceiveStatus")
	private Integer receivestatus;

	private static final long serialVersionUID = 1L;
}