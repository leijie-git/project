package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Base_MonthInspectDetial")
public class UtBaseMonthinspectdetial implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "CheckInfo")
    private String checkinfo;

    @Column(name = "OrderIndex")
    private Integer orderindex;

    @Column(name = "ResultA")
    private String resulta;

    @Column(name = "ResultB")
    private String resultb;

    @Column(name = "ResultC")
    private String resultc;

    @Column(name = "ResultD")
    private String resultd;

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
     * @return OrderIndex
     */
    public Integer getOrderindex() {
        return orderindex;
    }

    /**
     * @param orderindex
     */
    public void setOrderindex(Integer orderindex) {
        this.orderindex = orderindex;
    }

    /**
     * @return ResultA
     */
    public String getResulta() {
        return resulta;
    }

    /**
     * @param resulta
     */
    public void setResulta(String resulta) {
        this.resulta = resulta;
    }

    /**
     * @return ResultB
     */
    public String getResultb() {
        return resultb;
    }

    /**
     * @param resultb
     */
    public void setResultb(String resultb) {
        this.resultb = resultb;
    }

    /**
     * @return ResultC
     */
    public String getResultc() {
        return resultc;
    }

    /**
     * @param resultc
     */
    public void setResultc(String resultc) {
        this.resultc = resultc;
    }

    /**
     * @return ResultD
     */
    public String getResultd() {
        return resultd;
    }

    /**
     * @param resultd
     */
    public void setResultd(String resultd) {
        this.resultd = resultd;
    }
}