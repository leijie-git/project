package com.gw.mapper.entity;

import java.io.Serializable;
import javax.persistence.*;

@Table(name = "UT_Base_Server")
public class UtBaseServer implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "ServerName")
    private String servername;

    @Column(name = "ServerAddress")
    private String serveraddress;

    @Column(name = "OrderIndex")
    private Integer orderindex;

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
     * @return ServerName
     */
    public String getServername() {
        return servername;
    }

    /**
     * @param servername
     */
    public void setServername(String servername) {
        this.servername = servername;
    }

    /**
     * @return ServerAddress
     */
    public String getServeraddress() {
        return serveraddress;
    }

    /**
     * @param serveraddress
     */
    public void setServeraddress(String serveraddress) {
        this.serveraddress = serveraddress;
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
}