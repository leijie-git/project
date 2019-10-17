package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_message_log")
@Data
public class UtMessageLog implements Serializable {
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

    @Column(name = "message_type")
    private String messageType;

    private String content;

    private static final long serialVersionUID = 1L;
}