package com.gw.generatereport;
/**
 *@描述
 *@创建人 Jie.Lei
 *@参数
 *@返回值  
 *@创建时间 2019/8/8
 */
import lombok.Data;

import java.util.List;

@Data
public class GenerateMaintenanceUserData {

    //联网单位ID
    private String UnitID;
    //维保单位ID
    private String ManageID;
    //维保用户id
    private String userID;
    //维保人员姓名
    private String UserName;
}
