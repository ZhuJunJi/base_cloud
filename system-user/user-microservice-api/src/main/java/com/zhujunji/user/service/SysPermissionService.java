package com.zhujunji.user.service;


import com.zhujunji.user.dto.SysPermissionDTO;

import java.util.List;

/**
 * 权限 Service
 *
 * @Author J.zhu
 */
public interface SysPermissionService {
    /**
     * 获取用户权限列表
     *
     * @param permissionIds 权限 IDS
     * @return List<SysPermissionDTO>
     */
    List<SysPermissionDTO> findByPermissionIds(List<Long> permissionIds);
}
