package com.zhujunji.base.service.dto;

import com.zhujunji.common.request.BaseCreateRequest;
import lombok.Data;

import java.util.List;

@Data
public class SysWorkItemFieldCreateDTO extends BaseCreateRequest {
    /**
     * 工作项 ID
     */
    private Long workItemId;
    /**
     * 字段 ID （database sys_field.field_id）
     */
    private List<Long> fieldIdList;
}
