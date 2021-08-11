package com.zhujunji.base.service.vo;

import com.zhujunji.common.domain.BaseObject;
import lombok.Data;

/**
 * 字典实体类
 */
@Data
public class SysDictVO extends BaseObject {
    /**
     * 自增主键
     **/
    private Long dictId;
    /**
     * 父 ID
     **/
    private Long parentId;
    /**
     * 所有父 IDS
     **/
    private String parentIds;
    /**
     * 类型
     **/
    private String type;
    /**
     * 标签
     **/
    private String label;
    /**
     * 值
     **/
    private String value;
    /**
     * 排序号
     */
    private Integer sort;
}
