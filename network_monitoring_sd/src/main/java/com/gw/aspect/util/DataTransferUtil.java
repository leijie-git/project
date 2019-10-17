package com.gw.aspect.util;

import com.alibaba.fastjson.JSONObject;
import com.gw.aspect.data.OriginalAlarmVo;
import com.gw.aspect.data.OriginalRtuFaultVo;
import com.gw.aspect.dic.XinJiYuanDic;
import com.gw.util.Util;
import com.gw.util.UtilConv;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

public class DataTransferUtil {

   public static JSONObject alarmToTelling(OriginalAlarmVo vo){
       JSONObject json = new JSONObject();
       json.put("id",vo.getNetDeviceId().toString());
       json.put("alarmId", vo.getId().toString());
       json.put("alarmDevicedesc",vo.getAlarmSourcedesc()+" "+vo.getAlarmDevicedesc()+" "+vo.getAlarmWheredesc());
       json.put("level",dealStatus(vo.getAlarmStatus()));
       if(Util.isNotEmpty(vo.getIsdeal()) && 1 == vo.getIsdeal()){
           json.put("alarmStatus","1");
       }else {
           json.put("alarmStatus","0");
       }
       json.put("alarmTime", UtilConv.date2Str(new Date(), UtilConv.DATE_TIME_PAT_19));
       return json;
   }

   public static JSONObject rtuFaultToTelling(OriginalRtuFaultVo vo){

       JSONObject json = new JSONObject();
       json.put("id", vo.getNetDeviceId().toString());
       json.put("alarmId", vo.getId().toString());
       json.put("alarmDevicedesc",vo.getEquipmentname()+" "+vo.getEquipmentdetialname());
       json.put("level",dealStatus(vo.getAlarmStatus()));
       if(Util.isNotEmpty(vo.getIsdeal()) && 1 == vo.getIsdeal()){
           json.put("alarmStatus","1");
       }else {
           json.put("alarmStatus","0");
       }
       json.put("alarmTime", UtilConv.date2Str(vo.getAlarmtime(), UtilConv.DATE_TIME_PAT_19));
       return json;
   }
    /**
     * 新纪元上报报警数据到大数据平台,参数字段适配
     * @param vo
     * @return
     * @throws UnknownHostException
     */
    public static JSONObject alarmToXinJiYuan(OriginalAlarmVo vo) throws UnknownHostException {
        String eventTypeId = getEventTypeId(vo);
        String eventStatus = XinJiYuanDic.EVENT_STATUS_UPLOAD;
        String isHandled = "0";
        if(Util.isNotEmpty(vo.getDealresult())){
            eventStatus = XinJiYuanDic.EVENT_STATUS_DEAL;
            isHandled = "1";
        }
        String alarmTypeId = XinJiYuanDic.ALARM_STATUS_FIRE;
        switch (vo.getAlarmStatus()) {
            case 0:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_MANUAL;
                break;
            case 1:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_FIRE;
                break;
            case 2:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_WARNING;
                break;
            case 3:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_START;
                break;
            case 4:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_FEEDBACK;
                break;
            case 5:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_WARNING;
                break;
            case 6:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_WARNING;
                break;
            case 7:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_FIRE;
                break;
            case 8:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_FIRE;
                break;
            case 11:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_FIRE;
                break;
            case 12:
                alarmTypeId = XinJiYuanDic.ALARM_STATUS_WARNING;
                break;

        }
        JSONObject json = new JSONObject();
        //必填字段
        json.put("autoStatus",1);//是否设备自动上传
        String detectorId = vo.getAddressRelId() == null ? "0":vo.getAddressRelId().toString();
        json.put("detectorId",detectorId);//设备ID
        json.put("mainframeId",vo.getOnwercode());//网关ID
        json.put("eventTypeId",eventTypeId);//事件类型
        json.put("eventStatus",eventStatus);//事件状态
        json.put("alarmTypeId",alarmTypeId);//报警类型
        json.put("startTime", UtilConv.date2Str(vo.getTime(),UtilConv.DATE_TIME_PAT_19_));//事件开始时间
        json.put("eventAddress", vo.getAlarmWheredesc());//事件发生地
        json.put("isHandled",isHandled);//是否已处理
        String orgId = vo.getUnitid() == null ? "0":vo.getUnitid().toString();
        json.put("orgId", orgId);//单位ID
        json.put("platformCode", "1000100040");//平台编码
        json.put("recordCode",vo.getId().toString());//记录号
        //选填字段
        json.put("reportUserId","");//上报人ID
        json.put("eventContent", vo.getAlarmSourcedesc());//事件描述
        json.put("endTime","");//事件结束时间
        json.put("confirmUserId",vo.getDealuser());//事件确认人ID
        json.put("handlingSuggestion", vo.getDealinfo());//处理意见
        if(Util.isNotEmpty(vo.getDealtime())){
            json.put("handingTime", UtilConv.date2Str(vo.getDealtime(),UtilConv.DATE_TIME_PAT_19_));//处置时间/确认时间
        }
        if(Util.isNotEmpty(vo.getBuildId())) {
            json.put("buildingId", vo.getBuildId().toString());//建筑ID
        }
        json.put("resetTime","");//复位时间
        json.put("resetStatus",getRestStatus(vo));//复位状态
        if(Util.isNotEmpty(vo.getPicurl())){
            json.put("imageUrl","http://"+ InetAddress.getLocalHost().getHostAddress()+":8085" + vo.getPicurl());//图片链接
        }

        return json;
    }

