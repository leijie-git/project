package com.gw.inspect.data;

import lombok.Data;

/**
 * Created by JIE.LEI on 2019/7/3.
 *
 * 巡检数据录入
 */
@Data
public class InspectionInData {
     //编号
    private String siteCode;
    //名称
    private String siteName;
    //描述
    private String Desc;
    //位置
    private String site;
    //单位
    private String unit;
    //建筑
    private String building;
    //区域
    private String area;
    //执行人
    private String performName;
    //巡查频数
    private String inspectNum;
    //执行周期
    private String inspectCycle;
    //周期内执行起时间
    private String taskStart;
    //周期内执行止时间
    private String taskend;
    //对象名称
    private String objectName;
    //位置描述
    private String SiteDesc;
    //检查内容
    private String CheckInfo;
    //检查方式
    private String CheckMethod;
    //检查项默认状态为未检查


    @Override
    public String toString() {
        return "InspectionInData{" +
                "siteCode='" + siteCode + '\'' +
                ", siteName='" + siteName + '\'' +
                ", Desc='" + Desc + '\'' +
                ", site='" + site + '\'' +
                ", unit='" + unit + '\'' +
                ", building='" + building + '\'' +
                ", area='" + area + '\'' +
                ", performName='" + performName + '\'' +
                ", inspectNum='" + inspectNum + '\'' +
                ", inspectCycle='" + inspectCycle + '\'' +
                ", taskStart='" + taskStart + '\'' +
                ", objectName='" + objectName + '\'' +
                ", SiteDesc='" + SiteDesc + '\'' +
                ", CheckInfo='" + CheckInfo + '\'' +
                ", CheckMethod='" + CheckMethod + '\'' +
                '}';
    }
}
