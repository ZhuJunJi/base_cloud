package com.zhujunji.user.mapper;

import com.zhujunji.user.domain.SysRolePermissionDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysRolePermissionMapper {

    /**
     * 查询角色权限信息
     * @param roleId    角色 ID
     * @return List<SysRolePermissionDO>
     */
    List<SysRolePermissionDO> findByRoleId(Long roleId);
}
