package com.zhujunji.user.service.impl;

import com.zhujunji.user.domain.SysRoleDO;
import com.zhujunji.user.domain.SysRolePermissionDO;
import com.zhujunji.user.domain.SysUserRoleDO;
import com.zhujunji.user.dto.SysPermissionDTO;
import com.zhujunji.user.dto.SysRoleDTO;
import com.zhujunji.user.mapper.SysRoleMapper;
import com.zhujunji.user.mapper.SysRolePermissionMapper;
import com.zhujunji.user.mapper.SysUserRoleMapper;
import com.zhujunji.user.service.SysPermissionService;
import com.zhujunji.user.service.SysRoleService;
import com.zhujunji.user.vo.SysRolePermission;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author J.zhu
 * @date 2020/4/19
 */
@DubboService(protocol = "dubbo", version = "1.0.0")
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private SysRoleMapper sysRoleMapper;

    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;

    @Autowired
    private SysPermissionService sysPermissionService;


    @Override
    public List<SysRoleDTO> findByUserId(Long userId) {
        if(userId == null || userId < 1){
            return Collections.emptyList();
        }
        List<SysUserRoleDO> userRoleList = sysUserRoleMapper.findByUserId(userId);

        List<Long> roleIds = userRoleList.stream()
                .map(SysUserRoleDO::getRoleId)
                .collect(Collectors.toList());

        return findByRoleIds(roleIds);
    }

    @Override
    public List<SysRoleDTO> findByRoleIds(List<Long> roleIds) {
        if(CollectionUtils.isEmpty(roleIds)){
            return Collections.emptyList();
        }
        List<SysRoleDO> sysRoleDOList =  sysRoleMapper.findByRoleIds(roleIds);

        return  sysRoleDOList.stream()
                .map(this::doToDTO)
                .collect(Collectors.toList());
    }

    private SysRoleDTO doToDTO(SysRoleDO sysRoleDO){
        if(sysRoleDO == null){
            return null;
        }
        SysRoleDTO sysRoleDTO = new SysRoleDTO();
        BeanUtils.copyProperties(sysRoleDO,sysRoleDTO);

        List<Long> permissionIdList = sysRolePermissionMapper.findByRoleId(sysRoleDO.getRoleId())
                .stream()
                .map(SysRolePermissionDO::getPermissionId)
                .collect(Collectors.toList());

        List<SysPermissionDTO> sysPermissionDTOList = sysPermissionService.findByPermissionIds(permissionIdList);
        sysRoleDTO.setPermissionList(sysPermissionDTOList);

        return sysRoleDTO;
    }
}
