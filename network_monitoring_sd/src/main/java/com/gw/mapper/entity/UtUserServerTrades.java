package com.gw.mapper.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "UT_User_server_trades")
public class UtUserServerTrades implements Serializable {
    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "trade_time")
    private Date tradeTime;

    @Column(name = "trade_user")
    private String tradeUser;

    @Column(name = "trade_amount")
    private String tradeAmount;

    private String str1;

    private String str2;

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
     * @return trade_time
     */
    public Date getTradeTime() {
        return tradeTime;
    }

    /**
     * @param tradeTime
     */
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    /**
     * @return trade_user
     */
    public String getTradeUser() {
        return tradeUser;
    }

    /**
     * @param tradeUser
     */
    public void setTradeUser(String tradeUser) {
        this.tradeUser = tradeUser;
    }

    /**
     * @return trade_amount
     */
    public String getTradeAmount() {
        return tradeAmount;
    }

    /**
     * @param tradeAmount
     */
    public void setTradeAmount(String tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    /**
     * @return str1
     */
    public String getStr1() {
        return str1;
    }

    /**
     * @param str1
     */
    public void setStr1(String str1) {
        this.str1 = str1;
    }

    /**
     * @return str2
     */
    public String getStr2() {
        return str2;
    }

    /**
     * @param str2
     */
    public void setStr2(String str2) {
        this.str2 = str2;
    }
}