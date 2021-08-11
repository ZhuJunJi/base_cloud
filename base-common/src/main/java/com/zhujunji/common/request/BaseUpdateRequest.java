package com.zhujunji.common.request;

import lombok.Data;

@Data
public class BaseUpdateRequest extends BaseRequest{

    private Long updateBy;
}
