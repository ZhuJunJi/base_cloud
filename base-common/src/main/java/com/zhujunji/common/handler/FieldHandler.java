package com.zhujunji.common.handler;


import com.zhujunji.common.entity.Field;

public interface FieldHandler {

    /**
     * 处理字段
     * @param field     字段信息
     * @param value     字段值
     */
    Object handlerCreate(Field<?> field, Object value);

    /**
     * 处理字段
     * @param field     字段信息
     * @param value     字段值
     */
    Object handlerUpdate(Field<?> field, Object value);
}
