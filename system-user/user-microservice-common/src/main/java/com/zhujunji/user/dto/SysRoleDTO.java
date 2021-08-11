package com.zhujunji.user.dto;

import com.zhujunji.common.domain.BaseObject;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author J.zhu
 */
@Data
public class SysRoleDTO extends BaseObject {

    private Long roleId;

    private String roleName;

    private String roleCode;

    private String roleDescription;

    private List<SysPermissionDTO> permissionList;
}
