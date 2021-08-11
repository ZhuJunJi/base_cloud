package com.zhujunji.user.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysRolePermissionDO implements Serializable {

    private Long rolePermissionId;

    private Long roleId;

    private Long permissionId;
}
