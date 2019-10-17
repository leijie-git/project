package com.gw.swagger2.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
public class Swagger2 {
    //swagger2的配置文件，这里可以配置swagger2的一些基本的内容，比如扫描的包等等
    @Bean("告警接收接口")
    public Docket alarmApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //配置系统信息
                .apiInfo(apiInfo())
                //子模块名称
                .groupName("告警接收接口")
                .select()
                //指定controller路径（指定注解的Controller）
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //指定controller路径（指定包路径）(两种指定选一)
                //.apis(RequestHandlerSelectors.basePackage("com.gw.alarm.controller"))
                //模块路径
               // .paths(PathSelectors.any())
                .paths(PathSelectors.regex("/webHttp.*"))
                .build();
    }
    @Bean("通用接口API")
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                //配置系统信息
                .apiInfo(apiInfo())
                //子模块名称
                .groupName("通用接口API")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.gw.openApi.common.controller"))
                //模块路径
                .paths(PathSelectors.any())
                //.paths(PathSelectors.regex("/webHttp.*"))
                .build();
    }
//    @Bean("告警接收接口")
//    public Docket createRestApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                //配置系统信息
//                .apiInfo(apiInfo())
//                //子模块名称
//                //.groupName("告警接收接口")
//                .select()
//                //指定controller路径（指定注解的Controller）
//                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                //指定controller路径（指定包路径）(两种指定选一)
//                //.apis(RequestHandlerSelectors.basePackage("com.gw.alarm.controller"))
//                //模块路径
//               // .paths(PathSelectors.any())
//                .paths(PathSelectors.regex("/webHttp.*"))
//                .build();
//    }
    //构建 api文档的详细信息函数,注意这里的注解引用的是哪个
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("思迪消防管理平台对外接口API")
                //创建人
                .contact(new Contact("Jaymon", "http://www.sd-xfy.com:8085", ""))
                //版本号
                .version("1.0")
                //描述
                .description("对外接口")
                .build();
    }


}
