package com.gw.device.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.gw.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.gw.common.BaseController;
import com.gw.common.Json;
import com.gw.device.data.EqSystemOutData;
import com.gw.device.data.NetDeviceInData;
import com.gw.device.data.NetDeviceOutData;
import com.gw.device.service.NetDeviceService;
import com.gw.exception.ServiceException;
import com.gw.login.service.data.GetSessionInfoOutData;
import com.gw.util.UtilConst;

/**
 * 联网设备Controller层
 *
 * @author SY
 */
@Controller
@RequestMapping("/netDevice")
@ResponseBody
public class NetDeviceController extends BaseController {

    private Logger log = LoggerFactory.getLogger(NetDeviceController.class);

    @Autowired
    private NetDeviceService netDeviceService;

    /**
     * 添加联网设备
     *
     * @param inData
     * @return
     */
    @RequestMapping("/add")
    public Json add(HttpServletRequest request, NetDeviceInData inData) {
        Json json = new Json();
        GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);


        String authorize = request.getHeader("Authorization");
        long id = 0;
        String Account = null;
        String UnitID = null;



        try {
            if (authorize != null) {
                //解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                Account = tokenToMap.get("Account");
                UnitID = tokenToMap.get("UnitID");
                inData.setUserId(id);
            } else {
                inData.setUserId(sessionInfo.getId());
            }


            netDeviceService.add(inData);
            json.setSuccess(true);
            json.setMsg("新增成功");
        } catch (ServiceException se) {
            log.error(se.getMessage(), se);
            json.setMsg(se.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.setMsg("新增失败");
        }
        return json;
    }

