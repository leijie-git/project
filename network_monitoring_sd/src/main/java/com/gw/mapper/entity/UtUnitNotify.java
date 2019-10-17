package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_notify")
public class UtUnitNotify implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    private Long sender;

    private String title;

    private String content;

    @Column(name = "send_date")
    private Date sendDate;

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
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
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

}