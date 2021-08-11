package com.zhujunji.base.service.dto;

import com.zhujunji.common.enums.FieldTypeEnum;
import com.zhujunji.common.request.BaseCreateRequest;
import lombok.Data;

@Data
public class SysFieldCreateDTO extends BaseCreateRequest {
    /**
     * 字段 Key
     */
    private String fieldKey;
    /**
     * 字段名称（中文）
     */
    private String nameZh;
    /**
     * 字段名称（英文）
     */
    private String nameEn;
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
