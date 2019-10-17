package com.gw.inspect.controller;

import com.gw.inspect.service.MergeTableService;
import com.gw.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @开发人 Jie.Lei
 * @创建时间 2019/7/17
 * @描述
 */
@RequestMapping("/merge")
@Controller
public class MergeTabbleController {
    @Resource
    private MergeTableService mergerService;
    @ResponseBody
    @RequestMapping("/mergeTabble")
    public String mergeTabble() {
        String i = mergerService.mergeTabble();
        return i;
    }
}
