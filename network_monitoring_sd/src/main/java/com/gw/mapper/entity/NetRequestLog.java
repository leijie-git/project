package com.gw.mapper.entity;

import java.util.Date;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Table(name = "net_request_log")
public class NetRequestLog {

    @Id
    private Long reqId;

    /**
     * 请求方主机ip
     */
    @Column(name = "req_host")
    private String reqHost;

    /**
     * 请求url
     */
    @Column(name = "req_url")
    private String reqUrl;

    /**
     * 请求方式
     */
    @Column(name = "req_method")
    private String reqMethod;

    /**
     * 请求体
     */
    @Column(name = "req_body")
    private String reqBody;

    /**
     * http状态码
     */
    @Column(name = "res_status")
    private String resStatus;

    /**
     * 响应体
     */
    @Column(name = "res_body")
    private String resBody;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;
}