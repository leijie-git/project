package com.mapper;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @description:    @MybatisTest将检测一个默认注释了@SpringBootApplication的类。
 * 因此，根据bean定义方法，可能会出现意外错误，或者将不必要的组件加载到ApplicationContext中。
 * 通过将@SpringBootApplication类创建到与test类相同的包中，可以防止这种行为:
 **/

@SpringBootApplication
@MapperScan(basePackages = "com.gw.mapper")
public class MapperTestApplication {
}
