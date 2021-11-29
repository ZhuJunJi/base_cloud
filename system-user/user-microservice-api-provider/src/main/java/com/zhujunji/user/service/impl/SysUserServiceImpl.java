package com.zhujunji.user.service.impl;


import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import com.zhujunji.user.domain.SysUserDO;
import com.zhujunji.user.dto.SysRoleDTO;
import com.zhujunji.user.dto.SysUserDTO;
import com.zhujunji.user.mapper.SysUserMapper;
import com.zhujunji.user.service.SysRoleService;
import com.zhujunji.user.service.SysUserService;
import com.zhujunji.user.vo.SysUserVO;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author J.zhu
 * @date 2019/7/11
 */
@DubboService(version = "1.0.0")
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private SysRoleService roleService;

    @Resource
    private Environment environment;

    @Override
    public boolean save(SysUserVO user) {
        return sysUserMapper.insert(user) < 1;
    }

    @Override
    public SysUserDTO getById(Long userId) {
        if(userId == null || userId < 1){
            return null;
        }
        if(userId == 2){
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        SysUserDO sysUserDO = sysUserMapper.getById(userId);
        return doToDTO(sysUserDO);
    }

    @Override
    public List<SysUserDTO> findList() {
        List<SysUserDO> sysUserDOList = sysUserMapper.findList();
        return sysUserDOList.stream()
                .map(this::doToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SysUserDTO getByUsername(String username) {
        SysUserDO sysUserDO = sysUserMapper.getByUsername(username);
        return doToDTO(sysUserDO);
    }

    private SysUserDTO doToDTO(SysUserDO sysUserDO){
        if(sysUserDO == null || sysUserDO.getUserId() == null){
            return null;
        }

        SysUserDTO sysUserDTO = new SysUserDTO();
        BeanUtils.copyProperties(sysUserDO,sysUserDTO);
        // 查询角色信息
        List<SysRoleDTO> roleDTOList = roleService.findByUserId(sysUserDO.getUserId());
        sysUserDTO.setRoleList(roleDTOList);
        return sysUserDTO;
    }
}
