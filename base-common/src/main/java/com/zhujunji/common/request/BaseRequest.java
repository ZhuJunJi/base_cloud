package com.zhujunji.common.request;

import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class BaseRequest implements Serializable {

    /**
     * 请求 ID
     */
    private String requestId;
    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 请求者
     */
    private Long requester;

    private LanguageEnum language;

    public BaseRequest(){
        this.requestTime = new Date();
    }
}