    /**
     * 获取联网设备分页数据
     *
     * @param inData
     * @return
     */
    @RequestMapping("/list")
    public PageInfo<NetDeviceOutData> list(NetDeviceInData inData, HttpServletRequest request) {
        String authorize = request.getHeader("Authorization");
        long id = 0;
        String Account = null;
        String UnitID = null;

        try {
            if (authorize != null) {

                //解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                Account = tokenToMap.get("Account");
                UnitID = tokenToMap.get("UnitID");
                if (UnitID != null) {
                    inData.setUnitid(UnitID);
                }

                PageInfo<NetDeviceOutData> pageInfo = netDeviceService.list(inData, id);
                return pageInfo;
            } else {

                GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);


                PageInfo<NetDeviceOutData> pageInfo = netDeviceService.list(inData, sessionInfo.getId());
                return pageInfo;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 更新联网设备信息
     *
     * @param inData
     * @return
     */
    @RequestMapping("/update")
    public Json update(HttpServletRequest request, NetDeviceInData inData) {
        Json json = new Json();
          String authorize = request.getHeader("Authorization");
        long id = 0;
        String Account = null;
        String UnitID = null;
         if (authorize != null) {

                //解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                Account = tokenToMap.get("Account");
                UnitID = tokenToMap.get("UnitID");
                inData.setUserId(id);
            }else {

                GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
                inData.setUserId(sessionInfo.getId());
            }



        try {
            netDeviceService.update(inData);
            json.setSuccess(true);
            json.setMsg("更新成功");
        } catch (ServiceException se) {
            log.error(se.getMessage(), se);
            json.setMsg(se.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            json.setMsg("更新失败");
        }
        return json;
    }

    /**
     * 删除联网设备
     *
     * @param id
     * @return
     */
    @RequestMapping("/delete")
    public Json delete(String id) {
        Json json = new Json();
        try {
            netDeviceService.delete(id);
            json.setSuccess(true);
            json.setMsg("删除成功");
        } catch (ServiceException se) {
            json.setMsg(se.getMessage());
            log.error(se.getMessage(), se);
        } catch (Exception e) {
            json.setMsg("删除失败");
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 获得需要导入的数据
     *
     * @param unitid
     * @return
     */
    @RequestMapping("/getImportList")
    public PageInfo<NetDeviceOutData> getImportList(String unitid) {
        try {
            PageInfo<NetDeviceOutData> pageInfo = netDeviceService.getImportList(unitid);
            return pageInfo;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 添加全部
     *
     * @param ids
     * @param unitid
     * @return
     */
    @RequestMapping("/addAll")
    public Json addAll(HttpServletRequest request, String ids, String unitid) {
        Json json = new Json();

        String authorize = request.getHeader("Authorization");
        long id = 0;
        String Account = null;
        String UnitID = null;


        GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
        try {
            if (authorize != null) {
                //解析token
                Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
                id = Long.parseLong(tokenToMap.get("id"));
                Account = tokenToMap.get("Account");
                UnitID = tokenToMap.get("UnitID");
                netDeviceService.addAll(id, ids, unitid);
                json.setSuccess(true);
                json.setMsg("导入成功");
                return json;
            }

            netDeviceService.addAll(sessionInfo.getId(), ids, unitid);
            json.setSuccess(true);
            json.setMsg("导入成功");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 根据系统id获取联网设施列表
     *
     * @param inData
     * @return
     */
    @RequestMapping("/getArrayList")
    public Json getArrayList(NetDeviceInData inData) {
        Json json = new Json();
        try {
            List<NetDeviceOutData> list = netDeviceService.getArrayList(inData);
            json.setObj(list);
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @RequestMapping("/eqsystemidSelect")
    public Json eqsystemidSelect() {
        Json json = new Json();
        try {
            List<EqSystemOutData> list = netDeviceService.getEqSystemSelect();
            json.setSuccess(true);
            json.setObj(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    @RequestMapping("/getNetDeviceNameSelect")
    public Json getNetDeviceNameSelect(Long unitid) {
        Json json = new Json();
        try {
            List<NetDeviceOutData> list = netDeviceService.getNetDeviceNameSelect(unitid);
            json.setSuccess(true);
            json.setObj(list);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 判断当前联网设备下是否存在设备设施
     *
     * @return
     */
    @RequestMapping("/hasEquipments")
    public Json hasEquipments(String id) {
        Json json = new Json();
        Boolean flag = false;
        try {
            flag = netDeviceService.hasEquipments(id);
            json.setObj(flag);
            json.setSuccess(true);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 从外部数据库导入设备信息
     *
     * @return
     */
    @RequestMapping("/importDevicesFromOthers")
    public Json importDevicesFromOthers(HttpServletRequest request) {
        Json json = new Json();


        //获取token
        String authorize = request.getHeader("Authorization");

        try {
        if(authorize!=null){   long id=0;
            String Account=null;
            String UnitID=null;

        //解析token
            Map<String, String> tokenToMap = JwtUtil.getTokenToMap(authorize);
            id=Long.parseLong(tokenToMap.get("id"));
            Account=tokenToMap.get("Account");
            UnitID=tokenToMap.get("UnitID");
            netDeviceService.importDevicesFromOthers(id);
            json.setSuccess(true);
        }else {
            GetSessionInfoOutData sessionInfo = (GetSessionInfoOutData) getSessinInfo(request, UtilConst.USER_SESSION);
            netDeviceService.importDevicesFromOthers(sessionInfo.getId());
            json.setSuccess(true);}
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return json;
    }

    /**
     * 关联网关
     *
     * @param netDeviceID 联网设备id
     * @param gateWayID   网关id
     * @return
     */
    @RequestMapping("/addGateWay")
    public Json addGateWay(String netDeviceID, String gateWayID) {
        Json json = new Json();
        try {
            netDeviceService.addGateWay(netDeviceID, gateWayID);
            json.setSuccess(true);
            json.setMsg("更新成功");
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return json;
    }

    /**
     * 取消关联网关
     *
     * @param 联网设备id
     * @param gateWayID   网关id
     * @return
     */
    @RequestMapping("/removeGateWay")
    public Json removeGateWay(String gateWayID) {
        Json json = new Json();
        try {
            netDeviceService.removeGateWay(gateWayID);
            json.setSuccess(true);
            json.setMsg("更新成功");
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return json;
    }

    /**
     * 联网设备添加接口查看可编辑
     */
    @RequestMapping("/getNetEq")
    public Json getNetEq(String netDeviceId) {
        Json json = new Json();
        try {
            json.setObj(netDeviceService.getNetEq(netDeviceId));
            json.setSuccess(true);
        } catch (Exception e) {
            json.setMsg(e.getMessage());
            log.error(e.getMessage(), e);
            e.printStackTrace();
        }

        return json;
    }
}
