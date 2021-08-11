package com.zhujunji.base.mapper;

import com.zhujunji.base.domain.SysFieldDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字段 Mapper
 */
@Repository
public interface SysFieldMapper {

    /**
     * 新增字段信息
     * @param sysFieldDO    字段信息
     * @return int
     */
    int insert(SysFieldDO sysFieldDO);

    /**
     * 更新字段信息
     * @param sysFieldDO 字段信息
     * @return int
     */
    int update(SysFieldDO sysFieldDO);

    /**
     * 主键查询字段信息
     * @param fieldId       字段 ID
     * @return SysFieldDO
     */
    SysFieldDO getById(Long fieldId);

    /**
     * 主键批量查询
     * @param fieldIdList   字段主键列表
     * @return List<SysFieldDO>
     */
    List<SysFieldDO> findByIdList(@Param("fieldIdList") List<Long> fieldIdList);
}
