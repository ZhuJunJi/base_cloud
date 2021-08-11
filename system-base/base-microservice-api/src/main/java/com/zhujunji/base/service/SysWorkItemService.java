package com.zhujunji.base.service;

import com.zhujunji.base.service.dto.SysWorkItemCreateDTO;
import com.zhujunji.base.service.vo.SysWorkItemVO;
import com.zhujunji.common.enums.LanguageEnum;

/**
 * 工作项 Service
 * @Author J.zhu
 *
 */
public interface SysWorkItemService {

    /**
     * 新增工作项
     * @param sysWorkItemCreateDTO 工作项信息
     * @return int
     */
    boolean createWorkitem(SysWorkItemCreateDTO sysWorkItemCreateDTO);

    /**
     * ID 获取工作项信息
     * @param workItemId    工作项 ID
     * @return  SysWorkItemVO
     */
    SysWorkItemVO getById(Long workItemId);

    /**
     * ID 获取工作项信息
     * @param workItemId    工作项 ID
     * @param language      语言版本
     * @return  SysWorkItemVO
     */
    SysWorkItemVO getById(Long workItemId, LanguageEnum language);

}
