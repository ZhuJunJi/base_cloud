package com.zhujunji.base.service.impl;

import com.zhujunji.base.domain.SysWorkItemDO;
import com.zhujunji.base.mapper.SysWorkItemMapper;
import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.SysWorkItemService;
import com.zhujunji.base.service.dto.SysWorkItemCreateDTO;
import com.zhujunji.base.service.vo.SysWorkItemVO;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@DubboService(protocol = "dubbo", version = "1.0.0")
public class SysWorkItemServiceImpl implements SysWorkItemService {

    @Resource
    private SysWorkItemMapper sysWorkItemMapper;

    @Resource
    private SysWorkItemFieldService sysWorkItemFieldService;

    @Override
    public boolean createWorkitem(SysWorkItemCreateDTO sysWorkItemCreateDTO) {
        SysWorkItemDO sysWorkItemDO = checkParam(sysWorkItemCreateDTO);
        try {
            return sysWorkItemMapper.insert(sysWorkItemDO) > 0;
        } catch (DataAccessException e) {
            if (e instanceof DuplicateKeyException) {
                throw new CommonBizException(ExpCodeEnum.WORKITEM_EXSIT_EXCEPTION);
            }
            log.error("保存工作项信息信息失败：", e);
            throw new CommonBizException(ExpCodeEnum.DATA_ACCESS_EXCEPTION_CODE);
        }
    }

    @Override
    public SysWorkItemVO getById(Long workItemId) {
        return getById(workItemId, LanguageEnum.CHINESE);
    }

    @Override
    public SysWorkItemVO getById(Long workItemId, boolean withFields) {
        return getById(workItemId, LanguageEnum.CHINESE, withFields);
    }

    @Override
    public SysWorkItemVO getById(Long workItemId, LanguageEnum language) {
        return getById(workItemId, language, true);
    }

    @Override
    public SysWorkItemVO getById(Long workItemId, LanguageEnum language, boolean withFields) {
        SysWorkItemDO sysWorkItemDO = sysWorkItemMapper.getById(workItemId);
        if (sysWorkItemDO == null) {
            return null;
        }
        // DO TO VO
        SysWorkItemVO sysWorkItemVO = sysWorkItemDOTOWorkItemVO(sysWorkItemDO);

        if (withFields) {
            // 补全字段信息
            List<Field<?>> fieldList = sysWorkItemFieldService.findWorkItemFieldList(workItemId, language);
            sysWorkItemVO.setFieldList(fieldList);
        }
        return sysWorkItemVO;
    }

    /**
     * 校验工作项创建参数
     *
     * @param sysWorkItemCreateDTO 工作项信息
     */
    private SysWorkItemDO checkParam(SysWorkItemCreateDTO sysWorkItemCreateDTO) {
        if (sysWorkItemCreateDTO == null) {
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        if (StringUtils.isBlank(sysWorkItemCreateDTO.getCollection())) {
            throw new CommonBizException(ExpCodeEnum.WORKITEM_COLLECTION_NULL_EXCEPTION);
        }
        if (StringUtils.isBlank(sysWorkItemCreateDTO.getNameZh()) || StringUtils.isBlank(sysWorkItemCreateDTO.getNameEn())) {
            throw new CommonBizException(ExpCodeEnum.WORKITEM_NAME_NULL_EXCEPTION);
        }
        if (sysWorkItemCreateDTO.getCreateBy() == null) {
            throw new CommonBizException(ExpCodeEnum.CREATE_BY_NULL);
        }
        SysWorkItemDO sysWorkItemDO = new SysWorkItemDO();
        BeanUtils.copyProperties(sysWorkItemCreateDTO, sysWorkItemDO);
        sysWorkItemDO.setUpdateBy(sysWorkItemCreateDTO.getCreateBy());
        return sysWorkItemDO;
    }

    private SysWorkItemVO sysWorkItemDOTOWorkItemVO(SysWorkItemDO sysWorkItemDO) {
        SysWorkItemVO sysWorkItemVO = new SysWorkItemVO();
        BeanUtils.copyProperties(sysWorkItemDO, sysWorkItemVO);
        return sysWorkItemVO;
    }
}
