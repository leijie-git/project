package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.*;

@Data
@Table(name = "UT_Inspect_Site")
public class UtInspectSite implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SiteCode")
    private String sitecode;

    @Column(name = "SiteName")
    private String sitename;

    @Column(name = "SiteDesc")
    private String sitedesc;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "InspectCycle")
    private Integer inspectcycle;

    @Column(name = "InspectCycleType")
    private Integer inspectcycletype;

    @Column(name = "InspectCount")
    private Integer inspectcount;

    @Column(name = "TaskStart")
    private Date taskstart;

    @Column(name = "TaskEnd")
    private Date taskend;

    @Column(name = "NFCCode")
    private String nfccode;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "SiteClassID")
    private Long siteclassid;

    @Column(name = "buildID")
    private Long buildID;

    @Column(name = "buildAreaID")
    private Long buildAreaID;

    @Column(name = "executorID")
    private String executorID;

    @Column(name = "executorName")
    private String executorName;

    @Column(name = "qrCode")
    private String qrCode;

    //位置
    @Column(name="siteplace")
    private String siteplace;

    private static final long serialVersionUID = 1L;


}