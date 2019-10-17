package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Maintenance_ReUnit")
public class UtMaintenanceReunit implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ManageID")
    private Long manageid;

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
     * @return ManageID
     */
    public Long getManageid() {
        return manageid;
    }

    /**
     * @param manageid
     */
    public void setManageid(Long manageid) {
        this.manageid = manageid;
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