package com.gw.alarm.controller;

import com.gw.alarm.data.PhoneOutData;
import com.gw.alarm.service.AlarmInfoService;
import com.test.MockBaseTest;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

/**
 *
 * @description: alermInfoController测试类
 *
 * WebMvcTest  这样只会构建web层或者指定的一到多个Controller的bean。
 *
 **/

@WebMvcTest(AlarmInfoController.class)
public class MockAlarmInfoControllerTest extends MockBaseTest {

    @MockBean
    private AlarmInfoService service;

    @Test
    public void getPhones() throws Exception {

        PhoneOutData phone = new PhoneOutData();
        phone.setId("id");
        phone.setMsg("msg");
        phone.setPhone("红米note3");
        phone.setStatus("status");

        Mockito.when(service.getPhones()).thenReturn(Collections.singletonList(phone));

        mvc.perform(MockMvcRequestBuilders.get("/webHttp/getPhones"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
