package com.zhujunji.base.mapper;

import com.zhujunji.base.domain.SysWorkItemFieldDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysWorkItemFieldMapper {

    /**
     * 新增工作项字段信息
     * @param sysWorkItemFieldDO    工作项字段信息
     * @return int
     */
    int insert(SysWorkItemFieldDO sysWorkItemFieldDO);

    /**
     * 查询工作项字段列表
     * @param workItemId    工作项 ID
     * @return List<SysWorkItemFieldDO>
     */
    List<SysWorkItemFieldDO> findWorkItemFieldList(Long workItemId);
}
