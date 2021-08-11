package com.zhujunji.common.request;

import lombok.Data;

@Data
public class BaseCreateRequest extends BaseRequest{

    private Long createBy;
}
