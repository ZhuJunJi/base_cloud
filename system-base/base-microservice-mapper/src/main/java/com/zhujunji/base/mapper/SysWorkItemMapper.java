package com.zhujunji.base.mapper;

import com.zhujunji.base.domain.SysWorkItemDO;
import org.springframework.stereotype.Repository;

@Repository
public interface SysWorkItemMapper {

    /**
     * 新增工作项信息
     * @param workItemDO    工作项信息
     * @return int
     */
    int insert(SysWorkItemDO workItemDO);

    /**
     * 主键查询工作项信息
     * @param workItemId    工作项 ID
     * @return SysWorkItemDO
     */
    SysWorkItemDO getById(Long workItemId);
}
