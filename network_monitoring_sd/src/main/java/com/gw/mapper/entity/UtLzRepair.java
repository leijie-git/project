package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "UT_LZ_Repair")
public class UtLzRepair implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "RepairCode")
    private String repaircode;

    @Column(name = "RepairSite")
    private String repairsite;

    @Column(name = "BadDesc")
    private String baddesc;

    @Column(name = "DealUnitType")
    private Integer dealunittype;

    @Column(name = "CreateTime")
    private Date createtime;

    @Column(name = "DealStatus")
    private Integer dealstatus;

    @Column(name = "CurrentDealUser")
    private String currentdealuser;

    @Column(name = "DealDate")
    private Date dealdate;

    @Column(name = "DealInfo")
    private String dealinfo;

    @Column(name = "LastUpdateTime")
    private Date lastupdatetime;

    @Column(name = "LastUpdateUser")
    private String lastupdateuser;

    @Column(name = "LiableUser")
    private String liableuser;

    @Column(name = "RequirementDate")
    private Date requirementdate;

    @Column(name = "DelayDay")
    private Integer delayday;

    @Column(name = "IsTwoRemind")
    private Integer istworemind;

    @Column(name = "TwoRemindDesc")
    private String tworeminddesc;

    @Column(name = "TwoRemindUser")
    private String tworeminduser;

    @Column(name = "TwoRemindTime")
    private Date tworemindtime;

    @Column(name = "IsThreeRemind")
    private Integer isthreeremind;

    @Column(name = "ThreeRemindDesc")
    private String threereminddesc;

    @Column(name = "ThreeRemindUser")
    private String threereminduser;

    @Column(name = "ThreeRemindTime")
    private Date threeremindtime;

    @Column(name = "IsFourRemind")
    private Integer isfourremind;

    @Column(name = "FourRemindDesc")
    private String fourreminddesc;

    @Column(name = "FourRemindUser")
    private String fourreminduser;

    @Column(name = "FourRemindTime")
    private Date fourremindtime;

    @Column(name = "IsWBFK")
    private Integer iswbfk;

    @Column(name = "FKTime")
    private Date fktime;

    @Column(name = "WBFKInfo")
    private String wbfkinfo;

    @Column(name = "WBCLR")
    private String wbclr;

    @Column(name = "WBCLSJ")
    private Date wbclsj;

    @Column(name = "ZWUser")
    private String zwuser;

    @Column(name = "ZWDate")
    private Date zwdate;

    @Column(name = "ZWDesc")
    private String zwdesc;

    @Column(name = "DataFrom")
    private Integer datafrom;

    @Column(name = "DataFromID")
    private String datafromid;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "RepairType")
    private Integer repairtype;

    @Column(name = "BaseID")
    private Long baseid;

    @Column(name = "DealUnitID")
    private Long dealunitid;
    
    
    @Column(name = "picUrl")
    private String picurl;

    //执行人
    @Column(name = "executorID")
    private Long executorID;
    //任务开始时间
    @Column(name = "taskStartTime")
    private Date taskStartTime;
    //任务结束时间
    @Column(name = "taskEndTime")
    private Date taskEndTime;

    //设备设施id
    @Column(name = "eqid")
    private Long eqid;
    //设备设施名称
    @Column(name = "eqname")
    private String eqname;


    private static final long serialVersionUID = 1L;


}