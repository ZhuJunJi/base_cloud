package com.zhujunji.user.domain;

import com.zhujunji.common.domain.BaseObject;
import lombok.Data;

/**
 * 权限实体类
 * @author J.zhu
 */
@Data
public class SysPermissionDO extends BaseObject {

    private Long permissionId;

    private Long pid;

    private Integer type;

    private String name;

    private String code;

    private String uri;

    private Integer seq = 1;
}
