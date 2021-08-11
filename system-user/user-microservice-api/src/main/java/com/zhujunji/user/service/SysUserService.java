package com.zhujunji.user.service;


import com.zhujunji.user.dto.SysUserDTO;
import com.zhujunji.user.vo.SysUserVO;

import java.util.List;

/**
 * 用户 Service
 *
 * @Author J.zhu
 * @Date 2019/7/11
 */
public interface SysUserService {
    /**
     * 保存用户
     * @param user  用户信息
     * @return boolean
     */
    boolean save(SysUserVO user);
    /**
     * 查询用户信息
     * @param userId    用户 ID
     * @return SysUser
     */
    SysUserDTO getById(Long userId);
    /**
     * 查询所有的用户列表
     * @return List<User>
     */
    List<SysUserDTO> findList();

    /**
     * 用户名获取用户
     * @param username  用户名称
     * @return SysUser
     */
    SysUserDTO getByUsername(String username);

}
