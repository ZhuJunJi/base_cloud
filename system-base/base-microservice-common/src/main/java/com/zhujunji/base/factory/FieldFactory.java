package com.zhujunji.base.factory;

import com.zhujunji.common.entity.*;
import com.zhujunji.common.enums.FieldTypeEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class FieldFactory {

    private static final Map<FieldTypeEnum,Class<? extends Field<?>>> TYPE_FIELD_CLASS_MAP = new HashMap<>();

    static {
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.STRING, StringField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.BOOLEAN, BooleanField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.DATE_TIME, DateTimeField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.DOUBLE, DoubleField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.INTEGER, IntegerField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.LONG, LongField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.PICKLIST_DOUBLE, PicklistDoubleField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.PICKLIST_INTEGER, PicklistIntegerField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.PICKLIST_STRING, StringField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.HTML, StringField.class);
        TYPE_FIELD_CLASS_MAP.put(FieldTypeEnum.TREE, StringField.class);
    }

    public static Field<?> createField(FieldTypeEnum fieldType){
        if(!TYPE_FIELD_CLASS_MAP.containsKey(fieldType)){
            log.error("fieldType: {} 类型字段不存在！",fieldType);
            throw new CommonBizException(ExpCodeEnum.FIELD_CREATE_EXCEPTION);
        }
        try {
            Class<? extends Field<?>> fieldClass = TYPE_FIELD_CLASS_MAP.get(fieldType);
           return fieldClass.getDeclaredConstructor().newInstance();
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            log.error("字段类型创建字段实例失败！",e);
            throw new CommonBizException(ExpCodeEnum.FIELD_CREATE_EXCEPTION);
        }
    }
}
