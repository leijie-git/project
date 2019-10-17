package com.gw.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class JwtUtil {

    private static final String KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";

    /**
     * 由字符串生成加密key
     *
     * @return
     */
    public static SecretKey generalKey() {
        // 本地的密码解码
//        byte[] encodedKey = Base64.decodeBase64(stringKey);
        byte[] encodedKey = KEY.getBytes();

        // 根据给定的字节数组使用AES加密算法构造一个密钥
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "HS256");

        return key;
    }

    /**
     * 创建jwt
     *
     * @param id
     * @param issuer
     * @param subject
     * @param ttlMillis
     * @return
     * @throws Exception
     */
    public static String createJWT(String id, String issuer, String subject, long ttlMillis) throws Exception {

        // 指定签名的时候使用的签名算法，也就是header那部分，jjwt已经将这部分内容封装好了。
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成JWT的时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 创建payload的私有声明（根据特定的业务需要添加，如果要拿这个做验证，一般是需要和jwt的接收方提前沟通好验证方式的）
        Map<String, Object> claims = new HashMap<>();
        claims.put("uid", "123456");
        claims.put("user_name", "admin");
        claims.put("nick_name", "X-rapido");

        // 生成签名的时候使用的秘钥secret，切记这个秘钥不能外露哦。它就是你服务端的私钥，在任何场景都不应该流露出去。
        // 一旦客户端得知这个secret, 那就意味着客户端是可以自我签发jwt了。
        SecretKey key = generalKey();

        // 下面就是在为payload添加各种标准声明和私有声明了
        JwtBuilder builder = Jwts.builder() // 这里其实就是new一个JwtBuilder，设置jwt的body
                .setClaims(claims)          // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                .setId(id)                  // 设置jti(JWT ID)：是JWT的唯一标识，根据业务需要，这个可以设置为一个不重复的值，主要用来作为一次性token,从而回避重放攻击。
                .setIssuedAt(now)           // iat: jwt的签发时间
                .setIssuer(issuer)          // issuer：jwt签发人
                .setSubject(subject)        // sub(Subject)：代表这个JWT的主体，即它的所有人，这个是一个json格式的字符串，可以存放什么userid，roldid之类的，作为什么用户的唯一标志。
                .signWith(signatureAlgorithm, key); // 设置签名使用的签名算法和签名使用的秘钥

        // 设置过期时间
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }
        return builder.compact();
    }

    /**
     * 解密jwt
     *
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception {
        SecretKey key = generalKey();  //签名秘钥，和生成的签名的秘钥一模一样
        try{
            Claims claims = Jwts.parser()  //得到DefaultJwtParser
                    .setSigningKey(key)                 //设置签名的秘钥
                    .parseClaimsJws(jwt).getBody();
            return claims;
        }catch (ServiceException e){
            throw new ServiceException("不能解析");
        }


    }

    /**
     * 校验token是否合法
     */
    public static boolean checkToken(String jwt) {
        try {
            return parseJWT(jwt) == null ? false : true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 校验token是否合法
     */
    public static boolean checkToken(Claims claims) {
        return claims == null ? false : true;
    }

    public static String getTokenUserInfo(String queryParam) {
        HttpServletRequest request = getHttpServletRequest();
        String authorize = request.getHeader("Token");
        String token = authorize.substring("bearer".length() + 1);
        Claims claims = null;
        try {
            claims = parseJWT(token);

            Map<String, String> map = new HashMap<>();
            Set<Map.Entry<String, Object>> entrySet = claims.entrySet();
            Iterator it = entrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                String str = entry.getKey().toString();
                String str2 = entry.getValue().toString();
                map.put(str, str2);
            }

            return map.get(queryParam);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static HttpServletRequest getHttpServletRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }


    //解析token成具体 的map
    public static Map<String, String> getTokenToMap(String authorize) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            String token = authorize.substring("bearer".length() + 1);
            Claims claims = JwtUtil.parseJWT(token);
            Map<String, String> headers = new HashMap<String, String>() {
                {
                    putIfAbsent("Authorization", token);
                }
            };
            map = (Map<String, String>) JSON.parse(claims.getSubject());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }





    public static void main(String[] args) {
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJ1aWQiOiIxMjM0NTYiLCJzdWIiOiJ7XCJ1bml0SWRcIjpcIjFcIixcInVzZXJJZFwiOlwiMVwifSIsInVzZXJfbmFtZSI6ImFkbWluIiwibmlja19uYW1lIjoiWC1yYXBpZG8iLCJpc3MiOiIxIiwiZXhwIjoxNTYyMDM0Mjc5LCJpYXQiOjE1NjE5NDc4NzksImp0aSI6IjEifQ.pk-2zGophAnhs_NaTpUSIIvBWDMFn1yTGxDk0WJm6uM";

        try {
            Claims claims = parseJWT(token);
            //
           /* Map<String,String> map = new HashMap<>();
            Set<Map.Entry<String, Object>> entrySet = claims.entrySet();
            Iterator it = entrySet.iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                entry.ge
                String str =  entry.getKey().toString();
                System.out.println(str);
                String str2 =  entry.getValue().toString();
                map.put(str,str2);


            }
            System.out.println(map);*/

        } catch (Exception e) {
            e.printStackTrace();
        }

    }





}