package com.gw.aspect.data;

import lombok.Data;

import java.util.Date;

@Data
public class OriginalRtuFaultVo {
    //设备信息
    private Long id;
    private String alarmcode;
    private String equipmentname;
    private String equipmentdetialname;
    private String normalvalue;
    private Long netDeviceId;
    private Long eqId;
    private Long fireequipmentdetialid;

    //报警信息
    private String alarmvalue;
    private Date alarmtime;
    private Date lastupdate;
    private Integer alarmStatus;

    //处理信息
    private Integer isdeal;
    private Integer dealtype;
    private Date dealtime;
    private String dealuser;
    private String dealinfo;
    private Integer isneedrepair;
    private Integer isneedmaintain;
    private String maintaindesc;
    private Integer dealresult;
    private String dealcode;
    private String picurl;

    //关联单位信息
    private Long unitid;
    private Long buildId;
    private Long buildAreaId;
    private Long calibrationId;

}
