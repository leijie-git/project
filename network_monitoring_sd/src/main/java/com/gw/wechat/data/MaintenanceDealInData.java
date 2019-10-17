package com.gw.wechat.data;

import lombok.Data;

/**
 * 维保整改巡查
 * @author zfg
 *
 **/
@Data
public class MaintenanceDealInData {

	private String id;

    private String dealtype;

    private String dealdate;
    
    private String RepairCode;

    private String dealinfo;//维修描述
    
    private String userId;//当前处理人

    private String operateinfo;//问题描述

    private String operatetime;

    private String operateusername;

    private String repairid;//token

    private String dealuserid;

    private String operateuserid;
    
    private String picurl;//问题描述

    private String picpath; //现场照片

    private String longitude; //经度

    private String latitude; //纬度

    private String location;//位置信息
}
