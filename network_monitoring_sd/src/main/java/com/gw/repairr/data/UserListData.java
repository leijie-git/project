package com.gw.repairr.data;

import lombok.Data;

@Data
public class UserListData {
    private Long executorID;    //执行人id
    private String userName;    //执行人名字
    private Long planID;        //计划id
    private String planName;    //计划名称

}
