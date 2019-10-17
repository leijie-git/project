package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_user_duty_log")
public class UtUserDutyLog implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "shift_user")
    private Long shiftUser;

    @Column(name = "duty_user")
    private Long dutyUser;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

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
     * @return shift_user
     */
    public Long getShiftUser() {
        return shiftUser;
    }

    /**
     * @param shiftUser
     */
    public void setShiftUser(Long shiftUser) {
        this.shiftUser = shiftUser;
    }

    /**
     * @return duty_user
     */
    public Long getDutyUser() {
        return dutyUser;
    }

    /**
     * @param dutyUser
     */
    public void setDutyUser(Long dutyUser) {
        this.dutyUser = dutyUser;
    }

    /**
     * @return start_date
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return end_date
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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