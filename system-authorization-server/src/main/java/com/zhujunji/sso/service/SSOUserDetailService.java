package com.zhujunji.sso.service;

import com.alibaba.fastjson.JSON;
import com.zhujunji.sso.domain.SSOUser;
import com.zhujunji.user.dto.SysPermissionDTO;
import com.zhujunji.user.dto.SysRoleDTO;
import com.zhujunji.user.dto.SysUserDTO;
import com.zhujunji.user.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author J.zhu
 */
@Slf4j
@Service
public class SSOUserDetailService implements UserDetailsService {

    @DubboReference(version = "1.0.0", check = false)
    private SysUserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUserDTO user = userService.getByUsername(username);
        if (null == user) {
            log.warn("用户 {} 不存在", username);
            throw new UsernameNotFoundException(username);
        }
        List<String> permissionCodeList = user.getRoleList()
                .stream()
                .map(SysRoleDTO::getPermissionList)
                .flatMap(Collection::stream)
                .map(SysPermissionDTO::getCode)
                .distinct()
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(permissionCodeList)) {
            log.warn("用户 {} 还未配置任何权限！", username);
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(String.join(",", permissionCodeList));
        log.info("SSOUserDetailService 获取 用户 {} 权限信息成功，加载权限 {}", username, JSON.toJSONString(authorityList));
        return new SSOUser(user, authorityList);
    }
}