package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Unit_XFZEquipment")
public class UtUnitXfzequipment implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "EquipmentName")
    private String equipmentname;

    @Column(name = "EquipmentCount")
    private Integer equipmentcount;

    @Column(name = "Unit")
    private String unit;

    @Column(name = "UnitID")
    private Long unitid;

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
     * @return EquipmentName
     */
    public String getEquipmentname() {
        return equipmentname;
    }

    /**
     * @param equipmentname
     */
    public void setEquipmentname(String equipmentname) {
        this.equipmentname = equipmentname;
    }

    /**
     * @return EquipmentCount
     */
    public Integer getEquipmentcount() {
        return equipmentcount;
    }

    /**
     * @param equipmentcount
     */
    public void setEquipmentcount(Integer equipmentcount) {
        this.equipmentcount = equipmentcount;
    }

    /**
     * @return Unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return UnitID
     */
    public Long getUnitid() {
        return unitid;
    }

    /**
     * @param unitid
     */
    public void setUnitid(Long unitid) {
        this.unitid = unitid;
    }
}