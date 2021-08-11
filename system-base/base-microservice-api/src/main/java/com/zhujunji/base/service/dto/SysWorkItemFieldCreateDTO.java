package com.zhujunji.base.service.dto;

import com.zhujunji.common.request.BaseCreateRequest;
import lombok.Data;

import java.util.List;

@Data
public class SysWorkItemFieldCreateDTO extends BaseCreateRequest {

    private Long workItemId;

    private List<Long> fieldIdList;

}
