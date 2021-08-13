package com.zhujunji.common.request;

import lombok.Data;

import java.util.Date;

@Data
public class BaseCreateRequest extends BaseRequest{

    private Long createBy;

    private Date createTime;
}
