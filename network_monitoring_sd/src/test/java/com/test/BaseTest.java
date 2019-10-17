package com.test;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @description: test总基类
 *
 *  RunWith(SpringRunner.class) 声明在Spring的环境中进行单元测试
 **/
@RunWith(SpringRunner.class)
public class BaseTest {

    @Before
    public void before(){
        System.out.println("********************* 测试开始 ******************************");
    }

    @After
    public void after(){
        System.out.println("********************* 测试完成 ******************************");
    }
}
