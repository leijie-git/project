package com.gw.inspect.data;

import lombok.Data;

/**
 * Created by JIE.LEI on 2019/7/4.
 * 这是检查位置  检查对象 检查项 关联对象
 */
@Data
public class SiteClassBaseDetialData {
    //主键
    private Long ID;
    //巡检点ID
    private Long SiteID ;
    //检查对象ID
    private Long SiteClassBaseID;
    //检查项ID
    private Long SiteClassDetialID;
    //状态
    private String status;
}
