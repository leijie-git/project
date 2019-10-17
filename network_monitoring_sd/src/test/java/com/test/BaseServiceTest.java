package com.test;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 * @description:
 *
 * SpringBootTest  构建Spring Context
 *
 **/
@SpringBootTest
public class BaseServiceTest extends BaseTest{

    /**
     * 创立websocket endpoint
     */
    @MockBean
    private ServerEndpointExporter serverEndpointExporter;
}
