package com.gw.repairr.data;

import lombok.Data;

import java.util.Date;

@Data
public class RepairData {

    private Integer pageNumber;
    private Integer pageSize;
    private String id;
    private String unitID;//单位id
    private Long buildID;//建筑id
    private Long buildAreaID;//区域id
    private Integer isGenerate;//是否已生产任务
    private String planName;//维保计划名称
    private Long executorID;//维保执行人
    private String badDesc;//问题描述
    private String repairSite;//需维修位置
    private String planStartTime;//计划维保开始时间
    private String planEndTime;//计划维保结束时间
    private Date lastupdate;//最后更新时间
    private String UnitName;//单位名称
    private String BuildingName;//建筑名称
    private String BuildAreaName;//区域名称
    private String UserName;//执行人姓名
    private String CreateUserId;//创建人id
    private String CreateUserName;//创建人姓名

}
