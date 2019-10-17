package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_calibration")
public class UtUnitCalibration implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "calibration_type")
    private String calibrationType;

    @Column(name = "start_date")
    private Date startDate;

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
     * @return calibration_type
     */
    public String getCalibrationType() {
        return calibrationType;
    }

    /**
     * @param calibrationType
     */
    public void setCalibrationType(String calibrationType) {
        this.calibrationType = calibrationType;
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