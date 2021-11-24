package com.zhujunji.common.request;

import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;

@Data
public abstract class AbstractBaseRequest implements BaseRequest {

    /**
     * 请求 ID
     */
    private String requestId;
    /**
     * 请求时间
     */
    private LocalDateTime requestTime;
    /**
     * 请求者
     */
    private Long requester;

    private LanguageEnum language;

    @Override
    public void initBefore() {
        // default do noting support child override
    }

    @Override
    public void init(String requestId, Long requester, LanguageEnum language, LocalDateTime requestTime) {
        // 提供子类扩展初始化前处理逻辑方法
        initBefore();
        if (StringUtils.isBlank(this.requestId)) {
            this.requestId = requestId;
        }
        if (null == this.requester) {
            this.requester = requester;
        }
        if (null == this.language) {
            this.language = language;
        }
        this.requestTime = requestTime;
        // 提供子类扩展初始化后处理逻辑方法
        initAfter();
    }

    @Override
    public void initAfter() {
        // default do noting support child override
    }
}
