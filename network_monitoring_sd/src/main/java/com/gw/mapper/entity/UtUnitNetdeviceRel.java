package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "Ut_unit_netDevice_rel")
public class UtUnitNetdeviceRel implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "NetDeviceID")
    private Long netdeviceid;

    private String partcode;

    private String adress;

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
     * @return NetDeviceID
     */
    public Long getNetdeviceid() {
        return netdeviceid;
    }

    /**
     * @param netdeviceid
     */
    public void setNetdeviceid(Long netdeviceid) {
        this.netdeviceid = netdeviceid;
    }

    /**
     * @return partcode
     */
    public String getPartcode() {
        return partcode;
    }

    /**
     * @param partcode
     */
    public void setPartcode(String partcode) {
        this.partcode = partcode;
    }

    /**
     * @return adress
     */
    public String getAdress() {
        return adress;
    }

    /**
     * @param adress
     */
    public void setAdress(String adress) {
        this.adress = adress;
    }
}