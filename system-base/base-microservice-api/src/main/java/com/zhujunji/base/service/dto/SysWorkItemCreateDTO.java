package com.zhujunji.base.service.dto;

import com.zhujunji.common.request.BaseCreateRequest;
import lombok.Data;

@Data
public class SysWorkItemCreateDTO extends BaseCreateRequest {
    /**
     * 工作项名称（中文）
     */
    private String nameZh;
    /**
     * 工作项名称（英文）
     */
    private String nameEn;
    /**
     * 工作项描述
     */
    private String description;
    /**
     * 工作项数据保存的集合
     */
    private String collection;
}
