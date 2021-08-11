package com.zhujunji.base.convert;

import com.alibaba.fastjson.util.TypeUtils;
import com.zhujunji.base.factory.ConvertFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class DateConvert implements Convert<Date> {

    private final String pattern;

    private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    public DateConvert() {
        this.pattern = DEFAULT_PATTERN;
    }

    /**
     * 想自定义时间格式是修改 ConvertFactory 中的初始化实例即可
     * @see ConvertFactory#createConvert(Class)
     * @param pattern   时间格式
     */
    public DateConvert(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Date cast(Object value) {
        if(null == value){
            return null;
        }
        if(value instanceof Date){
            return (Date) value;
        }
        if(value instanceof String){
            return stringToValue((String)value);
        }
        return TypeUtils.castToJavaBean(value,Date.class);
    }

    @Override
    public String valueToString(Date value) {
        if (value != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            return simpleDateFormat.format(value);
        }
        return "";
    }

    @Override
    public Date stringToValue(String value) {
        if (StringUtils.isNotBlank(value)) {
            try {
                // 先尝试毫秒值
                long timeMillis = Long.parseLong(value);
                return new Date(timeMillis);
            } catch (NumberFormatException numberFormatException) {
                try {
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                    return simpleDateFormat.parse(value);
                } catch (ParseException parseException) {
                    log.error("String Convert failed! source: {} pattern: {} ", value, pattern);
                }
            }
        }
        return null;
    }
}
