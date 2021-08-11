package com.zhujunji.common.domain;

import com.zhujunji.common.enums.LanguageEnum;
import lombok.Data;

@Data
public class BaseLanguageObject extends BaseObject{
    /**
     * 中文名称
     */
    private String nameZh;
    /**
     * 英文名称
     */
    private String nameEn;

    public String getNameByLanguage(LanguageEnum language) {
        return LanguageEnum.ENGLISH.equals(language) ? nameEn : nameZh;
    }
}
