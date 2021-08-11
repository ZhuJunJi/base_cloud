package com.zhujunji.base.service.handler;

import com.zhujunji.base.convert.Convert;
import com.zhujunji.base.factory.ConvertFactory;
import com.zhujunji.common.constant.GlobalConstants;
import com.zhujunji.common.entity.Dict;
import com.zhujunji.common.entity.Field;
import com.zhujunji.common.entity.PicklistField;
import com.zhujunji.common.enums.FieldTypeEnum;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import com.zhujunji.common.handler.FieldHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component(GlobalConstants.DEFAULT_FIELD_HANDLER)
public class DefaultFieldHandler implements FieldHandler {

    @Override
    public Object handlerCreate(Field<?> field, Object value) {
        FieldTypeEnum fieldType = field.getFieldType();
        String fieldName = field.getName();
        if (field.getRequired() && value == null) {
            // 只读字段返回 null, 更新时 null 字段不更新
            log.error("fieldName: {}, is required!", fieldName);
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL, "fieldName: " + fieldName + ", is required!");
        }
        // value 类型转换
        Class<?> valueClass = fieldType.getValueClass();
        Convert<?> convert = ConvertFactory.createConvert(valueClass);
        Object castValue = convert.cast(value);

        // 选项类型字段校验传值是否在选项中存在
        if (field instanceof PicklistField && castValue != null) {
            PicklistField<?> picklistField = (PicklistField<?>) field;
            // 选中的选项
            Optional<? extends Dict<?>> selected = picklistField.getOptions()
                    .stream()
                    .filter(dict -> dict.getValue().equals(castValue))
                    .findFirst();
            selected.orElseThrow(() -> new CommonBizException(ExpCodeEnum.PICKLIST_FIELD_OPTION_NOT_EXSIT_EXCEPTION,
                    "fieldName: " + fieldName + ", option: " + castValue + ",not exits!"));
        }

        return castValue;
    }

    @Override
    public Object handlerUpdate(Field<?> field, Object value) {
        FieldTypeEnum fieldType = field.getFieldType();
        if (field.getReadonly()) {
            // 只读字段返回 null, 更新时 null 字段不更新
            return null;
        }
        // value 类型转换
        Class<?> valueClass = fieldType.getValueClass();
        Convert<?> convert = ConvertFactory.createConvert(valueClass);
        return convert.cast(value);
    }
}
