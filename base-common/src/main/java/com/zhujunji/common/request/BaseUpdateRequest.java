package com.zhujunji.common.request;

import lombok.Data;

import java.util.Date;

@Data
public class BaseUpdateRequest extends BaseRequest{

    private Long updateBy;

    private Date updateTime;
}
