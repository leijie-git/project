package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "UT_Unit_lookup_log")
public class UtUnitLookupLog implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "unit_id")
    private Long unitId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "send_date")
    private Date sendDate;

    private String status;

    @Column(name = "look_status")
    private String lookStatus;

    @Column(name = "receive_date")
    private Date receiveDate;

    @Column(name = "recerve_user")
    private String recerveUser;

    @Column(name = "user_name")
    private String userName;

    private String remark;

    private static final long serialVersionUID = 1L;

}