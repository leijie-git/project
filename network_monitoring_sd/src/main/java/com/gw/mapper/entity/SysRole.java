package com.gw.mapper.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Data
@Table(name = "SYS_ROLE")
public class SysRole implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "RoleName")
    private String rolename;

    @Column(name = "RoleType")
    private Integer roletype;

    @Column(name = "SortIndex")
    private Integer sortindex;

    @Column(name = "AddDate")
    private Date adddate;

    @Column(name = "UpdateDate")
    private Date updatedate;

    @Column(name = "IsAdmin")
    private Boolean isadmin;

    @Column(name = "TYPE")
    private String type;
    private static final long serialVersionUID = 1L;


}