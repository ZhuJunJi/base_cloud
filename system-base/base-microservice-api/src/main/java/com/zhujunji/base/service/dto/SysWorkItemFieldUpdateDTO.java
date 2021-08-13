package com.zhujunji.base.service.dto;

import com.zhujunji.common.request.BaseUpdateRequest;
import lombok.Data;

@Data
public class SysWorkItemFieldUpdateDTO extends BaseUpdateRequest {
    /**
     * 主键 ID （database sys_workitem_field）
     */
    private Long id;
    /**
     * 字段描述
     */
    private String fieldDes;
    /**
     * 字段名称（中文）
     */
    private String nameZh;
    /**
     * 字段名称（英文）
     */
    private String nameEn;
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

    public boolean notUpdate(){
        return null == fieldDes
                && null == nameZh
                && null == nameEn
                && null == minValue
                && null == maxValue
                && null == defaultValue
                && null == required
                && null == readonly
                && null == areaGroup
                && null == sort;
    }

    public boolean hasUpdate(){
        return !notUpdate();
    }
}
