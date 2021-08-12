package com.zhujunji.common.enums;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Optional;

public enum BooleanEnum implements Serializable {

    FALSE(0, false, "false", "否", "no"),
    TRUE(1, true, "true", "是", "yes");

    /**
     * 编号
     */
    private final Integer code;
    /**
     * 对应 boolean 类型值
     */
    private final Boolean value;
    /**
     * 描述
     */
    private final String labelNormal;
    /**
     * label 中文
     */
    private final String labelZh;
    /**
     * label 英文
     */
    private final String labelEn;

    BooleanEnum(Integer code, Boolean value, String labelNormal, String labelZh, String labelEn) {
        this.code = code;
        this.value = value;
        this.labelNormal = labelNormal;
        this.labelZh = labelZh;
        this.labelEn = labelEn;
    }

    public Integer getCode() {
        return code;
    }

    public Boolean getValue() {
        return value;
    }

    public String getLabelNormal() {
        return labelNormal;
    }

    public String getLabelZh() {
        return labelZh;
    }

    public String getLabelEn() {
        return labelEn;
    }

    public static Optional<BooleanEnum> getByCode(Integer code) {
        return Arrays.stream(BooleanEnum.values())
                .filter(languageEnum -> languageEnum.getCode().equals(code))
                .findFirst();
    }

    public static Optional<BooleanEnum> getByLabel(String label) {
        // 转换为小写
        String lowerCaseLabel = label == null ? null : label.toLowerCase();

        return Arrays.stream(BooleanEnum.values())
                .filter(booleanEnum -> booleanEnum.getLabelNormal().equals(lowerCaseLabel)
                        || booleanEnum.getLabelZh().equals(lowerCaseLabel)
                        || booleanEnum.getLabelEn().equals(lowerCaseLabel))
                .findFirst();
    }
}
