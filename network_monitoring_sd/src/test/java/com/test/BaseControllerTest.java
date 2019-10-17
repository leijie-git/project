package com.test;

import com.gw.WebApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/**
 *
 * @description:
 *
 * AutoConfigureMockMvc    获得自动配置的MockMvc实例。
 * SpringBootTest(classes = WebApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)  构建Spring Context，启动web容器并监听随机端口
 *
 **/
@AutoConfigureMockMvc
@SpringBootTest(classes = WebApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseControllerTest extends BaseTest {

    @Autowired
    protected MockMvc mvc;
}
