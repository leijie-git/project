package com.gw.generatereport;

import lombok.Data;

import java.util.List;

@Data
public class RemindExecutorIDAndLiableUserData {
    //当前时间
    private String nowTime;
    //任务结束时间跟现在时间相差的天数
    private String EndAsNow;
    //任务开始时间跟现在时间相差的天数
    private String StartAsNow;
    //任务开始时间
    private String taskStartTime;
    //任务结束时间
    private String taskEndTime;
    //维保单id
    private String id;
    //执行人ID
    private String executorID;
    //维保单完成情况ID
    private String RepairDetialID;
    //维保单完成情况中的维保单id
    private String RepairID;
    //是否执行这个维保任务了 1执行了  0未执行
    private String isExecute;
    //是否分配了任务 1 分配了  0是未分配
    private String IsDistribution;
    //账户
    private String Account;
    //单位ID
    private String UnitID;
    //用户名
    private String UserName;
    //手机型号
    private String phoneName;
    //通道ID
    private String channelId;


}
	

