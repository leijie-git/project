package com.gw.interceptor;

import com.alibaba.fastjson.JSON;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.gw.front.login.data.FrontUnitUserOutData;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.myAnnotation.PassToken;
import com.gw.myAnnotation.UserLoginToken;
import com.gw.util.JwtUtil;
import com.gw.util.Util;
import com.gw.util.UtilConst;
import com.gw.util.UtilConv;
import io.jsonwebtoken.Claims;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SecurityInterceptor implements HandlerInterceptor {

    @Resource
    private UtUnitUserMapper utUnitUserMapper;

    @Value("${server_expire_time}")
    private String termOfValidity;// 服务器有效时间

    @Value("${server_name_white_list}")
    private String whiteList;

    private static final Logger log = LoggerFactory.getLogger(SecurityInterceptor.class);
    private final static String SESSION_KEY = "UserId";

    /**
     * 在调用controller具体方法前拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
        //获取请求的url
        String requestUri = request.getRequestURI();
        //获取请求路径
        String contextPath = request.getContextPath();
        String url = requestUri.substring(contextPath.length());
        //白名单校验(未配置白名单,则跳过)
        if (Util.isNotEmpty(whiteList) && !whiteList.contains(request.getHeader("host"))) {
            response.setStatus(403);
            request.setAttribute("msg", "Host非法, 服务器拒绝了您的请求!");
            return false;
        }

        if (url.indexOf("/error") > -1) {
            return false;
        }
        if (!(object instanceof HandlerMethod)) {
            return true;
        }
        //拦截token
        //拦截方法
        HandlerMethod handlerMethod = (HandlerMethod) object;
        Method method = handlerMethod.getMethod();
        // 检查是否有passtoken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        //如果超过服务器时间也拦截
        String date2Str = UtilConv.date2Str(new Date(), UtilConv.DATE_YYYY_MM_DD_CHN);
        if (Util.isNotEmpty(termOfValidity) && date2Str.compareTo(termOfValidity) > 0) {
            response.setStatus(499);
            return false;
        }
        Object sessionInfo = null;

		if (url.indexOf("/app/") > -1) {
			// 如果不是映射到方法直接通过
			String token = request.getHeader("token");// 从 http 请求头中取出 token
			// 检查有没有需要用户权限的注解
			if (method.isAnnotationPresent(UserLoginToken.class)) {
				UserLoginToken userLoginToken = method.getAnnotation(UserLoginToken.class);
				if (userLoginToken.required()) {
					// 执行认证
					if (token == null) {
						throw new RuntimeException("无token，请重新登录");
					}
					// 获取 token 中的 user id
					String userId;
					try {
						userId = JWT.decode(token).getAudience().get(0);
                        MDC.put(SESSION_KEY, "app-" + userId);
						log.debug("userID:" + userId);
					} catch (JWTDecodeException j) {
						throw new RuntimeException("401");
					}
					UtUnitUser user = utUnitUserMapper.selectByPrimaryKey(Long.valueOf(userId));
					if (user == null) {
						throw new RuntimeException("用户不存在，请重新登录");
					}
					// 验证 token
					JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
					try {
						jwtVerifier.verify(token);
					} catch (JWTVerificationException e) {
						throw new RuntimeException("401");
					}
					return true;
				}
			}
		}
		if (url.indexOf("/front/") > -1) {
			sessionInfo = request.getSession().getAttribute(UtilConst.FRONT_USER_SESSION);
		} else if (url.indexOf("/wechat/") > -1) {
			sessionInfo = request.getSession().getAttribute(UtilConst.WECHAT_USER_SESSION);
		} //拦截后端
        else if (request.getHeader("Authorization") != null) {
            //请求头获取token
            String authorize = request.getHeader("Authorization");
            if (!StringUtils.isEmpty(authorize)) {
                //解析token
                String token = authorize.substring("bearer".length() + 1);
                try {
                    Claims claims = JwtUtil.parseJWT(token);
                    if (JwtUtil.checkToken(claims)) {
                        Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                        Long time = Integer.toUnsignedLong(  20*60 * 1000);
                        //在创建一个tokekn
                        Map<String, String> mapToken = new HashMap();
                        long a = Long.parseLong(tokenToMap.get("time"));
                        long b = System.currentTimeMillis();
                        //token过期时间20分钟  没超过1分钟 如果有请求 刷新一次
                        if ((b-a) >  2*60 * 1000) {
                            //刷新 token  （从新创建一个token）
                            mapToken.put("id", tokenToMap.get("id"));
                            mapToken.put("Account", tokenToMap.get("Account"));
                            mapToken.put("UnitID", tokenToMap.get("UnitID"));
                            mapToken.put("time", Long.toString(System.currentTimeMillis()));
                            JSONObject jsonToken = JSONObject.fromObject(mapToken);
                            // 如果登录成功创建一个token
                            String tokenString = JwtUtil.createJWT(tokenToMap.get("id"), tokenToMap.get("Account"), jsonToken.toString(), time);
                            //将新的token发送给前台
                            response.setHeader("tokenString", tokenString);
                            return true;
                        }


                        return true;
                    }
                } catch (Exception e) {
                    //如果token  过期 直接调回登录页面
                    response.setStatus(5000);
                    return false;
                }
            }
            return true;
        }else {
            //如果token  过期 直接调回登录页面
            response.setStatus(5000);
        }
		// 判断是否有session
		if (sessionInfo != null) {
			// 内部访问不做拦截
			return true;
		} else {
			response.setStatus(4999);
			request.setAttribute("msg", "您还没有登录或登录已超时，请重新登录，然后再刷新本功能！");
			return false;
		}
	}

    /**
     * 完成页面的render后调用
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object object,
                                Exception exception) throws Exception {


    }

    /**
     * 在调用controller具体方法后拦截
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object object,
                           ModelAndView modelAndView) throws Exception {


    }


}
