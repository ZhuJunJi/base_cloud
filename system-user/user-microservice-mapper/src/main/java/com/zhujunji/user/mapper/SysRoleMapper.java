package com.zhujunji.user.mapper;


import com.zhujunji.user.domain.SysRoleDO;
import com.zhujunji.user.vo.SysRole;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Date 2020/4/19
 * @Author J.zhu
 */
@Repository
public interface SysRoleMapper {

    /**
     * 新增角色信息
     * @param sysRoleDO 角色信息
     * @return int
     */
    int insert(SysRoleDO sysRoleDO);

    /**
     * 查询角色信息
     * @param roleIds   角色 IDS
     * @return List<SysRoleDO>
     */
    List<SysRoleDO> findByRoleIds(@Param("roleIds") List<Long> roleIds);
}
