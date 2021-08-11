package com.zhujunji.base.convert;

public interface Convert<T> {

    /**
     * 类型转换
     * @param value 值
     * @return T
     */
    T cast(Object value);

    /**
     * T to String
     * @param value     值
     * @return String
     */
    String valueToString(T value);

    /**
     * String TO T
     * @param value     字符值
     * @return T
     */
    T stringToValue(String value);
}
