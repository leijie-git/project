package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "SYS_USER")
public class SysUser implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "User_Name")
    private String userName;

    @Column(name = "Account")
    private String account;

    @Column(name = "Password")
    private String password;

    @Column(name = "USER_HEADER")
    private String userHeader;

    @Column(name = "CREATE_DATE")
    private Date createDate;

    @Column(name = "CREATE_USER")
    private String createUser;

    @Column(name = "UPDATE_DATE")
    private Date updateDate;

    @Column(name = "UPDATE_USER")
    private String updateUser;

    private String phone;

    @Column(name = "BIRTHDAY")
    private String birthday;

    @Column(name = "SEX")
    private String sex;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "ETHNIC_GROUP")
    private String ethnicGroup;

    @Column(name = "SIGN")
    private String sign;

    @Column(name = "REMARK")
    private String remark;

    private static final long serialVersionUID = 1L;


}