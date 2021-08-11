package com.zhujunji.base.web.utils;

import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.enums.LanguageEnum;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static com.zhujunji.common.constant.GlobalConstants.ACCEPT_LANGUAGE;

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

    public static LanguageEnum getRequestLanguage(){
        HttpServletRequest request = getHttpServletRequest();
        String acceptLanguage = request.getHeader(ACCEPT_LANGUAGE);
        // acceptLanguage 没有找到相应语言时，默认设置为中文
        return LanguageEnum.getByDescription(acceptLanguage, LanguageEnum.CHINESE);
    }

    private static HttpServletRequest getHttpServletRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        Assert.notNull(requestAttributes,"RequestAttributes 空异常，RequestUtil 只能在 Controller 层使用！");
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) requestAttributes;
        return servletRequestAttributes.getRequest();
    }
}