    private static String getEventTypeId(OriginalAlarmVo vo) {
        String eventTypeId = XinJiYuanDic.EVENT_TYPE_FIREALARM;
        if(Util.isNotEmpty(vo.getIsTest()) && 1 == vo.getIsTest()){
            eventTypeId = XinJiYuanDic.EVENT_TYPE_ALARMTEST;
        }else if(Util.isNotEmpty(vo.getDealresult()) && 1 == vo.getDealresult()){
            eventTypeId = XinJiYuanDic.EVENT_TYPE_ALARMMISTAKEN;
        }
        return eventTypeId;
    }

    private static String getRestStatus(OriginalAlarmVo vo){
        String resetStatus = "0";
        switch (vo.getAlarmStatus()) {
            case 7:
                resetStatus = "1";
                break;
            case 8:
                resetStatus = "1";
                break;
            case 11:
                resetStatus = "1";
                break;
            case 12:
                resetStatus = "1";
                break;
        }
        return resetStatus;
    }
    public static JSONObject alarmConfirmToXinJiYuan(OriginalAlarmVo vo) throws UnknownHostException {
        JSONObject json = new JSONObject();
        //必填
        json.put("eventId",vo.getId().toString());//事件ID
        Date dealDate = vo.getDealtime() == null ? new Date() : vo.getDealtime();
        json.put("checkTime", UtilConv.date2Str(dealDate, UtilConv.DATE_TIME_PAT_19_));//事件开始时间
        json.put("eventTypeId",getEventTypeId(vo));//确认类型
        json.put("platformCode", "1000100040");//平台编码
        json.put("recordCode",vo.getId().toString());//记录号
        String detectorId = vo.getAddressRelId() == null ? "0" : vo.getAddressRelId().toString();
        json.put("detectorId", detectorId);//设备ID
        json.put("confirmUserId",vo.getDealuser());//确认人ID
        json.put("resetStatus",getRestStatus(vo));//复位状态
        json.put("isHandled","1");//是否处理
        //选填
        json.put("firePictureName","");//火灾照片名称
        if(Util.isNotEmpty(vo.getPicurl())){
            json.put("firePicture","http://"+ InetAddress.getLocalHost().getHostAddress()+":8085" + vo.getPicurl());//图片链接
        }
        json.put("fireVideoName","");//火灾视频名称
        json.put("fireVideo","");//火灾视频地址
        json.put("handlingSuggestion",vo.getDealinfo());//处理意见
        return json;
    }

    public static JSONObject rtuFaultToXinJiYuan(OriginalAlarmVo vo) {
        JSONObject json = new JSONObject();
        //必填字段
        String orgId = vo.getUnitid() == null ? "0" : vo.getUnitid().toString();
        json.put("orgId", orgId);//单位ID
        json.put("autoStatus","1");//是否自动上报
        json.put("mainframeId", vo.getOnwercode());//网关id
        json.put("statusTypeId", "1");//流程状态id
        json.put("dangerTypeId", "0236");//故障类型id
        json.put("isRepair", "0");//是否修复
        json.put("isFault", "0");//是否设备故障
        json.put("platformCode", "1000100040");//平台编码
        json.put("recordCode", vo.getId().toString());//记录编码
        //选填字段
        String detectorId = vo.getAddressRelId() == null ? "0" : vo.getAddressRelId().toString();
        json.put("detectorId", detectorId);//设备ID
        json.put("content","");//上报内容
        json.put("imageUrl","");//图片地址
        json.put("videoUrl","");//视频地址
        json.put("audioUrl","");//音频地址
        json.put("startRepairTime","");//开始修复时间
        json.put("repairTime","");//修复时间
        json.put("reportUserId","");//上报人Id
        json.put("startTime","");//故障开始时间
        json.put("ifcsFiresystemId","");//消防系统id
        json.put("latentDangerTypeId", 2);//隐患类型id
        json.put("localSolution","");//是否现场解决
        json.put("reason","");//现场解决原因

        return json;
    }


    public static String dealStatus(Integer alarmStatus) {
        if (Util.isEmpty(alarmStatus)) {
            return "";
        }
        switch (alarmStatus) {
            case 0:
                return "手动报警";
            case 1:
                return "火警";
            case 2:
                return "故障";
            case 3:
                return "启动";
            case 4:
                return "反馈";
            case 5:
                return "监管";
            case 6:
                return "屏蔽";
            case 7:
                return "恢复";
            case 8:
                return "复位";
            case 9:
                return "用户传输装置操作";
            case 11:
                return "火警恢复";
            case 12:
                return "故障恢复";
            case 13:
                return "停止";
            case 14:
                return "反馈撤销";
            case 15:
                return "监管撤销";
            case 16:
                return "屏蔽撤销";
            case 20:
                return "消音";
            case 21:
                return "解除报警";
            case 22:
                return "自动火警有人";
            case 23:
                return "自动火警超时";
            case 24:
                return "自动火警无人";
            case 25:
                return "确认火警";
            case 26:
                return "紧急火警";
            case 27:
                return "预警";
            case 28:
                return "报警";
            case 30:
                return "传输装置开机";
            case 31:
                return "传输装置关机";
            case 32:
                return "控制器开机";
            case 33:
                return "控制器关机";
            case 34:
                return "RTU开机";
            case 35:
                return "RTU关机";
            case 200:
                return "水系统异常";
            case 201:
                return "水系统恢复";
            case 211:
                return "消火栓系统异常";
            case 212:
                return "自动喷水灭火系统异常";
            case 218:
                return "防烟排烟系统异常";
            case 219:
                return "防火门及卷帘系统异常";
            case 261:
                return "消火栓系统恢复";
            case 262:
                return "自动喷水灭火系统恢复";
            case 268:
                return "防烟排烟系统恢复";
            case 269:
                return "防火门及卷帘恢复";
            case 300:
                return "电气火灾异常";
            case 301:
                return "电气火灾恢复";
            default:
                break;
        }
        return "";
    }
}
