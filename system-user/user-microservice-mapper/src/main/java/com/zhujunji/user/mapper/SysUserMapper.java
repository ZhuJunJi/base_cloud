package com.zhujunji.user.mapper;

import com.zhujunji.user.domain.SysUserDO;
import com.zhujunji.user.vo.SysUserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 * @Author J.zhu
 * @Date 2019/7/11
 */
@Repository
public interface SysUserMapper {
    /**
     * 保存用户
     * @param user  用户信息
     * @return Integer
     */
    Integer insert(SysUserVO user);
    /**
     * 查询用户信息
     * @param userId    用户 ID
     * @return SysUser
     */
    SysUserDO getById(@Param("userId") Long userId);
    /**
     * 查询所有的用户列表
     * @return List<SysUser>
     */
    List<SysUserDO> findList();

    /**
     * 用户帐号查询用户信息
     * @param username  用户名称
     * @return SysUser
     */
    SysUserDO getByUsername(@Param("username") String username);


}
