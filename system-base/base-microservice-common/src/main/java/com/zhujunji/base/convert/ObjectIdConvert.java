package com.zhujunji.base.convert;

import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

@Slf4j
public class ObjectIdConvert implements Convert<ObjectId> {

    @Override
    public ObjectId cast(Object value) {
        if(value == null){
            return null;
        }
        if(value instanceof ObjectId){
            return (ObjectId)value;
        }
        return stringToValue(String.valueOf(value));
    }

    @Override
    public String valueToString(ObjectId value) {
        return value == null ? null : value.toString();
    }

    @Override
    public ObjectId stringToValue(String value) {
        if(StringUtils.isBlank(value)){
            return null;
        }
        try {
            return new ObjectId(value);
        }catch (IllegalArgumentException e){
            log.error("value: {} cast to ObjectId exception!",value);
            throw new CommonBizException(ExpCodeEnum.STRING_TO_OBJECT_ID_EXCEPTION);
        }
    }
}
