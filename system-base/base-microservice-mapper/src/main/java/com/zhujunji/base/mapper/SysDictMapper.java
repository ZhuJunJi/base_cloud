package com.zhujunji.base.mapper;

import com.zhujunji.base.domain.SysDictDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 字典 Mapper
 */
@Repository
public interface SysDictMapper {

    /**
     * 新增字典
     * @param sysDictDO 字典信息
     * @return int
     */
    int insert(SysDictDO sysDictDO);

    /**
     * 字典更新
     * @param sysDictDO 字典信息
     * @return int
     */
    int update(SysDictDO sysDictDO);

    /**
     * 主键获取字典信息
     * @param dictId    主键 ID
     * @return SysDict
     */
    SysDictDO getById(@Param("dictId") Long dictId);

    /**
     * 类型查询字典列表
     * @param type  字典类型
     * @return List<SysDictVO>
     */
    List<SysDictDO> findByType(@Param("type") String type);
}
