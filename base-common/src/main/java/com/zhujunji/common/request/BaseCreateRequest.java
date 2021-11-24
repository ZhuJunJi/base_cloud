package com.zhujunji.common.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseCreateRequest extends AbstractBaseRequest{

    private Long createBy;

    private LocalDateTime createTime;

    @Override
    public void initAfter() {
        this.createBy = this.getRequester();
        this.createTime = this.getRequestTime();
    }
}
