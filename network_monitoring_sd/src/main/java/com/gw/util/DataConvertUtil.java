package com.gw.util;

/**
 * @description: 数据格式转换util类
 **/
public class DataConvertUtil {

    public static Long parseLong(String longStr){
        return Util.isEmpty(longStr)? null : Long.parseLong(longStr);
    }

    public static Integer parseInt(String intStr) {
        return Util.isEmpty(intStr)? null : Integer.parseInt(intStr);
    }
}
