package com.gw.mapper.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "UT_LZ_FireEquipmentAlarm_History")
public class UtLzFireequipmentalarmHistory implements Serializable {
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

	private static final long serialVersionUID = 1L;

	/**
	 * @return ID
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return AlarmCode
	 */
	public String getAlarmcode() {
		return alarmcode;
	}

	/**
	 * @param alarmcode
	 */
	public void setAlarmcode(String alarmcode) {
		this.alarmcode = alarmcode;
	}

	/**
	 * @return EquipmentName
	 */
	public String getEquipmentname() {
		return equipmentname;
	}

	/**
	 * @param equipmentname
	 */
	public void setEquipmentname(String equipmentname) {
		this.equipmentname = equipmentname;
	}

	/**
	 * @return EquipmentDetialName
	 */
	public String getEquipmentdetialname() {
		return equipmentdetialname;
	}

	/**
	 * @param equipmentdetialname
	 */
	public void setEquipmentdetialname(String equipmentdetialname) {
		this.equipmentdetialname = equipmentdetialname;
	}

	/**
	 * @return NormalValue
	 */
	public String getNormalvalue() {
		return normalvalue;
	}

	/**
	 * @param normalvalue
	 */
	public void setNormalvalue(String normalvalue) {
		this.normalvalue = normalvalue;
	}

	/**
	 * @return AlarmValue
	 */
	public String getAlarmvalue() {
		return alarmvalue;
	}

	/**
	 * @param alarmvalue
	 */
	public void setAlarmvalue(String alarmvalue) {
		this.alarmvalue = alarmvalue;
	}

	/**
	 * @return AlarmTime
	 */
	public Date getAlarmtime() {
		return alarmtime;
	}

	/**
	 * @param alarmtime
	 */
	public void setAlarmtime(Date alarmtime) {
		this.alarmtime = alarmtime;
	}

	/**
	 * @return Lastupdate
	 */
	public Date getLastupdate() {
		return lastupdate;
	}

	/**
	 * @param lastupdate
	 */
	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	/**
	 * @return isDeal
	 */
	public Integer getIsdeal() {
		return isdeal;
	}

	/**
	 * @param isdeal
	 */
	public void setIsdeal(Integer isdeal) {
		this.isdeal = isdeal;
	}

	/**
	 * @return DealType
	 */
	public Integer getDealtype() {
		return dealtype;
	}

	/**
	 * @param dealtype
	 */
	public void setDealtype(Integer dealtype) {
		this.dealtype = dealtype;
	}

	/**
	 * @return DealTime
	 */
	public Date getDealtime() {
		return dealtime;
	}

	/**
	 * @param dealtime
	 */
	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}

	/**
	 * @return DealUser
	 */
	public String getDealuser() {
		return dealuser;
	}

	/**
	 * @param dealuser
	 */
	public void setDealuser(String dealuser) {
		this.dealuser = dealuser;
	}

	/**
	 * @return DealInfo
	 */
	public String getDealinfo() {
		return dealinfo;
	}

	/**
	 * @param dealinfo
	 */
	public void setDealinfo(String dealinfo) {
		this.dealinfo = dealinfo;
	}

	/**
	 * @return IsNeedRepair
	 */
	public Integer getIsneedrepair() {
		return isneedrepair;
	}

	/**
	 * @param isneedrepair
	 */
	public void setIsneedrepair(Integer isneedrepair) {
		this.isneedrepair = isneedrepair;
	}

	/**
	 * @return IsNeedMaintain
	 */
	public Integer getIsneedmaintain() {
		return isneedmaintain;
	}

	/**
	 * @param isneedmaintain
	 */
	public void setIsneedmaintain(Integer isneedmaintain) {
		this.isneedmaintain = isneedmaintain;
	}

	/**
	 * @return MaintainDesc
	 */
	public String getMaintaindesc() {
		return maintaindesc;
	}

	/**
	 * @param maintaindesc
	 */
	public void setMaintaindesc(String maintaindesc) {
		this.maintaindesc = maintaindesc;
	}

	/**
	 * @return DealResult
	 */
	public Integer getDealresult() {
		return dealresult;
	}

	/**
	 * @param dealresult
	 */
	public void setDealresult(Integer dealresult) {
		this.dealresult = dealresult;
	}

	/**
	 * @return UnitID
	 */
	public Long getUnitid() {
		return unitid;
	}

	/**
	 * @param unitid
	 */
	public void setUnitid(Long unitid) {
		this.unitid = unitid;
	}

	/**
	 * @return FireEquipmentDetialID
	 */
	public Long getFireequipmentdetialid() {
		return fireequipmentdetialid;
	}

	/**
	 * @param fireequipmentdetialid
	 */
	public void setFireequipmentdetialid(Long fireequipmentdetialid) {
		this.fireequipmentdetialid = fireequipmentdetialid;
	}

	public Long getCalibrationId() {
		return calibrationId;
	}

	public void setCalibrationId(Long calibrationId) {
		this.calibrationId = calibrationId;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	public Integer getAlarmStatus() {
		return alarmStatus;
	}

	public void setAlarmStatus(Integer alarmStatus) {
		this.alarmStatus = alarmStatus;
	}

	public String getDealcode() {
		return dealcode;
	}

	public void setDealcode(String dealcode) {
		this.dealcode = dealcode;
	}

}