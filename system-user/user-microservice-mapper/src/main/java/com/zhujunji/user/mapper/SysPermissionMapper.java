package com.zhujunji.user.mapper;


import com.zhujunji.user.domain.SysPermissionDO;
import com.zhujunji.user.vo.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 权限Mapper
 *
 * @Author J.zhu
 * @Date 2020/4/19
 */
@Repository
public interface SysPermissionMapper {

    /**
     * 新增权限信息
     * @param sysPermissionDO   权限信息
     * @return int
     */
    int insert(SysPermissionDO sysPermissionDO);

    /**
     * 查询权限信息列表
     * @param permissionIds 权限 IDS
     * @return List<SysPermissionDO>
     */
    List<SysPermissionDO> findByPermissionIds(@Param("permissionIds") List<Long> permissionIds);
}
