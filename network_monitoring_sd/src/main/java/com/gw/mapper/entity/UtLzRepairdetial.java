package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_LZ_RepairDetial")
@Data
public class UtLzRepairdetial implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DealType")
    private Integer dealtype;

    @Column(name = "DealDate")
    private Date dealdate;

    @Column(name = "DealInfo")
    private String dealinfo;

    @Column(name = "OperateInfo")
    private String operateinfo;

    @Column(name = "OperateTime")
    private Date operatetime;

    @Column(name = "OperateUserName")
    private String operateusername;

    @Column(name = "RepairID")
    private Long repairid;

    @Column(name = "DealUserID")
    private Long dealuserid;

    @Column(name = "OperateUserID")
    private Long operateuserid;
    
    @Column(name = "picPath")
    private String picpath;

    private String longitude;

    private String latitude;

    private String location;

    private static final long serialVersionUID = 1L;

}