package com.zhujunji.common.enums;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

public enum FieldTypeEnum implements Serializable {

    /**
     * 字符串
     */
    STRING("STRING",0, String.class),
    /**
     * 布尔
     */
    BOOLEAN("BOOLEAN",1, Boolean.class),
    /**
     * 时间
     */
    DATE_TIME("DATE_TIME",2, Date.class),
    /**
     * 双进度
     */
    DOUBLE("DOUBLE",3, Double.class),
    /**
     * 整型
     */
    INTEGER("INTEGER",4, Integer.class),
    /**
     * 长整型
     */
    LONG("LONG",5, Long.class),
    /**
     * 双精度选项框
     */
    PICKLIST_DOUBLE("PICKLIST_DOUBLE",6, Double.class),
    /**
     * 整型选项框
     */
    PICKLIST_INTEGER("PICKLIST_INTEGER",7, Integer.class),
    /**
     * 文本选项框
     */
    PICKLIST_STRING("PICKLIST_STRING",8, String.class),
    /**
     * HTML
     */
    HTML("HTML",8, String.class),
    /**
     * 级联
     */
    TREE("TREE",9, String.class),
    /**
     * 文档 ID
     */
    OBJECT_ID("OBJECT_ID",10, ObjectId.class),

    END("END",-1, Object.class);
    /**
     * Code
     */
    private final Integer code;
    /**
     * 描述
     */
    private final String description;
    /**
     * 值类型
     */
    private final Class<?> valueClass;

    FieldTypeEnum(String description, Integer code, Class<?> valueClass){
        this.description = description;
        this.code = code;
        this.valueClass = valueClass;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public Class<?> getValueClass() {
        return valueClass;
    }

    /**
     * Code 获取枚举
     * @param code 编号
     * @return FieldTypeEnum
     */
    public static FieldTypeEnum getByCode(Integer code){
        if(code == null || code < 0){
            return null;
        }
        return Arrays.stream(values())
                .filter(fieldTypeEnum -> fieldTypeEnum.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    /**
     * 通过 description 获取枚举
     * @param description   描述
     * @return FieldTypeEnum
     */
    public static FieldTypeEnum getByDescription(String description){
        if(StringUtils.isBlank(description)){
            return null;
        }
        return Arrays.stream(values())
                .filter(fieldTypeEnum -> fieldTypeEnum.description.equals(description))
                .findFirst()
                .orElse(null);
    }

    /**
     * 是否是 PickList 类型
     * @return FieldTypeEnum
     */
    public boolean isPickList(){
        return PICKLIST_INTEGER.equals(this) || PICKLIST_DOUBLE.equals(this)
                || PICKLIST_STRING.equals(this);
    }
}
