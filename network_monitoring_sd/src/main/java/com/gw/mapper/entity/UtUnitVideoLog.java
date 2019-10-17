package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_Video_log")
public class UtUnitVideoLog implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "v_position")
    private String vPosition;

    private String path;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "build_area")
    private String buildArea;

    @Column(name = "create_user")
    private String createUser;

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
     * @return v_position
     */
    public String getvPosition() {
        return vPosition;
    }

    /**
     * @param vPosition
     */
    public void setvPosition(String vPosition) {
        this.vPosition = vPosition;
    }

    /**
     * @return path
     */
    public String getPath() {
        return path;
    }

    /**
     * @param path
     */
    public void setPath(String path) {
        this.path = path;
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
     * @return build_area
     */
    public String getBuildArea() {
        return buildArea;
    }

    /**
     * @param buildArea
     */
    public void setBuildArea(String buildArea) {
        this.buildArea = buildArea;
    }

    /**
     * @return create_user
     */
    public String getCreateUser() {
        return createUser;
    }

    /**
     * @param createUser
     */
    public void setCreateUser(String createUser) {
        this.createUser = createUser;
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