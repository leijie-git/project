package com.gw.inspect.controller;

import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.inspect.data.InspectionInData;
import com.gw.inspect.service.InspectConfigService;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.systemManager.controller.SysResourceController;
import com.gw.util.UtilConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
@RequestMapping("/inspectConfig")
public class InspectConfigController extends BaseController {
    private Logger log = LoggerFactory.getLogger(InspectConfigController.class);
    @Autowired
    private InspectConfigService inspectConfigService;
    /**
     * 巡查点分类管理
     *
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "importConfigExcel", method = RequestMethod.POST)
    public Json importExcel(@RequestParam("uploadExcel") MultipartFile uploadExcel, HttpServletRequest request) {
        Json json = new Json();


        try {
            //获取excel的所有数据 将excel数据转为InspectionInData对象
            List<InspectionInData> projectImportDatas = importExcel(uploadExcel, 0, InspectionInData.class);
            int successNum=inspectConfigService.add(projectImportDatas);
            json.setSuccess(true);
     		json.setMsg("导入成功");
            json.setObj(successNum);
        } catch (Exception e) {
            json.setSuccess(false);
            log.error(e.getMessage(), e);
            json.setMsg("导入失败");
        }

        return json;
    }


}
