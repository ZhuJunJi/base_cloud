package com.zhujunji.user.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class SysUserRoleDO implements Serializable {

    private Long userRoleId;

    private Long userId;

    private Long roleId;
}
