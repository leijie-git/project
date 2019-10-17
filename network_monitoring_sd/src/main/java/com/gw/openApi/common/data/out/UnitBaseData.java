package com.gw.openApi.common.data.out;

import lombok.Data;

@Data
public class UnitBaseData {
    private String unitId;//单位id
    private String unitCode;//单位编号
    private String unitName;//单位名称
    private String unitTypeName;//code表中unitType字段
    private String unitAddress;//单位地址
    private String orgCode;//组织机构代码
    private String leaderName;//责任人姓名
    private String leaderPhone;//责任人电话
    private String superviseLevel;//监管等级
    private String superviseType;//监管类型
    private String upperUnitId;//上级单位id
    private int unitType;//单位类型:0,联网;1,维保;2,监管;
}
