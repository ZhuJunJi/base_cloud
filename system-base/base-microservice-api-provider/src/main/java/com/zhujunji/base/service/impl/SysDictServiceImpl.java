package com.zhujunji.base.service.impl;

import com.zhujunji.base.convert.Convert;
import com.zhujunji.base.domain.SysDictDO;
import com.zhujunji.base.mapper.SysDictMapper;
import com.zhujunji.base.service.SysDictService;
import com.zhujunji.base.service.dto.SysDictCreateDTO;
import com.zhujunji.base.service.dto.SysDictUpdateDTO;
import com.zhujunji.base.service.vo.SysDictVO;
import com.zhujunji.common.entity.Dict;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@DubboService(protocol = "dubbo", version = "1.0.0")
public class SysDictServiceImpl implements SysDictService {

    private static final String DEFAULT_PARENT_IDS = "-0-";

    @Resource
    private SysDictMapper sysDictMapper;

    @Override
    public boolean save(SysDictCreateDTO sysDictCreateDTO) {
        SysDictDO sysDictDO = checkParam(sysDictCreateDTO);

        try {
            return sysDictMapper.insert(sysDictDO) > 0;
        } catch (DataAccessException e) {
            if (e instanceof DuplicateKeyException) {
                throw new CommonBizException(ExpCodeEnum.DICT_EXSIT_EXCEPTION);
            }
            log.error("保存字典信息失败：", e);
            throw new CommonBizException(ExpCodeEnum.DATA_ACCESS_EXCEPTION_CODE);
        }
    }

    @Override
    public boolean update(SysDictUpdateDTO sysDictUpdateDTO) throws CommonBizException {
        SysDictDO sysDictDO = checkParam(sysDictUpdateDTO);
        try {
            return sysDictMapper.update(sysDictDO) > 0;
        } catch (DataAccessException e) {
            if (e instanceof DuplicateKeyException) {
                throw new CommonBizException(ExpCodeEnum.DICT_EXSIT_EXCEPTION);
            }
            log.error("更新字典信息失败：", e);
            throw new CommonBizException(ExpCodeEnum.DATA_ACCESS_EXCEPTION_CODE);
        }
    }

    /**
     * 校验字典更新参数
     * @param sysDictUpdateDTO  字典更新信息
     * @return SysDictDO
     */
    private SysDictDO checkParam(SysDictUpdateDTO sysDictUpdateDTO) {
        if(sysDictUpdateDTO == null){
            throw new CommonBizException(ExpCodeEnum.DICT_NULL_EXCEPTION);
        }
        Long dictId = sysDictUpdateDTO.getDictId();
        if (dictId == null || dictId< 1) {
            throw new CommonBizException(ExpCodeEnum.DICT_ID_EXCEPTION);
        }
        SysDictDO oldSysDictDO = sysDictMapper.getById(dictId);
        if(oldSysDictDO == null){
            log.error("字典更新校验失败！字典信息不存在 dictId: {}",dictId);
            throw new CommonBizException(ExpCodeEnum.DICT_EXSIT_EXCEPTION);
        }
        if(sysDictUpdateDTO.getUpdateBy() == null){
            throw new CommonBizException(ExpCodeEnum.UPDATE_BY_NULL);
        }
        Long newParentId = sysDictUpdateDTO.getParentId();
        Long oldParentId = oldSysDictDO.getParentId();

        String parentIds = null;
        // 字典位子节点发生变更
        if(!oldParentId.equals(newParentId) && newParentId != null){
            if(newParentId == 0){
                // 更新到根节点
                parentIds = DEFAULT_PARENT_IDS;
            }else {
                // 字典节点位子变更
                SysDictVO parent = getById(sysDictUpdateDTO.getParentId());
                // 新的父节点不
                if (parent == null) {
                    throw new CommonBizException(ExpCodeEnum.DICT_PARENT_NOT_EXSIT_EXCEPTION);
                }
                if(!oldSysDictDO.getType().equals(parent.getType())){
                    throw new CommonBizException(ExpCodeEnum.DICT_MOVE_TO_OTHER_TYPE_EXCEPTION);
                }
                parentIds = getParentIdsByParentId(parent);
            }
        }
        SysDictDO newSysDictDO = new SysDictDO();
        BeanUtils.copyProperties(sysDictUpdateDTO,newSysDictDO);
        newSysDictDO.setParentIds(parentIds);
        return newSysDictDO;
    }

