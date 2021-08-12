package com.zhujunji.base.convert;

import com.zhujunji.common.enums.BooleanEnum;

public class BooleanConvert implements Convert<Boolean> {

    @Override
    public Boolean cast(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof Boolean){
            return (Boolean) value;
        }
        return stringToValue(String.valueOf(value));
    }

    @Override
    public String valueToString(Boolean value) {
        return value == null ? "" : value.toString();
    }

    @Override
    public Boolean stringToValue(String value) {
        return  BooleanEnum.getByLabel(value).map(BooleanEnum::getValue).orElse(null);
    }
}
