package com.gw.inspect.controller;

import com.gw.myAnnotation.PassToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceController {

    /**
     * 维保计划录入
     * @return
     */
    @PassToken
    @GetMapping("/weibaoPlan")
    public String weibaoPlan() {
        return "/maintenance/weibaoPlan";
    }

    /**
     * 维保计划管理
     * @return
     */
    @PassToken
    @GetMapping("/weibaoTask")
    public String weibaoTask() {
        return "/maintenance/weibaoTask";
    }

}
