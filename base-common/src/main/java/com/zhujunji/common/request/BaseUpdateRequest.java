package com.zhujunji.common.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BaseUpdateRequest extends AbstractBaseRequest{

    private Long updateBy;

    private LocalDateTime updateTime;

    @Override
    public void initAfter() {
        this.updateBy = this.getRequester();
        this.updateTime = this.getRequestTime();
    }
}
