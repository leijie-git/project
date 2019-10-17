package com.gw.mapper.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @description: apk基础信息
 **/
@Data
@Table(name = "apk_info")
public class ApkInfo {

    @Id
    private Long id;

    /** apk文件名 */
    @Column(name = "apk_name")
    private String apkName;

    /** apk存储大小 */
    @Column(name = "apk_size")
    private Long apkSize;

    /** apk描述 */
    @Column(name = "apk_desc")
    private String apkDesc;

    /** apk下载地址 */
    @Column(name = "apk_url")
    private String apkUrl;

    /** 版本代号 */
    @Column(name = "ver_code")
    private Integer verCode;

    /** 版本号 */
    @Column(name = "ver_name")
    private String verName;

    /** 上传时间 */
    @JsonIgnore
    @Column(name = "create_date")
    private Date createDate;
}
