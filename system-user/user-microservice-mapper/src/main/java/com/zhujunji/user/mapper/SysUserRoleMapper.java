package com.zhujunji.user.mapper;


import com.zhujunji.user.domain.SysUserRoleDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2020/4/19
 * @Author J.zhu
 */
@Repository
public interface SysUserRoleMapper {

    /**
     * 新增用户角色
     * @param sysUserRoleDO 用户的角色信息
     * @return int
     */
    int insert(SysUserRoleDO sysUserRoleDO);

    /**
     * 删除用户的角色信息
     * @param userId    用户 ID
     * @param roleId    角色 ID
     * @return  int
     */
    int deleteByUserIdAndRoleId(@Param("userId") Long userId, @Param("roleId") Long roleId);

    /**
     * 获取用户角色列表
     * @param userId    用户 ID
     * @return List<SysUserRoleDO>
     */
    List<SysUserRoleDO> findByUserId(@Param("userId") Long userId);
}
