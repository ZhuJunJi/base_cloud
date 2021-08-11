package com.zhujunji.common.entity;


import com.zhujunji.common.enums.FieldTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 字段类
 */
@Data
public class Field<T> implements Serializable {

    /**
     * 字段 ID
     */
    private Long fieldId;
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段 Key
     */
    private String fieldKey;
    /**
     * 字段描述
     */
    private String fileDes;
    /**
     * value 的形式
     */
    private FieldTypeEnum fieldType;
    /**
     * 字典类型
     */
    private String dictType;
    /**
     * 最大值
     */
    private T minValue;
    /**
     * 最小值
     */
    private T maxValue;
    /**
     * 默认值
     */
    private T defaultValue;
    /**
     * 是否必填
     */
    private Boolean required;
    /**
     * 只读
     */
    private Boolean readonly;
    /**
     * 删除标识
     */
    private Boolean deleted;
}
