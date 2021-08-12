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
        Object defaultValue = field.getDefaultValue();
        // if value is null, value is defaultValue 必填字段为空时赋值为默认值
        value = null != value ? value : defaultValue;
        String fieldName = field.getName();
        if (field.getRequired() && value == null) {
            // 只读字段返回 null, 更新时 null 字段不更新
            log.error("fieldName: {}, is required!", fieldName);
            throw new CommonBizException(ExpCodeEnum.PARAM_NULL, "fieldName: " + fieldName + ", is required!");
        }
        // value 类型转换
        Object castValue = castValue(fieldType,value);
        // 校验 value 是否在选项范围内
        checkValueInPicklistFieldOptions(field,castValue);
        return castValue;
    }

    @Override
    public Object handlerUpdate(Field<?> field, Object value) {
        FieldTypeEnum fieldType = field.getFieldType();
        String fileName = field.getName();
        if (field.getReadonly()) {
            if(value != null){
                //
                log.warn("fieldName: {} is readonly, input value is ignore!",fileName);
            }
            // 只读字段返回 null, 更新时 null 字段不更新
            return null;
        }
        // value 类型转换
        Object castValue = castValue(fieldType,value);
        // 校验 value 是否在选项范围内
        checkValueInPicklistFieldOptions(field,castValue);
        return castValue;
    }

    /**
     * value 转换为相应类型值
     * @param fieldType 字段类型
     * @param value     值
     * @return Object
     */
    public Object castValue(FieldTypeEnum fieldType, Object value){
        Convert<?> convert = ConvertFactory.createConvert(fieldType.getValueClass());
        return convert.cast(value);
    }

    /**
     * 校验 value 是否在选项中
     * @param field 字段
     * @param value 值
     */
    public void checkValueInPicklistFieldOptions(Field<?> field, Object value){
        String fieldName = field.getName();
        // 选项类型字段校验传值是否在选项中存在
        if (field instanceof PicklistField && value != null) {
            PicklistField<?> picklistField = (PicklistField<?>) field;
            // 选中的选项
            Optional<? extends Dict<?>> selected = picklistField.getOptions()
                    .stream()
                    .filter(dict -> dict.getValue().equals(value))
                    .findFirst();
            selected.orElseThrow(() -> new CommonBizException(ExpCodeEnum.PICKLIST_FIELD_OPTION_NOT_EXSIT_EXCEPTION,
                    "fieldName: " + fieldName + ", option: " + value + ",not exits!"));
        }
    }
}
