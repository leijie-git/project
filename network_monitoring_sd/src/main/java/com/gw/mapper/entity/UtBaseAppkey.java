package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Base_AppKey")
public class UtBaseAppkey implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "AppID")
    private String appid;

    @Column(name = "AppKey")
    private String appkey;

    @Column(name = "IP")
    private String ip;

    @Column(name = "UnitName")
    private String unitname;

    @Column(name = "IsDelete")
    private Integer isdelete;

    @Column(name = "Validedate")
    private Date validedate;

    @Column(name = "CreateDate")
    private Date createdate;

    @Column(name = "CreateUser")
    private Long createuser;

    @Column(name = "Lastupdate")
    private Date lastupdate;

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
     * @return AppID
     */
    public String getAppid() {
        return appid;
    }

    /**
     * @param appid
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * @return AppKey
     */
    public String getAppkey() {
        return appkey;
    }

    /**
     * @param appkey
     */
    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    /**
     * @return IP
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return UnitName
     */
    public String getUnitname() {
        return unitname;
    }

    /**
     * @param unitname
     */
    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    /**
     * @return IsDelete
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * @param isdelete
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * @return Validedate
     */
    public Date getValidedate() {
        return validedate;
    }

    /**
     * @param validedate
     */
    public void setValidedate(Date validedate) {
        this.validedate = validedate;
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
}