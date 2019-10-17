package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Base_SiteClassDetialBase")
public class UtBaseSiteclassdetialbase implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CheckInfo")
    private String checkinfo;

    @Column(name = "CheckMethod")
    private String checkmethod;

    @Column(name = "SiteClassID")
    private Long siteclassid;

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
     * @return CheckMethod
     */
    public String getCheckmethod() {
        return checkmethod;
    }

    /**
     * @param checkmethod
     */
    public void setCheckmethod(String checkmethod) {
        this.checkmethod = checkmethod;
    }

    /**
     * @return SiteClassID
     */
    public Long getSiteclassid() {
        return siteclassid;
    }

    /**
     * @param siteclassid
     */
    public void setSiteclassid(Long siteclassid) {
        this.siteclassid = siteclassid;
    }
}