    /**
     * 校验字典新增参数
     * @param sysDictCreateDTO  字典新增信息
     * @return SysDictDO
     */
    private SysDictDO checkParam(SysDictCreateDTO sysDictCreateDTO) {
        if (sysDictCreateDTO == null) {
            throw new CommonBizException(ExpCodeEnum.DICT_NULL_EXCEPTION);
        }
        if (StringUtils.isBlank(sysDictCreateDTO.getType())) {
            throw new CommonBizException(ExpCodeEnum.CREATE_BY_NULL);
        }
        if (StringUtils.isBlank(sysDictCreateDTO.getLabelZh())) {
            throw new CommonBizException(ExpCodeEnum.DICT_LABEL_ZH_NULL_EXCEPTION);
        }
        if (StringUtils.isBlank(sysDictCreateDTO.getLabelEn())) {
            throw new CommonBizException(ExpCodeEnum.DICT_LABEL_EN_NULL_EXCEPTION);
        }
        if (StringUtils.isBlank(sysDictCreateDTO.getValue())) {
            throw new CommonBizException(ExpCodeEnum.DICT_VALUE_NULL_EXCEPTION);
        }
        if (sysDictCreateDTO.getCreateBy() == null) {
            throw new CommonBizException(ExpCodeEnum.CREATE_BY_NULL);
        }

        long parentId = sysDictCreateDTO.getParentId() == null ? 0 : sysDictCreateDTO.getParentId();

        String parentIds = getParentIdsByParentId(parentId);

        SysDictDO sysDictDO = new SysDictDO();
        BeanUtils.copyProperties(sysDictCreateDTO,sysDictDO);

        sysDictDO.setParentId(parentId);
        sysDictDO.setParentIds(parentIds);
        sysDictDO.setUpdateBy(sysDictCreateDTO.getCreateBy());
        return sysDictDO;
    }

    /**
     * parentId 获取 parentIds
     * @param parentId  父节点 ID
     * @return String   所有层级父节点
     */
    private String getParentIdsByParentId(long parentId){

        if (parentId == 0) {
            return  DEFAULT_PARENT_IDS;
        }
        SysDictVO parent = getById(parentId);
        if (parent == null) {
            throw new CommonBizException(ExpCodeEnum.DICT_PARENT_NOT_EXSIT_EXCEPTION);
        }
        // 设置 parentIds 方便查询
        return getParentIdsByParentId(parent);
    }

    private String getParentIdsByParentId(SysDictVO parent){
        // 设置 parentIds 方便查询
        return parent.getParentIds() + parent.getParentId() + "-";
    }

    @Override
    public SysDictVO getById(Long dictId) {
        return getById(dictId,LanguageEnum.CHINESE);
    }

    @Override
    public SysDictVO getById(Long dictId, LanguageEnum language) {
        if (dictId == null || dictId < 1) {
            return null;
        }
        SysDictDO sysDictDO = sysDictMapper.getById(dictId);

        return sysDictDOTOVO(sysDictDO, language);
    }

    @Override
    public List<SysDictVO> findByType(String type) {
        return findByType(type, LanguageEnum.CHINESE);
    }

    @Override
    public List<SysDictVO> findByType(String type, LanguageEnum language) {
        if (StringUtils.isBlank(type)) {
            return Collections.emptyList();
        }
        List<SysDictDO> sysDictVOList = sysDictMapper.findByType(type);

        return sysDictVOList.stream()
                .map(sysDictDO -> sysDictDOTOVO(sysDictDO,language))
                .collect(Collectors.toList());
    }
    @Override
    public List<Dict<?>> findByType(String type, Convert<?> valueConvert) {
        return findByType(type, valueConvert, LanguageEnum.CHINESE);
    }
    @Override
    public List<Dict<?>> findByType(String type, Convert<?> valueConvert, LanguageEnum language) {
        List<SysDictDO> sysDictDOList = sysDictMapper.findByType(type);
        return sysDictDOList.stream()
                .map(sysDictDO -> doToDict(sysDictDO, valueConvert, language))
                .collect(Collectors.toList());
    }

    private SysDictVO sysDictDOTOVO(SysDictDO sysDictDO, LanguageEnum language) {
        SysDictVO sysDictVO = new SysDictVO();
        BeanUtils.copyProperties(sysDictDO, sysDictVO);
        sysDictVO.setLabel(sysDictDO.getNameByLanguage(language));
        return sysDictVO;
    }

    @SuppressWarnings("all")
    private Dict<?> doToDict(SysDictDO sysDictDO, Convert<?> valueConvert, LanguageEnum language) {
        Dict dict = new Dict<>();
        BeanUtils.copyProperties(sysDictDO, dict, "value");
        dict.setLabel(sysDictDO.getNameByLanguage(language));
        dict.setValue(valueConvert.stringToValue(sysDictDO.getValue()));
        return dict;
    }
}
