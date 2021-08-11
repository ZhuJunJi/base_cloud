package com.zhujunji.base.domain;

import com.zhujunji.common.domain.BaseLanguageObject;
import com.zhujunji.common.domain.BaseObject;
import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;

/**
 * 字典实体类
 */
@Data
public class SysDictDO extends BaseLanguageObject {
    /**
     * 自增主键
     **/
    private Long dictId;
    /**
     * 父 ID
     **/
    private Long parentId;
    /**
     * 所有父 IDS
     **/
    private String parentIds;
    /**
     * 类型
     **/
    private String type;
    /**
     * 中文标签
     **/
    private String labelZh;
    /**
     * 英文标签
     **/
    private String labelEn;
    /**
     * 值
     **/
    private String value;
    /**
     * 排序号
     */
    private Integer sort;

    @Override
    public String getNameByLanguage(LanguageEnum language) {
        return LanguageEnum.ENGLISH.equals(language) ? labelEn : labelZh;
    }
}
