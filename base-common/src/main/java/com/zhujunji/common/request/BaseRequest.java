package com.zhujunji.common.request;

import com.zhujunji.common.enums.LanguageEnum;

import java.io.Serializable;
import java.util.Date;

public interface BaseRequest extends Serializable {


    /**
     * 改方法提供统一初始换请求通用参数的处理能力，清参考 system-base 模块 controller 的
     *
     * BaseRequestParameterHandler#doBefore(JoinPoint)
     * @param requestId
     * @param requester
     * @param language
     * @param requestTime
     * @see BaseRequestParameterHandler#doBefore(JoinPoint)
     */
    void init(String requestId, Long requester, LanguageEnum language, Date requestTime);
}
