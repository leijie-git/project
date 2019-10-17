package com.gw.alarm.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gw.aspect.data.OriginalAlarmVo;
import com.gw.aspect.util.DataTransferUtil;
import com.gw.aspect.util.RpcTokenUtil;
import com.gw.mapper.UtLzBjzjalarmMapper;
import com.gw.util.HttpClientUtil;
import com.gw.util.HttpJson;
import com.test.BaseControllerTest;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.io.IOException;

/**
 *
 * @description: alarmInfoController 测试类
 *
 **/
public class AlarmInfoControllerTest extends BaseControllerTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlarmInfoControllerTest.class);
    @Resource
    private UtLzBjzjalarmMapper bjzjalarmMapper;

    public static void main(String[] args) throws Exception {
        testSingleClient();
        return;
    }

    private static void testSingleClient() throws InterruptedException, IOException {
        System.out.println(System.currentTimeMillis());
        CloseableHttpClient httpClient = HttpClientUtil.getClient();
        final HttpGet httpGet1 = new HttpGet("https://www.sina.com.cn/");
        CloseableHttpResponse execute = httpClient.execute(httpGet1);
        EntityUtils.consume(execute.getEntity());
        for (int i = 1; i < 50; i++) {
            System.out.println("执行第i" + i + "个请求开始");
            int finalI = i;
            new Thread(() -> {
                final HttpGet get = new HttpGet("https://www.sina.com.cn/");
                CloseableHttpResponse res = null;
                try {
                    res = HttpClientUtil.getClient().execute(get);
                    //System.out.println(EntityUtils.toString(res.getEntity()));
                    EntityUtils.consume(res.getEntity());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("j" + finalI);
            }).start();
            System.out.println("执行第i" + i + "个请求结束");
        }

        for (int j = 1; j < 50; j++) {
            int finalI = j;
            System.out.println("执行第j" + j + "个请求开始");
            new Thread(() -> {
                final HttpGet get = new HttpGet("https://ju.taobao.com");
                CloseableHttpResponse res = null;
                try {
                    res = HttpClientUtil.getClient().execute(get);
                    //System.out.println(EntityUtils.toString(res.getEntity()));
                    EntityUtils.consume(res.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + "j" + finalI);
            }).start();
            System.out.println("执行第j" + j + "个请求结束");
        }

        httpClient.close();
    }


    @Test
    public void getPhones() throws Exception {

    }

    @Test
    public void sendRTUAlarm() {

    }

    @Test
    public void sendBJZJAlarm() throws Exception {
        OriginalAlarmVo alarmVo = bjzjalarmMapper.getOriginalAlarmById(506480645040504832L);
        JSONObject postJson = DataTransferUtil.rtuFaultToXinJiYuan(alarmVo);
        JSONObject json = new JSONObject();
        json.put("token", RpcTokenUtil.getBDOFIToken());
        json.put("type", "0");
        JSONArray array = new JSONArray();
        array.add(postJson);
        json.put("data", array);
//        JSONObject json = new JSONObject();
//        json = JSONObject.parseObject("{\"data\":[{\"eventId\":\"602664793773436928\",\"handlingSuggestion\":\"\",\"fireVideo\":\"\",\"resetStatus\":\"0\",\"firePictureName\":\"\",\"confirmUserId\":\"1\",\"isHandled\":\"1\",\"eventTypeId\":\"1\",\"checkTime\":\"2019-07-22 13:59:52\",\"detectorId\":\"585761746023612416\",\"fireVideoName\":\"\",\"recordCode\":\"602664793773436928\",\"platformCode\":\"100216\"}],\"type\":\"0\",\"token\":\"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxMDAyMTYiLCJjcmVhdGVkIjoxNTYzNzc1MTE4NzEwLCJleHAiOjE1NjM4NjE1MTh9.RSBcKk0511ysGoUFjwovZmBgRr5WopesTZQL3M5xiNFaeAg6bAT9hpNHRcNLTMKyHbhT_N4GIJ3pAePhoDYKpg\"}");
        HttpJson httpJson = HttpClientUtil.easemobPost("http://183.136.177.24:39099/BDOFI/report/fault", RpcTokenUtil.getBDOFIToken(), json);
        System.out.println(httpJson);

    }

    @Test
    public void updatePhoneStatus() {

    }

    @Test
    public void produceEncodeCipter() {

    }

    private void doPost(String url, String token, JSONObject json) {
        new Thread(() -> {
            try {
                HttpJson httpJson = HttpClientUtil.easemobPost(url, token, json);
            } catch (Exception e) {
            }
        }).start();
    }
}
