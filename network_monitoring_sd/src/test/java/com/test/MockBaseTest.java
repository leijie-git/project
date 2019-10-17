package com.test;

import org.mybatis.spring.boot.test.autoconfigure.AutoConfigureMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @description: mock 测试基类，继承此基类构建web容器
 *
 *  Mock的使用场景
 *
 * 多人协作时，可以通过mock进行无等待的测试先行。
 * 当测试目标的依赖对象需要访问外部的服务，而外部服务不易获得时，可以通过mock来模拟服务可用。
 * 当在排查不容易复现的问题场景时，通过mock来模拟问题。
 *
 * AutoConfigureMybatis 如果您使用 @mybatistest 和其他@***测试(例如:@ webmvctest)，请考虑使用 @autoconfiguremybatis，因为@***Test注释不能在同一个测试上指定两个或多个。
 **/

@AutoConfigureMybatis
public class MockBaseTest extends BaseTest {

    @Autowired
    protected MockMvc mvc;

    /**
     * 创立websocket endpoint
     */
    @MockBean
    protected ServerEndpointExporter serverEndpointExporter;
}
