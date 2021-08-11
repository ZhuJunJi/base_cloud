package com.zhujunji.user.domain;

import com.zhujunji.common.domain.BaseObject;
import lombok.Data;

/**
 * @author J.zhu
 */
@Data
public class SysRoleDO extends BaseObject {

    private Long roleId;

    private String roleName;

    private String roleCode;

    private String roleDescription;
}
