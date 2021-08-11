package com.zhujunji.base.domain;

import com.zhujunji.common.domain.BaseObject;
import lombok.Data;

@Data
public class SysWorkItemDO extends BaseObject {

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
     * 集合
     */
    private String collection;
}
