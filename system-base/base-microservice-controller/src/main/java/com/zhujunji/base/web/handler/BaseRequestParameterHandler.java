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
                // 通用创建请求
                if (baseRequest instanceof BaseCreateRequest) {
                    BaseCreateRequest baseCreateRequest = (BaseCreateRequest) baseRequest;
                    Long requester = baseCreateRequest.getRequester();
                    Date requestTime = baseCreateRequest.getRequestTime();
                    // 创建人
                    baseCreateRequest.setCreateBy(requester);
                    // 创建时间
                    baseCreateRequest.setCreateTime(requestTime);
                    // 通用文档创建请求
                    if (baseCreateRequest instanceof BaseJSONObjectCreateRequest) {
                        BaseJSONObjectCreateRequest baseJSONObjectCreateRequest = (BaseJSONObjectCreateRequest) baseRequest;
                        JSONObject data = baseJSONObjectCreateRequest.getData();
                        // 创建人
                        data.put("create_by", requester);
                        // 创建时间
                        data.put("create_time", requestTime);
                        // 更新人
                        data.put("update_by", requester);
                        // 更新时间
                        data.put("update_time", requestTime);
                    }
                }

                // 通用更新请求
                if (baseRequest instanceof BaseUpdateRequest) {
                    BaseUpdateRequest baseUpdateRequest = (BaseUpdateRequest) baseRequest;
                    Long requester = baseUpdateRequest.getRequester();
                    Date requestTime = baseUpdateRequest.getRequestTime();
                    // 更新人
                    baseUpdateRequest.setUpdateBy(requester);
                    // 更新时间
                    baseUpdateRequest.setUpdateTime(requestTime);
                    // 通用文档更新请求
                    if (baseUpdateRequest instanceof BaseJSONObjectUpdateRequest) {
                        BaseJSONObjectUpdateRequest baseJSONObjectUpdateRequest = (BaseJSONObjectUpdateRequest) baseUpdateRequest;
                        JSONObject data = baseJSONObjectUpdateRequest.getData();
                        // 更新人
                        data.put("update_by", requester);
                        // 更新时间
                        data.put("update_time", requestTime);
                    }
                }

            }
        }
    }
}
