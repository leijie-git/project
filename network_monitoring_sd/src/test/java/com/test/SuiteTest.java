package com.test;

import com.gw.alarm.controller.AlarmInfoControllerTest;
import com.gw.alarm.service.AlarmInfoServiceTest;
import com.mapper.ApkInfoMapperTest;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 *
 * @description: 联合 打包测试
 *  正常情况下我们写了5个测试类，我们需要一个一个执行。
 *  打包测试，就是新增一个类，然后将我们写好的其他测试类配置在一起，
 *  然后直接运行这个类就达到同时运行其他几个测试的目的
 *
 **/

@RunWith(Suite.class)
@Suite.SuiteClasses({AlarmInfoControllerTest.class, AlarmInfoServiceTest.class, ApkInfoMapperTest.class})
public class SuiteTest {

    @BeforeClass
    public static void before(){
        System.out.println("********************* 打包测试开始 ******************************");
    }

    @AfterClass
    public static void after(){
        System.out.println("********************* 打包测试完成 ******************************");
    }
}
