package com.zhujunji.base.service.impl;

import com.zhujunji.base.domain.SysWorkItemFieldDO;
import com.zhujunji.base.mapper.SysWorkItemFieldMapper;
import com.zhujunji.base.service.SysFiledService;
import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.convert.FieldConvert;
import com.zhujunji.base.service.dto.SysWorkItemFieldCreateDTO;
import com.zhujunji.base.service.vo.SysFieldVO;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DubboService(protocol = "dubbo", version = "1.0.0")
public class SysWorkItemFieldServiceImpl implements SysWorkItemFieldService {
    @Resource
    private SysWorkItemFieldMapper sysWorkItemFieldMapper;

    @Resource
    private SysFiledService sysFiledService;

    @Resource
    private FieldConvert fieldConvert;

    @Override
    public void createWorkitemField(SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO) {
        List<SysWorkItemFieldDO> workItemFieldDOList = checkParam(sysWorkItemFieldCreateDTO);
        workItemFieldDOList.forEach(sysWorkItemFieldDO -> sysWorkItemFieldMapper.insert(sysWorkItemFieldDO));
    }

    @Override
    public List<Field<?>> findWorkItemFieldList(Long workItemId) {
        return findWorkItemFieldList(workItemId, LanguageEnum.CHINESE);
    }

    @Override
    public List<Field<?>> findWorkItemFieldList(Long workItemId, LanguageEnum language) {
        // 查询工作项字段列表
        List<SysWorkItemFieldDO> sysWorkItemFieldDOList = sysWorkItemFieldMapper.findWorkItemFieldList(workItemId);

        return sysWorkItemFieldTOField(sysWorkItemFieldDOList, language);
    }

    private List<SysWorkItemFieldDO> checkParam(SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO) {
        if (sysWorkItemFieldCreateDTO == null) {
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        if (sysWorkItemFieldCreateDTO.getCreateBy() == null) {
            throw new CommonBizException(ExpCodeEnum.CREATE_BY_NULL);
        }
        Long workItemId = sysWorkItemFieldCreateDTO.getWorkItemId();
        if (workItemId == null) {
            throw new CommonBizException(ExpCodeEnum.WORK_ITEM_FIELD_ITEM_ID_NULL_EXCEPTION);
        }
        List<Long> fieldIdList = sysWorkItemFieldCreateDTO.getFieldIdList();

        if (CollectionUtils.isEmpty(fieldIdList)) {
            throw new CommonBizException(ExpCodeEnum.WORK_ITEM_FIELD_FIELD_ID_NULL_EXCEPTION);
        }
        List<SysFieldVO> sysFieldVOList = sysFiledService.findByIdList(fieldIdList);

        return sysFieldVOList.stream()
                .map(sysFieldVO -> {
                    SysWorkItemFieldDO sysWorkItemFieldDO = new SysWorkItemFieldDO();
                    BeanUtils.copyProperties(sysFieldVO, sysWorkItemFieldDO);
                    sysWorkItemFieldDO.setWorkItemId(workItemId);
                    sysWorkItemFieldDO.setCreateBy(sysWorkItemFieldCreateDTO.getRequester());
                    return sysWorkItemFieldDO;
                })
                .collect(Collectors.toList());
    }

    /**
     * sysWorkItemFieldDOList TO List<Field<?>>
     *
     * @param sysWorkItemFieldDOList 工作项字段列表
     * @param language               语言版本
     * @return List<Field < ?>>
     */
    private List<Field<?>> sysWorkItemFieldTOField(List<SysWorkItemFieldDO> sysWorkItemFieldDOList, LanguageEnum language) {
        return sysWorkItemFieldDOList.stream()
                .map(sysWorkItemFieldDO -> sysWorkItemFieldTOField(sysWorkItemFieldDO, language))
                .collect(Collectors.toList());
    }

    /**
     * SysWorkItemFieldDO TO Field
     *
     * @param sysWorkItemFieldDO 工作项字段
     * @param language           语言版本
     * @return Field<?>
     */
    private Field<?> sysWorkItemFieldTOField(SysWorkItemFieldDO sysWorkItemFieldDO, LanguageEnum language) {
        return fieldConvert.sysFieldDOToField(sysWorkItemFieldDO, language);
    }
}
