package com.zhujunji.base.service.impl;

import com.zhujunji.base.domain.SysFieldDO;
import com.zhujunji.base.mapper.SysFieldMapper;
import com.zhujunji.base.service.SysFiledService;
import com.zhujunji.base.service.convert.FieldConvert;
import com.zhujunji.base.service.dto.SysFieldCreateDTO;
import com.zhujunji.base.service.dto.SysFieldUpdateDTO;
import com.zhujunji.base.service.vo.SysFieldVO;
import com.zhujunji.common.enums.FieldTypeEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DubboService(protocol = "dubbo", version = "1.0.0")
public class SysFieldServiceImpl implements SysFiledService {

    @Resource
    private SysFieldMapper sysFieldMapper;

    @Resource
    private FieldConvert fieldConvert;

    @Override
    public boolean create(SysFieldCreateDTO sysFieldCreateDTO) {
        SysFieldDO sysFieldDO = checkParam(sysFieldCreateDTO);
        try {
            return sysFieldMapper.insert(sysFieldDO) > 0;
        } catch (DataAccessException e) {
            if (e instanceof DuplicateKeyException) {
                throw new CommonBizException(ExpCodeEnum.FIELD_EXSIT_EXCEPTION);
            }
            log.error("保存字段信息失败：", e);
            throw new CommonBizException(ExpCodeEnum.DATA_ACCESS_EXCEPTION_CODE);
        }
    }

    @Override
    public boolean update(SysFieldUpdateDTO sysFieldUpdateDTO) {
        SysFieldDO sysFieldDO = checkParam(sysFieldUpdateDTO);
        return sysFieldMapper.update(sysFieldDO) > 0;
    }

    @Override
    public SysFieldVO getById(Long fieldId) {

        if (fieldId == null || fieldId < 1) {
            return null;
        }

        SysFieldDO sysFieldDO = sysFieldMapper.getById(fieldId);

        return fieldConvert.sysFieldDOToFieldVO(sysFieldDO);
    }

    @Override
    public List<SysFieldVO> findByIdList(List<Long> fieldIdList) {
        if(CollectionUtils.isEmpty(fieldIdList)){
            return Collections.emptyList();
        }
        List<SysFieldDO> sysFieldDOList = sysFieldMapper.findByIdList(fieldIdList);

        return sysFieldDOList.stream()
                .map(sysFieldDO -> fieldConvert.sysFieldDOToFieldVO(sysFieldDO))
                .collect(Collectors.toList());
    }

    /**
     * 校验字段创建信息
     * @param sysFieldCreateDTO 字段创建信息
     * @return SysFieldDO
     */
    private SysFieldDO checkParam(SysFieldCreateDTO sysFieldCreateDTO) {
        if(sysFieldCreateDTO == null){
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        if(sysFieldCreateDTO.getCreateBy() == null){
            throw new CommonBizException(ExpCodeEnum.CREATE_BY_NULL);
        }
        if(StringUtils.isBlank(sysFieldCreateDTO.getFieldKey())){
            throw new CommonBizException(ExpCodeEnum.FIELD_KEY_NULL_EXCEPTION);
        }
        if(StringUtils.isBlank(sysFieldCreateDTO.getNameZh())){
            throw new CommonBizException(ExpCodeEnum.FIELD_NAME_ZH_NULL_EXCEPTION);
        }
        if(StringUtils.isBlank(sysFieldCreateDTO.getNameEn())){
            throw new CommonBizException(ExpCodeEnum.FIELD_NAME_EN_NULL_EXCEPTION);
        }
        if(StringUtils.isBlank(sysFieldCreateDTO.getFieldDes())){
            throw new CommonBizException(ExpCodeEnum.FIELD_DESC_NULL_EXCEPTION);
        }
        FieldTypeEnum fieldType = sysFieldCreateDTO.getFieldType();
        if(fieldType == null){
            throw new CommonBizException(ExpCodeEnum.FIELD_TYPE_NULL_EXCEPTION);
        }
        boolean isPickList = FieldTypeEnum.isPickList(fieldType);
        if(isPickList && StringUtils.isBlank(sysFieldCreateDTO.getDictType())){
            throw new CommonBizException(ExpCodeEnum.FIELD_DICT_TYP_NULL_EXCEPTION);
        }
        sysFieldCreateDTO.setDictType(isPickList ? sysFieldCreateDTO.getDictType() : null);
        SysFieldDO sysFieldDO = new SysFieldDO();
        BeanUtils.copyProperties(sysFieldCreateDTO,sysFieldDO);
        sysFieldDO.setUpdateBy(sysFieldCreateDTO.getCreateBy());
        return sysFieldDO;

    }

    /**
     * 校验字段更新信息
     * @param sysFieldUpdateDTO 字段更新信息
     * @return SysFieldDO
     */
    private SysFieldDO checkParam(SysFieldUpdateDTO sysFieldUpdateDTO) {
        if(sysFieldUpdateDTO == null){
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL);
        }
        if(sysFieldUpdateDTO.getUpdateBy() == null){
            throw new CommonBizException(ExpCodeEnum.UPDATE_BY_NULL);
        }
        FieldTypeEnum fieldType = sysFieldUpdateDTO.getFieldType();
        if(fieldType != null){
            // 更新了字段类型类型
            boolean isPicklist = FieldTypeEnum.isPickList(fieldType);
            if(isPicklist && StringUtils.isBlank(sysFieldUpdateDTO.getDictType())){
                throw new CommonBizException(ExpCodeEnum.FIELD_DICT_TYP_NULL_EXCEPTION);
            }
            // 更新为非 picklist 类型字段时，dictType 需要置空
            sysFieldUpdateDTO.setDictType(isPicklist ? sysFieldUpdateDTO.getDictType() : "");
        }else {
            // 没有更新字段类型时，dictType 也不能更新
            sysFieldUpdateDTO.setDictType(null);
        }

        SysFieldDO sysFieldDO = new SysFieldDO();
        BeanUtils.copyProperties(sysFieldUpdateDTO,sysFieldDO);
        return sysFieldDO;
    }
}
