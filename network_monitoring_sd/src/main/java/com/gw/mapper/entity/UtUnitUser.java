package com.gw.mapper.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_Unit_User")
@Data
public class UtUnitUser implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Account")
    private String account;

    @Column(name = "Password")
    private String password;

    @Column(name = "UserType")
    private Integer usertype;

    @Column(name = "UserRole")
    private Integer userrole;

    @Column(name = "Sex")
    private Integer sex;

    @Column(name = "Birthday")
    private Date birthday;

    @Column(name = "Email")
    private String email;

    @Column(name = "MobilePhone")
    private String mobilephone;

    @Column(name = "Department")
    private String department;

    @Column(name = "Post")
    private String post;

    @Column(name = "IsDelete")
    private Integer isdelete;

    @Column(name = "AddDate")
    private Date adddate;

    @Column(name = "AddUserID")
    private String adduserid;

    @Column(name = "UserID")
    private Long userid;

    @Column(name = "OpenID")
    private String openid;

    @Column(name = "CertificatesType")
    private String certificatestype;

    @Column(name = "CertificatesNO")
    private String certificatesno;

    @Column(name = "Photo")
    private String photo;

    @Column(name = "UnitID")
    private Long unitid;

    @Column(name = "CertificatesPic")
    private String certificatespic;

    @Column(name = "CertificatesDate")
    private Date certificatesdate;

    @Column(name = "LicenseNO")
    private String licenseno;

    @Column(name = "isPushFault")
    private String ispushfault;

    @Column(name = "channelId")
    private String channelid;

    @Column(name = "ExpiryTime")
    private Date expirytime;

    @Column(name = "ReceiveAlarmType")
    private String receivealarmtype;

    @Column(name = "phone_name")
    private String phoneName;

    //增加字段
    //CREATE_DATE   创建时间
    @Column(name = "createDate")
    private Date createDate;

       //用户头像（关联file id）
    @Column(name = "userHeader")
    private String  userHeader;

    //ADDRESS 居住地址
    @Column(name = "address")
    private String address;

    //ETHNIC_GROUP 名族
    @Column(name = "ethnicGroup")
    private String ethnicGroup;

    //SIGN 签名
    @Column(name = "sign")
    private String sign;

    //REMARK 备注
    @Column(name = "remark")
    private String remark;

    //CREATE_USER 创建人
    @Column(name = "createUser")
    private String createUser;

    //UPDATE_USER 修改人
    @Column(name = "updateUser")
    private String updateUser;

    //UPDATE_DATE 修改时间
    @Column(name = "updateDate")
    private Date updateDate;

    //是否是SysUser表的数据
    @Column(name = "isSysUser")
    private String isSysUser;
    private static final long serialVersionUID = 1L;

}