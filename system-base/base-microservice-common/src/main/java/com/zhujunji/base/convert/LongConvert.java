package com.zhujunji.base.convert;

import com.alibaba.fastjson.util.TypeUtils;
import org.apache.commons.lang3.StringUtils;

public class LongConvert implements Convert<Long> {

    @Override
    public Long cast(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof Long){
            return (Long) value;
        }
        if(value instanceof String){
            return stringToValue((String) value);
        }
        return TypeUtils.castToJavaBean(value,Long.class);
    }

    @Override
    public String valueToString(Long value) {
        return value == null ? "" : String.valueOf(value);
    }

    @Override
    public Long stringToValue(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }
        return Long.parseLong(value);
    }
}
