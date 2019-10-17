package com.gw.wechat.data;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class PhoneTaskDetailOutData {

    private String taskdetailid;//任务详情id

    private String taskid;//任务id

    private String planid;//计划id

    private String plandetailid;//计划详情id

    private String planname;//计划名称

    private String starttime;//任务结束时间

    private String endtime;//任务开始时间

    private String sitename;//位置名称

    private String sitecode;//位置编号

    private String siteclassid;//点位分类id

    private String siteid;//点位id

    private String siteClassDetailid;//检查项id

    private String unitid;//单位id

    private String inspectcycletype;//巡查周期类型

    private String changeuserid;//转单人

    private String ischange;//是否转单

    private String changetime;//转单时间

    private String receiveuserid;//接单人

    private String isreceive;//是否接单

    private String receivetime;//接单时间

    private String nfcCode;//nfc唯一标识

    private boolean bindStatus;//是否绑定nfc

    private Integer receiveStatus;//转单状态

    private List<SiteClassDetailOutData> siteClassDetailList;//该任务对应检查项列表
}
