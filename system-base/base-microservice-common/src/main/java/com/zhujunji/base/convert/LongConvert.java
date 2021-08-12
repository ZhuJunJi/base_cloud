package com.zhujunji.base.convert;

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
        return stringToValue(String.valueOf(value));
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
