package com.zhujunji.common.request;

import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

@Data
public abstract class AbstractBaseRequest implements BaseRequest {

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

    @Override
    public void init(String requestId, Long requester, LanguageEnum language, Date requestTime) {
        if (StringUtils.isBlank(this.requestId)) {
            this.requestId = requestId;
        }
        if (null == this.requester) {
            this.requester = requester;
        }
        if (null == this.language) {
            this.language = language;
        }
        if (null == this.requestTime) {
            this.requestTime = requestTime;
        }
    }
}
