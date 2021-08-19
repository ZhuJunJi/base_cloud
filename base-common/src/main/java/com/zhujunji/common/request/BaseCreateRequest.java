package com.zhujunji.common.request;

import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;

import java.util.Date;

@Data
public class BaseCreateRequest extends AbstractBaseRequest{

    private Long createBy;

    private Date createTime;

    @Override
    public void init(String requestId, Long requester, LanguageEnum language, Date requestTime) {
        super.init(requestId, requester, language, requestTime);
        this.createBy = this.getRequester();
        this.createTime = this.getRequestTime();
    }
}
