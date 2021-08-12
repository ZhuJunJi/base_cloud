package com.zhujunji.base.factory;

import com.zhujunji.base.convert.*;
import com.zhujunji.common.exception.CommonBizException;
import com.zhujunji.common.exception.ExpCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class ConvertFactory {

    private static final Map<Class<?>, Convert<?>> CONVERT_MAP = new HashMap<>();

    static {
        CONVERT_MAP.put(String.class,new StringConvert());
        CONVERT_MAP.put(Integer.class,new IntegerConvert());
        CONVERT_MAP.put(Double.class,new DoubleConvert());
        CONVERT_MAP.put(Long.class,new LongConvert());
        CONVERT_MAP.put(Date.class,new DateConvert());
        CONVERT_MAP.put(Boolean.class,new BooleanConvert());
        CONVERT_MAP.put(ObjectId.class,new ObjectIdConvert());
    }

    public static Convert<?> createConvert(Class<?> clazz){
        if(!CONVERT_MAP.containsKey(clazz)){
            log.error("Class: {}, 对应的转换器不存在！",clazz);
            throw new CommonBizException(ExpCodeEnum.CONVERT_NOT_EXSIT_EXCEPTION);
        }
        return CONVERT_MAP.get(clazz);
    }
}
