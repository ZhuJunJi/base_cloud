package com.zhujunji.user.service.impl;


import com.zhujunji.user.domain.SysPermissionDO;
import com.zhujunji.user.dto.SysPermissionDTO;
import com.zhujunji.user.mapper.SysPermissionMapper;
import com.zhujunji.user.service.SysPermissionService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author J.zhu
 */
@DubboService(protocol = "dubbo", version = "1.0.0")
public class SysPermissionServiceImpl implements SysPermissionService {

    @Autowired
    private SysPermissionMapper permissionMapper;

    @Override
    public List<SysPermissionDTO> findByPermissionIds(List<Long> permissionIds) {
        if(CollectionUtils.isEmpty(permissionIds)){
            return Collections.emptyList();
        }
        return permissionMapper.findByPermissionIds(permissionIds)
                .stream()
                .map(this::doToDTO)
                .collect(Collectors.toList());
    }

    /**
     * DO TO DTO
     * @param sysPermissionDO
     * @return SysPermissionDTO
     */
    private SysPermissionDTO doToDTO(SysPermissionDO sysPermissionDO){
        if(sysPermissionDO == null){
            return null;
        }
        SysPermissionDTO sysPermissionDTO = new SysPermissionDTO();
        BeanUtils.copyProperties(sysPermissionDO,sysPermissionDTO);
        return sysPermissionDTO;
    }
}
