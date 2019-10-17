package com.gw.inspect.data;

import lombok.Data;

@Data
public class InspectTaskInData {

    private String id;

    private String nfccode;

    private String sitecode;

    private String sitename;

    private String userName;

    private String sitedesc;

    private String orderindex;

    private String remark;

    private String taskstart;

    private String taskend;

    private String epirationTime;//到期提醒时间

    private String isinspect;

    private String inspecttime;

    private String inspectuserid;

    private String isok;

    private String unitid;

    private String plandetialid;

    private String planid;

    private String siteid;

    private String siteclassid;

    private String supervisorID;//监督人ID
    private String supervisorName;//监督人名字
    private String InspectUserName;
    private Integer pageNumber;//第几页

    private Integer pageSize;//每页条数

}
