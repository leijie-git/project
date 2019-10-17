package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_LZ_RepeatAlarm")
public class UtLzRepeatalarm implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "AlarmType")
    private Integer alarmtype;

    @Column(name = "AlarmStatus")
    private Integer alarmstatus;

    @Column(name = "AlarmLevel")
    private Integer alarmlevel;

    @Column(name = "AlarmCode")
    private String alarmcode;

    @Column(name = "AlarmSite")
    private String alarmsite;

    @Column(name = "AlarmTitle")
    private String alarmtitle;

    @Column(name = "AlarmInfo")
    private String alarminfo;

    @Column(name = "IsUrgent")
    private Integer isurgent;

    @Column(name = "AlarmTime")
    private Date alarmtime;

    @Column(name = "RequirementTime")
    private Date requirementtime;

    @Column(name = "IsFeedback")
    private Integer isfeedback;

    @Column(name = "FeedbackTime")
    private Date feedbacktime;

    @Column(name = "FeedbackInfo")
    private String feedbackinfo;

    @Column(name = "FeedbackUser")
    private String feedbackuser;

    @Column(name = "DealTime")
    private Date dealtime;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "IsOverAlarm")
    private Integer isoveralarm;

    @Column(name = "LastUpdateDate")
    private Date lastupdatedate;

    @Column(name = "RequirementCount")
    private Integer requirementcount;

    @Column(name = "RequirementCountUnit")
    private Integer requirementcountunit;

    @Column(name = "DealStatus")
    private Integer dealstatus;

    @Column(name = "CurrentDealUser")
    private String currentdealuser;

    @Column(name = "DealDate")
    private Date dealdate;

    @Column(name = "DealInfo")
    private String dealinfo;

    @Column(name = "DealResult")
    private Integer dealresult;

    @Column(name = "IsNeedRepair")
    private Integer isneedrepair;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "BaseID")
    private Long baseid;

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
     * @return AlarmType
     */
    public Integer getAlarmtype() {
        return alarmtype;
    }

    /**
     * @param alarmtype
     */
    public void setAlarmtype(Integer alarmtype) {
        this.alarmtype = alarmtype;
    }

    /**
     * @return AlarmStatus
     */
    public Integer getAlarmstatus() {
        return alarmstatus;
    }

    /**
     * @param alarmstatus
     */
    public void setAlarmstatus(Integer alarmstatus) {
        this.alarmstatus = alarmstatus;
    }

    /**
     * @return AlarmLevel
     */
    public Integer getAlarmlevel() {
        return alarmlevel;
    }

    /**
     * @param alarmlevel
     */
    public void setAlarmlevel(Integer alarmlevel) {
        this.alarmlevel = alarmlevel;
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
     * @return AlarmSite
     */
    public String getAlarmsite() {
        return alarmsite;
    }

    /**
     * @param alarmsite
     */
    public void setAlarmsite(String alarmsite) {
        this.alarmsite = alarmsite;
    }

    /**
     * @return AlarmTitle
     */
    public String getAlarmtitle() {
        return alarmtitle;
    }

    /**
     * @param alarmtitle
     */
    public void setAlarmtitle(String alarmtitle) {
        this.alarmtitle = alarmtitle;
    }

    /**
     * @return AlarmInfo
     */
    public String getAlarminfo() {
        return alarminfo;
    }

    /**
     * @param alarminfo
     */
    public void setAlarminfo(String alarminfo) {
        this.alarminfo = alarminfo;
    }

    /**
     * @return IsUrgent
     */
    public Integer getIsurgent() {
        return isurgent;
    }

    /**
     * @param isurgent
     */
    public void setIsurgent(Integer isurgent) {
        this.isurgent = isurgent;
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
     * @return RequirementTime
     */
    public Date getRequirementtime() {
        return requirementtime;
    }

    /**
     * @param requirementtime
     */
    public void setRequirementtime(Date requirementtime) {
        this.requirementtime = requirementtime;
    }

    /**
     * @return IsFeedback
     */
    public Integer getIsfeedback() {
        return isfeedback;
    }

    /**
     * @param isfeedback
     */
    public void setIsfeedback(Integer isfeedback) {
        this.isfeedback = isfeedback;
    }

    /**
     * @return FeedbackTime
     */
    public Date getFeedbacktime() {
        return feedbacktime;
    }

    /**
     * @param feedbacktime
     */
    public void setFeedbacktime(Date feedbacktime) {
        this.feedbacktime = feedbacktime;
    }

    /**
     * @return FeedbackInfo
     */
    public String getFeedbackinfo() {
        return feedbackinfo;
    }

    /**
     * @param feedbackinfo
     */
    public void setFeedbackinfo(String feedbackinfo) {
        this.feedbackinfo = feedbackinfo;
    }

    /**
     * @return FeedbackUser
     */
    public String getFeedbackuser() {
        return feedbackuser;
    }

    /**
     * @param feedbackuser
     */
    public void setFeedbackuser(String feedbackuser) {
        this.feedbackuser = feedbackuser;
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
     * @return Status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return IsOverAlarm
     */
    public Integer getIsoveralarm() {
        return isoveralarm;
    }

    /**
     * @param isoveralarm
     */
    public void setIsoveralarm(Integer isoveralarm) {
        this.isoveralarm = isoveralarm;
    }

    /**
     * @return LastUpdateDate
     */
    public Date getLastupdatedate() {
        return lastupdatedate;
    }

    /**
     * @param lastupdatedate
     */
    public void setLastupdatedate(Date lastupdatedate) {
        this.lastupdatedate = lastupdatedate;
    }

    /**
     * @return RequirementCount
     */
    public Integer getRequirementcount() {
        return requirementcount;
    }

    /**
     * @param requirementcount
     */
    public void setRequirementcount(Integer requirementcount) {
        this.requirementcount = requirementcount;
    }

    /**
     * @return RequirementCountUnit
     */
    public Integer getRequirementcountunit() {
        return requirementcountunit;
    }

    /**
     * @param requirementcountunit
     */
    public void setRequirementcountunit(Integer requirementcountunit) {
        this.requirementcountunit = requirementcountunit;
    }

    /**
     * @return DealStatus
     */
    public Integer getDealstatus() {
        return dealstatus;
    }

    /**
     * @param dealstatus
     */
    public void setDealstatus(Integer dealstatus) {
        this.dealstatus = dealstatus;
    }

    /**
     * @return CurrentDealUser
     */
    public String getCurrentdealuser() {
        return currentdealuser;
    }

    /**
     * @param currentdealuser
     */
    public void setCurrentdealuser(String currentdealuser) {
        this.currentdealuser = currentdealuser;
    }

    /**
     * @return DealDate
     */
    public Date getDealdate() {
        return dealdate;
    }

    /**
     * @param dealdate
     */
    public void setDealdate(Date dealdate) {
        this.dealdate = dealdate;
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
     * @return BaseID
     */
    public Long getBaseid() {
        return baseid;
    }

    /**
     * @param baseid
     */
    public void setBaseid(Long baseid) {
        this.baseid = baseid;
    }
}