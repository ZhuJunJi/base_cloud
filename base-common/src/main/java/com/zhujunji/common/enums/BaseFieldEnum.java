package com.zhujunji.common.enums;

import java.io.Serializable;

public enum BaseFieldEnum implements Serializable {
    CREATE_BY("create_by","创建者","Create By"),
    CREATE_TIME("create_time","创建时间","Create By"),
    UPDATE_BY("update_by","更新者","Update By"),
    UPDATE_TIME("update_time","更新时间","Update Time"),
    IS_DELETE("is_delete","是否删除","Is Delete");

    BaseFieldEnum(String fieldKey, String nameZh, String nameEn){
        this.fieldKey = fieldKey;
        this.nameZh = nameZh;
        this.nameEn = nameEn;
    }

    /**
     * 字段 Key
     */
    private String fieldKey;
    /**
     * 字段名称（中文）
     */
    private String nameZh;
    /**
     * 字段名称（英文）
     */
    private String nameEn;

    public String getFieldKey() {
        return fieldKey;
    }

    public String getNameZh() {
        return nameZh;
    }

    public String getNameEn() {
        return nameEn;
    }
}
