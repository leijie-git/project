package com.gw.util;

import java.text.ParseException;
import java.util.*;

import com.alibaba.fastjson.JSONObject;
import com.gw.alarm.service.impl.AlarmInfoServiceImpl;
import com.gw.mapper.UtPhoneLogMapper;
import com.gw.mapper.entity.UtPhoneLog;
import com.gw.mapper.entity.UtUnitBaseinfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.alicom.mns.tools.DefaultAlicomMessagePuller;
import com.alicom.mns.tools.MessageListener;
import com.aliyun.mns.model.Message;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

/**
 * 只能用于接收云通信的消息，不能用于接收其他业务的消息
 * 短信上行消息接收demo
 */
@Component
public class ReceiveAlicomMsg {

    @Autowired
    private UtPhoneLogMapper utPhoneLogMapper;

    public static ReceiveAlicomMsg rceiveAlicomMsg;

    @PostConstruct
    public void init() {
        rceiveAlicomMsg = this;
        rceiveAlicomMsg.utPhoneLogMapper = this.utPhoneLogMapper;
    }

    @Autowired
    private AlarmInfoServiceImpl alarmInfoServiceImpl;

    @PostConstruct
    public void initTwo() {
        rceiveAlicomMsg = this;
        rceiveAlicomMsg.alarmInfoServiceImpl = this.alarmInfoServiceImpl;
    }

    private static Log logger = LogFactory.getLog(AliPhoneServiceUtil.class);

    private static List<Map<Object,Object>> logTwo = new ArrayList<>();

    private static int number = 0;

    private static JSONObject jsonTwo;

    private static UtUnitBaseinfo utUnitTwo;

    public static class MyMessageListener implements MessageListener{
        private Gson gson=new Gson();
        private UtPhoneLog callLog;
        public UtPhoneLog getCallLog() {
            return callLog;
        }

        @Override
        public boolean dealMessage(Message message) {

            //消息的几个关键值
            System.out.println("message handle: " + message.getReceiptHandle());
            System.out.println("message body: " + message.getMessageBodyAsString());
            System.out.println("message id: " + message.getMessageId());
            System.out.println("message dequeue count:" + message.getDequeueCount());

            try{
                Map<String,Object> contentMap=gson.fromJson(message.getMessageBodyAsString(), HashMap.class);
                //依据自己的消息类型，获取对应的字段
//                String callId=(String)contentMap.get("call_id");
//                String startTime=(String)contentMap.get("start_time");
//                String endTime=(String)contentMap.get("end_time");
//                String duration=(String)contentMap.get("duration");
//                String statusCode=(String)contentMap.get("status_code");
//                String statusMsg=(String)contentMap.get("status_msg");
//                String outId=(String)contentMap.get("out_id");
//                String dtmf=(String)contentMap.get("dtmf");
                //被叫号码
//                String callee =(String)contentMap.get("callee");
                String call_id =(String)contentMap.get("call_id");
                logger.info("call_id=============================="+contentMap.get("call_id"));

                //TODO 这里开始写业务代码

                logger.info("callid============================="+logTwo.get(0).get("callid"));
                for (int i = 0; i < logTwo.size(); i++){
                    //判断语音回执callid是否一致，并且通话时长不等于空不等于0
                    if (call_id.equals(logTwo.get(i).get("callid")) && !Util.isEmpty(contentMap.get("duration")) && !"0".equals(contentMap.get("duration"))){
                        logger.info("id是==============="+logTwo.get(i).get("id")+"====的通话时长=============" + contentMap.get("duration").toString());
                        number +=  Integer.parseInt(contentMap.get("duration").toString());
                        this.callLog = new UtPhoneLog();
                        callLog.setId(Long.valueOf(logTwo.get(i).get("id").toString()));
                        callLog.setLongTime(Integer.parseInt(contentMap.get("duration").toString()));
                        try{
                            logger.info("消息回执/调用方法进行数据存储==============================" + i);
                            rceiveAlicomMsg.alarmInfoServiceImpl.updateUnitPhone(callLog);
                        }catch (Exception e){
                            logger.error("消息回执/调用方法报错信息=======================" + e.getMessage());
                        }
                    }
                }
            }catch(com.google.gson.JsonSyntaxException e){
                logger.error("error_json_format:"+message.getMessageBodyAsString(),e);
            }finally{
                logger.info("总的通话时长========="+number+"=========="+logTwo.get(0).get("type").equals(0));
                if (number == 0 && logTwo.get(0).get("type").equals(0)){
                    logger.info("调用拨打单位电话方法==============================");
                    try{
                        rceiveAlicomMsg.alarmInfoServiceImpl.dialTheUnitPhone(jsonTwo,utUnitTwo);
                    }catch (Exception e){
                        e.getMessage();
                        logger.error("调用拨打单位电话方法报错信息=======================" + e.getMessage());
                    }
                }
            }
            Boolean dealResult=true;
            return dealResult;//返回true，则工具类自动删除已拉取的消息。
        }
    }

    public static void  voiceReturn(String accessKeyId, String accessKeySecret, List<Map<Object,Object>> list, JSONObject json, UtUnitBaseinfo baseInfo) throws com.aliyuncs.exceptions.ClientException, ParseException {
        List<UtPhoneLog> listLog = new ArrayList<>();
        logTwo = list;
        jsonTwo = json;
        utUnitTwo = baseInfo;
        DefaultAlicomMessagePuller puller=new DefaultAlicomMessagePuller();
        String messageType="VoiceReport"; //注意替换成你自己需要获取的消息的类型
        String queueName="Alicom-Queue-1574116383601019-VoiceReport";//在云通信页面开通相应业务消息后，就能在页面上获得对应的queueName
        MyMessageListener myMessageListener = new MyMessageListener();
        puller.startReceiveMsg(accessKeyId,CodecUtil.decode64(accessKeyId,accessKeySecret),messageType,queueName, myMessageListener);
    }
}
