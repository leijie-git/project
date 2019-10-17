package com.gw.openApi.common.controller;

import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.myAnnotation.PassToken;
import com.gw.openApi.common.data.out.UserOut;
import com.gw.openApi.common.service.IUserApiService;
import com.gw.openApi.common.data.CheckAcountBaseData;
import com.gw.util.UtilConst;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags ="用户信息")
@RestController
@RequestMapping("/api/user")
public class UserApiController {

    @Autowired
    private IUserApiService userApiService;

    @ApiOperation(value = "单位用户信息数据明细", notes = "单位用户信息数据明细",httpMethod = "GET")
    @PassToken
    @RequestMapping("/userInfo")
    public Json getUserInfo(CheckAcountBaseData baseData){
        Json json = new Json();
        try {
            UserOut userInfo = userApiService.getUserInfo(baseData);
            json.setSuccess(true);
            json.setObj(userInfo);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }

    @ApiOperation(value = "用户信息数据明细", notes = "用户信息数据明细",httpMethod = "GET")
    @RequestMapping("/sysUser")
    public Json getSysUser(CheckAcountBaseData baseData){
        Json json = new Json();
        try {
            UtUnitUser userInfo = userApiService.getSysUser(baseData);
            json.setSuccess(true);
            json.setObj(userInfo);
        } catch (ServiceException e) {
            json.setSuccess(false);
            json.setMsg(e.getMessage());
        } catch (Exception e) {
            json.setSuccess(false);
            json.setMsg(UtilConst.ERRO_SYSTEM);
        }
        return json;
    }
}
