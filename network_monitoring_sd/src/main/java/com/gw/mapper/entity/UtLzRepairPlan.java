package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Table(name = "UT_LZ_RepairPlan")
@Data
public class UtLzRepairPlan implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "unitID")
    private Long unitID;

    @Column(name = "buildID")
    private Long buildID;

    @Column(name = "buildAreaID")
    private Long buildAreaID;

    @Column(name = "isGenerate")
    private Integer isGenerate;

    @Column(name = "planName")
    private String planName;

    @Column(name = "executorID")
    private Long executorID;

    @Column(name = "planStartTime")
    private Date planStartTime;

    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "badDesc")
    private String badDesc;

    @Column(name = "repairSite")
    private String repairSite;

    @Column(name = "lastupdate")
    private Date lastupdate;

    @Column(name = "planEndTime")
    private Date planEndTime;

}
