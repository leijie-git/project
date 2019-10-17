package com.gw.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_BuildArea")
public class UtUnitBuildarea implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "BuildAreaName")
    private String buildareaname;

    @Column(name = "BuildAreaSite")
    private String buildareasite;

    @Column(name = "BuildAreabg")
    private String buildareabg;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "BgWidth")
    private BigDecimal bgwidth;

    @Column(name = "BgHeight")
    private BigDecimal bgheight;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "Lastupdate")
    private Date lastupdate;

    @Column(name = "BuildID")
    private Long buildid;
    
    @Column(name = "UnitID")
    private Long unitid;
    
    @Column(name = "BuildImgbg")
    private String buildimgbg;
    
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
     * @return ID
     */
    public Long getUnitid() {
        return unitid;
    }

    /**
     * @param id
     */
    public void setUnitid(Long unitId) {
        this.unitid = unitId;
    }


    /**
     * @return BuildAreaName
     */
    public String getBuildareaname() {
        return buildareaname;
    }

    /**
     * @param buildareaname
     */
    public void setBuildareaname(String buildareaname) {
        this.buildareaname = buildareaname;
    }

    /**
     * @return BuildAreaSite
     */
    public String getBuildareasite() {
        return buildareasite;
    }

    /**
     * @param buildareasite
     */
    public void setBuildareasite(String buildareasite) {
        this.buildareasite = buildareasite;
    }

    /**
     * @return BuildAreabg
     */
    public String getBuildareabg() {
        return buildareabg;
    }

    /**
     * @param buildareabg
     */
    public void setBuildareabg(String buildareabg) {
        this.buildareabg = buildareabg;
    }
    
    
    /**
     * @return BuildImgbg
     */
    public String getBuildimgbg() {
        return buildimgbg;
    }

    /**
     * @param BuildImgbg
     */
    public void setBuildimgbg(String buildimgbg) {
        this.buildimgbg = buildimgbg;
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
     * @return BgWidth
     */
    public BigDecimal getBgwidth() {
        return bgwidth;
    }

    /**
     * @param bgwidth
     */
    public void setBgwidth(BigDecimal bgwidth) {
        this.bgwidth = bgwidth;
    }

    /**
     * @return BgHeight
     */
    public BigDecimal getBgheight() {
        return bgheight;
    }

    /**
     * @param bgheight
     */
    public void setBgheight(BigDecimal bgheight) {
        this.bgheight = bgheight;
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
     * @return Lastupdate
     */
    public Date getLastupdate() {
        return lastupdate;
    }

    /**
     * @param lastupdate
     */
    public void setLastupdate(Date lastupdate) {
        this.lastupdate = lastupdate;
    }

    /**
     * @return BuildID
     */
    public Long getBuildid() {
        return buildid;
    }

    /**
     * @param buildid
     */
    public void setBuildid(Long buildid) {
        this.buildid = buildid;
    }
}