package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_BaseInfo_Relation")
public class UtUnitBaseinfoRelation implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SoureAddress")
    private String soureaddress;

    @Column(name = "IsGB")
    private Integer isgb;

    @Column(name = "Lastupdate")
    private Date lastupdate;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "SystemID")
    private Long systemid;

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
     * @return SoureAddress
     */
    public String getSoureaddress() {
        return soureaddress;
    }

    /**
     * @param soureaddress
     */
    public void setSoureaddress(String soureaddress) {
        this.soureaddress = soureaddress;
    }

    /**
     * @return IsGB
     */
    public Integer getIsgb() {
        return isgb;
    }

    /**
     * @param isgb
     */
    public void setIsgb(Integer isgb) {
        this.isgb = isgb;
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
     * @return SystemID
     */
    public Long getSystemid() {
        return systemid;
    }

    /**
     * @param systemid
     */
    public void setSystemid(Long systemid) {
        this.systemid = systemid;
    }
}