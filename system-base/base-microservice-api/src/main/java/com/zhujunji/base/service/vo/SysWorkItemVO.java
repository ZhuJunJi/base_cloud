package com.zhujunji.base.service.vo;

import com.zhujunji.common.domain.BaseLanguageObject;
import com.zhujunji.common.entity.Field;
import lombok.Data;

import java.util.List;

@Data
public class SysWorkItemVO extends BaseLanguageObject {

    /**
     * 描述
     */
    private String description;
    /**
     * 集合
     */
    private String collection;

    /**
     * 工作项字段列表
     */
    private List<Field<?>> fieldList;
}
