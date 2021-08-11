package com.zhujunji.base.convert;

import com.alibaba.fastjson.util.TypeUtils;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import org.apache.commons.lang3.StringUtils;

public class IntegerConvert implements Convert<Integer> {

    @Override
    public Integer cast(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof Integer){
            return (Integer) value;
        }
        if(value instanceof String){
            return stringToValue((String) value);
        }
        return TypeUtils.castToJavaBean(value,Integer.class);
    }

    @Override
    public String valueToString(Integer value) {
        return value == null ? "" : String.valueOf(value);
    }

    @Override
    public Integer stringToValue(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }
        try {
            return Integer.parseInt(value);
        }catch (NumberFormatException e){
            throw new CommonBizException(ExpCodeEnum.NUMBER_FORMAT_EXCEPTION);
        }
    }
}
