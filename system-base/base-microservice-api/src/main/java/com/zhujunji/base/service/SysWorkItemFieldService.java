package com.zhujunji.base.service;

import com.zhujunji.base.service.dto.SysWorkItemFieldCreateDTO;
import com.zhujunji.base.service.dto.SysWorkItemFieldUpdateDTO;
import com.zhujunji.base.service.vo.SysWorkItemFieldVO;
import com.zhujunji.base.service.vo.SysWorkItemVO;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.enums.LanguageEnum;
import com.zhujunji.common.exception.CommonBizException;

import java.util.List;

public interface SysWorkItemFieldService {

    /**
     * 新增工作项字段信息
     * @param sysWorkItemFieldCreateDTO 工作项字段信息
     * @throws CommonBizException   自定义业务异常
     */
    void create(SysWorkItemFieldCreateDTO sysWorkItemFieldCreateDTO) throws CommonBizException;

    /**
     * 工作项字段更新
     * @param sysWorkItemFieldUpdateDTO 工作项字段更新信息
     * @throws CommonBizException   自定义业务异常
     */
    boolean update(SysWorkItemFieldUpdateDTO sysWorkItemFieldUpdateDTO) throws CommonBizException;

    /**
     * 获取工作项字段信息展示列表 （用户可编辑列表展示）
     * @param workItemId    工作项 ID
     * @return SysWorkItemFieldVO
     */
    List<SysWorkItemFieldVO> findWorkItemFieldVOList(Long workItemId);

    /**
     * 获取工作项字段信息
     * @param workItemId    工作项 ID
     * @return  List<Field<?>>
     */
    List<Field<?>> findWorkItemFieldList(Long workItemId);


    /**
     * 获取工作项字段信息
     * @param workItemId    工作项 ID
     * @param language      语言版本
     * @return  List<Field<?>>
     */
    List<Field<?>> findWorkItemFieldList(Long workItemId, LanguageEnum language);
}
