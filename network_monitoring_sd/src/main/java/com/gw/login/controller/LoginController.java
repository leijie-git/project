package com.gw.login.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.gw.token.TokenBean;
import com.gw.util.JwtUtil;
import com.gw.util.Token;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.login.service.LoginService;
import com.gw.login.service.data.GetLoginInData;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.myAnnotation.PassToken;
import com.gw.util.UtilConst;
import com.gw.util.UtilMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/console")
public class LoginController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    @Resource
    private TokenBean token;

    //	@Autowired
//	private GenerateReport generateReport;
    @PassToken
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Json login(HttpServletRequest request, GetLoginInData inData) {
        Json json = new Json();

        try {
            GetSessionInfoOutData login = loginService.login(request, inData);
            json.setObj(login);
            if (login != null) {
              //获取当前时间的毫秒值
                long start = System.currentTimeMillis();
                //token的创建
                //设定token过期时间
                Long time = Integer.toUnsignedLong(  20*60 * 1000);
                Map<String, String> mapToken = new HashMap();
                mapToken.put("id", login.getId().toString());
                mapToken.put("Account", login.getAccount());
                mapToken.put("UnitID", login.getUnitId());
                mapToken.put("time",Long.toString(System.currentTimeMillis()));
                JSONObject jsonToken = JSONObject.fromObject(mapToken);

                // 如果登录成功创建一个token
                String tokenString = JwtUtil.createJWT(login.getId().toString(), login.getUnitId(), jsonToken.toString(), time);
                login.setToken(tokenString);
                json.setSuccess(true);
            }


        } catch (ServiceException e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return json;
    }
//	@PassToken
//	@ResponseBody
//	@RequestMapping(value = "/test")
//	public Json test(HttpServletRequest request) {
//		Json json = new Json();
//		try {
//			generateReport.generateReportDo();
//			json.setSuccess(true);
//		} catch (ServiceException e) {
//			json.setMsg(e.getMessage());
//			log.error(e.getMessage());
//		} catch (Exception e) {
//			log.error(e.getMessage());
//		}
//		return json;
//	}

    @PassToken
    @RequestMapping("/index")
    public String index(HttpServletRequest request) throws Exception {
        //获取作用域里面的值
        GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
        if ((sessionInfo != null) && (sessionInfo.getAccount() != null)) {
            return "home";
        }

        return "login/login";
    }

    /**
     * 退出
     *
     * @param request
     * @return
     */
    @PassToken
    @ResponseBody
    @RequestMapping("/logout")
    public Json logout(HttpServletRequest request) {
        Json json = new Json();
        HttpSession session = request.getSession();
        if (session != null) {
            // 注销session
            session.removeAttribute(UtilConst.USER_SESSION);
        }
        json.setSuccess(true);
        json.setMsg(UtilMessage.LOGOUT_SUCCESS);
        return json;
    }

    /**
     * 忘记密码跳转页面
     *
     * @param request
     * @return
     * @throws Exception
     */
    @PassToken
    @RequestMapping("/resetPwd")
    public String resetPwd(HttpServletRequest request) throws Exception {
        return "resetPwd";
    }

    /**
     * 忘记密码
     *
     * @param account
     * @param request
     * @return
     * @throws Exception
     */
    @PassToken
    @ResponseBody
    @RequestMapping(value = "/resetPassWord")
    public Json resetPassWord(HttpServletRequest request, String account) throws Exception {
        Json json = new Json();
        try {
            loginService.resetPassWord(account);
            json.setSuccess(true);
            json.setMsg(UtilMessage.RESET_PASSWORD_SUCCESS);
        } catch (ServiceException e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return json;
    }

    /**
     * 修改密码
     *
     * @param passwordNew
     * @param request
     * @return
     */
    @RequestMapping("/updatePassword")
    @ResponseBody
    public Json updatePassword(String passwordNew, HttpServletRequest request) {
        Json json = new Json();
        GetSessionInfoOutData userSession = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
        try {
            loginService.updatePassword(userSession.getId(), passwordNew, request);
            json.setSuccess(true);
            json.setMsg(UtilMessage.UPDATE_PWD_SUCCESS);
        } catch (ServiceException e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return json;
    }

}
