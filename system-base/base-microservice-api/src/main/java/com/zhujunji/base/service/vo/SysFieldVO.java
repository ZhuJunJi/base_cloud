package com.zhujunji.base.service.vo;

import com.zhujunji.common.domain.BaseLanguageObject;
import com.zhujunji.common.enums.FieldTypeEnum;
import lombok.Data;

@Data
public class SysFieldVO extends BaseLanguageObject {
    /**
     * 字段 ID
     */
    private Long fieldId;
    /**
     * 字段 Key
     */
    private String fieldKey;
    /**
     * 字段描述
     */
    private String fieldDes;
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
    private String minValue;
    /**
     * 最小值
     */
    private String maxValue;
    /**
     * 默认值
     */
    private String defaultValue;
    /**
     * 是否必填
     */
    private Boolean required;
    /**
     * 只读
     */
    private Boolean readonly;
    /**
     * 展示区域分组
     */
    private String areaGroup;
    /**
     * 排序编号
     */
    private Integer sort;
}
