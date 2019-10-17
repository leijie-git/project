package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "ut_rpc_defination")
public class UtRpcDefination implements Serializable {
    @Id
    private Long id;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "push_type")
    private Integer pushType;

    @Column(name = "data_type")
    private Integer dataType;

    @Column(name = "push_url")
    private String pushUrl;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
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
     * @return push_type
     */
    public Integer getPushType() {
        return pushType;
    }

    /**
     * @param pushType
     */
    public void setPushType(Integer pushType) {
        this.pushType = pushType;
    }

    /**
     * @return data_type
     */
    public Integer getDataType() {
        return dataType;
    }

    /**
     * @param dataType
     */
    public void setDataType(Integer dataType) {
        this.dataType = dataType;
    }

    /**
     * @return push_url
     */
    public String getPushUrl() {
        return pushUrl;
    }

    /**
     * @param pushUrl
     */
    public void setPushUrl(String pushUrl) {
        this.pushUrl = pushUrl;
    }
}