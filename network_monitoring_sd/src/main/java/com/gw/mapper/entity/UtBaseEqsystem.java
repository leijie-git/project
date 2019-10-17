package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Base_EqSystem")
public class UtBaseEqsystem implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "EqSystemCode")
    private Integer eqsystemcode;

    @Column(name = "EqSystemName")
    private String eqsystemname;

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
     * @return EqSystemCode
     */
    public Integer getEqsystemcode() {
        return eqsystemcode;
    }

    /**
     * @param eqsystemcode
     */
    public void setEqsystemcode(Integer eqsystemcode) {
        this.eqsystemcode = eqsystemcode;
    }

    /**
     * @return EqSystemName
     */
    public String getEqsystemname() {
        return eqsystemname;
    }

    /**
     * @param eqsystemname
     */
    public void setEqsystemname(String eqsystemname) {
        this.eqsystemname = eqsystemname;
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