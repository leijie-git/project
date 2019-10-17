package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Inspect_PlanDetial")
public class UtInspectPlandetial implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "PlanID")
    private Long planid;

    @Column(name = "SiteID")
    private Long siteid;

    @Column(name = "Status")
    private Integer status;
    
    @Column(name = "TaskUserID")
    private String taskuserid;
    
    @Column(name = "TaskUserName")
    private String taskusername;
    
    private static final long serialVersionUID = 1L;

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
     * @return ID
     */
    public String getTaskUserId() {
        return taskuserid;
    }

    /**
     * @param id
     */
    public void setTaskUserId(String taskuserid) {
        this.taskuserid = taskuserid;
    }
    
    /**
     * @return ID
     */
    public String getTaskUserName() {
        return taskusername;
    }

    /**
     * @param id
     */
    public void setTaskUserName(String taskusername) {
        this.taskusername = taskusername;
    }
    
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
     * @return PlanID
     */
    public Long getPlanid() {
        return planid;
    }

    /**
     * @param planid
     */
    public void setPlanid(Long planid) {
        this.planid = planid;
    }

    /**
     * @return SiteID
     */
    public Long getSiteid() {
        return siteid;
    }

    /**
     * @param siteid
     */
    public void setSiteid(Long siteid) {
        this.siteid = siteid;
    }
}