package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Unit_Dangerous")
public class UtUnitDangerous implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "DangerousName")
    private String dangerousname;

    @Column(name = "DangerousCount")
    private Integer dangerouscount;

    @Column(name = "CountUnit")
    private String countunit;

    @Column(name = "Site")
    private String site;

    @Column(name = "DangerousLevel")
    private String dangerouslevel;

    @Column(name = "DangerousUsed")
    private String dangerousused;

    @Column(name = "DangerousCZCS")
    private String dangerousczcs;

    @Column(name = "JSFZR")
    private String jsfzr;

    @Column(name = "JSFZRDH")
    private String jsfzrdh;

    @Column(name = "AQGLR")
    private String aqglr;

    @Column(name = "AQGLRDH")
    private String aqglrdh;

    @Column(name = "IsXFBS")
    private Integer isxfbs;

    @Column(name = "OperateDesc")
    private String operatedesc;

    @Column(name = "DangerousType")
    private String dangeroustype;

    @Column(name = "DangerousState")
    private String dangerousstate;

    @Column(name = "DangerousImageName")
    private String dangerousimagename;

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
     * @return DangerousName
     */
    public String getDangerousname() {
        return dangerousname;
    }

    /**
     * @param dangerousname
     */
    public void setDangerousname(String dangerousname) {
        this.dangerousname = dangerousname;
    }

    /**
     * @return DangerousCount
     */
    public Integer getDangerouscount() {
        return dangerouscount;
    }

    /**
     * @param dangerouscount
     */
    public void setDangerouscount(Integer dangerouscount) {
        this.dangerouscount = dangerouscount;
    }

    /**
     * @return CountUnit
     */
    public String getCountunit() {
        return countunit;
    }

    /**
     * @param countunit
     */
    public void setCountunit(String countunit) {
        this.countunit = countunit;
    }

    /**
     * @return Site
     */
    public String getSite() {
        return site;
    }

    /**
     * @param site
     */
    public void setSite(String site) {
        this.site = site;
    }

    /**
     * @return DangerousLevel
     */
    public String getDangerouslevel() {
        return dangerouslevel;
    }

    /**
     * @param dangerouslevel
     */
    public void setDangerouslevel(String dangerouslevel) {
        this.dangerouslevel = dangerouslevel;
    }

    /**
     * @return DangerousUsed
     */
    public String getDangerousused() {
        return dangerousused;
    }

    /**
     * @param dangerousused
     */
    public void setDangerousused(String dangerousused) {
        this.dangerousused = dangerousused;
    }

    /**
     * @return DangerousCZCS
     */
    public String getDangerousczcs() {
        return dangerousczcs;
    }

    /**
     * @param dangerousczcs
     */
    public void setDangerousczcs(String dangerousczcs) {
        this.dangerousczcs = dangerousczcs;
    }

    /**
     * @return JSFZR
     */
    public String getJsfzr() {
        return jsfzr;
    }

    /**
     * @param jsfzr
     */
    public void setJsfzr(String jsfzr) {
        this.jsfzr = jsfzr;
    }

    /**
     * @return JSFZRDH
     */
    public String getJsfzrdh() {
        return jsfzrdh;
    }

    /**
     * @param jsfzrdh
     */
    public void setJsfzrdh(String jsfzrdh) {
        this.jsfzrdh = jsfzrdh;
    }

    /**
     * @return AQGLR
     */
    public String getAqglr() {
        return aqglr;
    }

    /**
     * @param aqglr
     */
    public void setAqglr(String aqglr) {
        this.aqglr = aqglr;
    }

    /**
     * @return AQGLRDH
     */
    public String getAqglrdh() {
        return aqglrdh;
    }

    /**
     * @param aqglrdh
     */
    public void setAqglrdh(String aqglrdh) {
        this.aqglrdh = aqglrdh;
    }

    /**
     * @return IsXFBS
     */
    public Integer getIsxfbs() {
        return isxfbs;
    }

    /**
     * @param isxfbs
     */
    public void setIsxfbs(Integer isxfbs) {
        this.isxfbs = isxfbs;
    }

    /**
     * @return OperateDesc
     */
    public String getOperatedesc() {
        return operatedesc;
    }

    /**
     * @param operatedesc
     */
    public void setOperatedesc(String operatedesc) {
        this.operatedesc = operatedesc;
    }

    /**
     * @return DangerousType
     */
    public String getDangeroustype() {
        return dangeroustype;
    }

    /**
     * @param dangeroustype
     */
    public void setDangeroustype(String dangeroustype) {
        this.dangeroustype = dangeroustype;
    }

    /**
     * @return DangerousState
     */
    public String getDangerousstate() {
        return dangerousstate;
    }

    /**
     * @param dangerousstate
     */
    public void setDangerousstate(String dangerousstate) {
        this.dangerousstate = dangerousstate;
    }

    /**
     * @return DangerousImageName
     */
    public String getDangerousimagename() {
        return dangerousimagename;
    }

    /**
     * @param dangerousimagename
     */
    public void setDangerousimagename(String dangerousimagename) {
        this.dangerousimagename = dangerousimagename;
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