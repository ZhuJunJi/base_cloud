package com.zhujunji.base.web.utils;

import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RequestUtil {

    /**
     * 获取请求 ID
     * @return String
     */
    public static String getRequestId(){
        return getRequestId(getHttpServletRequest());
    }

    /**
     * 获取请求 ID
     * @param request   HttpServletRequest
     * @return String
     */
    public static String getRequestId(HttpServletRequest request){
        return request.getParameter(GlobalConstants.REQUEST_ID);
    }

    public static Long getRequester(){
        HttpServletRequest request = getHttpServletRequest();
        return getRequester(request);
    }

    public static Long getRequester(HttpServletRequest request){
        String authorization = request.getHeader(GlobalConstants.AUTHORIZATION);
        return null;
    }

    public static LanguageEnum getRequestLanguage(HttpServletRequest request){
        String acceptLanguage = request.getHeader(GlobalConstants.ACCEPT_LANGUAGE);
        // acceptLanguage 没有找到相应语言时，默认设置为中文
        return LanguageEnum.getByDescription(acceptLanguage, LanguageEnum.CHINESE);
    }

    public static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Assert.notNull(requestAttributes,"RequestAttributes 空异常，RequestUtil 只能在 Controller 层使用！");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }

    public static Long getRequestTimeStamp(HttpServletRequest request) {
        String timeStamp = request.getHeader(GlobalConstants.TIME_STAMP);
        try {
            return Long.parseLong(timeStamp);
        }catch (NumberFormatException e){
            log.error("请求时间戳: {} 格式错误！请传入正确的时间戳",timeStamp);
        }
        throw new CommonBizException(ExpCodeEnum.TIME_STAMP_PARSE_EXCEPTION);
    }

    public static String getNonce(HttpServletRequest request) {
        return request.getHeader(GlobalConstants.NONCE);
    }
}
