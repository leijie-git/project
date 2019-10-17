package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_LZ_MonthInspect")
public class UtLzMonthinspect implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "InspectDate")
    private Date inspectdate;

    @Column(name = "InspectSite")
    private String inspectsite;

    @Column(name = "InspectDepartment")
    private String inspectdepartment;

    @Column(name = "ProblemDesc")
    private String problemdesc;

    @Column(name = "DealInfo")
    private String dealinfo;

    @Column(name = "CheckUser")
    private String checkuser;

    @Column(name = "CheckedUser")
    private String checkeduser;

    @Column(name = "RecordUser")
    private String recorduser;

    @Column(name = "ManageUser")
    private String manageuser;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "IsOK")
    private Integer isok;

    @Column(name = "InspectName")
    private String inspectname;

    @Column(name = "InspectInfo")
    private String inspectinfo;

    @Column(name = "InspectReport")
    private String inspectreport;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "IDFromData")
    private Long idfromdata;

    @Column(name = "CreateUser")
    private Long createuser;

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
     * @return InspectDate
     */
    public Date getInspectdate() {
        return inspectdate;
    }

    /**
     * @param inspectdate
     */
    public void setInspectdate(Date inspectdate) {
        this.inspectdate = inspectdate;
    }

    /**
     * @return InspectSite
     */
    public String getInspectsite() {
        return inspectsite;
    }

    /**
     * @param inspectsite
     */
    public void setInspectsite(String inspectsite) {
        this.inspectsite = inspectsite;
    }

    /**
     * @return InspectDepartment
     */
    public String getInspectdepartment() {
        return inspectdepartment;
    }

    /**
     * @param inspectdepartment
     */
    public void setInspectdepartment(String inspectdepartment) {
        this.inspectdepartment = inspectdepartment;
    }

    /**
     * @return ProblemDesc
     */
    public String getProblemdesc() {
        return problemdesc;
    }

    /**
     * @param problemdesc
     */
    public void setProblemdesc(String problemdesc) {
        this.problemdesc = problemdesc;
    }

    /**
     * @return DealInfo
     */
    public String getDealinfo() {
        return dealinfo;
    }

    /**
     * @param dealinfo
     */
    public void setDealinfo(String dealinfo) {
        this.dealinfo = dealinfo;
    }

    /**
     * @return CheckUser
     */
    public String getCheckuser() {
        return checkuser;
    }

    /**
     * @param checkuser
     */
    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    /**
     * @return CheckedUser
     */
    public String getCheckeduser() {
        return checkeduser;
    }

    /**
     * @param checkeduser
     */
    public void setCheckeduser(String checkeduser) {
        this.checkeduser = checkeduser;
    }

    /**
     * @return RecordUser
     */
    public String getRecorduser() {
        return recorduser;
    }

    /**
     * @param recorduser
     */
    public void setRecorduser(String recorduser) {
        this.recorduser = recorduser;
    }

    /**
     * @return ManageUser
     */
    public String getManageuser() {
        return manageuser;
    }

    /**
     * @param manageuser
     */
    public void setManageuser(String manageuser) {
        this.manageuser = manageuser;
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
     * @return InspectName
     */
    public String getInspectname() {
        return inspectname;
    }

    /**
     * @param inspectname
     */
    public void setInspectname(String inspectname) {
        this.inspectname = inspectname;
    }

    /**
     * @return InspectInfo
     */
    public String getInspectinfo() {
        return inspectinfo;
    }

    /**
     * @param inspectinfo
     */
    public void setInspectinfo(String inspectinfo) {
        this.inspectinfo = inspectinfo;
    }

    /**
     * @return InspectReport
     */
    public String getInspectreport() {
        return inspectreport;
    }

    /**
     * @param inspectreport
     */
    public void setInspectreport(String inspectreport) {
        this.inspectreport = inspectreport;
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

    /**
     * @return IDFromData
     */
    public Long getIdfromdata() {
        return idfromdata;
    }

    /**
     * @param idfromdata
     */
    public void setIdfromdata(Long idfromdata) {
        this.idfromdata = idfromdata;
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
}