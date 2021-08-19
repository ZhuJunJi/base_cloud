package com.zhujunji.common.request;

import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;

import java.util.Date;

@Data
public class BaseUpdateRequest extends AbstractBaseRequest{

    private Long updateBy;

    private Date updateTime;

    @Override
    public void init(String requestId, Long requester, LanguageEnum language, Date requestTime) {
        super.init(requestId, requester, language, requestTime);
        this.updateBy = this.getRequester();
        this.updateTime = this.getRequestTime();
    }
}
