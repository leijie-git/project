package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Inspect_TaskDetial")
public class UtInspectTaskdetial implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CheckInfo")
    private String checkinfo;

    @Column(name = "IsOK")
    private Integer isok;

    @Column(name = "BadDesc")
    private String baddesc;

    @Column(name = "BadLevel")
    private Integer badlevel;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateUser")
    private String createuser;

    @Column(name = "IsNeedRepair")
    private Integer isneedrepair;

    @Column(name = "pic_path")
    private String picPath;

    @Column(name = "TaskID")
    private Long taskid;

    @Column(name = "ClassDetialID")
    private Long classdetialid;

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
     * @return CheckInfo
     */
    public String getCheckinfo() {
        return checkinfo;
    }

    /**
     * @param checkinfo
     */
    public void setCheckinfo(String checkinfo) {
        this.checkinfo = checkinfo;
    }

    /**
     * @return IsOK
     */
    public Integer getIsok() {
        return isok;
    }

    /**
     * @param isok
     */
    public void setIsok(Integer isok) {
        this.isok = isok;
    }

    /**
     * @return BadDesc
     */
    public String getBaddesc() {
        return baddesc;
    }

    /**
     * @param baddesc
     */
    public void setBaddesc(String baddesc) {
        this.baddesc = baddesc;
    }

    /**
     * @return BadLevel
     */
    public Integer getBadlevel() {
        return badlevel;
    }

    /**
     * @param badlevel
     */
    public void setBadlevel(Integer badlevel) {
        this.badlevel = badlevel;
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
     * @return CreateUser
     */
    public String getCreateuser() {
        return createuser;
    }

    /**
     * @param createuser
     */
    public void setCreateuser(String createuser) {
        this.createuser = createuser;
    }

    /**
     * @return IsNeedRepair
     */
    public Integer getIsneedrepair() {
        return isneedrepair;
    }

    /**
     * @param isneedrepair
     */
    public void setIsneedrepair(Integer isneedrepair) {
        this.isneedrepair = isneedrepair;
    }

    /**
     * @return pic_path
     */
    public String getPicPath() {
        return picPath;
    }

    /**
     * @param picPath
     */
    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }

    /**
     * @return TaskID
     */
    public Long getTaskid() {
        return taskid;
    }

    /**
     * @param taskid
     */
    public void setTaskid(Long taskid) {
        this.taskid = taskid;
    }

    /**
     * @return ClassDetialID
     */
    public Long getClassdetialid() {
        return classdetialid;
    }

    /**
     * @param classdetialid
     */
    public void setClassdetialid(Long classdetialid) {
        this.classdetialid = classdetialid;
    }
}