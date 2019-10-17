package com.gw.front.index.data;

import lombok.Data;

import java.io.Serializable;

/**
 * Created by JIE.LEI on 2019/7/11.
 * 统计当前用户的单位下的检查点的状态统计
 */
@Data
public class UserUnitIDSiteStatus implements Serializable {

    //总共多少检查点
    private int sumAbnormal;
    //未检查
    private int unchecked;
    //正常
    private int normal;
    //异常数
    private int abnormal;
    //立即整改
    private int nowRectification;
    //生成维修
    private  int generateMaintenance;
    // 已整改
    private  int rectificationOk;



    //任务检查点个数
    private int sumSiteCount;
    //已完成巡查点个数
    private int sumOkCheckCount;



}
