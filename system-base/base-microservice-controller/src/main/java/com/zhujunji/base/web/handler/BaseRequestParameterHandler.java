package com.zhujunji.base.web.handler;

import com.zhujunji.base.web.utils.RequestUtil;
import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.handler.AccessLimitExceptionHandler;
import com.zhujunji.common.request.BaseRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collection;


/**
 * controller 切面
 * @Date 2021-11-24
 * @Author J.zhu
 */
@Slf4j
@Aspect
@Component
@Order
public class BaseRequestParameterHandler {

    @Resource
    private Collection<AccessLimitExceptionHandler> accessLimitExceptionHandlerList;

    @Pointcut("execution(public * com.zhujunji..*.controller..*.*(..))")
    public void defaultParameterSet() {

    }

    @Before("defaultParameterSet()")
    public void doBefore(JoinPoint joinPoint) {
        HttpServletRequest request = RequestUtil.getHttpServletRequest();
        // 恶意请求处理
        accessLimitExceptionHandle(request);

        Object[] args = joinPoint.getArgs();
        // BaseRequestParam 处理
        baseRequestParamHandle(args, request);
    }

    /**
     * 恶意请求处理
     * @param request
     */
    public void accessLimitExceptionHandle(HttpServletRequest request){
        if(CollectionUtils.isNotEmpty(accessLimitExceptionHandlerList)){
            String uri = request.getRequestURI();
            String ip = request.getRemoteAddr();
            Long timeStamp = RequestUtil.getRequestTimeStamp(request);
            String nonce = RequestUtil.getNonce(request);
            accessLimitExceptionHandlerList.forEach(accessLimitExceptionHandler ->
                    accessLimitExceptionHandler.check(uri, ip, timeStamp, nonce));
        }
    }

    /**
     * BaseRequest 参数处理
     * @param args
     * @param request
     */
    public void baseRequestParamHandle(Object[] args, HttpServletRequest request){
        for (Object arg : args) {
            if (arg instanceof BaseRequest) {
                BaseRequest baseRequest = (BaseRequest) arg;
                Long timeStamp = RequestUtil.getRequestTimeStamp(request);
                String requestId = RequestUtil.getRequestId(request);
                LanguageEnum language = RequestUtil.getRequestLanguage(request);
                LocalDateTime requestTime = LocalDateTime.ofEpochSecond(timeStamp / 1000, 0,
                        GlobalConstants.ZONE_CN_SHANGHAI);
                baseRequest.init(requestId, 1L, language, requestTime);
            }
        }
    }
}
