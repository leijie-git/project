package com.test;

import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

/**
 *
 * @description: mapper 测试基类
 *
 * AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)  使用外部数据库
 * MybatisTest  负责构建mybatis-mapper层的bean
 *
 **/
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BaseMapperTest extends BaseTest {
}
