package com.gw.mapper.entity;

import lombok.Data;


import java.io.Serializable;
import java.util.Date;

@Data
public class UtInspectSiteOut implements Serializable {
    //检查点id
    private String siteId;
    //点位编号
    private String siteCode;
    //点位名称
    private String siteName;
    //计划id
    private String planId;
    //计划详情id
    private String PlanDetialID;
    private String planName;
    //位置
    private String sitePlace;
    // 计划责任人id
    private String supervisorID;
    //计划责任人名字
    private String supervisorName;
    //执行周期
    private String inspectcycle;
    // 巡查频数
    private String inspectcount;

//周期内开始时间
    private String taskstart;
    //'周期内结束时间'
    private String taskend;
    //单位id
    private String UnitID;
    //执行人id
    private String executorID;
    // 执行人名称
    private String executorName;
    //是否关联
    private String isPlaned;
    private String Status;
    //位置描述
    private String SiteDesc;
    //NFC编号
   private String NFCCode;
    // 类型ID
    private String SiteClassID;
    //任务是否已生成
    private String isTasked;
    private Integer pageNumber;//第几页

    private Integer pageSize;//每页条数


}