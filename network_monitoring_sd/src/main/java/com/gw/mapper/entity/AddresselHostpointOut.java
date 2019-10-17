package com.gw.mapper.entity;

import lombok.Data;

/**
 * @开发人 Jie.Lei
 * @创建时间 2019/7/23
 * @描述
 */
@Data
public class AddresselHostpointOut {
   //单位名称
   private String unitName;
   //建筑名称
   private String buildingName;
   //区域名称
   private String buildAreaName;
   //设备名称
   private String name;
   //部位号
   private String partcode;
    //真实地址
    private String  adress;
    //点位类型
    private String  pointType;
    //是否独立上线
    private String is_independent;

}
