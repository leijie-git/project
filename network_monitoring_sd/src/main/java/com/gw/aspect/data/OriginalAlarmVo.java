package com.gw.aspect.data;

import lombok.Data;

import java.util.Date;

@Data
public class OriginalAlarmVo{
    //告警设备信息
    private String onwercode;//联网设备
    private Integer deviceindex;//设备类型
    private Integer deviceno;//设备子号
    private String alarmReserve;//末端设备部位号
    private Long addressRelId;
    private Long eqId;
    private Long netDeviceId;

    //告警信息
    private Long id;
    private Date time;//告警时间
    private Integer alarmStatus;//告警状态
    private String alarmSourcedesc;
    private String alarmDevicedesc;//设备描述
    private String alarmWheredesc;//设备位置
    private Integer isTest;//是否测试

    //告警处理信息
    private Integer isdeal;//是否处理
    private Integer dealtype;//处理类型
    private Date dealtime;//处理时间
    private String dealuser;//处理人
    private String dealinfo;//处理信息
    private Integer isneedmaintain;//是否需要维保
    private String maintaindesc;//维保描述
    private Integer dealresult;//处理结果
    private String dealcode;//处理编号
    private String picurl;//图片信息

    //告警关联单位信息
    private Long unitid;
    private Long buildId;
    private Long buildAreaId;

}
