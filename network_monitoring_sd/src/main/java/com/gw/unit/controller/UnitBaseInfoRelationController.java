package com.gw.unit.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gw.build.data.BuildAreaOutData;
import com.gw.unit.data.NetworkingUserOutData;
import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.Json;
import com.gw.exception.ServiceException;
import com.gw.unit.data.UnitBaseInfoRelationInData;
import com.gw.unit.data.UnitBaseInfoRelationOutData;
import com.gw.unit.service.UnitBaseInfoRelationService;


/**
 * 联网单位编号绑定Controller
 *
 * @author SY
 */
@Controller
@RequestMapping("/baseInfoRelation")
@ResponseBody
public class UnitBaseInfoRelationController {
    private Logger log = LoggerFactory.getLogger(UnitBaseInfoRelationController.class);

    @Autowired
    private UnitBaseInfoRelationService unitBaseInfoRelationService;

    @RequestMapping("/list")
    public PageInfo<UnitBaseInfoRelationOutData> list(HttpServletRequest request, String unitId) {
        try {
            PageInfo<UnitBaseInfoRelationOutData> list = unitBaseInfoRelationService.list(request, unitId);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping("/getAllUnitRel")
    public PageInfo<UnitBaseInfoRelationOutData> getAllUnitRel(HttpServletRequest request,UnitBaseInfoRelationInData inData) {

        PageInfo<BuildAreaOutData> pager=null;
        //获取token
        String authorize = request.getHeader("Authorization");
        long id=0;
        String Account=null;
        String UnitID=null;
        //解析token
        Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
        id=Long.parseLong(tokenToMap.get("id"));
        Account=tokenToMap.get("Account");
        UnitID=tokenToMap.get("UnitID");

        try {
            if(UnitID!=null){
                inData.setUnitid(UnitID);}
            PageInfo<UnitBaseInfoRelationOutData> list = unitBaseInfoRelationService.getAllUnitRel(inData);
            return list;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    @RequestMapping("/add")
    public Json add(UnitBaseInfoRelationInData baseInfoRelationInData) {
        Json json = new Json();
        try {
            unitBaseInfoRelationService.addBaseInfoRelation(baseInfoRelationInData);
            json.setSuccess(true);
            json.setMsg("添加成功");
        } catch (ServiceException se) {
            json.setMsg(se.getMessage());
            log.error(se.getMessage(), se);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @RequestMapping("/update")
    public Json update(UnitBaseInfoRelationInData baseInfoRelationInData) {
        Json json = new Json();
        try {
            unitBaseInfoRelationService.updateBaseInfoRelation(baseInfoRelationInData);
            json.setSuccess(true);
            json.setMsg("保存成功");
        } catch (ServiceException se) {
            json.setMsg(se.getMessage());
            log.error(se.getMessage(), se);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @RequestMapping("/delete")
    public Json delete(String id) {
        Json json = new Json();
        try {
            unitBaseInfoRelationService.deleteBaseInfoRelation(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (ServiceException se) {
            log.error(se.getMessage(), se);
            json.setMsg(se.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.setMsg("删除失败，请稍后再试");
        }
        return json;
    }

    @RequestMapping("/soureAddressSelect")
    public Json soureAddressSelect(String unitid) {
        Json json = new Json();
        try {
            List<UnitBaseInfoRelationOutData> list = unitBaseInfoRelationService.soureaddressSelect(unitid);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return json;
    }


    /**
     * 提供根据单位查询单位用户接口
     * jie.lei 根据单位ID查询用户
     */
    @RequestMapping("/selectAccount")
    public Json selectAccount(String UnitID,String role) {
        Json json = new Json();
        try {
            json.setObj(unitBaseInfoRelationService.selectAccountByUnitID(UnitID,role));
            json.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

}
