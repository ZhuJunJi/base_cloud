package com.zhujunji.base.service.dto;

import com.zhujunji.common.request.BaseCreateRequest;
import lombok.Data;

/**
 * @Author J.zhu
 */
@Data
public class SysDictCreateDTO extends BaseCreateRequest {
    /**
     * 父 ID
     **/
    private Long parentId;
    /**
     * 类型
     **/
    private String type;
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
