package com.zhujunji.common.entity;

import lombok.Data;

import java.util.List;

@Data
public class PicklistField<T> extends Field<T> {
    /**
     * 选项字段的
     */
    private List<Dict<T>> options;
}
