package com.zhujunji.base.web.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhujunji.base.web.utils.RequestUtil;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.request.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.Ordered;
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
                // 设置请求 ID
                if (StringUtils.isBlank(baseRequest.getRequestId())) {
                    baseRequest.setRequestId(RequestUtil.getRequestId());
                }
                // 请求语言
                if (baseRequest.getLanguage() == null) {
                    baseRequest.setLanguage(RequestUtil.getRequestLanguage());
                }
                // 设置请求时间
                if (baseRequest.getRequestTime() == null) {
                    baseRequest.setRequestTime(new Date());
                }
                if (baseRequest.getRequester() == null) {
                    baseRequest.setRequester(1L);
                }
                if (baseRequest instanceof BaseCreateRequest) {
                    BaseCreateRequest baseCreateRequest = (BaseCreateRequest) baseRequest;
                    Long requester = baseCreateRequest.getRequester();
                    baseCreateRequest.setCreateBy(requester);

                    if (baseCreateRequest instanceof BaseJSONObjectCreateRequest) {
                        BaseJSONObjectCreateRequest baseJSONObjectCreateRequest = (BaseJSONObjectCreateRequest) baseRequest;
                        JSONObject data = baseJSONObjectCreateRequest.getData();
                        Date requestTime = baseJSONObjectCreateRequest.getRequestTime();
                        data.put("create_by", requester);
                        data.put("create_time", requestTime);
                        data.put("update_by", requester);
                        data.put("update_time", requestTime);
                    }
                }

                if (baseRequest instanceof BaseUpdateRequest) {
                    BaseUpdateRequest baseUpdateRequest = (BaseUpdateRequest) baseRequest;
                    Long requester = baseUpdateRequest.getRequester();
                    baseUpdateRequest.setUpdateBy(requester);
                    if (baseUpdateRequest instanceof BaseJSONObjectUpdateRequest) {
                        BaseJSONObjectUpdateRequest baseJSONObjectUpdateRequest = (BaseJSONObjectUpdateRequest) baseUpdateRequest;
                        JSONObject data = baseJSONObjectUpdateRequest.getData();
                        Date requestTime = baseJSONObjectUpdateRequest.getRequestTime();
                        data.put("update_by", requester);
                        data.put("update_time", requestTime);
                    }
                }

            }
        }
    }
}
