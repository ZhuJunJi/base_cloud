package com.zhujunji.base.convert;

public class StringConvert implements Convert<String> {

    @Override
    public String cast(Object value) {
        if(value == null){
            return null;
        }
        return stringToValue(String.valueOf(value));
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
