package com.zhujunji.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

public enum LanguageEnum implements Serializable {

    CHINESE(0,"zh-CN"),
    ENGLISH(1,"en-US");

    /**
     * 编号
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String description;

    LanguageEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Optional<LanguageEnum> getByCode(Integer code){
        return Arrays.stream(LanguageEnum.values())
                .filter(languageEnum -> languageEnum.getCode().equals(code))
                .findFirst();
    }
    public static Optional<LanguageEnum> getByDescription(String description){
        return Optional.ofNullable(getByDescription(description,null));
    }

    public static LanguageEnum getByDescription(String description, LanguageEnum defaultLanguage){
        return Arrays.stream(LanguageEnum.values())
                .filter(languageEnum -> languageEnum.getDescription().equals(description))
                .findFirst()
                .orElse(defaultLanguage);
    }
}
