package com.zhujunji.base.convert;

import org.apache.commons.lang3.StringUtils;

public class DoubleConvert implements Convert<Double> {
    @Override
    public Double cast(Object value) {
        if(null == value){
            return null;
        }
        if(value instanceof Double){
            return (Double)value;
        }
        return stringToValue(String.valueOf(value));
    }

    @Override
    public String valueToString(Double value) {
        return value == null ? "" : String.valueOf(value);
    }

    @Override
    public Double stringToValue(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }
        return Double.parseDouble(value);
    }
}
