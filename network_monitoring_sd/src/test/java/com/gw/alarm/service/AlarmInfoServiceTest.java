package com.gw.alarm.service;

import com.gw.alarm.data.AlarmRTUInData;
import com.gw.alarm.data.PhoneOutData;
import com.gw.util.Util;
import com.test.BaseServiceTest;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;


/**
 * @description: alarmInfoService测试类
 **/
public class AlarmInfoServiceTest extends BaseServiceTest {

    @Autowired
    private AlarmInfoService service;

    public static void main(String[] args) {
        HttpClient httpclient = HttpClients.createDefault();
        try {

            HttpPost httppost = new HttpPost("http://221.6.68.150:4007/Configuration/DeleteOwner");

            HttpResponse response = httpclient.execute(httppost);
            String conResult = EntityUtils.toString(response.getEntity());
            System.out.println(conResult);
        } catch (Exception e) {

        }
    }

    @Test
    public void getPhones() throws Exception {
        List<PhoneOutData> phones = service.getPhones();
        System.out.println(phones.size());
    }

    @Test
    public void sendRTUAlarm() throws Exception {

        /*  UtLzFireequipmentalarm
        Long id;                    514882081554169856
        String alarmcode;
        String equipmentname;       卷帘门监测器
        String equipmentdetialname; 电源
        String normalvalue;         1
        String alarmvalue;          不行
        Date alarmtime;             2018-11-21 19:17:34.000
        Date lastupdate;            2018-11-21 19:17:34.000
        Integer isdeal;             1
        Integer dealtype;
        Date dealtime;
        String dealuser;            思迪-测试
        String dealinfo;
        Integer isneedrepair;
        Integer isneedmaintain;     2
        String maintaindesc;
        Integer dealresult;         1
        Long unitid;                513994012173008896
        Long fireequipmentdetialid; 514378306921955328
        Long calibrationId;
        String picurl;
        Integer alarmStatus;
        String dealcode;            F20181206152423875

        unitname        思迪11-19
        unitId          513994012173008896
        */

        AlarmRTUInData inData = new AlarmRTUInData();
        inData.setStatus("1");  //报警类型
        inData.setAlarmtime("2018-11-21 19:17:34.000");     //报警时间
        inData.setAlarmvalue("不行"); //报警值
        inData.setDeviceindex("3");    //设备索引
        inData.setDeviceno("1");   //设备编号
        inData.setIoport("6"); //端口号
        inData.setIotype("2"); //端口类型
        inData.setOnwercode("201811200001");     //单位编号
        //inData.setAlarmStatus();    //报警状态
        //service.sendRTUAlarm(inData);
    }

    @Test
    public void spiltPhone() throws Exception {

        String phones = "15117931947,1,2,15117931948";
        List<String> phoneList = new ArrayList<>();

        if (Util.isEmpty(phones))
            return;

        String[] split = phones.split(",");
        System.out.println(split.length);
        for (String phone : split) {
            if (phone.matches("^1([38][0-9]|4[579]|5[0-3,5-9]|6[6]|7[0135678]|9[89])\\d{8}$"))
                phoneList.add(phone);
        }

        System.out.println(phoneList);
    }

    @Test
    public void annotationTest() {

    }
}