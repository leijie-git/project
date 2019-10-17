package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_LZ_MonthInspectDetial")
public class UtLzMonthinspectdetial implements Serializable {
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

    @Column(name = "CheckResult")
    private Integer checkresult;

    @Column(name = "CheckDesc")
    private String checkdesc;

    @Column(name = "ID")
    private Long id;

    @Column(name = "MonthInspectID")
    private Long monthinspectid;

    private static final long serialVersionUID = 1L;

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

    /**
     * @return CheckResult
     */
    public Integer getCheckresult() {
        return checkresult;
    }

    /**
     * @param checkresult
     */
    public void setCheckresult(Integer checkresult) {
        this.checkresult = checkresult;
    }

    /**
     * @return CheckDesc
     */
    public String getCheckdesc() {
        return checkdesc;
    }

    /**
     * @param checkdesc
     */
    public void setCheckdesc(String checkdesc) {
        this.checkdesc = checkdesc;
    }

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
     * @return MonthInspectID
     */
    public Long getMonthinspectid() {
        return monthinspectid;
    }

    /**
     * @param monthinspectid
     */
    public void setMonthinspectid(Long monthinspectid) {
        this.monthinspectid = monthinspectid;
    }
}