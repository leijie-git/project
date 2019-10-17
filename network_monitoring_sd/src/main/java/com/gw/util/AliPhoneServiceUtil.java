package com.gw.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsRequest;
import com.aliyuncs.dyvmsapi.model.v20170525.SingleCallByTtsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class AliPhoneServiceUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AliPhoneServiceUtil.class);

    //产品名称:云通信语音API产品,开发者无需替换
    static final String product = "Dyvmsapi";
    //产品域名,开发者无需替换
    static final String domain = "dyvmsapi.aliyuncs.com";

    //TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)

    /**
     * 文字转语音外呼
     * @param callNum 被叫号码
     * @param showNum 呼叫显示号码
     * @return
     */
    public static Map singleCallByTts(String accessKeyId, String accessKeySecret, String ttsCode, String timeout, String callNum, String showNum, JSONObject jsonParams) {
        LOGGER.info("=========准备打电话"+callNum+"======="+jsonParams.toString());
        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", timeout);
        System.setProperty("sun.net.client.defaultReadTimeout", timeout);
        Map map = new HashMap();

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, CodecUtil.decode64(accessKeyId,accessKeySecret));
        try {
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
        } catch (ClientException e) {
            LOGGER.info("=========初始化acsClient失败=================" + e.getMessage());
            map.put("succ",false);
            return map;
        }
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SingleCallByTtsRequest request = new SingleCallByTtsRequest();
        //必填-被叫显号,可在语音控制台中找到所购买的显号
        request.setCalledShowNumber(showNum);
        //必填-被叫号码
        request.setCalledNumber(callNum);
        //必填-Tts模板ID
        request.setTtsCode(ttsCode);
        // 可选-当模板中存在变量时需要设置此值
//        request.setTtsParam("{\"maintenanceName\":\"苏州思迪\",\"unitName\":\"某某单位\",\"alarmTime\":\"2019年5月17日11点30分\",\"alarmWheredesc\":\"办公楼三楼\",\"alarmSourceDesc\":\"无线烟感\"}");
        request.setTtsParam(jsonParams.toJSONString());
        //可选-外部扩展字段,此ID将在回执消息中带回给调用方
//        request.setOutId("11111111");
        request.setPlayTimes(1);
        //hint 此处可能会抛出异常，注意catch
        SingleCallByTtsResponse singleCallByTtsResponse = null;
        try {
            singleCallByTtsResponse = acsClient.getAcsResponse(request);
        } catch (ClientException e) {
            LOGGER.info("=========呼叫失败=================" + e.getMessage());
            map.put("succ",false);
            return map;
        }

        LOGGER.info("=========拨打电话结束:"+callNum+"====="+showNum+"====="+singleCallByTtsResponse.getMessage());
        LOGGER.info("=========拨打电话结束:================================"+singleCallByTtsResponse.getCallId());
//        return (singleCallByTtsResponse.getCode() != null && singleCallByTtsResponse.getCode().equals("OK"));
        map.put("succ",(singleCallByTtsResponse.getCode() != null && singleCallByTtsResponse.getCode().equals("OK")));
        map.put("callid",singleCallByTtsResponse.getCallId());
        return map;
    }

}
