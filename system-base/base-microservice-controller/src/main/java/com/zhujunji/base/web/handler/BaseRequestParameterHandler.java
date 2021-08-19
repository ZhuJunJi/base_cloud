package com.zhujunji.base.web.handler;

import com.zhujunji.base.web.utils.RequestUtil;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.request.BaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Aspect
@Component
@Order
public class BaseRequestParameterHandler {

    @Pointcut("execution(public * com.zhujunji..*.controller..*.*(..))")
    public void defaultParameterSet() {

    }

    @Before("defaultParameterSet()")
    public void doBefore(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof BaseRequest) {
                BaseRequest baseRequest = (BaseRequest) arg;
                String requestId = RequestUtil.getRequestId();
                LanguageEnum language = RequestUtil.getRequestLanguage();
                Date requestTime = new Date();
                // request 初始化
                baseRequest.init(requestId,1L,language,requestTime);
            }
        }
    }
}
