package com.zhujunji.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Dict<T> implements Serializable {

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
    private T value;
}
