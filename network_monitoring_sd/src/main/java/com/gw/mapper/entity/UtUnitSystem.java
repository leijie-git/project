package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_System")
public class UtUnitSystem implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SystemName")
    private String systemname;

    @Column(name = "SystemSite")
    private String systemsite;

    @Column(name = "Brand")
    private String brand;

    @Column(name = "MakeFrom")
    private String makefrom;

    @Column(name = "Manager")
    private String manager;

    @Column(name = "Tel")
    private String tel;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "OrderIndex")
    private Integer orderindex;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateUser")
    private Long createuser;

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
     * @return SystemName
     */
    public String getSystemname() {
        return systemname;
    }

    /**
     * @param systemname
     */
    public void setSystemname(String systemname) {
        this.systemname = systemname;
    }

    /**
     * @return SystemSite
     */
    public String getSystemsite() {
        return systemsite;
    }

    /**
     * @param systemsite
     */
    public void setSystemsite(String systemsite) {
        this.systemsite = systemsite;
    }

    /**
     * @return Brand
     */
    public String getBrand() {
        return brand;
    }

    /**
     * @param brand
     */
    public void setBrand(String brand) {
        this.brand = brand;
    }

    /**
     * @return MakeFrom
     */
    public String getMakefrom() {
        return makefrom;
    }

    /**
     * @param makefrom
     */
    public void setMakefrom(String makefrom) {
        this.makefrom = makefrom;
    }

    /**
     * @return Manager
     */
    public String getManager() {
        return manager;
    }

    /**
     * @param manager
     */
    public void setManager(String manager) {
        this.manager = manager;
    }

    /**
     * @return Tel
     */
    public String getTel() {
        return tel;
    }

    /**
     * @param tel
     */
    public void setTel(String tel) {
        this.tel = tel;
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
     * @return OrderIndex
     */
    public Integer getOrderindex() {
        return orderindex;
    }

    /**
     * @param orderindex
     */
    public void setOrderindex(Integer orderindex) {
        this.orderindex = orderindex;
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