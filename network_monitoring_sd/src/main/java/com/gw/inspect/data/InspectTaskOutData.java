package com.gw.inspect.data;

import lombok.Data;

@Data
public class InspectTaskOutData {

    private String id;

    private String nfccode;

    private String sitecode;

    private String sitename;

    private String sitedesc;

    private String orderindex;

    private String remark;

    private String taskstart;

    private String taskend;

    private String isinspect;

    private String inspecttime;





    private String isok;

    private String unitid;

    private String plandetialid;

    private String siteid;

    private String siteclassid;
    //计划责任人ID
    private String supervisorID;
    //计划责任人名字
    private String supervisorName;
    //检查点个数
    private int siteCount;
    //已完成检查个数
    private String okCheckCount;
    //转单状态
    private String ReceiveStatus;
    //执行人id
    private String inspectuserid;
    //执行人Name
    private String inspectusername;
    //当前页
    private int pageNum;
    //每页的数量
    private int pageSize;





}
