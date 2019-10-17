package com.gw.util;

import com.alibaba.fastjson.JSON;
import io.jsonwebtoken.Claims;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @开发人 Jie.Lei
 * @创建时间 2019/7/16
 * @描述
 */
public class Test {



    public Map<String, String> getToken(String authorize) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        String token = authorize.substring("bearer".length() + 1);
       Claims claims = JwtUtil.parseJWT(token);

        System.out.println(claims.getSubject());
        Map<String, String> headers = new HashMap<String, String>() {

            {
                putIfAbsent("Authorization", token);
            }
        };
        map = (Map<String, String>) JSON.parse(claims.getSubject());

        return map;
    }

    public static void main(String[] args) throws  Exception {

       String authorize="Bearer eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0NTYiLCJzdWIiOiJ7XCJBY2NvdW50XCI6XCJhZG1pblwiLFwiVW5pdElEXCI6bnVsbCxcImlkXCI6XCIxXCIsXCJ0aW1lXCI6XCIyMDE5LTA4LTAxIDEyOjE0OjMyXCJ9IiwidXNlcl9uYW1lIjoiYWRtaW4iLCJuaWNrX25hbWUiOiJYLXJhcGlkbyIsImV4cCI6MTU2NDYzMjkwMCwiaWF0IjoxNTY0NjI1NzAwLCJqdGkiOiIxIn0.iG74AqtDQaK94t3RtGpdLFf5gC0R5BHu2L92shF2u6Y";
//
//
//
//        long id=0;
//        String Account=null;
//        String UnitID=null;
////
        //解析token
        Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);


        System.out.println(tokenToMap.get("time"));

//        long t1=System.currentTimeMillis();
//        System.out.println(t1);
//        Date date2=new Date();
//        date2.setTime(t1);
//        System.err.println(date2);
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String fmDate=simpleDateFormat.format(date2);
//        System.err.println(fmDate);

}}
