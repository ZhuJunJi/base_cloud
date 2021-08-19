package com.zhujunji.base.web.wrapper;

import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.utils.UUIDUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求参数 Wrapper
 */
public class RequestParameterWrapper extends HttpServletRequestWrapper {

    private final Map<String, String[]> parameterMap = new HashMap<>();

    public RequestParameterWrapper(HttpServletRequest request) {
        super(request);
        // 统一处理所有前端参数中首位空字符
        request.getParameterMap().forEach((k,v)->{
            for (int i = 0; i < v.length; i++) {
                if(v[i] != null){
                    v[i] = v[i].trim();
                }
                this.parameterMap.put(k,v);
            }
        });
        // 请求参数中没有 requestId
        if(!parameterMap.containsKey(GlobalConstants.REQUEST_ID) || parameterMap.get(GlobalConstants.REQUEST_ID) == null){
            parameterMap.put(GlobalConstants.REQUEST_ID, new String[]{UUIDUtil.generateUUID()});
        }
        if(!parameterMap.containsKey("language") || parameterMap.get("language") == null){
            LanguageEnum language = LanguageEnum.getByDescription(request.getHeader(GlobalConstants.ACCEPT_LANGUAGE))
                    .orElse(LanguageEnum.CHINESE);
            parameterMap.put("language", new String[]{language.name()});
        }
    }

    @Override
    public String getParameter(String name) {
        String[] value = this.parameterMap.get(name);
        return value == null ? null : value[0];
    }

    @Override
    public String[] getParameterValues(String name) {
        return this.parameterMap.get(name);
    }
}
