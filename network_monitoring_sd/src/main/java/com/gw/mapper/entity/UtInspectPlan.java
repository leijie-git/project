package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Inspect_Plan")
public class UtInspectPlan implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PlanName")
    private String planname;

    @Column(name = "PlanStart")
    private Date planstart;

    @Column(name = "PlanEnd")
    private Date planend;

    @Column(name = "DefaultUserID")
    private String defaultuserid;

    @Column(name = "DefaultUserName")
    private String defaultusername;

    @Column(name = "CreateUser")
    private Long createuser;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name="supervisorID")
    //监督人（计划责任人）
    private String supervisorID;

    public String getSupervisorID() {
        return supervisorID;
    }

    public void setSupervisorID(String supervisorID) {
        this.supervisorID = supervisorID;
    }

    private static final long serialVersionUID = 1L;

    /**
     * @return ID
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return PlanName
     */
    public String getPlanname() {
        return planname;
    }

    /**
     * @param planname
     */
    public void setPlanname(String planname) {
        this.planname = planname;
    }

    /**
     * @return PlanStart
     */
    public Date getPlanstart() {
        return planstart;
    }

    /**
     * @param planstart
     */
    public void setPlanstart(Date planstart) {
        this.planstart = planstart;
    }

    /**
     * @return PlanEnd
     */
    public Date getPlanend() {
        return planend;
    }

    /**
     * @param planend
     */
    public void setPlanend(Date planend) {
        this.planend = planend;
    }

    /**
     * @return DefaultUserID
     */
    public String getDefaultuserid() {
        return defaultuserid;
    }

    /**
     * @param defaultuserid
     */
    public void setDefaultuserid(String defaultuserid) {
        this.defaultuserid = defaultuserid;
    }

    /**
     * @return DefaultUserName
     */
    public String getDefaultusername() {
        return defaultusername;
    }

    /**
     * @param defaultusername
     */
    public void setDefaultusername(String defaultusername) {
        this.defaultusername = defaultusername;
    }

    /**
     * @return CreateUser
     */
    public Long getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser
     */
    public void setCreateuser(Long createuser) {
        this.createuser = createuser;
    }

    /**
     * @return CreateDate
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * @param createdate
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * @return Remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * @return Status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return UnitID
     */
    public Long getUnitid() {
        return unitid;
    }

    /**
     * @param unitid
     */
    public void setUnitid(Long unitid) {
        this.unitid = unitid;
    }
}