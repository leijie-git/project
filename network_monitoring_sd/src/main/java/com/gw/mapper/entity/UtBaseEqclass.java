package com.gw.mapper.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "UT_Base_EqClass")
public class UtBaseEqclass implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ClassCode")
    private Integer classcode;

    @Column(name = "ClassName")
    private String classname;

    @Column(name = "ClassType")
    private Integer classtype;

    @Column(name = "AreaCode")
    private String areacode;

    @Column(name = "ClassImg")
    private String classimg;

    @Column(name = "ImgWidth")
    private BigDecimal imgwidth;

    @Column(name = "ImgHeight")
    private BigDecimal imgheight;

    @Column(name = "MonitorNum")
    private Integer monitornum;

    @Column(name = "Status")
    private Integer status;

    @Column(name = "Remark")
    private String remark;

    @Column(name = "IsDelete")
    private Integer isdelete;

    @Column(name = "SortOrder")
    private Integer sortorder;

    @Column(name = "Cycle")
    private Integer cycle;

    @Column(name = "CycleUnit")
    private Integer cycleunit;

    @Column(name = "Frequency")
    private Integer frequency;

    @Column(name = "CycleInfo")
    private String cycleinfo;

    @Column(name = "MinCycleInfo")
    private String mincycleinfo;

    @Column(name = "InspectCycle")
    private Integer inspectcycle;

    @Column(name = "InspectCycleUnit")
    private Integer inspectcycleunit;

    @Column(name = "InspectFrequency")
    private Integer inspectfrequency;

    @Column(name = "EqSystemID")
    private Long eqsystemid;

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
     * @return ClassCode
     */
    public Integer getClasscode() {
        return classcode;
    }

    /**
     * @param classcode
     */
    public void setClasscode(Integer classcode) {
        this.classcode = classcode;
    }

    /**
     * @return ClassName
     */
    public String getClassname() {
        return classname;
    }

    /**
     * @param classname
     */
    public void setClassname(String classname) {
        this.classname = classname;
    }

    /**
     * @return ClassType
     */
    public Integer getClasstype() {
        return classtype;
    }

    /**
     * @param classtype
     */
    public void setClasstype(Integer classtype) {
        this.classtype = classtype;
    }

    /**
     * @return AreaCode
     */
    public String getAreacode() {
        return areacode;
    }

    /**
     * @param areacode
     */
    public void setAreacode(String areacode) {
        this.areacode = areacode;
    }

    /**
     * @return ClassImg
     */
    public String getClassimg() {
        return classimg;
    }

    /**
     * @param classimg
     */
    public void setClassimg(String classimg) {
        this.classimg = classimg;
    }

    /**
     * @return ImgWidth
     */
    public BigDecimal getImgwidth() {
        return imgwidth;
    }

    /**
     * @param imgwidth
     */
    public void setImgwidth(BigDecimal imgwidth) {
        this.imgwidth = imgwidth;
    }

    /**
     * @return ImgHeight
     */
    public BigDecimal getImgheight() {
        return imgheight;
    }

    /**
     * @param imgheight
     */
    public void setImgheight(BigDecimal imgheight) {
        this.imgheight = imgheight;
    }

    /**
     * @return MonitorNum
     */
    public Integer getMonitornum() {
        return monitornum;
    }

    /**
     * @param monitornum
     */
    public void setMonitornum(Integer monitornum) {
        this.monitornum = monitornum;
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

    /**
     * @return IsDelete
     */
    public Integer getIsdelete() {
        return isdelete;
    }

    /**
     * @param isdelete
     */
    public void setIsdelete(Integer isdelete) {
        this.isdelete = isdelete;
    }

    /**
     * @return SortOrder
     */
    public Integer getSortorder() {
        return sortorder;
    }

    /**
     * @param sortorder
     */
    public void setSortorder(Integer sortorder) {
        this.sortorder = sortorder;
    }

    /**
     * @return Cycle
     */
    public Integer getCycle() {
        return cycle;
    }

    /**
     * @param cycle
     */
    public void setCycle(Integer cycle) {
        this.cycle = cycle;
    }

    /**
     * @return CycleUnit
     */
    public Integer getCycleunit() {
        return cycleunit;
    }

    /**
     * @param cycleunit
     */
    public void setCycleunit(Integer cycleunit) {
        this.cycleunit = cycleunit;
    }

    /**
     * @return Frequency
     */
    public Integer getFrequency() {
        return frequency;
    }

    /**
     * @param frequency
     */
    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    /**
     * @return CycleInfo
     */
    public String getCycleinfo() {
        return cycleinfo;
    }

    /**
     * @param cycleinfo
     */
    public void setCycleinfo(String cycleinfo) {
        this.cycleinfo = cycleinfo;
    }

    /**
     * @return MinCycleInfo
     */
    public String getMincycleinfo() {
        return mincycleinfo;
    }

    /**
     * @param mincycleinfo
     */
    public void setMincycleinfo(String mincycleinfo) {
        this.mincycleinfo = mincycleinfo;
    }

    /**
     * @return InspectCycle
     */
    public Integer getInspectcycle() {
        return inspectcycle;
    }

    /**
     * @param inspectcycle
     */
    public void setInspectcycle(Integer inspectcycle) {
        this.inspectcycle = inspectcycle;
    }

    /**
     * @return InspectCycleUnit
     */
    public Integer getInspectcycleunit() {
        return inspectcycleunit;
    }

    /**
     * @param inspectcycleunit
     */
    public void setInspectcycleunit(Integer inspectcycleunit) {
        this.inspectcycleunit = inspectcycleunit;
    }

    /**
     * @return InspectFrequency
     */
    public Integer getInspectfrequency() {
        return inspectfrequency;
    }

    /**
     * @param inspectfrequency
     */
    public void setInspectfrequency(Integer inspectfrequency) {
        this.inspectfrequency = inspectfrequency;
    }

    /**
     * @return EqSystemID
     */
    public Long getEqsystemid() {
        return eqsystemid;
    }

    /**
     * @param eqsystemid
     */
    public void setEqsystemid(Long eqsystemid) {
        this.eqsystemid = eqsystemid;
    }
}