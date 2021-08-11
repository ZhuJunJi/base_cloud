package com.zhujunji.base.service.dto;

import com.zhujunji.common.request.BaseUpdateRequest;
import lombok.Data;

@Data
public class SysDictUpdateDTO extends BaseUpdateRequest {

    /**
     * 字段主键
     */
    private Long dictId;
    /**
     * 父 ID
     **/
    private Long parentId;
    /**
     * 中文标签
     **/
    private String labelZh;
    /**
     * 英文标签
     **/
    private String labelEn;
    /**
     * 值
     **/
    private String value;
    /**
     * 排序号
     */
    private Integer sort;
}
