package com.zhujunji.base.service;

import com.zhujunji.base.service.dto.SysFieldCreateDTO;
import com.zhujunji.base.service.dto.SysFieldUpdateDTO;
import com.zhujunji.base.service.vo.SysFieldVO;
import com.zhujunji.common.exception.CommonBizException;

import java.util.List;

/**
 * 字段 Service
 *
 */
public interface SysFiledService {

    /**
     * 创建字段信息
     * @param sysFieldCreateDTO 字段创建信息
     * @return boolean
     */
    boolean create(SysFieldCreateDTO sysFieldCreateDTO) throws CommonBizException;

    /**
     * 修改字段信息
     * @param sysFieldUpdateDTO 字段更新信息
     * @return boolean
     */
    boolean update(SysFieldUpdateDTO sysFieldUpdateDTO) throws CommonBizException;

    /**
     * ID 主键获取字段信息
     * @param fieldId   主键 ID
     * @return SysFieldVO
     */
    SysFieldVO getById(Long fieldId);

    /**
     * 主键批量查询字段信息
     * @param fieldIdList   字段主键列表
     * @return List<SysFieldVO>
     */
    List<SysFieldVO> findByIdList(List<Long> fieldIdList);
}