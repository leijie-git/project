package com.gw.apppush;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gw.apppush.impl.BDPushAppMsg;
import com.gw.apppush.impl.HWPushAppMsg;
import com.gw.apppush.impl.MiPushAppMsg;
import com.gw.exception.ServiceException;
import com.gw.util.Util;
import com.xiaomi.xmpush.server.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.gw.apppush.AppPushConfig.*;

/**
 * app消息推送管理器
 **/
public class PushMsgManage {
    private static final Logger LOGGER = LoggerFactory.getLogger(PushMsgManage.class);

    /**
     * 推送单条通知至单个设备
     * @param phoneType 设备名称
     * @param title 通知标题
     * @param content 通知内容
     * @param chennelId 设备唯一标识
     * @throws ServiceException 推送失败
     */
    public static void pushMsg(String phoneType, String title, String content, String chennelId) throws ServiceException{

        boolean succ = false;
        phoneType = Util.isEmpty(phoneType) ? "" : phoneType;
        switch (phoneType){
            case "小米" :
                //构建小米消息
                Message miMsg = MiPushAppMsg.getInstance().buildAndroidMessage(title, content, MI_PACKAGE_NAME);
                //推送
                succ = MiPushAppMsg.getInstance().sendMessage(miMsg, chennelId, MI_default_retries);
                break;
            case "华为" :
                //构建华为消息
                JSONObject hwMsg = HWPushAppMsg.getInstance().buildMsg(title, content, HW_PACKAGE_NAME);
                //推送
                JSONArray deviceTokens = new JSONArray();//目标设备Token
                deviceTokens.add(chennelId);
                succ = HWPushAppMsg.getInstance().sendPushMessage(deviceTokens, hwMsg);
                break; //可选
            case "苹果" :
                break;
            default :
                //构建百度消息
                JSONObject bdMsg = BDPushAppMsg.getInstance().buildAndroidMessage(title, content);
                //推送
                succ = BDPushAppMsg.getInstance().sendMessage(bdMsg,BD_DEVICETYPE_Android,BD_MSGTYPE_notice,chennelId);
        }
        if (!succ)
            LOGGER.error("==============推送APP消息失败=============");
    }

    /**
     * 推送单条通知至批量设备
     * @param deviceName 设备名称
     * @param title 通知标题
     * @param desc 通知内容
     * @param deviceNos 设备唯一标识
     * @throws ServiceException 推送失败
     */
    public static void pushMsgBatch(String deviceName, String title, String desc, List<String> deviceNos) throws ServiceException{

        boolean succ = false;
        switch (deviceName){
            case "小米" :
                //构建小米消息
                Message miMsg = MiPushAppMsg.getInstance().buildAndroidMessage(title, desc, MI_PACKAGE_NAME);
                //推送
                succ = MiPushAppMsg.getInstance().sendMessage(miMsg, deviceNos, MI_default_retries);
                break;
            case "华为" :
                //构建华为消息
                JSONObject hwMsg = HWPushAppMsg.getInstance().buildMsg(title, desc, HW_PACKAGE_NAME);
                //推送
                succ = HWPushAppMsg.getInstance().sendPushMessage(JSONArray.parseArray(deviceNos.toString()), hwMsg);
                break; //可选
            case "苹果" :
                break;
            default :
                //构建百度消息
                JSONObject bdMsg = BDPushAppMsg.getInstance().buildAndroidMessage(title, desc);
                //推送
                succ = BDPushAppMsg.getInstance().sendMessage(bdMsg,BD_DEVICETYPE_Android,BD_MSGTYPE_notice,deviceNos);
        }
        if (!succ)
            throw new ServiceException("app消息推送失败");
    }

    public static void main(String[] args) throws ServiceException {

        //pushMsg("小米", "小米通知标题", "小米通知描述", "hr3sXFhNok87DAZs4BuXcbT91QCIb4JbKVX91f49yPH3TUDahJ8Ry356J3SyjxQq");
        //pushMsg("小米","小米通知标题","小米通知描述","异常测试");
        pushMsg("华为", "华为通知标题", "华为通知描述", "0869830034152876300003908000CN01");
        //pushMsg("其他","百度通知标题","百度通知描述","4121395503482736846");
        //pushMsg(null,"百度通知标题","百度通知描述","4121395503482736846");
        //pushMsg("其他","百度通知标题","百度通知描述","异常测试");
    }
}
