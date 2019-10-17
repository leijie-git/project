package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Data
@Table(name = "UT_LZ_FireEquipmentAlarm")
public class UtLzFireequipmentalarm implements Serializable {
	@Id
	@Column(name = "ID")
	private Long id;

	@Column(name = "AlarmCode")
	private String alarmcode;

	@Column(name = "EquipmentName")
	private String equipmentname;

	@Column(name = "EquipmentDetialName")
	private String equipmentdetialname;

	@Column(name = "NormalValue")
	private String normalvalue;

	@Column(name = "AlarmValue")
	private String alarmvalue;

	@Column(name = "AlarmTime")
	private Date alarmtime;

	@Column(name = "Lastupdate")
	private Date lastupdate;

	@Column(name = "isDeal")
	private Integer isdeal;

	@Column(name = "isTest")
	private Integer isTest;

	@Column(name = "DealType")
	private Integer dealtype;

	@Column(name = "DealTime")
	private Date dealtime;

	@Column(name = "DealUser")
	private String dealuser;

	@Column(name = "DealInfo")
	private String dealinfo;

	@Column(name = "IsNeedRepair")
	private Integer isneedrepair;

	@Column(name = "IsNeedMaintain")
	private Integer isneedmaintain;

	@Column(name = "MaintainDesc")
	private String maintaindesc;

	@Column(name = "DealResult")
	private Integer dealresult;

	@Column(name = "UnitID")
	private Long unitid;

	@Column(name = "FireEquipmentDetialID")
	private Long fireequipmentdetialid;

	@Column(name = "calibration_id")
	private Long calibrationId;
	
	@Column(name = "picUrl")
	private String picurl;
	
    @Column(name = "Alarm_Status")
    private Integer alarmStatus;
    
    @Column(name = "dealCode")
    private String dealcode;

	@Column(name = "buildAreaID")
	private Long buildAreaID;

	private static final long serialVersionUID = 1L;


}