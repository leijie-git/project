package com.gw.alarm;

/**
 * @description: 告警模块常量
 **/
public class AlarmConstants {

    //分隔符
    public static String SPLIT_FALG = ",";


    /*报警通知日志*/
    //通知成功
    public static String LOG_STATUS_SUCC = "1";
    //通知失败
    public static String LOG_STATUS_FAIL = "0";
    //短信通知
    public static String LOG_MSGTYPE_SMS = "1";
    //电话通知
    public static String LOG_MSGTYPE_PHE = "2";

    /**报警类型*/
    //火警
    public static String ALARM_TYPE_FIRE = "1";
    //故障
    public static String ALARM_TYPE_TROUBLE = "2";
    //启动
    public static String ALARM_TYPE_START = "3";
    //反馈
    public static String ALARM_TYPE_FEEDBACK = "4";
    //监管
    public static String ALARM_TYPE_SUPERVISE = "5";
    //屏蔽
    public static String ALARM_TYPE_SHIELD = "6";
    //复位
    public static String ALARM_TYPE_RST = "8";
    //其他
    public static String ALARM_TYPE_OTHER = "9";

}
