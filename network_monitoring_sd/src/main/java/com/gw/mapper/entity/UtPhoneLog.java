package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_phone_log")
public class UtPhoneLog implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "unit_id")
    private Long unitId;

    private Long sender;

    private String receiver;

    private String phone;

    @Column(name = "send_date")
    private Date sendDate;
    
    private String status;
    
    @Column(name = "long_time")
    private Integer longTime;

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
     * @return unit_id
     */
    public Long getUnitId() {
        return unitId;
    }

    /**
     * @param unitId
     */
    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }

    /**
     * @return sender
     */
    public Long getSender() {
        return sender;
    }

    /**
     * @param sender
     */
    public void setSender(Long sender) {
        this.sender = sender;
    }

    /**
     * @return receiver
     */
    public String getReceiver() {
        return receiver;
    }

    /**
     * @param receiver
     */
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return send_date
     */
    public Date getSendDate() {
        return sendDate;
    }

    /**
     * @param sendDate
     */
    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getLongTime() {
		return longTime;
	}

	public void setLongTime(int longTime) {
		this.longTime = longTime;
	}
    
}