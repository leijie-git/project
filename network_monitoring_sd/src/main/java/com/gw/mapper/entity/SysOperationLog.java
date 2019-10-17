package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "sys_operation_log")
public class SysOperationLog implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    private String content;

    private String url;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "current_status")
    private String currentStatus;
    
    @Column(name = "record_type")
    private String recordType;
    
    @Column(name = "unit_id")
    private Long unitId;
    
    private String address;

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
     * @return user_id
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * @return user_id
     */
    public Long getUnitId() {
        return unitId;
    }

    /**
     * @param userId
     */
    public void setUnitId(Long unitId) {
        this.userId = unitId;
    }

    
    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
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
     * @return record_type
     */
    public String getRecordType() {
        return recordType;
    }

    /**
     * @param recordType
     */
    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
    
    /**
     * @return current_status
     */
    public String getCurrentStatus() {
        return currentStatus;
    }

    /**
     * @param currentStatus
     */
    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }

    /**
     * @return address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
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