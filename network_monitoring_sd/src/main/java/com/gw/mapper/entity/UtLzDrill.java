package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_LZ_Drill")
public class UtLzDrill implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DrillDateS")
    private Date drilldates;

    @Column(name = "DrillDateE")
    private Date drilldatee;

    @Column(name = "DrillSite")
    private String drillsite;

    @Column(name = "DrillDepartment")
    private String drilldepartment;

    @Column(name = "DrillManager")
    private String drillmanager;

    @Column(name = "DrillManagerTel")
    private String drillmanagertel;

    @Column(name = "DrillInfo")
    private String drillinfo;

    @Column(name = "DrillFile")
    private String drillfile;

    @Column(name = "DrillFileShow")
    private String drillfileshow;

    @Column(name = "DrillProgramme")
    private String drillprogramme;

    @Column(name = "JoinUser")
    private String joinuser;

    @Column(name = "JoinUserCount")
    private Integer joinusercount;

    @Column(name = "DrillResult")
    private String drillresult;

    @Column(name = "DrillSummary")
    private String drillsummary;

    @Column(name = "AmendOpinion")
    private String amendopinion;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateUser")
    private String createuser;

    @Column(name = "DataFromID")
    private String datafromid;

    @Column(name = "DataFrom")
    private Integer datafrom;

    @Column(name = "OtherCreateUserName")
    private String othercreateusername;

    @Column(name = "UnitID")
    private Long unitid;

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
     * @return DrillDateS
     */
    public Date getDrilldates() {
        return drilldates;
    }

    /**
     * @param drilldates
     */
    public void setDrilldates(Date drilldates) {
        this.drilldates = drilldates;
    }

    /**
     * @return DrillDateE
     */
    public Date getDrilldatee() {
        return drilldatee;
    }

    /**
     * @param drilldatee
     */
    public void setDrilldatee(Date drilldatee) {
        this.drilldatee = drilldatee;
    }

    /**
     * @return DrillSite
     */
    public String getDrillsite() {
        return drillsite;
    }

    /**
     * @param drillsite
     */
    public void setDrillsite(String drillsite) {
        this.drillsite = drillsite;
    }

    /**
     * @return DrillDepartment
     */
    public String getDrilldepartment() {
        return drilldepartment;
    }

    /**
     * @param drilldepartment
     */
    public void setDrilldepartment(String drilldepartment) {
        this.drilldepartment = drilldepartment;
    }

    /**
     * @return DrillManager
     */
    public String getDrillmanager() {
        return drillmanager;
    }

    /**
     * @param drillmanager
     */
    public void setDrillmanager(String drillmanager) {
        this.drillmanager = drillmanager;
    }

    /**
     * @return DrillManagerTel
     */
    public String getDrillmanagertel() {
        return drillmanagertel;
    }

    /**
     * @param drillmanagertel
     */
    public void setDrillmanagertel(String drillmanagertel) {
        this.drillmanagertel = drillmanagertel;
    }

    /**
     * @return DrillInfo
     */
    public String getDrillinfo() {
        return drillinfo;
    }

    /**
     * @param drillinfo
     */
    public void setDrillinfo(String drillinfo) {
        this.drillinfo = drillinfo;
    }

    /**
     * @return DrillFile
     */
    public String getDrillfile() {
        return drillfile;
    }

    /**
     * @param drillfile
     */
    public void setDrillfile(String drillfile) {
        this.drillfile = drillfile;
    }

    /**
     * @return DrillFileShow
     */
    public String getDrillfileshow() {
        return drillfileshow;
    }

    /**
     * @param drillfileshow
     */
    public void setDrillfileshow(String drillfileshow) {
        this.drillfileshow = drillfileshow;
    }

    /**
     * @return DrillProgramme
     */
    public String getDrillprogramme() {
        return drillprogramme;
    }

    /**
     * @param drillprogramme
     */
    public void setDrillprogramme(String drillprogramme) {
        this.drillprogramme = drillprogramme;
    }

    /**
     * @return JoinUser
     */
    public String getJoinuser() {
        return joinuser;
    }

    /**
     * @param joinuser
     */
    public void setJoinuser(String joinuser) {
        this.joinuser = joinuser;
    }

    /**
     * @return JoinUserCount
     */
    public Integer getJoinusercount() {
        return joinusercount;
    }

    /**
     * @param joinusercount
     */
    public void setJoinusercount(Integer joinusercount) {
        this.joinusercount = joinusercount;
    }

    /**
     * @return DrillResult
     */
    public String getDrillresult() {
        return drillresult;
    }

    /**
     * @param drillresult
     */
    public void setDrillresult(String drillresult) {
        this.drillresult = drillresult;
    }

    /**
     * @return DrillSummary
     */
    public String getDrillsummary() {
        return drillsummary;
    }

    /**
     * @param drillsummary
     */
    public void setDrillsummary(String drillsummary) {
        this.drillsummary = drillsummary;
    }

    /**
     * @return AmendOpinion
     */
    public String getAmendopinion() {
        return amendopinion;
    }

    /**
     * @param amendopinion
     */
    public void setAmendopinion(String amendopinion) {
        this.amendopinion = amendopinion;
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
     * @return DataFromID
     */
    public String getDatafromid() {
        return datafromid;
    }

    /**
     * @param datafromid
     */
    public void setDatafromid(String datafromid) {
        this.datafromid = datafromid;
    }

    /**
     * @return DataFrom
     */
    public Integer getDatafrom() {
        return datafrom;
    }

    /**
     * @param datafrom
     */
    public void setDatafrom(Integer datafrom) {
        this.datafrom = datafrom;
    }

    /**
     * @return OtherCreateUserName
     */
    public String getOthercreateusername() {
        return othercreateusername;
    }

    /**
     * @param othercreateusername
     */
    public void setOthercreateusername(String othercreateusername) {
        this.othercreateusername = othercreateusername;
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