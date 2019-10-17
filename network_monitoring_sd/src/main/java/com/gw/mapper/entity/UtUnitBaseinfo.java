package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Table(name = "UT_Unit_BaseInfo")
public class UtUnitBaseinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID")
	private Long id;
	@Column(name = "UnitCode")
	private String unitcode;
	@Column(name = "unitLink")
	private String unitlink;
	@Column(name = "UnitName")
	private String unitname;
	@Column(name = "OrgCode")
	private String orgcode;
	@Column(name = "UnitType")
	private String unittype;
	@Column(name = "UnitAddress")
	private String unitaddress;
	@Column(name = "Phone")
	private String phone;
	@Column(name = "UnitSummary")
	private String unitsummary;
	@Column(name = "UnitBg")
	private String unitbg;
	@Column(name = "NetworkStatus")
	private Integer networkstatus;
	@Column(name = "PostCode")
	private String postcode;
	@Column(name = "FireroomPhone")
	private String fireroomphone;
	@Column(name = "SafeResponsiblePersonName")
	private String saferesponsiblepersonname;
	@Column(name = "SafeResponsiblePersonCard")
	private String saferesponsiblepersoncard;
	@Column(name = "SafeResponsiblePersonPhone")
	private String saferesponsiblepersonphone;
	@Column(name = "SafeManagerName")
	private String safemanagername;
	@Column(name = "SafeManagerCard")
	private String safemanagercard;
	@Column(name = "SafeManagerPhone")
	private String safemanagerphone;
	@Column(name = "AssistManagerName")
	private String assistmanagername;
	@Column(name = "AssistManagerCard")
	private String assistmanagercard;
	@Column(name = "AssistManagerPhone")
	private String assistmanagerphone;
	@Column(name = "LegalPersonName")
	private String legalpersonname;
	@Column(name = "LegalPpersonCard")
	private String legalppersoncard;
	@Column(name = "LegalPersonPhone")
	private String legalpersonphone;
	@Column(name = "StaffCount")
	private Integer staffcount;
	@Column(name = "EstablishmentTime")
	private Date establishmenttime;
	@Column(name = "SuperiorUnit")
	private String superiorunit;
	@Column(name = "AdministUnit")
	private String administunit;
	@Column(name = "EconomicType")
	private String economictype;
	@Column(name = "FixedAssets")
	private BigDecimal fixedassets;
	@Column(name = "Area")
	private BigDecimal area;
	@Column(name = "BuildingArea")
	private BigDecimal buildingarea;
	@Column(name = "OnlineDate")
	private Date onlinedate;
	@Column(name = "SuperviseLevel")
	private String superviselevel;
	@Column(name = "SuperviseType")
	private String supervisetype;
	@Column(name = "BelongCenterName")
	private String belongcentername;
	@Column(name = "SvgFile")
	private String svgfile;
	@Column(name = "UnitPoint")
	private String unitpoint;
	@Column(name = "AddressDetial")
	private String addressdetial;
	@Column(name = "AppID")
	private String appid;
	@Column(name = "AppKey")
	private String appkey;
	@Column(name = "InspectDate")
	private Date inspectdate;
	@Column(name = "DataFrom")
	private Integer datafrom;
	@Column(name = "DataFromID")
	private String datafromid;
	@Column(name = "Important")
	private Boolean important;
	@Column(name = "ImportantID")
	private String importantid;
	@Column(name = "SocialCreditCode")
	private String socialcreditcode;
	@Column(name = "PropertyUnitName")
	private String propertyunitname;
	@Column(name = "CreateDate")
	private Date createdate;
	@Column(name = "Lastupdate")
	private Date lastupdate;
	@Column(name = "LZScore")
	private BigDecimal lzscore;
	@Column(name = "LZScoreLevel")
	private Integer lzscorelevel;
	@Column(name = "XFZImg")
	private String xfzimg;
	@Column(name = "FireCount")
	private Integer firecount;
	@Column(name = "WaterCount")
	private Integer watercount;
	@Column(name = "VideoCount")
	private Integer videocount;
	@Column(name = "ElectricCount")
	private Integer electriccount;
	@Column(name = "ProviceID")
	private Long proviceid;
	@Column(name = "CityID")
	private Long cityid;
	@Column(name = "RegionID")
	private Long regionid;
	@Column(name = "TownID")
	private Long townid;
	@Column(name = "ChildStationNum")
	private Long childstationnum;
	@Column(name = "UnitDangerLevel")
	private String unitdangerlevel;
	@Column(name = "TechnicalRepresentative")
	private String technicalrepresentative;
	@Column(name = "UserRepresentative")
	private String userrepresentative;
	@Column(name = "InductionPointCount")
	private Integer inductionpointcount;
	@Column(name = "MessagePhone")
	private String messagephone;
	@Column(name = "UnitProximity")
	private String unitproximity;
	@Column(name = "FireRating")
	private String firerating;
	@Column(name = "calibration_id")
	private Long calibrationId;
	@Column(name = "XFContractBook")
	private String xfcontractbook;
	@Column(name = "XFContractBookName")
	private String xfcontractbookname;
	private String email;
	@Column(name = "isDelete")
	private Integer isdelete;
	@Column(name = "MonitorUnitName")
	private String monitorunitname;
	@Column(name = "MonitorUnitUser")
	private String monitorunituser;
	@Column(name = "MonitorUnitPhone")
	private String monitorunitphone;
	@Column(name = "AdvanceReminderDay")
	private Integer advancereminderday;
	@Column(name = "ReceiveAlarmType")
	private String receivealarmtype;
	@Column(name = "MessageNumber")
	private Integer messagenumber;
	@Column(name = "QuantitySent")
	private Integer quantitysent;
	@Column(name = "isAutoCall")
	private Integer isautocall;
	@Column(name = "AutoCallDelay")
	private Integer autocalldelay;
	@Column(name = "AutoCallNum")
	private String autoCallNum;
	@Column(name = "logoUrl")
	private String logourl;
	@Column(name = "isUploadA")
	private Integer isuploada;
	@Column(name = "isUploadB")
	private Integer isuploadb;
	@Column(name = "systemNumber")
	private String systemNumber;
	/*萤石云密钥*/
	@Column(name = "video_appkey")
	private String videoAppkey;
	@Column(name = "video_appsecret")
	private String videoAppsecret;
	/* 电话通知相关信息，可通话总数，已通话次数，电话告警类型 */
	@Column(name = "call_total")
	private Integer callTotal;
	@Column(name = "call_used")
	private Integer callUsed;
	@Column(name = "call_alarm_type")
	private String callAlarmType;
	@Column(name = "total_node")
	private Integer totalNode;
	@Column(name = "total_nbnode")
	private Integer totalNbNode;
	@Column(name = "beforehand")
	private String beforehand;
	@Column(name = "beforehandName")
	private String beforehandName;

}