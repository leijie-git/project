package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_devicestatus_stat")
public class UtDevicestatusStat implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "unit_id")
    private Long unitId;

    private Integer online;

    private Integer outline;

    @Column(name = "create_date")
    private Date createDate;

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
     * @return unit_id
     */
    public Long getUnitId() {
        return unitId;
    }

    /**
     * @param unitId
     */
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    /**
     * @return online
     */
    public Integer getOnline() {
        return online;
    }

    /**
     * @param online
     */
    public void setOnline(Integer online) {
        this.online = online;
    }

    /**
     * @return outline
     */
    public Integer getOutline() {
        return outline;
    }

    /**
     * @param outline
     */
    public void setOutline(Integer outline) {
        this.outline = outline;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * @param createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * @return remark
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