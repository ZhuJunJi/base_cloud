package com.zhujunji.user.service;



import com.zhujunji.user.dto.SysRoleDTO;
import com.zhujunji.user.vo.SysRole;

import java.util.List;

/**
 * 角色 Service
 *
 * @Author J.zhu
 */
public interface SysRoleService {
    /**
     * 获取用户角色列表
     * @param userId    用户 ID
     * @return List<SysRole>
     */
    List<SysRoleDTO> findByUserId(Long userId);

    /**
     * 查询角色列表
     * @param roleIds
     * @return List<SysRoleDTO>
     */
    List<SysRoleDTO> findByRoleIds(List<Long> roleIds);
}
