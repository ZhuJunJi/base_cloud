package com.zhujunji.base.service.impl;

import com.zhujunji.base.domain.SysFieldDO;
import com.zhujunji.base.domain.SysWorkItemFieldDO;
import com.zhujunji.base.mapper.SysWorkItemFieldMapper;
import com.zhujunji.base.service.SysFiledService;
import com.zhujunji.base.service.SysWorkItemFieldService;
import com.zhujunji.base.service.SysWorkItemService;
import com.zhujunji.base.service.convert.FieldConvert;
import com.zhujunji.base.service.dto.SysWorkItemFieldCreateDTO;
import com.zhujunji.base.service.dto.SysWorkItemFieldUpdateDTO;
import com.zhujunji.base.service.vo.SysFieldVO;
import com.zhujunji.base.service.vo.SysWorkItemFieldVO;
import com.zhujunji.base.service.vo.SysWorkItemVO;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@DubboService(protocol = "dubbo", version = "1.0.0")
public class SysWorkItemFieldServiceImpl implements SysWorkItemFieldService {

    @Resource
    private FieldConvert fieldConvert;

    @Resource
    private SysFiledService sysFiledService;

    @Resource
    private SysWorkItemService sysWorkItemService;

    @Resource
    private SysWorkItemFieldMapper sysWorkItemFieldMapper;

    @Override
    public void create(SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO) {
        List<SysWorkItemFieldDO> workItemFieldDOList = checkParam(sysWorkItemFieldCreateDTO);
        workItemFieldDOList.forEach(sysWorkItemFieldDO -> sysWorkItemFieldMapper.insert(sysWorkItemFieldDO));
    }

    @Override
    public boolean update(SysWorkItemFieldUpdateDTO sysWorkItemFieldUpdateDTO) throws CommonBizException {
        Optional<SysWorkItemFieldDO> updateOptional = checkParam(sysWorkItemFieldUpdateDTO);
        return updateOptional
                .map(sysWorkItemFieldDO -> sysWorkItemFieldMapper.update(sysWorkItemFieldDO) > 0)
                .orElse(false);

    }

    @Override
    public List<SysWorkItemFieldVO> findWorkItemFieldVOList(Long workItemId) {
        if(null == workItemId || workItemId < 1){
            return Collections.emptyList();
        }
        // 查询数据库
        List<SysWorkItemFieldDO> sysWorkItemFieldDOList = sysWorkItemFieldMapper.findWorkItemFieldList(workItemId);
        // 转换为 VO
        return sysWorkItemFieldDOTOVO(sysWorkItemFieldDOList);
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

    private Optional<SysWorkItemFieldDO> checkParam(SysWorkItemFieldUpdateDTO sysWorkItemFieldUpdateDTO) {
        if(null == sysWorkItemFieldUpdateDTO){
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        if(null == sysWorkItemFieldUpdateDTO.getId()){
            throw new CommonBizException(ExpCodeEnum.WORK_ITEM_FIELD_FIELD_ID_NULL_EXCEPTION);
        }
        if(null == sysWorkItemFieldUpdateDTO.getUpdateBy()){
            throw new CommonBizException(ExpCodeEnum.UPDATE_BY_NULL);
        }
        if(sysWorkItemFieldUpdateDTO.notUpdate()){
            return Optional.empty();
        }

        Optional<SysWorkItemFieldDO> optional = Optional.ofNullable(sysWorkItemFieldUpdateDTO.hasUpdate() ?
                new SysWorkItemFieldDO() : null);

        optional.ifPresent(sysWorkItemFieldDO -> {
            BeanUtils.copyProperties(sysWorkItemFieldUpdateDTO,sysWorkItemFieldDO);
        });
        return optional;

    }

    private List<SysWorkItemFieldDO> checkParam(SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO) {
        if (sysWorkItemFieldCreateDTO == null) {
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }

        Long createBy = sysWorkItemFieldCreateDTO.getCreateBy();
        if (null == createBy) {
            throw new CommonBizException(ExpCodeEnum.CREATE_BY_NULL);
        }

        Long workItemId = sysWorkItemFieldCreateDTO.getWorkItemId();
        if (null == workItemId) {
            throw new CommonBizException(ExpCodeEnum.WORK_ITEM_FIELD_ITEM_ID_NULL_EXCEPTION);
        }
        SysWorkItemVO sysWorkItemVO = sysWorkItemService.getById(workItemId,false);
        if(null == sysWorkItemVO){
            // 工作项不存在
            throw new CommonBizException(ExpCodeEnum.WORKITEM_NOT_EXSIT_EXCEPTION);
        }

        List<Long> fieldIdList = sysWorkItemFieldCreateDTO.getFieldIdList();
        if (fieldIdList == null) {
            throw new CommonBizException(ExpCodeEnum.WORK_ITEM_FIELD_FIELD_ID_NULL_EXCEPTION);
        }
        // 查询工作项已有字段
        List<SysWorkItemFieldDO> sysWorkItemFieldDOList = sysWorkItemFieldMapper.findWorkItemFieldList(workItemId);
        List<Long>  _fieldIdList = sysWorkItemFieldDOList.stream().map(SysFieldDO::getFieldId).collect(Collectors.toList());

        List<Long> addFieldIdList = fieldIdList.stream()
                // 过滤出工作项中没有的字段
                .filter(fieldId->!_fieldIdList.contains(fieldId))
                .collect(Collectors.toList());
        // 需要新增的工作项字段
        List<SysFieldVO> addSysFieldVOList = sysFiledService.findByIdList(addFieldIdList);

        return addSysFieldVOList.stream()
                // 转换为 VO
                .map(sysFieldVO -> {
                    SysWorkItemFieldDO sysWorkItemFieldDO = new SysWorkItemFieldDO();
                    BeanUtils.copyProperties(sysFieldVO, sysWorkItemFieldDO);
                    sysWorkItemFieldDO.setWorkItemId(workItemId);
                    sysWorkItemFieldDO.setCreateBy(createBy);
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

    /**
     * List<SysWorkItemFieldDO> TO List<SysWorkItemFieldVO>
     * @param sysWorkItemFieldDOList 工作项字段持久信息
     * @return List<SysWorkItemFieldVO>
     */
    private List<SysWorkItemFieldVO> sysWorkItemFieldDOTOVO(List<SysWorkItemFieldDO> sysWorkItemFieldDOList){
        if(CollectionUtils.isEmpty(sysWorkItemFieldDOList)){
            return Collections.emptyList();
        }
        return sysWorkItemFieldDOList.stream()
                .map(this::sysWorkItemFieldDOTOVO)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
    /**
     * SysWorkItemFieldDO TO SysWorkItemFieldVO
     * @param sysWorkItemFieldDO 工作项字段持久信息
     * @return SysWorkItemFieldVO
     */
    private SysWorkItemFieldVO sysWorkItemFieldDOTOVO(SysWorkItemFieldDO sysWorkItemFieldDO){
        if(null == sysWorkItemFieldDO){
            return null;
        }
        SysWorkItemFieldVO sysWorkItemFieldVO = new SysWorkItemFieldVO();
        BeanUtils.copyProperties(sysWorkItemFieldDO,sysWorkItemFieldVO);
        return sysWorkItemFieldVO;
    }
}
