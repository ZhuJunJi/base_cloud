package com.zhujunji.base.convert;

import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.lang3.StringUtils;

public class BooleanConvert implements Convert<Boolean> {

    @Override
    public Boolean cast(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof Boolean){
            return (Boolean) value;
        }
        if(value instanceof String){
            return stringToValue((String)value);
        }
        return TypeUtils.castToJavaBean(value,Boolean.class);
    }

    @Override
    public String valueToString(Boolean value) {
        return value == null ? "" : value.toString();
    }

    @Override
    public Boolean stringToValue(String value) {
        return StringUtils.isBlank(value) ? null : Boolean.parseBoolean(value);
    }
}
