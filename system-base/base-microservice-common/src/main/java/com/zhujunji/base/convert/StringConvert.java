package com.zhujunji.base.convert;

import com.alibaba.fastjson.util.TypeUtils;

public class StringConvert implements Convert<String> {

    @Override
    public String cast(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof String){
            return (String)value;
        }
        return TypeUtils.castToJavaBean(value,String.class);
    }

    @Override
    public String valueToString(String value) {
        return value;
    }

    @Override
    public String stringToValue(String value) {
        return value;
    }
}
