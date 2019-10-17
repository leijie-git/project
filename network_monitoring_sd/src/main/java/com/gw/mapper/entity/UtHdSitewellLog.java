package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_hd_sitewell_log")
public class UtHdSitewellLog implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "SiteWellID")
    private Long sitewellid;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "CREATEDATE")
    private Date createdate;

    @Column(name = "Remark")
    private String remark;

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
     * @return SiteWellID
     */
    public Long getSitewellid() {
        return sitewellid;
    }

    /**
     * @param sitewellid
     */
    public void setSitewellid(Long sitewellid) {
        this.sitewellid = sitewellid;
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
     * @return CREATEDATE
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
}