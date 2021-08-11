package com.zhujunji.base.service;

import com.zhujunji.base.service.dto.SysWorkItemFieldCreateDTO;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;

import java.util.List;

public interface SysWorkItemFieldService {

    /**
     * 新增工作项字段信息
     * @param sysWorkItemFieldCreateDTO 工作项字段信息
     */
    void createWorkitemField(SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO) throws CommonBizException;

    /**
     * 获取工作项字段信息列表
     * @param workItemId    工作项 ID
     * @return  List<Field<?>>
     */
    List<Field<?>> findWorkItemFieldList(Long workItemId);


    /**
     * 获取工作项字段信息列表
     * @param workItemId    工作项 ID
     * @param language      语言版本
     * @return  List<Field<?>>
     */
    List<Field<?>> findWorkItemFieldList(Long workItemId, LanguageEnum language);
}
