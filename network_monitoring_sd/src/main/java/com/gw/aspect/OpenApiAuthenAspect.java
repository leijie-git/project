package com.gw.aspect;

import com.gw.mapper.UtUnitUserMapper;
import com.gw.mapper.entity.UtUnitUser;
import com.gw.openApi.common.data.CheckAcountBaseData;
import org.apache.commons.codec.digest.DigestUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Aspect
@Component
@ComponentScan
public class OpenApiAuthenAspect {
    private final static Logger logger = LoggerFactory.getLogger(OpenApiAuthenAspect.class);
    @Autowired
    private UtUnitUserMapper unitUserMapper;

    /**
     * A:@Pointcut("execution(* com.aijava.springcode.service..*.*(..))")
     * 第一个*表示匹配任意的方法返回值，..(两个点)表示零个或多个，上面的第一个..表示service包及其子包,第二个*表示所有类,第三个*表示所有方法，第二个..表示方法的任意参数个数
     * B:@Pointcut("within(com.aijava.springcode.service.*)")
     * within限定匹配方法的连接点,上面的就是表示匹配service包下的任意连接点
     * C:@Pointcut("this(com.aijava.springcode.service.UserService)")
     * this用来限定AOP代理必须是指定类型的实例，如上，指定了一个特定的实例，就是UserService
     * D:@Pointcut("bean(userService)") bean也是非常常用的,bean可以指定IOC容器中的bean的名称
     */
    @Pointcut("execution(public * com.gw.openApi.common.controller.*.*(..)) ")
    public void doAuthen() {
    }

    @Before("doAuthen()")
    public void doBefore(JoinPoint joinPoint) throws Exception {
        // ------------API测试-------------
        Object[] paramArgs = joinPoint.getArgs();
        logger.info("=====CheckAccountAspect-className:" + joinPoint.getTarget().getClass().getName());
        logger.info("=====Aspect-methodName:" + joinPoint.getSignature().getName());
        // ------------API测试-------------
        CheckAcountBaseData baseData = null;
        for (int i = 0;i<paramArgs.length;i++) {
            if(paramArgs[i]instanceof CheckAcountBaseData){
                baseData = (CheckAcountBaseData) paramArgs[i];
                break;
            }
        }
        if(null != baseData){
            String password = baseData.getPassword();
            String md5Hex = DigestUtils.md5Hex(password);
            Example example = new Example(UtUnitUser.class);
            example.createCriteria().andEqualTo("account", baseData.getAccount()).andEqualTo("password", md5Hex)
                    .andEqualTo("isdelete", "0");
            List<UtUnitUser> userList = unitUserMapper.selectByExample(example);
            if(null!= userList && userList.size() != 0){
                baseData.setAccountChecked(true);
                baseData.setUserId(userList.get(0).getId().toString());
            }else{
                baseData.setAccountChecked(false);
            }
        }

    }

}
