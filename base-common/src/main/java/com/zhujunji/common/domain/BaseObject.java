package com.zhujunji.common.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * BaseEntity
 */
@Data
public class BaseObject implements Serializable {
    /**
     * 主键 ID
     */
    private Long id;
    /**
     * 更新者
     **/
    private Long updateBy;

    /**
     * 修改时间
     **/
    private Date updateTime;

    /**
     * 创建者
     **/
    private Long createBy;

    /**
     * 创建时间
     **/
    private Date createTime;

    /**
     * 删除标识
     **/
    private Boolean deleted;
}
