package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "UT_LZ_BJZJAlarm_History")
public class UtLzBjzjalarmHistory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "ID")
    private Long id;
    @Column(name = "AlarmCode")
    private String alarmcode;
    @Column(name = "OnwerCode")
    private String onwercode;
    @Column(name = "DeviceIndex")
    private Integer deviceindex;
    @Column(name = "DeviceNo")
    private Integer deviceno;
    @Column(name = "Time")
    private Date time;
    @Column(name = "Alarm_Status")
    private Integer alarmStatus;
    @Column(name = "Alarm_SourceDesc")
    private String alarmSourcedesc;
    @Column(name = "Alarm_DeviceDesc")
    private String alarmDevicedesc;
    @Column(name = "Alarm_WhereDesc")
    private String alarmWheredesc;
    @Column(name = "Alarm_Reserve")
    private String alarmReserve;
    @Column(name = "ZJID")
    private String zjid;
    @Column(name = "HLID")
    private String hlid;
    @Column(name = "JDID")
    private String jdid;
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
    @Column(name = "IsNeedMaintain")
    private Integer isneedmaintain;
    @Column(name = "MaintainDesc")
    private String maintaindesc;
    @Column(name = "DealResult")
    private Integer dealresult;
    @Column(name = "UnitID")
    private Long unitid;
    @Column(name = "calibration_id")
    private Long calibrationId;
    @Column(name = "dealCode")
    private String dealcode;
    @Column(name = "picUrl")
    private String picurl;
    @Column(name = "isPhone")
    private String isphone;

}