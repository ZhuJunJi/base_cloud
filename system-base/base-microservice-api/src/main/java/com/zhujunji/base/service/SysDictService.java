package com.zhujunji.base.service;

import com.zhujunji.base.convert.Convert;
import com.zhujunji.base.service.dto.SysDictCreateDTO;
import com.zhujunji.base.service.dto.SysDictUpdateDTO;
import com.zhujunji.base.service.vo.SysDictVO;
import com.zhujunji.common.entity.Dict;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;

import java.util.List;

public interface SysDictService {

    /**
     * 保存字典信息
     * @param sysDictCreateDTO   字典信息
     * @return boolean
     * @throws CommonBizException 通用业务异常
     */
    boolean save(SysDictCreateDTO sysDictCreateDTO) throws CommonBizException;

    /**
     * 修改字典信息
     * @param sysDictUpdateDTO  修改信息
     * @return boolean
     * @throws CommonBizException 通用业务异常
     */
    boolean update(SysDictUpdateDTO sysDictUpdateDTO) throws CommonBizException;

    /**
     * 主键获取字典信息
     * @param dictId    主键 ID
     * @return Optional<SysDict>
     */
    SysDictVO getById(Long dictId);

    /**
     * 主键获取字典信息
     * @param dictId    主键 ID
     * @param language  语言版本
     * @return Optional<SysDict>
     */
    SysDictVO getById(Long dictId, LanguageEnum language);

    /**
     * 指定类型获取字典列表
     * @param type  字典类型
     * @return List<SysDict>
     */
    List<SysDictVO> findByType(String type);

    /**
     * 指定类型获取字典列表
     * @param type      字典类型
     * @param language  语言版本
     * @return List<SysDict>
     */
    List<SysDictVO> findByType(String type, LanguageEnum language);

    /**
     * 通过字典类型获取字典列表并通过 fieldType 转换为相应的 dict
     * @param type          字典类型
     * @param valueConvert  字段类型
     * @return List<Dict<?>>
     */
    List<Dict<?>> findByType(String type, Convert<?> valueConvert);

    /**
     * 通过字典类型获取字典列表并通过 fieldType 转换为相应的 dict
     * @param type          字典类型
     * @param valueConvert  字段类型
     * @param language      语言版本
     * @return List<Dict<?>>
     */
    List<Dict<?>> findByType(String type, Convert<?> valueConvert, LanguageEnum language);


}
